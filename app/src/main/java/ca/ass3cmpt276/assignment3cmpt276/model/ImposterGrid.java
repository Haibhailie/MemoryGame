package ca.ass3cmpt276.assignment3cmpt276.model;

import java.util.Random;

// Singleton Class
public class ImposterGrid {
    private int row;
    private int column;
    private int imposterCount;
    private int grid[][];
    private int imposterPosition[];


    private static ImposterGrid instance;
    public static ImposterGrid getInstance(int row, int column, int imposterCount){
        if(instance == null){
            instance = new ImposterGrid(row, column, imposterCount);
        }
        return instance;
    }

    // private constructor as Singleton Class
    private ImposterGrid(int row, int column, int imposterCount) {
        this.row = row;
        this.column = column;
        this.imposterCount = imposterCount;
        grid = new int[row][column];
        imposterPosition = new int[row * column];
        initializeImposterPosition();
    }


    private void initializeImposterPosition() {
        for (int i = 0; i < (row * column); i++){
            imposterPosition[i] = i;
        }
    }

    public void populateGrid(){
        randomizeImposterPosition();
        int randomRow;
        int randomColumn;
        for(int i = 0; i < imposterCount; i++){
            randomRow = imposterPosition[i] / column;
            randomColumn = imposterPosition[i] % column;

            grid[randomRow][randomColumn] = 1;
        }
    }

    private void randomizeImposterPosition() {
        Random random = new Random();
        int randomIndex;
        int randomValue;

        for(int i = 0; i < (row * column); i++){
            randomIndex = random.nextInt(row * column);

            randomValue = imposterPosition[randomIndex];
            imposterPosition[randomIndex] = imposterPosition[i];
            imposterPosition[i] = randomValue;
        }
    }

}
