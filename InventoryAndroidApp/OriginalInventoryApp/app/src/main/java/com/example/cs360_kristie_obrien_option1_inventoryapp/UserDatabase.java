package com.example.cs360_kristie_obrien_option1_inventoryapp;

import android.content.ContentValues;

import android.content.Context;

import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

// This creates the UserDatabase class that is used to create the UserDatabase as an SQLite database.
public class UserDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "InventoryDatabase.db"; // This declares the Database's name.

    private static final int VERSION = 6; // This defines the version of the databsae.

    public UserDatabase (Context context) { // This creates the UserDatabase based on the context.
        super(context, DATABASE_NAME, null, VERSION); // This creates the super context for the database taking in the database name and version.
    }
    private static final class UserTable{ // This creates the UserTable which will store user information.
        private static final String TABLE = "users"; // This defines the table for users.
        private static final String COL_ID = "_id"; // This defines the column id for ids.
        private static final String COL_USERNAME = "username"; // This defines the column username for usernames.
        private static final String COL_PASSWORD = "password"; // This defines the column password for passwords.

    }

    private static final class InventoryTable{ // This creates the InventoryTable which will store inventory information.
        private static final String TABLE = "inventory"; // This defines the table for inventory,
        private static final String COL_ID = "_id"; // This defines the column id for ids.
        private static final String COL_USER_ID = "user_id"; // This defines the column User_ID for user_ids
        private static final String COL_ITEM_NAME = "itemName"; // This defines the column Item_Name for itemName.
        private static final String COL_ITEM_AMOUNT = "itemAmount"; // This defines the column Item_Amount for itemAmount

    }

    @Override
    public void onCreate(SQLiteDatabase db) {  // This creates the on create class that is used when the UserDatabase class is opened up for the first time. It is where the SQLite databases will be created.

        db.execSQL("create table " + UserTable.TABLE + " (" + // This calls for an SQLite database to be created called UserTable.
                UserTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // This creates a primary key that will auto increment for the Id.
                UserTable.COL_USERNAME + " TEXT, " + // This creates a column of text for the username.
                UserTable.COL_PASSWORD + " TEXT)"); // This creates a column of text for the password.

        db.execSQL("create table " + InventoryTable.TABLE + " (" + // This calls for an SQLite database to be created called InventoryTable.
                InventoryTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // This creates a primary key that will auto increment for the Id.
                InventoryTable.COL_USER_ID + " INTEGER, " + // This creates a column of integers for the user id.
                InventoryTable.COL_ITEM_AMOUNT + " INTEGER, " + // This creates a column of integers for the item amounts .
                InventoryTable.COL_ITEM_NAME + " TEXT, " + // This creates a column of text for the item name.
                "FOREIGN KEY(" + InventoryTable.COL_USER_ID + ") REFERENCES " + UserTable.TABLE + "(" + UserTable.COL_ID + "))"); // This creates a foreign key based on the id for the user from the UserTable that will associate specific inventory with a specific user.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) { // This creates the onUpgrade function that will be called to upgrade the table when needed.
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE); // This drops the  User table when needed to upgrade it with new information.
        db.execSQL("DROP TABLE IF EXISTS " + InventoryTable.TABLE); // This drops the  Inventory table when needed to upgrade it with new information.
        onCreate(db); // This calls the onCreate function.

    }

    public boolean addUser(String username, String password) { // This creates the addUser function that will be used to add a new user to the database.
        SQLiteDatabase db = this.getWritableDatabase(); // This calls an instance of the SQLite database and calls it as a writable database.
        ContentValues contentValues = new ContentValues(); // This declares a new ContentValues variable as contentValues.
        contentValues.put(UserTable.COL_USERNAME, username); // This inputs a new username.
        contentValues.put(UserTable.COL_PASSWORD, password); // This inputs a new password.
        long result = db.insert(UserTable.TABLE, null, contentValues); // This updates the table.
        return result != -1; // This returns that the user was added successfully.
    }

    public boolean existingUser(String username, String password) { // This creates the existingUser function that checks to see if a user exists.
        SQLiteDatabase db = this.getReadableDatabase(); // This calls an instance of the SQLite database and calls it as a readable database.
        String query = "SELECT * FROM " + UserTable.TABLE + " WHERE " + UserTable.COL_USERNAME + " = ? AND " + UserTable.COL_PASSWORD + " = ?"; // This asks whether the username and password exist in the table.
        Cursor cursor = db.rawQuery(query, new String[]{username, password}); // This sends the query as a cursor to check the database.

        boolean exists = cursor.getCount() > 0; // This declares a boolean variable exists that is true if amount of cursor's that return are greater than 0, implying that the user exists in the database.
        cursor.close(); // This closes the cursor when it is no longer needed.
        return exists; // This returns the value of exists.
    }

    public int getUserId(String username) { // This declares the function getUserId.
        SQLiteDatabase db = this.getReadableDatabase(); // This calls an instance of the SQLite database and calls it as a readable database.
        Cursor cursor = db.rawQuery("SELECT " + UserTable.COL_ID + " FROM " + UserTable.TABLE + " WHERE " + UserTable.COL_USERNAME + " = ?", new String[]{username}); // This asks whether the username and matching user id exist in the table.
        if (cursor.moveToFirst()) { // This asks if there is more data to search through.
            int userId = cursor.getInt(0); // This declares the integer variable userId and stores the found userIds in it.
            cursor.close(); // This closes the cursor when it is no longer needed.
            return userId; // This returns the value of userId.
        }
        cursor.close(); // This closes the cursor when it is no longer needed.
        return -1; // This returns -1.
    }

    public boolean AddItem(String itemName,int itemAmount,int userId) { // This creates the AddItem function that will be used to add a new item to the database.
        SQLiteDatabase db = this.getWritableDatabase(); // This calls an instance of the SQLite database and calls it as a writable database.
        ContentValues values = new ContentValues(); // This declares a new ContentValues variable as contentValues.
        values.put(InventoryTable.COL_USER_ID, userId); // This inputs a new userId.
        values.put(InventoryTable.COL_ITEM_NAME, itemName); // This inputs a new itemName.
        values.put(InventoryTable.COL_ITEM_AMOUNT, itemAmount); // This inputs a new itemAmount
        long result = db.insert(InventoryTable.TABLE, null, values); // This updates the table.
        return result != -1; // This returns that the item was added successfully.
    }

    public boolean updateItemAmount (int itemId, int newAmount) { // This declares the function updateItemAmount.
        SQLiteDatabase db = this.getWritableDatabase(); // This calls an instance of the SQLite database and calls it as a writable database.
        ContentValues values = new ContentValues(); // This declares a new ContentValues variable as contentValues.
        values.put(InventoryTable.COL_ITEM_AMOUNT, newAmount); // This puts in the newAmount into the table.
        int rowsAffected = db.update(InventoryTable.TABLE, values, InventoryTable.COL_ID + " = ?", new String[]{String.valueOf(itemId)}); // This finds the item information associated with the itemId.
        return rowsAffected > 0; // This returns the rows affected.
    }

    public boolean deleteItem(int itemId) { // This declares the function deleteItem.
        SQLiteDatabase db = this.getWritableDatabase(); // This calls an instance of the SQLite database and calls it as a writable database.
        int rowsDeleted = db.delete(InventoryTable.TABLE, InventoryTable.COL_ID + " = ?", new String[]{String.valueOf(itemId)}); // This finds the item information associated with the itemId.
        return rowsDeleted > 0; // This returns the rows deleted.
    }

    public Cursor getUserInventory(int userId) { // This declares the function getUserInventroy.
        SQLiteDatabase db = this.getReadableDatabase(); // This calls an instance of the SQLite database and calls it as a readable database.

        return db.rawQuery("SELECT * FROM " + InventoryTable.TABLE + " WHERE " + InventoryTable.COL_USER_ID + " = ? ", new String[]{String.valueOf(userId)}); // This finds the inventory information associated with the userId.
    }

}


