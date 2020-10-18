package ca.ass3cmpt276.assignment3cmpt276;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class helpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        enableCharacterAnimation();
        enableHyperlinksOnActivity();
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void enableCharacterAnimation(){
        ImageView rotateImage = (ImageView) findViewById(R.id.ImposterLogo);
        ImageView rotateImage2 = (ImageView) findViewById(R.id.ImposterLogoB);
        Animation rotateAndMove = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_item);
        Animation rotateAndMove2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_item_2);
        rotateImage.startAnimation(rotateAndMove);
        rotateImage2.startAnimation(rotateAndMove2);
    }
    private void enableHyperlinksOnActivity(){
        TextView backgroundLink = (TextView)findViewById(R.id.backgroundHyperlink);
        backgroundLink.setMovementMethod(LinkMovementMethod.getInstance());
        backgroundLink.setLinkTextColor(Color.BLUE);

        TextView characterLink = (TextView)findViewById(R.id.characterPNG);
        characterLink.setMovementMethod(LinkMovementMethod.getInstance());
        characterLink.setLinkTextColor(Color.BLUE);

        TextView homeScreenLink = (TextView)findViewById(R.id.homeScreenImage);
        homeScreenLink.setMovementMethod(LinkMovementMethod.getInstance());
        homeScreenLink.setLinkTextColor(Color.BLUE);

    }

    public static Intent makeLaunchIntent(Context c) {
        Intent intent = new Intent(c, helpScreen.class);
        return intent;
    }
}