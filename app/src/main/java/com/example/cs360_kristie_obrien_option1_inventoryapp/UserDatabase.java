package com.example.cs360_kristie_obrien_option1_inventoryapp;

import android.content.ContentValues;

import android.content.Context;

import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

import java.util.regex.Pattern;

import java.security.NoSuchAlgorithmException;

import java.security.MessageDigest;


// This creates the UserDatabase class that is used to create the UserDatabase as an SQLite database
// The creation of the database includes declaring the Database's name, defining the version of the database, and the context for the database.
public class UserDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "InventoryDatabase.db";
    private static final int VERSION = 6;
    public UserDatabase (Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // The following code declares the UserTable which will store the user information.
    // The creation of the UserTable includes the table name, the column id for ids, the column username for usernames,
    // and the column password for passwords.
    private static final class UserTable{
        private static final String TABLE = "users";
        private static final String COL_ID = "_id";
        private static final String COL_USERNAME = "username";
        private static final String COL_PASSWORD = "password";
    }

    // The following code declares the InventoryTable that will relate a table of inventory items specific to a specific user.
    // The creation of the InventoryTable includes the table name, the column id for ids, the column user_id for user_ids,
    // the column itemName for itemName, and the column itemAmount for itemAmount.
    private static final class InventoryTable{
        private static final String TABLE = "inventory";
        private static final String COL_ID = "_id";
        private static final String COL_USER_ID = "user_id";
        private static final String COL_ITEM_NAME = "itemName";
        private static final String COL_ITEM_AMOUNT = "itemAmount";
    }

    // The following code creates a method to hash passwords for increased security.
    // Note that this function uses the SHA-256 hashing method.
    // The code uses try and catch blocks to handle what happens when a password is not successfully hashed.
    // Additionally, it uses bytes in order to properly hash the passwords.
    // By hashing the passwords, should the database be breached, it would be harder for someone to tell what the password data says.
    private String hash(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b: hashBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        }
        catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    // The following code creates a method to detect and protect against SQL Injection attacks.
    // SQL Injection attacks commonly occur when there are tautologies in an SQL query. For example, 1=1, 2=2, and=and, and so on.
    // The following code uses regex pattern recognition to detect suspicious queries.
    private static final Pattern INJECTION_PATTERN = Pattern.compile( "(\\bOR\\b|\\bAND\\b|--|/\\*|;|\\bUNION\\b|\\bDROP\\b|=)",
    Pattern.CASE_INSENSITIVE
    );
    private boolean isInjectionSuspected(String... inputs) {
        for (String string : inputs) {
            if (string != null && INJECTION_PATTERN.matcher(string).find())
                return true;
        }
        return false;
    }

    // The following function creates the database when the UserDatabase class is opened for the first time.
    // It then calls the appropriate tables for specific users.
    // Note that when the InventoryTable is created, a foreign key is created too based on the id for the user from
    // the UserTable that will associate specific inventory with a specific user.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + UserTable.TABLE + " (" +
                UserTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserTable.COL_USERNAME + " TEXT, " +
                UserTable.COL_PASSWORD + " TEXT)");

        db.execSQL("create table " + InventoryTable.TABLE + " (" +
                InventoryTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                InventoryTable.COL_USER_ID + " INTEGER, " +
                InventoryTable.COL_ITEM_AMOUNT + " INTEGER, " +
                InventoryTable.COL_ITEM_NAME + " TEXT, " +
                "FOREIGN KEY(" + InventoryTable.COL_USER_ID + ") REFERENCES " + UserTable.TABLE + "(" + UserTable.COL_ID + "))");
    }

    // The following code creates the onUpgrade function that will be called to upgrade the table when needed.
    // It will drop the tables when needed and call the onCreate function to start again.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + InventoryTable.TABLE);
        onCreate(db);
    }

    // The following code creates a function to add a new user to the database.
    // Note that this function now incorporates protection against SQL Injection attacks should the user try and enter
    // a potentially harmful username or password.
    // This code also hashes a newly entered password via the hash functionality.
    public boolean addUser(String username, String password) {
        if (isInjectionSuspected(username))
            return false;

        if (isInjectionSuspected(password))
            return false;

        String hashed = hash(password);
        if (hashed == null)
            return false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserTable.COL_USERNAME, username);
        contentValues.put(UserTable.COL_PASSWORD, hashed);
        long result = db.insert(UserTable.TABLE, null, contentValues);
        return result != -1; // This returns that the user was added successfully.
    }

    // The following creates the existingUser function that checks to see if a user exists.
    // Note that this function now incorporates protection against SQL Injection attacks should the user try and enter
    // a potentially harmful username or password.
    // This code also hashes a newly entered password via the hash functionality.
    public boolean existingUser(String username, String password) {
        if(isInjectionSuspected(username))
            return false;

        String hashed = hash(password);
        if (hashed == null)
            return false;

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + UserTable.TABLE + " WHERE " + UserTable.COL_USERNAME + " = ? AND " + UserTable.COL_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, hashed});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // The following code declares the function getUserId.
    // It retrieves the userId from the SQLite database when needed.
    public int getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + UserTable.COL_ID + " FROM " + UserTable.TABLE + " WHERE " + UserTable.COL_USERNAME + " = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(0);
            cursor.close();
            return userId;
        }
        cursor.close(); // This closes the cursor when it is no longer needed.
        return -1; // This returns -1.
    }

    // The following code creates the AddItem function that will be used to add a new item to the database.
    // Note that this code also uses SQL Injection protection in case someone tries to enter a potentially harmful item name.
    public boolean AddItem(String itemName,int itemAmount,int userId) {
        if (isInjectionSuspected(itemName))
            return false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryTable.COL_USER_ID, userId);
        values.put(InventoryTable.COL_ITEM_NAME, itemName);
        values.put(InventoryTable.COL_ITEM_AMOUNT, itemAmount);
        long result = db.insert(InventoryTable.TABLE, null, values);
        return result != -1;
    }

    // The following code declares the function updateItemAmount.
    public boolean updateItemAmount (int itemId, int newAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryTable.COL_ITEM_AMOUNT, newAmount);
        int rowsAffected = db.update(InventoryTable.TABLE, values, InventoryTable.COL_ID + " = ?", new String[]{String.valueOf(itemId)});
        return rowsAffected > 0;
    }

    // The following code declares the function deleteItem based on the itemId.
    public boolean deleteItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(InventoryTable.TABLE, InventoryTable.COL_ID + " = ?", new String[]{String.valueOf(itemId)});
        return rowsDeleted > 0;
    }

    // The following code declares the function getUserInventory based on the userId.
    public Cursor getUserInventory(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InventoryTable.TABLE + " WHERE " + InventoryTable.COL_USER_ID + " = ? ", new String[]{String.valueOf(userId)});
    }

}


