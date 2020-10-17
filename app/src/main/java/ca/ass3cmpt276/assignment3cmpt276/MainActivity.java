package ca.ass3cmpt276.assignment3cmpt276;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import android.view.WindowManager;
import android.widget.Button;

import model.optionsClass;

public class MainActivity extends AppCompatActivity {

    private String gameGrid;
    private int gameImposterCount;
    private int highScore;
    private int gamesPlayed;
    private optionsClass options = optionsClass.getInstance();
    private final String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent splashScreen = new Intent(this, IntroScreen.class);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        checkSharedPreferences();
        startActivity(splashScreen);
        Button optionsButton = findViewById(R.id.optionsButton);
        Button startButton = findViewById(R.id.startButton);
        final Button helpButton = findViewById(R.id.helpButton);
        Log.d(TAG, "Main Activity: Have the following values: " + options.getGrid() + " and " + options.getImpostorCount());

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = OptionsActivity.makeLaunchIntent(MainActivity.this);
                v.getContext().startActivity(i);
                Log.d(TAG, "onCreate: Returned to main activity. From Options");
            }
        });
        Log.d(TAG, "Main Activity: After changing options has the values: " + options.getGrid() + " and " + options.getImpostorCount());
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = helpScreen.makeLaunchIntent(MainActivity.this);
                v.getContext().startActivity(i);
            }
        });
    }

    private void checkSharedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        //editor.clear();
        //editor.commit();
        String checkPreferences = preferences.getString("gridOption", "Empty");
        if (checkPreferences.compareTo("Empty") == 0) {
            gameGrid = "a";
            gameImposterCount = 6;
            gamesPlayed = 0;
            highScore = 0;
            options.setGrid("a");
            options.setImpostorCount(6);
            options.setGamesPlayed(0);
            options.setHighScore(0);
        } else {
            options.setGrid(preferences.getString("gridOption", "Empty"));
            options.setImpostorCount(preferences.getInt("countOption",0));
            options.setHighScore(preferences.getInt("highScore",0));
            options.setGamesPlayed(preferences.getInt("timesPlayed",0));
            gameGrid = options.getGrid();
            gameImposterCount = options.getImpostorCount();
            highScore = options.getHighScore();
            gamesPlayed = options.getGamesPlayed();
        }
    }
}
