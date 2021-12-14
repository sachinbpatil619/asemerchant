package com.shimoga.asesolMerchant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shimoga.asesolMerchant.Common.Common;
import com.shimoga.asesolMerchant.Common.LoggedUser;
import com.shimoga.asesolMerchant.Model.User;

public class SignUp extends AppCompatActivity {
    EditText edtPhone, edtName, edtPassword, edtEmail;
    Button btnSignUp;
    private String verificationId;

    private SQLiteDatabase sql;
    //DatabaseHelper db1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        edtName = findViewById(R.id.fullname);
        edtPassword = findViewById(R.id.password);
        edtPhone = findViewById(R.id.phone);
        edtEmail = findViewById(R.id.email);

        final String number = getIntent().getStringExtra("phone_number");

        btnSignUp = (Button) findViewById(R.id.signup);
        edtPhone.setText(number);
        edtPhone.setEnabled(false);

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        //db1 = new DatabaseHelper(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String phone, name, password, email;
                final String isStaff="false";
                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                name = edtName.getText().toString().trim();
                phone=edtPhone.getText().toString().trim();

                //final String number = getIntent().getStringExtra("phone_number");
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()  )
                {
                    Toast.makeText(SignUp.this, "Please Provide all the details", Toast.LENGTH_SHORT).show();
                }
                else if (name.length()<3)
                {
                    Toast.makeText(SignUp.this, "Please provide your full name", Toast.LENGTH_SHORT).show();
                    edtName.requestFocus();
                }
                else if (password.length()<6)
                {
                    Toast.makeText(SignUp.this, "Please choose a strong password", Toast.LENGTH_SHORT).show();
                    edtPassword.requestFocus();
                }
                else if (email.length()<13)
                {
                    Toast.makeText(SignUp.this, "Invalid E-mail ID", Toast.LENGTH_SHORT).show();
                    edtEmail.requestFocus();
                }
                else {
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //check if user phone already exist
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                Toast.makeText(SignUp.this, "Phone Number already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                User user = new User(name, password, email,phone,isStaff);
                                table_user.child(number).setValue(user);
                                Toast.makeText(SignUp.this, "Sign-in Successful", Toast.LENGTH_SHORT).show();
                                Common.currentUser = user;
                                LoggedUser loggedUser = new LoggedUser();
                                loggedUser.setPhone(number);

                                //boolean isInserted = db1.addUser(number);

                                Intent intent = new Intent(SignUp.this, HomeActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("password", password);
                                intent.putExtra("email", email);
                                //intent.putExtra("phone_number",number);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

        });

    }

    /*@Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }*/
}

