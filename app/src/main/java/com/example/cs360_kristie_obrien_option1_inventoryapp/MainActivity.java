package com.example.cs360_kristie_obrien_option1_inventoryapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.widget.EditText;

import android.widget.Button;

import android.widget.TextView;

import android.view.View;

import android.content.Intent;


// This begins the MainActivity class which is used as the login screen and the beginning of running the whole app.
public class MainActivity extends AppCompatActivity {

    EditText Password, Username; // This declares the edit text variables Username and Password that will be used to retrieve the user entered username and password.

    TextView textGreeting; // This declares the text view variable textGreeting which will be used to display text information based on user actions.

    Button buttonLogin, buttonSignUp; // This declares the buttons buttonLogin and buttonSignUp.

    UserDatabase userDatabase; // This declares an instance of the UserDatabsae called userDatabase.


    @Override
    protected void onCreate(Bundle savedInstanceState) { // This creates the on create class that is used when the MainActivity class is opened up for the first time.
        super.onCreate(savedInstanceState); // This calls the super on create method which will save the instance of the current state.

        setContentView(R.layout.activity_main);  // This sets the current layout which is tied to the activity_main.xml file.
        Username = findViewById(R.id.Username); // This gets the Username information from the view.
        Password = findViewById(R.id.Password); // This gets the Password information from the view.
        buttonLogin = findViewById(R.id.addNewItemButton); // This gets the buttonLogin information from the view.
        buttonSignUp = findViewById(R.id.buttonSignUp); // This gets the buttonSignUp information from the view.
        textGreeting = findViewById(R.id.textGreeting); // This gets the textGreeting information from the view.
        userDatabase = new UserDatabase(this); // This declares an instance of the UserDatabase and stores it in userDatabase.

    }

    public void handleLogin(View view) { // This declares a function that will be used to handle the login events.
        String username = Username.getText().toString(); // This declares the string variable username and gets the Username information as a string.
        String password = Password.getText().toString(); // This declares the string variable password and gets the Password information as a string.

        if (Username.getText().toString().matches("") || (Password.getText().toString().matches(""))) { // This asks if either the field for username or password is empty.
            textGreeting.setText("Please enter all fields "); // If either condition above is found to be true, this line is reached which prompts the user through the textGreeting line to enter all information.
        }

        else { // If the above conditions are not true, meaning all information is entered, this line is reached.
            boolean userExists = userDatabase.existingUser(username, password); // This declares a boolean variable userExists that will be used to check if the user already exists and can therefore log in.
            if (userExists) { // This asks if it was found that the user did in fact exist.
                int userId = userDatabase.getUserId(username); // This declares an integer variable called userId and it stores the user id for that specific username.
                textGreeting.setText("Logging you in "); // This displays the message through textGreeting that the user is being logged in.
                Intent intent = new Intent (MainActivity.this, InventoryActivity.class); // This creates a new intent to move to the activity InventoryActivity. In other words, this takes the user to the inventory page.
                intent.putExtra("USERNAME", username);  // This puts the extra intent on the username to ensure the proper user's information is displayed on the Inventory screen.
                intent.putExtra("USERID", userId); // This puts the extra intent on the userId based on user id to ensure the proper user's information is displayed on the Inventory screen.
                startActivity(intent); // This starts the activity.
                finish(); // This finishes the activity.

            }

            else { // If the above is found to not be true, in other words the user does not exist, this line is reached.
                textGreeting.setText("This User does not exist "); // This displays the message through textGreeting that the user does not exist.
            }

        }
    }

    public void registerUser(View view) { // This creates the function to register a new user.
        String user = Username.getText().toString(); // This declares the string variable user and gets the Username information as a string.
        String pass = Password.getText().toString(); // This declares the string variable pass and gets the Password information as a string.

        if (Username.getText().toString().matches("") || (Password.getText().toString().matches(""))) { // This asks if either the field for username or password is empty.
            textGreeting.setText("Please enter all fields "); // If either condition above is found to be true, this line is reached which prompts the user through the textGreeting line to enter all information.
        }

        else { // If the above conditions are not true, meaning all information is entered, this line is reached.
            boolean added = userDatabase.addUser(user, pass); // This declares a boolean variable added that will be used to add the new user to the database.
            if (added) { // This asks if the user was in fact added to the database.
                textGreeting.setText("Your user has been added! "); // This displays the message through textGreeting that the user was added.

            }

            else { // If the above if statement was not found to be true, meaning that the user was not added, this line is reached.
                textGreeting.setText("Your user was not added "); // This displays the message through textGreeting that the user was not added.
            }
        }

    }
}


