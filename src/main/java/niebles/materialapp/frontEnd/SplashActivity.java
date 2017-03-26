package niebles.materialapp.frontEnd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import niebles.materialapp.frontEnd.Profile;

/**
 * Created by user on 25/03/2017.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();
    }
}
