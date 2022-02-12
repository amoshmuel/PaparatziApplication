package com.main.paparatzi.View.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.main.paparatzi.Control.FireBase;
import com.main.paparatzi.R;

public class SignupActivity extends AppCompatActivity {

    //views
    private Button BTN_submit;
    private TextInputLayout EDT_email;
    private TextInputLayout EDT_password;
    private TextInputLayout EDT_confirm_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("pttt", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViews();
        initViews();
    }
    private void initViews() {
        Log.d("pttt", "initViews: ");
        BTN_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForVaildInput();
            }
        });
    }

    private void checkForVaildInput() {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(EDT_email.getEditText().getText().toString()).matches()) {
            Log.d("pttt", "checkForValidInputs: Email invalid");
            EDT_email.setError("pleas enter current email");
            BTN_submit.setClickable(true);
            return;
        }
        if(EDT_password.getEditText().getText().toString().length()<6 || EDT_password.getEditText().getText().toString().equals(" ")) {
            EDT_password.setError("password must contain at least 6 digits .");
            BTN_submit.setClickable(true);
            return;
        }
        if(EDT_password.getEditText().getText().toString().compareTo(EDT_confirm_password.getEditText().getText().toString())!=0) {
            EDT_confirm_password.setError("passwords don't match !");
            BTN_submit.setClickable(true);
            return;
        }

        registerToCloudUser();
    }

    private void registerToCloudUser() {
        FireBase.getInstance().
                addUserByEmailAndPassword(EDT_email.getEditText().getText().toString(),
                        EDT_password.getEditText().getText().toString(), SignupActivity.this);
        Toast.makeText(SignupActivity.this,"Register Successed",Toast.LENGTH_SHORT).show();
        Toast.makeText(SignupActivity.this,"Welcome",Toast.LENGTH_SHORT).show();
        this.finish();
    }

    private void findViews() {
        BTN_submit =findViewById(R.id.activity_signup_BTN_submit);
        EDT_email =findViewById(R.id.activity_signup_LBL_email);
        EDT_password =findViewById(R.id.activity_signup_LBL_password);
        EDT_confirm_password =findViewById(R.id.activity_signup_LBL_password_confirm);
    }
}