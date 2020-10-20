package ca.ass3cmpt276.assignment3cmpt276;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import ca.ass3cmpt276.assignment3cmpt276.model.optionsClass;

/*
* Options class that gets called when we click on the options button from the main menu
* Has class variables for the active grid option and active number option, which stores the last clicked on options in the SharedPreferences
* It checks for on click, and initializes the active layout as the one that's selected (grid/number of imposters)
* It stores the high score of each layout in an array, which are linked to the high score reset buttons. and
* Clicking the high score will reset the high score for the said layout
* Same thing with the number of games played, on click resets the total number of games played.
 */
public class OptionsActivity extends AppCompatActivity {

    Button activeGridButton;
    Button activeNumButton;
    private int highScore[] = new int[4];

    private optionsClass options;//singleton class that stores the options
    private final String TAG = "OptionsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        options = optionsClass.getInstance();
        highScore = options.getHighScore();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        initializeBackButton();
        Log.d(TAG, "Options Activity: Has the following values: " + options.getGrid() + " and " + options.getImpostorCount());
        getActiveButtons();
        activeGridButton = initializeGridButtons(activeGridButton);
        activeNumButton = initializeNumberButtons(activeNumButton);
        checkGridButtonOnClick();
        checkNumButtonOnClick();
        setResetButtons();
    }

    private void setResetButtons() {

        final Button highA = findViewById(R.id.highA);
        highA.setText(String.valueOf(options.getHighScore()[0]));
        highA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundOnClick();
                if (options.getHighScore()[0] != 0) {
                    highA.setBackground(getResources().getDrawable(R.drawable.delete_button));
                    highA.setText(String.valueOf(0));
                    options.setHighScore(new int[]{0, highScore[1], highScore[2], highScore[3]});
                } else
                    Toast.makeText(getApplicationContext(), "High score for 4 x 6 is already 0!", Toast.LENGTH_SHORT).show();
            }
        });

        final Button highB = findViewById(R.id.highB);
        highB.setText(String.valueOf(options.getHighScore()[1]));
        highB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundOnClick();
                if (options.getHighScore()[1] != 0) {
                    highB.setBackground(getResources().getDrawable(R.drawable.delete_button));
                    highB.setText(String.valueOf(0));
                    options.setHighScore(new int[]{highScore[0], 0, highScore[2], highScore[3]});
                } else
                    Toast.makeText(getApplicationContext(), "High score for 5 x 10 is already 0!", Toast.LENGTH_SHORT).show();
            }
        });

        final Button highC = findViewById(R.id.highC);
        highC.setText(String.valueOf(options.getHighScore()[2]));
        highC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundOnClick();
                if (options.getHighScore()[2] != 0) {
                    highC.setBackground(getResources().getDrawable(R.drawable.delete_button));
                    highC.setText(String.valueOf(0));
                    options.setHighScore(new int[]{highScore[0], highScore[1], 0, highScore[3]});
                } else
                    Toast.makeText(getApplicationContext(), "High score for 6 x 15 is already 0!", Toast.LENGTH_SHORT).show();
            }
        });

        final Button highD = findViewById(R.id.highD);
        highD.setText(String.valueOf(options.getHighScore()[3]));
        highD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundOnClick();
                if (options.getHighScore()[3] != 0) {
                    highD.setBackground(getResources().getDrawable(R.drawable.delete_button));
                    highD.setText(String.valueOf(0));
                    options.setHighScore(new int[]{highScore[0], highScore[1], highScore[2], 0});
                } else
                    Toast.makeText(getApplicationContext(), "High score for 7 x 18 is already 0!", Toast.LENGTH_SHORT).show();
            }
        });

        final Button gamesPlayed = findViewById(R.id.numGames);
        gamesPlayed.setText(String.valueOf(options.getGamesPlayed()));
        gamesPlayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundOnClick();
                if (options.getGamesPlayed() != 0) {
                    gamesPlayed.setBackground(getResources().getDrawable(R.drawable.delete_button));
                    gamesPlayed.setText(String.valueOf(0));
                    options.setGamesPlayed(0);
                } else
                    Toast.makeText(getApplicationContext(), "Total games played is already 0!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void playSoundOnClick(){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button_click_sound);
        mp.start();
    }
    private void checkNumButtonOnClick() {

        final Button numA = findViewById(R.id.no6);
        numA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundOnClick();
                options.setImpostorCount(6);
                activeNumButton = initializeNumberButtons(activeNumButton);
            }
        });
        final Button numB = findViewById(R.id.no10);
        numB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundOnClick();
                options.setImpostorCount(10);
                activeNumButton = initializeNumberButtons(activeNumButton);
            }
        });
        final Button numC = findViewById(R.id.no15);
        numC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundOnClick();
                options.setImpostorCount(15);
                activeNumButton = initializeNumberButtons(activeNumButton);
            }
        });
        final Button numD = findViewById(R.id.no20);
        numD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundOnClick();
                options.setImpostorCount(20);
                activeNumButton = initializeNumberButtons(activeNumButton);
            }
        });
    }

    private void checkGridButtonOnClick() {
        final Button gridA = findViewById(R.id.x46);
        gridA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundOnClick();
                options.setGrid("a");
                activeGridButton = initializeGridButtons(activeGridButton);
            }
        });
        final Button gridB = findViewById(R.id.x510);
        gridB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundOnClick();
                options.setGrid("b");
                activeGridButton = initializeGridButtons(activeGridButton);
            }
        });
        final Button gridC = findViewById(R.id.x615);
        gridC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundOnClick();
                options.setGrid("c");
                activeGridButton = initializeGridButtons(activeGridButton);
            }
        });
        final Button gridD = findViewById(R.id.x718);
        gridD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundOnClick();
                options.setGrid("d");
                activeGridButton = initializeGridButtons(activeGridButton);
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
                playSoundOnClick();
                editor.putString("gridOption", options.getGrid());
                editor.putInt("countOption", options.getImpostorCount());
                editor.putString("highScore", options.getHighScore()[0] + "," + options.getHighScore()[1] + "," + options.getHighScore()[2] + "," + options.getHighScore()[3]);
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