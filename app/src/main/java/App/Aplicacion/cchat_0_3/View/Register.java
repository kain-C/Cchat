package App.Aplicacion.cchat_0_3.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aplicacion.cchat_0_3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

        EditText username,fullname,email,password;
        Button register;
        TextView txt_login;
        FirebaseAuth auth;
        DatabaseReference reference;
        ProgressDialog pd;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            username = findViewById(R.id.Username);
            fullname = findViewById(R.id.Fullname);
            email = findViewById(R.id.Email);
            password = findViewById(R.id.Password);
            register = findViewById(R.id.Register);
            txt_login = findViewById(R.id.txt_login);

            auth = FirebaseAuth.getInstance();
            txt_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Register.this, LogInActivity.class);
                    startActivity(i);

                }
            });
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pd = new ProgressDialog(Register.this);
                    pd.setMessage("please wait....");
                    pd.show();

                    String str_username = username.getText().toString();
                    String str_fullname = fullname.getText().toString();
                    String str_email = email.getText().toString();
                    String str_password = password.getText().toString();

                    if(TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullname)
                            || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
                        Toast.makeText(Register.this, "allfileds are required", Toast.LENGTH_SHORT).show();
                    }else if(str_password.length()<6){
                        Toast.makeText(Register.this, "password mut have 6 characters", Toast.LENGTH_SHORT).show();
                    }else{
                        register(str_username,str_fullname,str_email,str_password);
                    }

                }
            });
        }
        private void register(final String username,final String fullname, String email, String password){
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if( task.isSuccessful()) {
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                String userid = firebaseUser.getUid();

                                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("id", userid);
                                hashMap.put("username", username.toLowerCase());
                                hashMap.put("fullname", fullname);
                                hashMap.put("bio", "");
                                hashMap.put("imageurl", "https://.ebasestorage.googleapis.com/v0/b/cchat-742c5.appspot.com/o/imagenes%20publicadas%2Fone.png?alt=media&token=962fcb3e-bc27-4f25-aaca-ed1be35ff0ab");

                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            pd.dismiss();
                                            Intent intent = new Intent(Register.this, StarActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);

                                        }
                                    }
                                });
                            }
                        }
                    });

        }
    }