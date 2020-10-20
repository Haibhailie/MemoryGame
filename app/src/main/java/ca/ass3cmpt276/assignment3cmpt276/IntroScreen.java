package ca.ass3cmpt276.assignment3cmpt276;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class IntroScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);
        getSupportActionBar().hide();
        Button startButton = findViewById(R.id.startButton);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_click_sound);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ImageView rotateImage = (ImageView) findViewById(R.id.ImposterLogo);
        Animation rotateAndMove = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_item);
        rotateImage.startAnimation(rotateAndMove);

        final Thread splashThread = new Thread() {
            public void run() {
                try {
                    sleep(8000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mp.start();
            }
        });
        splashThread.start();
    }

    public static Intent makeLaunchIntent(Context c) {
        Intent intent = new Intent(c, IntroScreen.class);
        return intent;
    }

}
