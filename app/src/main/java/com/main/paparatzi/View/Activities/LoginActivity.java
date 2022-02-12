package com.main.paparatzi.View.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.main.paparatzi.Control.FireBase;
import com.main.paparatzi.Control.OnSuccsessCallBack;
import com.main.paparatzi.Control.Permission;
import com.main.paparatzi.R;

import static com.main.paparatzi.Control.Permission.PERMISSIONS_BACKGROUND_LOCATION_REQUEST_CODE;
import static com.main.paparatzi.Control.Permission.PERMISSIONS_CAMERA_REQUEST_CODE;
import static com.main.paparatzi.Control.Permission.PERMISSIONS_READ_STORAGE_AND_CAMERA_REQUEST_CODE;
import static com.main.paparatzi.Control.Permission.PERMISSIONS_REGULAR_LOCATION_REQUEST_CODE;

public class LoginActivity extends AppCompatActivity {



    private MaterialButton BTN_submit;
    private TextInputLayout EDT_email;
    private TextInputLayout EDT_password;
    private TextView LBL_signUp;

    private Permission permission;

    private boolean storageFlag=true,cameraFlag=true,locationFlag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        permission=new Permission(this);
        permission.requestForStorage();
        findViews();
        initViews();
        boolean curUser = FireBase.getInstance().checkCurrentUser();
        if (curUser) {
            Log.d("pttt", "onStart: "+curUser);
            openMainActivity();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    private void initViews() {
        Log.d("pttt", "initViews: ");
        BTN_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForVaildInput();
            }
        });
        LBL_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pttt", "onClick: SIGN UP");
                openSignUpActivity();
            }
        });
    }

    private void openSignUpActivity() {
        Intent myIntent = new Intent(LoginActivity.this, SignupActivity.class);
        LoginActivity.this.startActivity(myIntent);
    }

    private void checkForVaildInput() {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(EDT_email.getEditText().getText().toString()).matches()) {
            Log.d("pttt", "checkForValidInputs: Email invalid");
            EDT_email.setError("please enter current email");
            return;
        }
        if(EDT_password.getEditText().getText().toString().equals("")){
            Log.d("pttt", "checkForValidInputs: invalid password");
            EDT_password.setError("password can not be null");
            return;
        }
        if (EDT_password.getEditText().getText().toString().length() < 6) {
            Log.d("pttt", "checkForValidInputs: short password");
            EDT_password.setError("password must have at least digits");
            return;
        }
        // After the credentials are well formatted, check for validity
        loginUserWithEmailAndPassword();
    }

    private void loginUserWithEmailAndPassword() {
        Log.d("pttt", "loginUserWithEmailAndPassword: ");
        FireBase.getInstance().loginByEmailAndPassword(EDT_email.getEditText().getText().toString()
                , EDT_password.getEditText().getText().toString(),this, new OnSuccsessCallBack() {
                    @Override
                    public void isSuccess(boolean b) {
                        if(b){
                            openMainActivity();
                            Toast.makeText(LoginActivity.this,"LOGIN SUCCESS",Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(LoginActivity.this,"USER NOT EXIST . SIGNUP?",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openMainActivity() {
        Log.d("pttt", "openMainActivity: ");
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(myIntent);
        LoginActivity.this.finish();
    }

    private void findViews() {
        BTN_submit =findViewById(R.id.activity_login_BTN_submit);
        EDT_email =findViewById(R.id.activity_login_LBL_email);
        EDT_password =findViewById(R.id.activity_login_LBL_password);
        LBL_signUp =findViewById(R.id.login_LBL_signUp);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_READ_STORAGE_AND_CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permission.requestForCamera();
                } else {
                    storageFlag= permission.requestPermissionWithRationaleCheck(Manifest.permission.WRITE_EXTERNAL_STORAGE
                            , PERMISSIONS_READ_STORAGE_AND_CAMERA_REQUEST_CODE);
                    if(!storageFlag)//do not ask me again
                        permission.requestForCamera();
                }
                return;
            }
            case PERMISSIONS_CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permission.requestForLocation();
                } else {
                    cameraFlag=permission.requestPermissionWithRationaleCheck(Manifest.permission.CAMERA, PERMISSIONS_CAMERA_REQUEST_CODE);
                    if(!cameraFlag)//do not ask me again
                        permission.requestForLocation();
                }
                return;
            }
            case PERMISSIONS_REGULAR_LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(!storageFlag||!cameraFlag) {
                        permission.openPermissionSettingDialog();
                        return;
                    }
                }else {
                    locationFlag=permission.requestPermissionWithRationaleCheck(Manifest.permission.ACCESS_FINE_LOCATION,PERMISSIONS_REGULAR_LOCATION_REQUEST_CODE);
                    if(!locationFlag) {
                        permission.openPermissionSettingDialog();
                        return;
                    }
                }

                return;
            }
            case PERMISSIONS_BACKGROUND_LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("pttt", "BACKGROUND_LOCATION_CODE");
                }else {
                    permission.requestPermissionWithRationaleCheck(Manifest.permission.ACCESS_BACKGROUND_LOCATION
                            ,PERMISSIONS_BACKGROUND_LOCATION_REQUEST_CODE);
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(!permission.checkForPermission(Manifest.permission.READ_EXTERNAL_STORAGE)||
                !permission.checkForPermission(Manifest.permission.CAMERA)||
                (!permission.checkForFineLocationPermission()||!permission.checkForCoarseLocationPermission()))
            permission.openPermissionSettingDialog();

    }

}