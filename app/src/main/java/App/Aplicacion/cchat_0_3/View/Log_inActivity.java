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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Log_inActivity extends AppCompatActivity {
    EditText email,password;
    Button login,irregistro;

    TextView txt_signup;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //si el ususario ya a ingresado passa directamente a la principal
        firebaseUser  = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            Intent intent = new Intent(Log_inActivity.this, StarActivity.class );
            startActivity(intent);
            finish();
        }
        email =findViewById(R.id.emailAdress_L);
        password = findViewById(R.id.password_L);
        login = findViewById(R.id.login);
        txt_signup = findViewById(R.id.txt_signup);
        auth = FirebaseAuth.getInstance();

        irregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Log_inActivity.this, Register.class);
                startActivity(i);

            }
        });
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Log_inActivity.this, Register.class));


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog pd = new ProgressDialog( Log_inActivity.this);
                pd.setMessage("Please wait....");
                pd.show();

                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
                if(TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
                    Toast.makeText(Log_inActivity.this, "all fileds are required", Toast.LENGTH_SHORT).show();
                }else {
                    auth.signInWithEmailAndPassword(str_email, str_password)
                            .addOnCompleteListener(Log_inActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if( task.isSuccessful()){
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                                .child(auth.getCurrentUser().getUid());

                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                                pd.dismiss();
                                                Intent intent = new Intent(Log_inActivity.this, StarActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                pd.dismiss();
                                            }
                                        });
                                    }else{
                                        pd.dismiss();
                                        Toast.makeText(Log_inActivity.this, "Autentication failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
            }
        });

    }

}