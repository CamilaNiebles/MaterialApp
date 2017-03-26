package niebles.materialapp.frontEnd;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import niebles.materialapp.R;
import niebles.materialapp.contentProvider.ContractComics;
import com.mikhaellopez.circularimageview.CircularImageView;


public class PersonalProfile extends AppCompatActivity implements View.OnClickListener{

    Uri profilesUri= ContractComics.urisProfile.Content_Uri;

    TextView name,nameUser,mailUser;
    Button button;
    Uri nuevo;

    private FirebaseAuth mAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);

        name=(TextView)findViewById(R.id.welcome);
        nameUser=(TextView)findViewById(R.id.putUserName);
        mailUser=(TextView)findViewById(R.id.putUserMail);
        button=(Button)findViewById(R.id.bGo);
        CircularImageView imageView=(CircularImageView)findViewById(R.id.imagenProfile);

        button.setOnClickListener(this);
        String name=user.getDisplayName();
        String mail=user.getEmail();
        Uri path=user.getPhotoUrl();
        String url=path.toString();

        nameUser.setText(name);
        mailUser.setText(mail);
        Glide.with(PersonalProfile.this).load(url).into(imageView);


    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,Favorites.class);
        startActivity(intent);

    }
}
