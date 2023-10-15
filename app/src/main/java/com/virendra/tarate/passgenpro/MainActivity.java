//Warning
// Unauthorized use, reproduction, or distribution of this code, in whole or in part, without the explicit permission of the owner, is strictly prohibited and may result in severe legal consequences under the relevant IT Act and other applicable laws.
// To use this code, you must first obtain written permission from the owner. For inquiries regarding licensing, collaboration, or any other use of the code, please contact virendratarte22@gmail.com.
// Thank you for respecting the intellectual property rights of the owner.

package com.virendra.tarate.passgenpro;

import static java.lang.Integer.parseInt;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    private ClipboardManager myClipboard;
    private ClipData myClip;
    private long pressedTime;
    private EditText lengthEditText;
    private CheckBox uppercaseCheckBox, lowercaseCheckBox, digitsCheckBox, specialCharsCheckBox;
    private Button generateButton;
    private TextView passwordTextView;
    ImageView credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lengthEditText = findViewById(R.id.edtLength);
        uppercaseCheckBox = findViewById(R.id.upper);
        lowercaseCheckBox = findViewById(R.id.lower);
        digitsCheckBox = findViewById(R.id.digits);
        specialCharsCheckBox = findViewById(R.id.symbols);
        generateButton = findViewById(R.id.pssbtn);
        passwordTextView = findViewById(R.id.myPass);
        credits = findViewById(R.id.creadits);
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);


        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CRIntent = new Intent(MainActivity.this, Credits.class);
                startActivity(CRIntent);
            }
        });

        //Copy Password
        passwordTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(passwordTextView.getText().toString() == "Your Password" || passwordTextView.getText().toString().equals("Your Password") || passwordTextView.getText().toString() == "your password"){
                    return false;
                }else{
                    String text;
                    text = passwordTextView.getText().toString();
                    myClip = ClipData.newPlainText("text", text);
                    myClipboard.setPrimaryClip(myClip);
                    Toast.makeText(MainActivity.this, "Password Copied",
                            Toast.LENGTH_SHORT).show();

                    return true;
                }

            }
        });


        //After Clicking Button
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lengthEditText.getText().toString().isEmpty() || lengthEditText.getText().toString().isBlank() || parseInt(lengthEditText.getText().toString()) == 0){
                    Toast.makeText(MainActivity.this, "Password Length is Required", Toast.LENGTH_SHORT).show();
                }
                else if(!uppercaseCheckBox.isChecked() && !lowercaseCheckBox.isChecked() && !digitsCheckBox.isChecked() && !specialCharsCheckBox.isChecked()){
                    Toast.makeText(MainActivity.this, "Please Cheack At Least One Check Box", Toast.LENGTH_SHORT).show();
                }
                else if(parseInt(lengthEditText.getText().toString()) >=65){
                    Toast.makeText(MainActivity.this, "The Length Of Password Must be Less than 64", Toast.LENGTH_SHORT).show();
                }else{
                    generatePassword();
                }
            }
        });


    }

    private void generatePassword() {
        int length = parseInt(lengthEditText.getText().toString());
        boolean useUppercase = uppercaseCheckBox.isChecked();
        boolean useLowercase = lowercaseCheckBox.isChecked();
        boolean useDigits = digitsCheckBox.isChecked();
        boolean useSpecialChars = specialCharsCheckBox.isChecked();

        String password = generateRandomPassword(length, useUppercase, useLowercase, useDigits, useSpecialChars);

        passwordTextView.setText(password);
    }


    private String generateRandomPassword(int length, boolean useUppercase, boolean useLowercase, boolean useDigits, boolean useSpecialChars) {
        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        String digitChars = "0123456789";
        String specialChars = "!@#$%^&*()-_=+[]{}|;:'\",.<>?";

        StringBuilder validChars = new StringBuilder();
        if (useUppercase) validChars.append(uppercaseChars);
        if (useLowercase) validChars.append(lowercaseChars);
        if (useDigits) validChars.append(digitChars);
        if (useSpecialChars) validChars.append(specialChars);

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(validChars.length());
            password.append(validChars.charAt(randomIndex));
        }

        return password.toString();
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

}