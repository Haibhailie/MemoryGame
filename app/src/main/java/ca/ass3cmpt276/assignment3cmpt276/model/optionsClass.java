package ca.ass3cmpt276.assignment3cmpt276.model;

//Singleton class that stores present active grid, impostor count, games played and the high scores for each of the given layouts
public class optionsClass {
    private String grid;
    private int impostorCount;
    private int highScore[];

    private int gamesPlayed;
    private static optionsClass instance;

    private optionsClass(){
        //Private constructor
    }
    public static optionsClass getInstance(){
        if(instance == null){
            instance = new optionsClass();
        }
        return instance;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    public int getImpostorCount() {
        return impostorCount;
    }

    public void setImpostorCount(int impostorCount) {
        this.impostorCount = impostorCount;
    }

    public int[] getHighScore() {
        return highScore;
    }

    public void setHighScore(int[] highScore) {
        this.highScore = highScore;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
}
