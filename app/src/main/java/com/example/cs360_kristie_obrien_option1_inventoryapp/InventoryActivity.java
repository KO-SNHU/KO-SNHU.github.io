package com.example.cs360_kristie_obrien_option1_inventoryapp;

import android.os.Bundle;

import android.content.SharedPreferences;
import android.telephony.SmsManager;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import android.database.Cursor;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import android.view.View;

import android.content.Intent;


// The following class is the Inventory Activity class and is where the bulk of the app functions will run
// or branch off to based on user input.
// In this class, the user can see their inventory and modify it as well as access navigation to other screens.
public class InventoryActivity extends AppCompatActivity {

    UserDatabase userDatabase; // This declares a variable based on the User's Database called userDatabase that will be used to access that database.

    String loggedInUser; // This creates a string variable called loggedInUser that will be used to validate which user is logged in at a given time.
    int userId; // This creates a string variable called userId that will be used to validate userId information for the user that is logged in at the given time.

    LinearLayout inventoryLayout; // This creates a linear layout variable called inventoryLayout which will be used for dynamically adding new rows of information as the user adds new information.


    @Override
    protected void onCreate(Bundle savedInstanceState) { // This creates the on create class that is used when the InventoryActivity class is opened up for the first time.
        super.onCreate(savedInstanceState); // This calls the super on create method which will save the instance of the current state.
        setContentView(R.layout.activity_inventory); // This sets the current layout which is tied to the activity_inventory.xml file.

        userId = getIntent().getIntExtra("USERID", -1); // This gets the userId information for the currently logged in user.

        loggedInUser = getIntent().getStringExtra("USERNAME"); // This gets the logged in user's username.

        userDatabase = new UserDatabase(this); // This declares an instance of the UserDatabase and stores it in userDatabase.

        inventoryLayout = findViewById(R.id.linearLayout); // This gets the current layout information.

        loadUserInventory(); // This loads the current user's inventory.
    }

    private void sendSmsNotification(String itemName) { // This declares the class that will send SMS notifications to the user.

        SharedPreferences preferences = getSharedPreferences("UserSettings", MODE_PRIVATE); // This gets the current settings for the SMS.
        boolean isSmsEnabled = preferences.getBoolean(loggedInUser + "SMS_ENABLED", false); // This checks to see if SMS is currently enabled for this specific user or not.

        if (!isSmsEnabled) { // This asks if SMS is not enabled
            return; // If the above is found to be true, then the return is called to end the function.
        }

        String phoneNumber = "(650)555-1212"; // This sets the phone number to be used. Note that this uses the emulator's phone number
        String message = "Alert: Your inventory item '" + itemName + "' has run out. You may wish to restock this item."; // This declares the message that will be sent if the item is 0 and puts it with the appropriate item name.

        try { // This creates a try block for error handling
            SmsManager smsManager = SmsManager.getDefault(); // This calls the SMS manager
            smsManager.sendTextMessage(phoneNumber, null, message, null, null); // This calls for the text message to be sent.

            Toast.makeText(this, "SMS Sent!", Toast.LENGTH_SHORT).show(); // This displays a toast message notifying that a message was sent.

        } catch (Exception e) { // This catches the exception in case the message does not sent with the SMS manager.
            Toast.makeText(this, "Failed to send SMS. Please check your SMS settings.", Toast.LENGTH_SHORT).show(); // This displays a toast message saying the message did not send.
        }
    }

    private void loadUserInventory() { // This creates the function that will load the user's specific inventory.
        inventoryLayout.removeAllViews(); // This calls that all previous views for the inventory layout be removed to avoid overlapping data or old views.
        Cursor cursor = userDatabase.getUserInventory(userId); // This calls a cursor to provide access to the results of queries made on the user's inventory accessed with that user's id.

        if (cursor.moveToFirst()){ // This asks if the cursor is still moving through data. In other words, we are not at the end of the data yet.
            do { // This begins a do loop that will keep looping as long as the conditions in the while statement at the end are met.
                String itemName = cursor.getString(cursor.getColumnIndexOrThrow("itemName")); // This declares a string called itemName and retrieves it from the database.
                int itemAmount = cursor.getInt(cursor.getColumnIndexOrThrow("itemAmount")); // This declares a string called itemAmount and retrieves it from the database.
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow("_id")); // This declares a string called itemId and retrieves it from the database.

                View inventoryItemView = getLayoutInflater().inflate(R.layout.activity_item_added, inventoryLayout, false); // This inflates the current layout based on the layout defined in activity_item_added.xml file. So in other words, activity_item_added.xml is the template for all new rows created and all these new rows will be displayed in activity_inventory.xml.
                TextView itemNameText = inventoryItemView.findViewById(R.id.ItemName); // This declares the new TextView for the itemName.
                TextView itemAmountText = inventoryItemView.findViewById(R.id.ItemNumber); // This declares the new TextView for the itemNumber.
                Button minusButton = inventoryItemView.findViewById(R.id.minusNew); // This declares the new button for the minus button.
                Button addButton = inventoryItemView.findViewById(R.id.addNew); // This declares the new button for the add button.
                ImageView imageView2 = inventoryItemView.findViewById(R.id.imageView2); // This declares the new ImageView for the image view item.

                itemNameText.setText(itemName); // This sets the new itemNameText as the item name found in the query.
                itemAmountText.setText(String.valueOf(itemAmount)); // This sets the new itemAmountText as the item amount found in the query.

                minusButton.setOnClickListener(v -> { // This creates an on click listener for the minus button.
                    int newAmount = Math.max(itemAmount - 1, 0); // This sets the new amount as the old amount decreased by 1 but states that it can never go below 0 as negative inventory does not make sense here.
                    userDatabase.updateItemAmount(itemId, newAmount); // This updates the user database with the new amount for the item of the specific itemId.
                    loadUserInventory(); // This reloads the inventory to reflect the new information.

                    if (newAmount == 0) { // This asks if the new amount is equal to 0.
                        sendSmsNotification(itemName); // If the new amount is found to be equal to 0, the item name for that item is sent to the function that will send the SMS notification regarding that item being at 0.
                    }

                });

                addButton.setOnClickListener(v -> { // This creates an on click listener for the add button.
                    int newAmount = itemAmount + 1; // This sets the new amount as the old amount increased by 1.
                    userDatabase.updateItemAmount(itemId, newAmount); // This updates the user database with the new amount for the item of the specific itemId.
                    loadUserInventory(); // This reloads the inventory to reflect the new information.
                });

                imageView2.setOnClickListener(v -> { // This creates an on click listener for the image view item.
                    userDatabase.deleteItem(itemId); // This deletes the information from the database associated with the specified user id.
                    loadUserInventory(); // This reloads the inventory to reflect the new information.
                });

                inventoryLayout.addView(inventoryItemView); // This sets the new inventory layout reflecting any changes that may have been made.


            }
            while(cursor.moveToNext()); // This sets the while condition which states that the loop will keep looping as long as there is more data in the database to move to. In other words, until the end of the data is reached, the loop will continue.
        }

        cursor.close(); // This closes the cursor after it is no longer needed.
    }

    public void AddNewItem(View view) { // This create the AddNewItem function which is used to navigate to the Add New Item screen by pressing the Add New Item button.
        Intent intent = new Intent (InventoryActivity.this, AddNewItem.class); // This creates an intent used to indicate which activity to move to. In this case, it specifies that the AddNewItem class should be loaded.
        intent.putExtra("USERID", userId); // This puts the extra intent on the userId ensuring that the proper logged in user information is kept across activities by way of the user's id.
        startActivity(intent); // This starts the activity.
        finish(); // This finishes the activity.

        }

    public void LogOut(View view) { // This create the LogOut function which is used to navigate back to the Login screen by pressing the logout button.
        Intent intent = new Intent (InventoryActivity.this, MainActivity.class); // This creates an intent used to indicate which activity to move to. In this case, it specifies that the MainActivity class should be loaded.
        startActivity(intent);  // This starts the activity.
        finish(); // This finishes the activity.


    }

    public void SMS(View view) { // This create the SMS function which is used to navigate to the SMS screen by pressing the SMS button.
        Intent intent = new Intent (InventoryActivity.this, SMSNotification.class); // This creates an intent used to indicate which activity to move to. In this case, it specifies that the SMS class should be loaded.
        intent.putExtra("USERNAME", loggedInUser); // This puts the extra intent on the username ensuring that the proper logged in user information is kept across activities by way of the user's username.
        intent.putExtra("USERID", userId); // This puts the extra intent on the userId ensuring that the proper logged in user information is kept across activities by way of the user's id.
        startActivity(intent);  // This starts the activity.
        finish(); // This finishes the activity.

    }
}

