package ca.ass3cmpt276.assignment3cmpt276;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toolbar;

import model.optionsClass;

public class OptionsActivity extends AppCompatActivity {

    private String gameGrid;
    private int gameImposterCount;
    private int highScore;
    private int gamesPlayed;
    private optionsClass options = optionsClass.getInstance(); //singleton class that stores the options
    private final String TAG = "OptionsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        initializeBackButton();
        getOptionsFromSingleton();

    }

    private void getOptionsFromSingleton(){
        gameGrid = options.getGrid();
        gameImposterCount = options.getImpostorCount();
        highScore = options.getHighScore();
        gamesPlayed = options.getGamesPlayed();
        Log.d(TAG, "getOptionsFromSingleton: Got the following values: "+gameGrid+gameImposterCount+highScore+gamesPlayed);
    }
    private void initializeBackButton(){
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public static Intent makeLaunchIntent(Context c, String grid, int imposterCount, int highScore, int gamesPlayed) {
        Intent intent = new Intent(c, OptionsActivity.class);
        return intent;
    }
}