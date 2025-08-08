package com.example.cs360_kristie_obrien_option1_inventoryapp;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;

// This declares the class SMSNotification that will be used for sending SMS notifications.
public class SMSNotification extends AppCompatActivity {
    private static final int SMS_PERMISSION_REQUEST_CODE = 1; // This declares the integer variable related to the SMS permission request code and sets it equal to 1.
    private Switch smsSwitch; // This declares the Switch variable smsSwitch.
    private SharedPreferences preferences; // This declares the SharePreferences variable preferences.
    String loggedInUser; // This declares the string loggedInUser variable.
    int userId; // This declares the integer variable userId.

    @Override
    protected void onCreate(Bundle savedInstanceState) { // This creates the on create class that is used when the SMSNotification class is opened up for the first time.
        super.onCreate(savedInstanceState); // This calls the super on create method which will save the instance of the current state.
        setContentView(R.layout.activity_toggle_sms); // This sets the current layout which is tied to the activity_toggle_sms.xml file.

        userId = getIntent().getIntExtra("USERID", -1); // This gets the userId information for the currently logged in user.
        loggedInUser = getIntent().getStringExtra("USERNAME"); // This gets the logged in user's username.

        smsSwitch = findViewById(R.id.switch1); // This finds the smsSwitch information by the view.
        preferences = getSharedPreferences("UserSettings", Context.MODE_PRIVATE); // This gets the share preferences information and stores it in preferences.

        boolean isSmsEnabled = preferences.getBoolean(loggedInUser + "SMS_ENABLED", false); // This sets the boolean variable isSmsEnabled and sets the default value to false.
        smsSwitch.setChecked(isSmsEnabled);  // This checks the switch onChecked setting with the result of isSmsEnabled.

        smsSwitch.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> { // This sets an OnCheckedChangeListener to see if the switch has been changed.
            if (isChecked) { // This asks if the switch is checked.
                checkTelephonyFeature(); // This calls the telephony feature to be checked to ensure what permissions were enabled.
            } else {
                saveSmsPreference(false); // This saves any changes made to the settings.
            }
        });
    }

    private void checkTelephonyFeature() { // This creates the function checkTelephonyFeature
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); // This calls the telephony manager to check services.
        if (telephonyManager != null && telephonyManager.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE) { // This ensures the phone type is enabled properly.
            requestSmsPermission(); // This calls the requestSmsPermission function.
        }

        else { // If the above if statement is not met, this line is reached.
            Toast.makeText(this, "This device does not support SMS", Toast.LENGTH_SHORT).show(); // This displays a message with toast letting the user know their device will not support SMS.
            smsSwitch.setChecked(false); // This sets the switch value to false.
        }
    }

    private void requestSmsPermission() { // This creates the requestSmsPermission function.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) { // This asks if permission has not been granted.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE); // This initiates a permission request.
        }

        else { // If the above if statement was not met, this line of code is reached.
            saveSmsPreference(true); // This sets the SMS enabled as equal to true.
        }
    }

    private void saveSmsPreference(boolean enabled) { // This creates the saveSmsPreference function.
        SharedPreferences preferences = getSharedPreferences("UserSettings", MODE_PRIVATE); // This retrieves the preferences and stores them in preferences.
        SharedPreferences.Editor editor = preferences.edit(); // This creates the option to edit the preferences.

        editor.putBoolean(loggedInUser + "SMS_ENABLED", enabled); // This ensures that the proper user is assigned to whether SMS is enabled while the enabling is happening.
        editor.apply(); // This applys the changes
        Toast.makeText(this, "SMS Notifications " + (enabled ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show(); // This displays through a toast message whether SMS is enabled or disabled respectively.
    }

    public void ReturnHome(View view) { // This create the ReturnHome function which is used to navigate to the Inventory screen by pressing the Return button.
        Intent intent = new Intent (SMSNotification.this, InventoryActivity.class);
        intent.putExtra("USERNAME", loggedInUser);   // This puts the extra intent on the loggedInUser based on username to ensure the proper user's information is displayed on the Inventory screen.
        intent.putExtra("USERID", userId); // This puts the extra intent on the userId based on user id to ensure the proper user's information is displayed on the Inventory screen
        startActivity(intent); // This starts the activity.
        finish(); // This finishes the activity


    }

}


