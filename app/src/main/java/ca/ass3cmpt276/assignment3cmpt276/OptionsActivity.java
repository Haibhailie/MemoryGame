package ca.ass3cmpt276.assignment3cmpt276;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toolbar;

import model.optionsClass;

public class OptionsActivity extends AppCompatActivity {

    Button activeGridButton;
    Button activeNumButton;

    private optionsClass options;//singleton class that stores the options
    private final String TAG = "OptionsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        options=optionsClass.getInstance();
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
        Log.d(TAG, "Options Activity: Has the following values: "+options.getGrid()+" and "+options.getImpostorCount());
        getActiveButtons();
        activeGridButton=initializeGridButtons(activeGridButton);
        activeNumButton=initializeNumberButtons(activeNumButton);
        checkGridButtonOnClick();
        checkNumButtonOnClick();
    }

    private void checkNumButtonOnClick(){

        final Button numA = findViewById(R.id.no6);
        numA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options.setImpostorCount(6);
                activeNumButton=initializeNumberButtons(activeNumButton);
            }
        });
        final Button numB = findViewById(R.id.no10);
        numB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options.setImpostorCount(10);
                activeNumButton=initializeNumberButtons(activeNumButton);
            }
        });
        final Button numC = findViewById(R.id.no15);
        numC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options.setImpostorCount(15);
                activeNumButton=initializeNumberButtons(activeNumButton);
            }
        });
        final Button numD = findViewById(R.id.no20);
        numD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options.setImpostorCount(20);
                activeNumButton=initializeNumberButtons(activeNumButton);
            }
        });
    }

    private void checkGridButtonOnClick(){
        final Button gridA = findViewById(R.id.x46);
        gridA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options.setGrid("a");
                activeGridButton=initializeGridButtons(activeGridButton);
            }
        });
        final Button gridB = findViewById(R.id.x510);
        gridB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options.setGrid("b");
                activeGridButton=initializeGridButtons(activeGridButton);
            }
        });
        final Button gridC = findViewById(R.id.x615);
        gridC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options.setGrid("c");
                activeGridButton=initializeGridButtons(activeGridButton);
            }
        });
        final Button gridD = findViewById(R.id.x718);
        gridD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options.setGrid("d");
                activeGridButton=initializeGridButtons(activeGridButton);
            }
        });
    }

    private void getActiveButtons() {

        if (options.getGrid().compareTo("a") == 0) {
            activeGridButton = findViewById(R.id.x46);
        } else if (options.getGrid().compareTo("b") == 0) {
            activeGridButton = findViewById(R.id.x510);
        } else if (options.getGrid().compareTo("c") == 0) {
            activeGridButton = findViewById(R.id.x615);
        } else if (options.getGrid().compareTo("d") == 0) {
            activeGridButton = findViewById(R.id.x718);
        }

        if (options.getImpostorCount() == 6) {
            activeNumButton = findViewById(R.id.no6);
        } else if (options.getImpostorCount() == 10) {
            activeNumButton = findViewById(R.id.no10);
        } else if (options.getImpostorCount() == 15) {
            activeNumButton = findViewById(R.id.no15);
        } else if (options.getImpostorCount() == 20) {
            activeNumButton = findViewById(R.id.no20);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private Button initializeGridButtons(Button activeGridButton) {

        activeGridButton.setBackground(getResources().getDrawable(R.drawable.button_background));
        Button gridA = findViewById(R.id.x46);
        Button gridB = findViewById(R.id.x510);
        Button gridC = findViewById(R.id.x615);
        Button gridD = findViewById(R.id.x718);

        if (options.getGrid().compareTo("a") == 0) {
            gridA.setBackground(getResources().getDrawable(R.drawable.focused_button));
            activeGridButton = gridA;
        } else if (options.getGrid().compareTo("b") == 0) {
            gridB.setBackground(getResources().getDrawable(R.drawable.focused_button));
            activeGridButton = gridB;
        } else if (options.getGrid().compareTo("c") == 0) {
            gridC.setBackground(getResources().getDrawable(R.drawable.focused_button));
            activeGridButton = gridC;
        } else if (options.getGrid().compareTo("d") == 0) {
            gridD.setBackground(getResources().getDrawable(R.drawable.focused_button));
            activeGridButton = gridD;
        }
        return activeGridButton;
    }

    private Button initializeNumberButtons(Button activeNumButton) {

        activeNumButton.setBackground(getResources().getDrawable(R.drawable.button_background));
        Button numA = findViewById(R.id.no6);
        Button numB = findViewById(R.id.no10);
        Button numC = findViewById(R.id.no15);
        Button numD = findViewById(R.id.no20);

        if (options.getImpostorCount() == 6) {
            numA.setBackground(getResources().getDrawable(R.drawable.focused_button));
            activeNumButton = numA;
        } else if (options.getImpostorCount() == 10) {
            numB.setBackground(getResources().getDrawable(R.drawable.focused_button));
            activeNumButton = numB;
        } else if (options.getImpostorCount() == 15) {
            numC.setBackground(getResources().getDrawable(R.drawable.focused_button));
            activeNumButton = numC;
        } else if (options.getImpostorCount() == 20) {
            numD.setBackground(getResources().getDrawable(R.drawable.focused_button));
            activeNumButton = numD;
        }
        return activeNumButton;
    }

    private void initializeBackButton() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("gridOption", options.getGrid());
                editor.putInt("countOption", options.getImpostorCount());
                editor.putInt("highScore", options.getHighScore());
                editor.putInt("timesPlayed", options.getGamesPlayed());
                editor.commit();
                finish();
            }
        });
    }

    public static Intent makeLaunchIntent(Context c) {
        Intent intent = new Intent(c, OptionsActivity.class);
        return intent;
    }
}