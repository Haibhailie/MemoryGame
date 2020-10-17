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

import java.util.StringTokenizer;

import model.optionsClass;

public class MainActivity extends AppCompatActivity {

    private String gameGrid;
    private int gameImposterCount;
    private int highScore[] = new int[4];
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
        View currentView = this.findViewById(android.R.id.content);
        Intent splash = IntroScreen.makeLaunchIntent(MainActivity.this);
        currentView.getContext().startActivity(splash);
        //startActivity(splashScreen);
        Button optionsButton = findViewById(R.id.optionsButton);
        Button startButton = findViewById(R.id.startButton);
        final Button helpButton = findViewById(R.id.helpButton);

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

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
            highScore = new int[]{0, 0, 0, 0};
            options.setGrid("a");
            options.setImpostorCount(6);
            options.setGamesPlayed(0);
            options.setHighScore(new int[]{0, 0, 0, 0});
        } else {
            options.setGrid(preferences.getString("gridOption", "Empty"));
            options.setImpostorCount(preferences.getInt("countOption",0));
            String highScoreString = preferences.getString("highScore","0,0,0,0");
            StringTokenizer st = new StringTokenizer(highScoreString, ",");
            for(int i=0;i<4;i++){
                Log.d(TAG, "Writing high score" + i + " and " + highScoreString);
                highScore[i]=Integer.valueOf(st.nextToken());
            }
            options.setHighScore(highScore);
            options.setGamesPlayed(preferences.getInt("timesPlayed",0));
            gameGrid = options.getGrid();
            gameImposterCount = options.getImpostorCount();
            highScore = options.getHighScore();
            gamesPlayed = options.getGamesPlayed();
        }
    }
}
