package App.Aplicacion.cchat_0_3.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import App.Aplicacion.cchat_0_3.Model.UserList;

import App.Aplicacion.cchat_0_3.View.Fragments.ListUsersFragment;
import App.Aplicacion.cchat_0_3.View.Fragments.chatsFragment;

import com.aplicacion.cchat_0_3.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
       // username = findViewById(R.id.username);

        //profile_image = findViewById(R.id.profile_image);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                UserList user = datasnapshot.getValue(UserList.class);
               // username.setText(user.getUsername());
               /* if ((user.getImageurl().equals("default"))){
                    profile_image.setImageResource(R.mipmap.ic_launcher);

                }else {
                    Glide.with(Principal.this).load(user.getImageurl()).into(profile_image);
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        final Fragment chatsFragment = new chatsFragment();
        final Fragment ListUsersFragment = new ListUsersFragment();
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, ListUsersFragment).commit();
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (item.getItemId() == R.id.MessageItem) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, ListUsersFragment).commit();
            }
            return true;
        });

    }

}