package ca.ass3cmpt276.assignment3cmpt276;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import ca.ass3cmpt276.assignment3cmpt276.model.ImpostorGrid;

public class GameSpace extends AppCompatActivity {

    private static final int NUM_ROWS = 4;
    private static final int NUM_COLS = 6;
    private static final int DEFAULT_ROW = 4;
    private static final int DEFAULT_COLUMN = 6;
    private static final int DEFAULT_IMPOSTOR_COUNT = 6;
    private int impostorIcon = 0;

    Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
    ImpostorGrid impostorGrid;
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
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        impostorGrid = ImpostorGrid.getInstance(DEFAULT_ROW ,
                DEFAULT_COLUMN , DEFAULT_IMPOSTOR_COUNT);
        impostorGrid.reset();
        populateButtons();
        displayStats();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setCancelable(false);
        ab.setTitle("Exit");
        ab.setMessage("Are you sure to exit?");
        ab.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        ab.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ab.show();
    }

    private void displayStats() {
        TextView textFoundImpostors = (TextView) findViewById(R.id.textFoundImpostors);
        textFoundImpostors.setText("Found " + impostorGrid.getImpostorsFound() + " of " +
                impostorGrid.getImpostorCount() + " Impostors");

        TextView textScanUsed = (TextView) findViewById(R.id.textScansUsed);
        textScanUsed.setText("" + impostorGrid.getScanCount() + " Scans used");
    }

    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableForMines);

        for (int row = 0; row < NUM_ROWS; row ++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for(int col = 0; col < NUM_COLS; col ++){
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
        int checkAction = impostorGrid.onGridClicked(row, col);

        if(checkAction == 1){
            // display impostor
            // vibrate and ping sound
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
            button.setText(""+ impostorGrid.getImpostorInRowsAndColumns(row, col));
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

    private void checkIfWon() {
        if(impostorGrid.getImpostorsFound() == impostorGrid.getImpostorCount()){
            AlertDialog.Builder ab = new AlertDialog.Builder(this);
            ab.setCancelable(false);
            ab.setTitle("Congratulations! Your score is " + impostorGrid.getScanCount());
            ab.setMessage("Return to Home Screen? ");
            ab.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            ab.setNegativeButton("Play Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    impostorGrid.reset();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });

            ab.show();
        }
    }

    private void updateAllGrid(int row, int col) {
        Button button;
        for(int c = 0; c < NUM_COLS; c ++){
            if(impostorGrid.getGridValue(row, c) == 2) {
                button = buttons[row][c];
                button.setText("" + impostorGrid.getImpostorInRowsAndColumns(row, c));
                button.setPadding(0, 0, 0, 0);
            }
        }
        for(int r = 0; r < NUM_ROWS; r++){
            if(impostorGrid.getGridValue(r, col) == 2) {
                button = buttons[r][col];
                button.setText("" + impostorGrid.getImpostorInRowsAndColumns(r, col));
                button.setPadding(0, 0, 0, 0);
            }
        }
    }

    private void lockButtonSizes() {
        for(int i = 0; i < NUM_ROWS; i++){
            for(int j = 0; j < NUM_COLS; j++){
                Button button = buttons[i][j];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getWidth();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }


}