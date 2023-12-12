package App.Aplicacion.cchat_0_3.View;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aplicacion.cchat_0_3.R;
import com.google.firebase.auth.FirebaseAuth;

import App.Aplicacion.cchat_0_3.View.Fragments.ProfileFragment;

public class optionsActivity extends AppCompatActivity {
 Button volver, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        volver = findViewById(R.id.volver);
        logout = findViewById(R.id.logout);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(optionsActivity.this, StarActivity.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(optionsActivity.this, LogInActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}