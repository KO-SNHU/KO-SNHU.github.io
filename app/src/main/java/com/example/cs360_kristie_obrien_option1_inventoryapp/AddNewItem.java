package com.example.cs360_kristie_obrien_option1_inventoryapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


// The following class is the AddNewItem class and it is used to add a new item to the user's specific inventory.
public class AddNewItem extends AppCompatActivity {

    EditText ItemName, ItemAmount; // This declares the edit text variables ItemName and ItemAmount that will be used to retrieve the user entered item name and item amount.

    TextView textGreeting2; // This declares the text view variable textGreeting2 which will be used to display text information based on user actions.

    UserDatabase userDatabase; // This declares an instance of the UserDatabsae called userDatabase.

    String loggedInUser; // This declares the string loggedInUser that will be used to validate which user is currently logged in.
    int userId; // This declares the string userId that will be used to validate which user is currently logged in.


    @Override
    protected void onCreate(Bundle savedInstanceState) { // This creates the on create class that is used when the AddNewItem class is opened up for the first time.
        super.onCreate(savedInstanceState); // This calls the super on create method which will save the instance of the current state.

        userDatabase = new UserDatabase(this);  // This declares an instance of the UserDatabase and stores it in userDatabase.

        userId = getIntent().getIntExtra("USERID", -1); // This gets the userId information for the currently logged in user.

        loggedInUser = getIntent().getStringExtra("USERNAME"); // This gets the logged in user's username.

        setContentView(R.layout.activity_add_new_item); // This sets the current layout which is tied to the activity_add_new_item.xml file.

        textGreeting2 = findViewById(R.id.textGreeting2); // This gets the textGreeting2 information from the view.
        ItemName = findViewById(R.id.ItemName); // This gets the ItemName from the view.
        ItemAmount = findViewById(R.id.ItemAmount); // This gets the ItemAmount from the view.

    }

    public void addNewItemClick(View view) { // This declares a function that will be used to add the item to the database upon clicking the Add Item button.
        String name = ItemName.getText().toString(); // This declares the string variable name and bases it off of ItemName.
        String amountString = ItemAmount.getText().toString(); // This declares the string variable amountString and bases it off of ItemAmount.

        if (ItemName.getText().toString().matches("") || (ItemAmount.getText().toString()).matches("")) { // This asks if either the field for ItemName or ItemAmount is empty.
            textGreeting2.setText("Please enter all fields "); // If either condition above is found to be true, this line is reached which prompts the user through the textGreeting2 line to enter all information.
        }

        else { // If the above conditions are not true, meaning all information is entered, this line is reached.
            int amount = Integer.parseInt(amountString); // This declares the integer variable amount and sets it equal to amountString as an integer.
            boolean added = userDatabase.AddItem(name, amount, userId); // This declares a boolean variable added that will be used to check if the item was added to the databse or not. Note that it calls the AddItem function, thus adding the new item to the database.
            if (added) { // This asks if the item was in fact added.
                textGreeting2.setText("Your Item has been added! "); // This displays a message letting the user know that the item was added.
                Intent intent = new Intent (AddNewItem.this, InventoryActivity.class); // This creates a new intent to move to the activity InventoryActivity. In other words, this takes the user back to the inventory page.
                intent.putExtra("USERNAME", loggedInUser);  // This puts the extra intent on the loggedInUser based on username to ensure the proper user's information is displayed on the Inventory screen.
                intent.putExtra("USERID", userId);  // This puts the extra intent on the userId based on user id to ensure the proper user's information is displayed on the Inventory screen.
                startActivity(intent); // This starts the activity.
                finish(); // This finishes the activity.

            } else { // If the above statement was found to be false, in other words the item was not added, this line is reached.
                textGreeting2.setText("Your Item was not added "); // This displays the message telling the user their item was not added.
            }
        }

    }

}
