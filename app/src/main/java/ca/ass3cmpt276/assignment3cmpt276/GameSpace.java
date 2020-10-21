package ca.ass3cmpt276.assignment3cmpt276;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.preference.PreferenceManager;
import android.util.Log;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import ca.ass3cmpt276.assignment3cmpt276.model.optionsClass;

/*
* This is the class which maintains the game play of the application (activity_game_space).
* Initializes the 2d array for the grid of impostors, and creates the required layouts in the UI.
* Checks and sets high-scores after game play.
* Creates a winning_screen_fragment (alert dialog) on exit/victory.
 */
public class GameSpace extends AppCompatActivity {

    private static final int DEFAULT_ROW = 4;
    private static final int DEFAULT_COLUMN = 6;
    private static final int DEFAULT_IMPOSTOR_COUNT = 6;
    private int impostorIcon = 0;

    private  Button[][] buttons;
    private  optionsClass optionsClass;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_space);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        initializeValues();
        optionsClass.increaseGamesPlayed();
        optionsClass.reset();
        populateButtons();
        displayStats();
    }

    private void initializeValues() {
        optionsClass = optionsClass.getInstance(DEFAULT_ROW ,
                DEFAULT_COLUMN , DEFAULT_IMPOSTOR_COUNT);
        buttons = new Button[optionsClass.getRow()][optionsClass.getColumn()];
    }

    private void playSoundOnClick(){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.laser_gun);
        mp.start();
    }

    private void vibrateOnClick(long duration){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(duration);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setCancelable(false);
        ab.setTitle(R.string.exit);
        ab.setMessage(R.string.exit_confirm);
        ab.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(GameSpace.this);
                final SharedPreferences.Editor editor = preferences.edit();

                playSoundOnClick();
                editor.putString("gridOption", optionsClass.getGridOption());
                editor.putInt("countOption", optionsClass.getImpostorCount());
                editor.putString("highScore", optionsClass.getHighScore()[0] + "," + optionsClass.getHighScore()[1] + "," + optionsClass.getHighScore()[2] + "," + optionsClass.getHighScore()[3]);
                editor.putInt("timesPlayed", optionsClass.getGamesPlayed());
                editor.commit();
                dialog.dismiss();
                finish();
            }
        });
        ab.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ab.show();
    }

    private void displayStats() {
        TextView textFoundImpostors = (TextView) findViewById(R.id.textFoundImpostors);
        String impostorStatDisplay = getString(R.string.found);
        impostorStatDisplay = impostorStatDisplay.concat(String.valueOf(optionsClass.getImpostorsFound()));
        impostorStatDisplay = impostorStatDisplay.concat(getString(R.string.of));
        impostorStatDisplay = impostorStatDisplay.concat(String.valueOf(optionsClass.getImpostorCount()));
        impostorStatDisplay = impostorStatDisplay.concat(getString(R.string.impostors));

        textFoundImpostors.setText(impostorStatDisplay);

        TextView textScanUsed = (TextView) findViewById(R.id.textScansUsed);
        String scansUsedDisplay = String.valueOf(optionsClass.getScanCount());
        scansUsedDisplay = scansUsedDisplay.concat(getString(R.string.scans_used));

        textScanUsed.setText(scansUsedDisplay);

        TextView gamesPlayed = (TextView) findViewById(R.id.textGamesPlayed);
        String gamesPlayedString = "Games played: ";
        gamesPlayedString = gamesPlayedString.concat(String.valueOf(optionsClass.getGamesPlayed()));
        gamesPlayed.setText(gamesPlayedString);

        TextView highScore = (TextView) findViewById(R.id.textHighScore);
        String highScoreString = "High score: ";
        if(optionsClass.getGridOption().compareTo("a") == 0){
            highScoreString = highScoreString.concat(String.valueOf(optionsClass.getHighScore()[0]));
        }
        else if(optionsClass.getGridOption().compareTo("b") == 0){
            highScoreString = highScoreString.concat(String.valueOf(optionsClass.getHighScore()[1]));
        }
        else if(optionsClass.getGridOption().compareTo("c") == 0){
            highScoreString = highScoreString.concat(String.valueOf(optionsClass.getHighScore()[2]));
        }
        else if(optionsClass.getGridOption().compareTo("d") == 0){
            highScoreString = highScoreString.concat(String.valueOf(optionsClass.getHighScore()[3]));
        }
        highScore.setText(highScoreString);
    }

    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableForMines);

        for (int row = 0; row < optionsClass.getRow(); row ++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for(int col = 0; col < optionsClass.getColumn(); col ++){
                final int FINAL_ROW = row;
                final int FINAL_COL = col;
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setBackgroundResource(R.drawable.button_background);


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(FINAL_ROW, FINAL_COL);
                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void gridButtonClicked(int row, int col) {

        // update all buttons clicked
        Button button = buttons[row][col];
        // Lock button sizes:
        lockButtonSizes();



        int checkAction = optionsClass.onGridClicked(row, col);

        if(checkAction == 1){
            // display impostor
            playSoundOnClick();
            vibrateOnClick(1000);
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), randomImpostorIcon());
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));
            updateAllGrid(row, col);
        }

        else if(checkAction == 0){
            // display number of impostors in rows and columns
            vibrateOnClick(400);
            button.setText(String.valueOf(optionsClass.getImpostorInRowsAndColumns(row, col)));
            button.setTextColor(getResources().getColor(R.color.design_default_color_background, null));
            button.setPadding(0, 0, 0, 0);
        }
        displayStats();
        checkIfWon();
    }

    private int randomImpostorIcon() {
        switch (impostorIcon){
            case 0:
                impostorIcon = (impostorIcon + 1) % 8;
                return R.drawable.blue;
            case 1:
                impostorIcon = (impostorIcon + 1) % 8;
                return R.drawable.cyan;
            case 3:
                impostorIcon = (impostorIcon + 1) % 8;
                return R.drawable.dark_green;
            case 4:
                impostorIcon = (impostorIcon + 1) % 8;
                return R.drawable.red;
            case 5:
                impostorIcon = (impostorIcon + 1) % 8;
                return R.drawable.white;
            case 6:
                impostorIcon = (impostorIcon + 1) % 8;
                return  R.drawable.yellow;
            default:
                impostorIcon = (impostorIcon + 1) % 8;
                return R.drawable.green;
        }
    }

    private void checkHighScores(){

        if(optionsClass.getGridOption().compareTo("a")==0){
            if(optionsClass.getHighScore()[0]==0)
                optionsClass.setHighScore(new int[]{optionsClass.getScanCount(), optionsClass.getHighScore()[1], optionsClass.getHighScore()[2], optionsClass.getHighScore()[3]});
            else if(optionsClass.getScanCount()<optionsClass.getHighScore()[0])
                optionsClass.setHighScore(new int[]{optionsClass.getScanCount(), optionsClass.getHighScore()[1], optionsClass.getHighScore()[2], optionsClass.getHighScore()[3]});
        }

        if(optionsClass.getGridOption().compareTo("b")==0){
            if(optionsClass.getHighScore()[1]==0)
                optionsClass.setHighScore(new int[]{optionsClass.getHighScore()[0], optionsClass.getScanCount(), optionsClass.getHighScore()[2], optionsClass.getHighScore()[3]});
            else if(optionsClass.getScanCount()<optionsClass.getHighScore()[1])
                optionsClass.setHighScore(new int[]{optionsClass.getHighScore()[0], optionsClass.getScanCount(), optionsClass.getHighScore()[2], optionsClass.getHighScore()[3]});
        }

        if(optionsClass.getGridOption().compareTo("c")==0){
            if(optionsClass.getHighScore()[2]==0)
                optionsClass.setHighScore(new int[]{optionsClass.getHighScore()[0], optionsClass.getHighScore()[1], optionsClass.getScanCount(), optionsClass.getHighScore()[3]});
            else if(optionsClass.getScanCount()<optionsClass.getHighScore()[2])
                optionsClass.setHighScore(new int[]{optionsClass.getHighScore()[0], optionsClass.getHighScore()[1], optionsClass.getScanCount(), optionsClass.getHighScore()[3]});
        }

        if(optionsClass.getGridOption().compareTo("d")==0){
            if(optionsClass.getHighScore()[3]==0)
                optionsClass.setHighScore(new int[]{optionsClass.getHighScore()[0], optionsClass.getHighScore()[1], optionsClass.getHighScore()[2], optionsClass.getScanCount()});
            else if(optionsClass.getScanCount()<optionsClass.getHighScore()[3])
                optionsClass.setHighScore(new int[]{optionsClass.getHighScore()[0], optionsClass.getHighScore()[1], optionsClass.getHighScore()[2], optionsClass.getScanCount()});
        }
    }

    private void checkIfWon() {
        if(optionsClass.getImpostorsFound() == optionsClass.getImpostorCount()){
            checkHighScores();
            FragmentManager manager = getSupportFragmentManager();
            winning_screen_fragment dialog = new winning_screen_fragment();
            dialog.show(manager, "winning_screen_dialog");
        }
    }

    private void updateAllGrid(int row, int col) {
        Button button;
        for(int c = 0; c < optionsClass.getColumn(); c ++){
            if(optionsClass.getGridValue(row, c) == 2) {
                button = buttons[row][c];
                button.setText(String.valueOf(optionsClass.getImpostorInRowsAndColumns(row, c)));
                button.setPadding(0, 0, 0, 0);
            }
        }
        for(int r = 0; r < optionsClass.getRow(); r++){
            if(optionsClass.getGridValue(r, col) == 2) {
                button = buttons[r][col];
                button.setText(String.valueOf(optionsClass.getImpostorInRowsAndColumns(r, col)));
                button.setPadding(0, 0, 0, 0);
            }
        }
    }

    private void lockButtonSizes() {
        for(int i = 0; i < optionsClass.getRow(); i++){
            for(int j = 0; j < optionsClass.getColumn(); j++){
                Button button = buttons[i][j];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }
    public static Intent makeLaunchIntent(Context c) {
        Intent intent = new Intent(c, GameSpace.class);
        return intent;
    }
}