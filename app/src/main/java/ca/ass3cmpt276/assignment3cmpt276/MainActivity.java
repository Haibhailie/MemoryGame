package ca.ass3cmpt276.assignment3cmpt276;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.StringTokenizer;

import ca.ass3cmpt276.assignment3cmpt276.model.optionsClass;

/*
* This is the main activity class of the game which has the following features:
* Calls the splash screen (intro screen) on startup
* Reads the SharedPreferences and checks if anything has been saved before on the given device prior to this execution
* Has buttons that lead to the game, options and the help screen
* If no SharedPreferences are found, it creates new default values and stores them for first run
 */
public class MainActivity extends AppCompatActivity {

    private String gameGrid;
    private int gameImpostorCount;
    private int highScore[] = new int[4];
    private int gamesPlayed;
    private optionsClass options = optionsClass.getInstance(4, 6, 6);
    private final String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_click_sound);
        final MediaPlayer mpStartGame = MediaPlayer.create(this, R.raw.laser_gun);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        checkSharedPreferences();
        View currentView = this.findViewById(android.R.id.content);

        Intent splash = IntroScreen.makeLaunchIntent(MainActivity.this);
        currentView.getContext().startActivity(splash);


        Button optionsButton = findViewById(R.id.optionsButton);
        Button startButton = findViewById(R.id.startGameButton);
        final Button helpButton = findViewById(R.id.helpButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpStartGame.start();
                Intent i = GameSpace.makeLaunchIntent(MainActivity.this);
                v.getContext().startActivity(i);
            }
        });

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = OptionsActivity.makeLaunchIntent(MainActivity.this);
                mp.start();
                v.getContext().startActivity(i);
                Log.d(TAG, "onCreate: Returned to main activity. From Options");
            }
        });
        Log.d(TAG, "Main Activity: After changing options has the values: " + options.getGridOption() + " and " + options.getImpostorCount());

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = helpScreen.makeLaunchIntent(MainActivity.this);
                mp.start();
                v.getContext().startActivity(i);
            }
        });

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                finish();
            }
        });
    }

    private void setupStartAnimation() {

    }

    private void checkSharedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        //editor.clear();
        //editor.commit();
        String checkPreferences = preferences.getString("gridOption", "Empty");
        if (checkPreferences.compareTo("Empty") == 0) {
            gameGrid = "a";
            gameImpostorCount = 6;
            gamesPlayed = 0;
            highScore = new int[]{0, 0, 0, 0};
            options.setGridOption("a");
            options.setImpostorCount(6);
            options.setGamesPlayed(0);
            options.setHighScore(new int[]{0, 0, 0, 0});
        } else {
            options.setGridOption(preferences.getString("gridOption", "Empty"));
            options.setImpostorCount(preferences.getInt("countOption",0));
            String highScoreString = preferences.getString("highScore","0,0,0,0");
            StringTokenizer st = new StringTokenizer(highScoreString, ",");
            for(int i=0;i<4;i++){
                Log.d(TAG, "Writing high score" + i + " and " + highScoreString);
                highScore[i]=Integer.valueOf(st.nextToken());
            }
            options.setHighScore(highScore);
            options.setGamesPlayed(preferences.getInt("timesPlayed",0));
            gameGrid = options.getGridOption();
            gameImpostorCount = options.getImpostorCount();
            highScore = options.getHighScore();
            gamesPlayed = options.getGamesPlayed();
        }
    }
}
