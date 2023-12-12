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
import android.widget.Toast;

import com.aplicacion.cchat_0_3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;

public class
Post_U_Activity extends AppCompatActivity {
    EditText description,Title,TextPost;
    private StorageTask uploadTask;
    StorageReference storageRef;
    String miUrlOk = "";
    Button btn_Post;
    ProgressDialog pd;
    FirebaseAuth auth;
    FirebaseUser f_user;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_uactivity);
        btn_Post = findViewById(R.id.btn_Post);
        description = findViewById(R.id.description_);
        TextPost = findViewById(R.id.textPost_);
        Title = findViewById(R.id.Title_);
        Intent intent;
        auth = FirebaseAuth.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference("posts");
        intent = getIntent();
        final String userid = intent.getStringExtra("userid");

        btn_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(Post_U_Activity.this);
                pd.setMessage("Subiendo....");
                pd.show();
                String st_Title = Title.getText().toString();
                String st_description = description.getText().toString();
                String st_Textpost = TextPost.getText().toString();
                if (TextUtils.isEmpty(st_Title) || TextUtils.isEmpty(st_description) || TextUtils.isEmpty(st_Textpost)) {
                    Toast.makeText(Post_U_Activity.this, "Rellena los campos", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(st_Title)){
                    Toast.makeText(Post_U_Activity.this, "debes agregar un titulo", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(st_description)){
                    Toast.makeText(Post_U_Activity.this, "debes agregar un descripcion", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(st_Textpost)){
                    Toast.makeText(Post_U_Activity.this, "agrega algo que postear", Toast.LENGTH_SHORT).show();
                }else {
                    addPost(st_Title,st_description,st_Textpost);
                }


            }
        });
    }
    private void addPost(final String Title,final String description,final String TextPost  ){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

        String postid = reference.push().getKey();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("postid", postid);
        hashMap.put("Title", Title);
        hashMap.put("description", description);
        hashMap.put("textPost",TextPost);
        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.child(postid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    pd.dismiss();
                    Intent intent = new Intent(Post_U_Activity.this, StarActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }
        });


    }

}