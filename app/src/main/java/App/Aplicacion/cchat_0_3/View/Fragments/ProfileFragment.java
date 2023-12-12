package App.Aplicacion.cchat_0_3.View.Fragments;



import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import App.Aplicacion.cchat_0_3.Model.Post;
import App.Aplicacion.cchat_0_3.Model.User;

import App.Aplicacion.cchat_0_3.View.EditProfileActivity;
import App.Aplicacion.cchat_0_3.View.LogInActivity;
import App.Aplicacion.cchat_0_3.View.infoActivity;
import App.Aplicacion.cchat_0_3.View.optionsActivity;

import com.aplicacion.cchat_0_3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileFragment extends Fragment {
    TextView posts, followers, following, fullname, bio, username;
    DatabaseReference reference;
    Button edit_profile,Logout,info;
    FirebaseUser firebaseUser;
    String profileid;
    FirebaseUser fuser;
    private List<Post> postList;
    private List<Post> postList_saves;
    Intent intent;
    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences prefs = getContext().getSharedPreferences("PREFS", MODE_PRIVATE);
        profileid = prefs.getString("profileid", "none");
        posts = view.findViewById(R.id.posts);
        Logout = view.findViewById(R.id.btn_logout);
        fullname = view.findViewById(R.id.fullname);
        bio = view.findViewById(R.id.bio);
        info = view.findViewById(R.id.info);
        edit_profile = view.findViewById(R.id.edit_profile);
       username = view.findViewById(R.id.usernameP);
        postList = new ArrayList<>();
        FirebaseUser fuser;
       // intent = getIntent();

       // final String userid = intent.getStringExtra("userid");
        //reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);


        userInfo();
       // addNotification();
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), infoActivity.class));
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), optionsActivity.class));
            }
        });
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });

           // username = view.findViewById(R.id.usernameP);

            storageReference = FirebaseStorage.getInstance().getReference("uploads");

            fuser = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    username.setText(user.getUsername());
                    fullname.setText(user.getFullname());
                    bio.setText(user.getBio());


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            return view;
    }

    private void addNotification(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(profileid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "started following you");
        hashMap.put("postid", "");
        hashMap.put("ispost", false);

        reference.push().setValue(hashMap);
    }
    private void userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (getContext() == null){
                    return;
                }
                User user = dataSnapshot.getValue(User.class);
               // username.setText(user.getUsername());
                //fullname.setText(user.getFullname());
               // bio.setText(user.getBio());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void getNrPosts(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);
                    if (post.getPublisher().equals(profileid)){
                        i++;
                    }
                }
                posts.setText(""+i);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}