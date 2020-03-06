package ca.georgebrown.comp3074.simplechatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputFirstName, inputLastName;
    private Button btnSignUp, btnSignIn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    //*** Handling the already login user ***//
    /*
    @Override
    protected void onStart() {
        super.onStart();

        if(auth.getCurrentUser() != null){

        }
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // *** Variable declaration *** //
        auth = FirebaseAuth.getInstance();

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        inputFirstName = findViewById(R.id.txtFirstName);
        inputLastName = findViewById(R.id.txtLastName);

        progressBar = findViewById(R.id.progressBar);


        //*** Start of Sign Up Button ***//
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstName = inputFirstName.getText().toString().trim();
                final String lastName = inputLastName.getText().toString().trim();
                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                /*
                if(TextUtils.isEmpty(firstName)){
                    Toast.makeText(getApplicationContext(), "Enter your First Name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(lastName)){
                    Toast.makeText(getApplicationContext(), "Enter your Last Name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                */

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed !", Toast.LENGTH_LONG).show();
                                } else {

                                    /*User user = new User(firstName, lastName, email);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user);*/
                                    Toast.makeText(SignupActivity.this, "Authentication succeeded !", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
        //*** End of Sign Up Code ***//

        //*** Start of Sign In Button ***//
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, SigninActivity.class));
                finish();
            }
        });
        //*** End of Sign In Button ***//
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
