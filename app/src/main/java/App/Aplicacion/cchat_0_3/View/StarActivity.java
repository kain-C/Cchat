package App.Aplicacion.cchat_0_3.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import App.Aplicacion.cchat_0_3.View.Fragments.HomeFragment;
import App.Aplicacion.cchat_0_3.View.Fragments.PostFragment;
import App.Aplicacion.cchat_0_3.View.Fragments.ProfileFragment;

import com.aplicacion.cchat_0_3.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StarActivity extends AppCompatActivity {

    private FloatingActionButton btn_post;
    private ImageButton btn_chat;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        btn_chat = findViewById(R.id.btn_chat);

        final Fragment homeFragment = new HomeFragment();
        final Fragment PostFragment = new PostFragment();
        final Fragment profileFragment = new ProfileFragment();


        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, homeFragment).commit();
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (item.getItemId() == R.id.inicioItem) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, homeFragment).commit();
            } else if (item.getItemId() == R.id.buscarItem) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, PostFragment).commit();
            } else if (item.getItemId() == R.id.perfilItem) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, profileFragment).commit();
            }
            return true;
        });

        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.inicioItem);
        badge.setNumber(7);
        badge.setVisible(true);


        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StarActivity.this,ChatActivity.class);
                startActivity(i);
            }
        });

    }
}