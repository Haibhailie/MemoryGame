package ca.ass3cmpt276.assignment3cmpt276;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toolbar;

import ca.ass3cmpt276.assignment3cmpt276.model.ImpostorGrid;

public class OptionsActivity extends AppCompatActivity {
    private static final int DEFAULT_ROW_VALUE = 4;
    private static final int DEFAULT_COLUMN_VALUE = 6;
    private static final int DEFAULT_IMPOSTOR_COUNT = 6;
    private int row;
    private int column;
    private int impostorCount;

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

        setupBackButton();
        setDefaultValue();
        getGridOptions();
        getImpostorCountOption();
    }

    private void setupBackButton() {
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImpostorGrid.getInstance(row, column, impostorCount);
                finish();
            }
        });
    }

    private void setDefaultValue() {
        row = DEFAULT_ROW_VALUE;
        column = DEFAULT_COLUMN_VALUE;
        impostorCount = DEFAULT_IMPOSTOR_COUNT;
    }

    private void getImpostorCountOption() {
        Button no6 = (Button) findViewById(R.id.no6);
        no6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                impostorCount = 6;
            }
        });

        Button no10 = (Button) findViewById(R.id.no10);
        no6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                impostorCount = 10;
            }
        });

        Button no15 = (Button) findViewById(R.id.no15);
        no6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                impostorCount = 15;
            }
        });

        Button no20 = (Button) findViewById(R.id.no20);
        no6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                impostorCount = 20;
            }
        });
    }

    public void getGridOptions(){
        Button x46 = (Button) findViewById(R.id.x46);
        x46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row = 4;
                column = 6;
            }
        });

        Button x510 = (Button) findViewById(R.id.x510);
        x510.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row = 5;
                column = 10;
            }
        });

        Button x615 = (Button) findViewById(R.id.x615);
        x615.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row = 6;
                column = 15;
            }
        });

        Button x718 = (Button) findViewById(R.id.x718);
        x718.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row = 7;
                column = 18;
            }
        });
    }

    public static Intent makeLaunchIntent(Context c) {
        Intent intent = new Intent(c, OptionsActivity.class);
        return intent;
    }
}