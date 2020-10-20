package ca.ass3cmpt276.assignment3cmpt276;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/*
* Help screen activity, which is called when we click the help button on the main screen
* Has the sources where we got our media from
* They are hyperlinks, so can be clicked
* Hyperlinking takes place in the strings.xml file and the on clickable is in the layout, all this class does is make them blue
 */
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
                        //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        enableCharacterAnimation();
        enableHyperlinksOnActivity();
        Button backButton = findViewById(R.id.backButton);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_click_sound);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
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

        TextView developerLink = (TextView)findViewById(R.id.aboutDeveloperLink);
        developerLink.setMovementMethod(LinkMovementMethod.getInstance());
        developerLink.setLinkTextColor(Color.BLUE);

        TextView audioLink = (TextView)findViewById(R.id.audioSound);
        audioLink.setMovementMethod(LinkMovementMethod.getInstance());
        audioLink.setLinkTextColor(Color.BLUE);

    }

    public static Intent makeLaunchIntent(Context c) {
        Intent intent = new Intent(c, helpScreen.class);
        return intent;
    }
}