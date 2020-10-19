package ca.ass3cmpt276.assignment3cmpt276.model;

import java.util.Random;

// Singleton Class
public class ImpostorGrid {
    private int row;
    private int column;
    private int impostorCount;
    private int grid[][];
    private int impostorPosition[];
    private int scanCount;
    private int impostorsFound;


    private static ImpostorGrid instance;
    public static ImpostorGrid getInstance(int row, int column, int impostorCount){
        if(instance == null){
            instance = new ImpostorGrid(row, column, impostorCount);
        }
        return instance;
    }


    // private constructor as Singleton Class
    private ImpostorGrid(int row, int column, int impostorCount) {
        this.row = row;
        this.column = column;
        this.impostorCount = impostorCount;
        scanCount = 0;
        impostorsFound = 0;
        grid = new int[row][column];
        impostorPosition = new int[row * column];
        initializeImpostorPosition();
        initializeGrid();
        populateGrid();
    }

    private void initializeGrid() {
        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                grid[i][j] = 0;
            }
        }
    }

    public int getScanCount(){
        return scanCount;
    }

    public int getImpostorsFound(){
        return impostorsFound;
    }

    public int getImpostorCount(){
        return impostorCount;
    }

    private void initializeImpostorPosition() {
        for (int i = 0; i < (row * column); i++){
            impostorPosition[i] = i;
        }
    }

    public void populateGrid(){
        randomizeImpostorPosition();
        int randomRow;
        int randomColumn;
        for(int i = 0; i < impostorCount; i++){
            randomRow = impostorPosition[i] / column;
            randomColumn = impostorPosition[i] % column;

            grid[randomRow][randomColumn] = 1;
        }
    }

    private void randomizeImpostorPosition() {
        Random random = new Random();
        int randomIndex;
        int randomValue;

        for(int i = 0; i < (row * column); i++){
            randomIndex = random.nextInt(row * column);

            randomValue = impostorPosition[randomIndex];
            impostorPosition[randomIndex] = impostorPosition[i];
            impostorPosition[i] = randomValue;
        }
    }

    public int onGridClicked(int row, int column){
        // impostor selected
        if(grid[row][column] == 1){
            grid[row][column] = 0;
            impostorsFound++;
            return 1;
        }
        // scan used
        if(grid[row][column] == 0) {
            grid[row][column] = 2;
            scanCount++;
            return 0;
        }
        return grid[row][column];
    }

    public int getGridValue(int row, int column){
        return grid[row][column];
    }

    public int getImpostorInRowsAndColumns(int r, int c) {
        int count = 0;
        for(int i = 0; i < column; i++){
            if(grid[r][i] == 1){
                count++;
            }
        }
        for(int i = 0; i < row; i++){
            if(grid[i][c] == 1){
                count++;
            }
        }
        return count;
    }
}
