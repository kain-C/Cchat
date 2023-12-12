package App.Aplicacion.cchat_0_3.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aplicacion.cchat_0_3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;

public class PostActivity extends AppCompatActivity {
    EditText description,Title,TextPost;
    private StorageTask uploadTask;
    StorageReference storageRef;
    String miUrlOk = "";
    TextView post;
    ProgressDialog pd;
    FirebaseAuth auth;
    FirebaseUser f_user;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        description = findViewById(R.id.description);
        Title = findViewById(R.id.Title);
        TextPost = findViewById(R.id.textPost);
        Intent intent;
        auth = FirebaseAuth.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference("posts");
        intent = getIntent();
        final String userid = intent.getStringExtra("userid");

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(PostActivity.this);
                pd.setMessage("please wait....");
                pd.show();

                String str_title = Title.getText().toString();
                String str_description = description.getText().toString();
                String str_TextPost = TextPost.getText().toString();


                if (TextUtils.isEmpty(str_title) || TextUtils.isEmpty(str_description) || TextUtils.isEmpty(str_TextPost)){
                    Toast.makeText(PostActivity.this, "Rellene todos los campos ", Toast.LENGTH_SHORT).show();
                }else{
                    post(str_title,str_description,str_TextPost);
                }
            }
        });
    }
    private void post(final String str_title,final String str_description,final String str_TextPost){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Posting");
        pd.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

        String postid = reference.push().getKey();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("postid", postid);
        hashMap.put("Title", Title);
        hashMap.put("description",description);
        hashMap.put("TextPost", TextPost);
        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.child(postid).push().setValue(hashMap);
        pd.dismiss();

        startActivity(new Intent(PostActivity.this, StarActivity.class));
        finish();
    }

}