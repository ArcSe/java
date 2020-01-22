package com.javarush.task.task35.task3513;


import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    protected int score;
    protected int maxTile;
    private Stack<Tile[][]> previousStates = new Stack<>();
    private Stack<Integer> previousScores = new Stack<>();
    private boolean isSaveNeeded = true;

    public void autoMove(){
        PriorityQueue<MoveEfficiency> priorityQueue = new PriorityQueue<>(4, Collections.reverseOrder());
        priorityQueue.offer(getMoveEfficiency(this::left));
        priorityQueue.offer(getMoveEfficiency((this::right)));
        priorityQueue.offer(getMoveEfficiency((this::up)));
        priorityQueue.offer(getMoveEfficiency((this::down)));

        priorityQueue.peek().getMove().move();
    }

    public MoveEfficiency getMoveEfficiency(Move move){
        move.move();
        if (!hasBoardChanged()) {
            rollback();
            return new MoveEfficiency(-1, 0, move);
        }

        int emptyTilesCount = getEmptyTiles().size();

        MoveEfficiency moveEfficiency = new MoveEfficiency(emptyTilesCount, score, move);
        rollback();

        return moveEfficiency;
    }

    public boolean hasBoardChanged(){
        Tile[][] tempList = previousStates.peek();
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles.length; j++) {
                if(gameTiles[i][j].value != tempList[i][j].value){
                    return true;
                }
            }
        }
        return false;
    }

    public void randomMove(){
        int n = ((int) (Math.random() * 100)) % 4;
        switch (n){
            case (0):{
                left();
                break;
            }
            case (1):{
                right();
                break;
            }
            case (2):{
                down();
                break;
            }
            case (3):{
                up();
                break;
            }
        }
    }

    private void saveState(Tile[][] tiles){
        previousScores.push(score);
        Tile[][] saveTiles = new  Tile[gameTiles.length][gameTiles.length];
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j <gameTiles.length; j++) {
                saveTiles[i][j] = new Tile(gameTiles[i][j].value);
            }
        }
        previousStates.push(saveTiles);
        isSaveNeeded = false;
    }

    public void rollback(){
        if(!previousStates.isEmpty()&!previousScores.isEmpty()) {
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }
    }

    public boolean canMove(){
        if(!getEmptyTiles().isEmpty())return true;

        for(int i = 0; i < FIELD_WIDTH; i++) {
            for(int j = 0; j < FIELD_WIDTH-1; j++) {
                if (gameTiles[i][j].value == gameTiles[i][j+1].value) return true;
            }
        }

        for(int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH-1; j++) {
                if(gameTiles[j][i].value == gameTiles[j+1][i].value) return true;
            }
        }
        return false;
    }

    private void turnMatrixRight (){
        Tile[][] tempTile = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles.length; j++) {
                tempTile[i][FIELD_WIDTH - 1 - j] = gameTiles[j][i];
            }
        }
        gameTiles = tempTile.clone();
    }
    public void down(){
        saveState(gameTiles);
        turnMatrixRight();
        left();
        for (int i = 0; i < 3; i++) {
             turnMatrixRight();
        }
    }

    public void up(){
        saveState(gameTiles);
        for (int i = 0; i < 3; i++) {
            turnMatrixRight();
        }
        left();
        turnMatrixRight();

    }

    public void right(){
        saveState(gameTiles);
        for (int i = 0; i < 2; i++) {
            turnMatrixRight();
        }
        left();
        for (int i = 0; i < 2; i++) {
            turnMatrixRight();
        }
        
    }

    public void left(){
        if(isSaveNeeded){
            saveState(gameTiles);
        }
        boolean isMoved = false;
        for (Tile[] gameTile : gameTiles) {
            boolean isCompressed = compressTiles(gameTile);
            boolean isMerged = mergeTiles(gameTile);
            if (isCompressed | isMerged) {
                isMoved = true;
            }
        }
        if(isMoved){
            addTile();
        }
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    private boolean mergeTiles(Tile[] tiles){
        boolean isMerged = false;
        for (int i = 1; i < tiles.length; i++) {
            if((tiles[i-1].value==tiles[i].value)&&(tiles[i].value!=0)){
                tiles[i-1].value *=2;
                tiles[i].value = 0;
                isMerged = true;
                score += tiles[i-1].value;
                if(tiles[i-1].value>maxTile){
                    maxTile = tiles[i-1].value;
                }

            }
            compressTiles(tiles);

        }
        return isMerged;

    }


    private boolean compressTiles(Tile[] tiles) {
        boolean isChanged = false;
        int count = 0;
        for (int j = 0; j < tiles.length; j++) {
            if (!tiles[j].isEmpty()) {
                tiles[count++] = tiles[j];
                if(count-1!=j)
                isChanged = true;
            }
        }
        while (count < tiles.length) {
            tiles[count++] = new Tile();
        }
        return isChanged;

    }

    private void addTile() {
        List<Tile> emptyTile = getEmptyTiles();
        if (emptyTile.size() > 0) {
            int indexTile = (int) (Math.random() * emptyTile.size());
            emptyTile.get(indexTile).value = Math.random() < 0.9 ? 2 : 4;
        }


    }


    private List<Tile> getEmptyTiles() {
        ArrayList<Tile> list = new ArrayList<>();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].isEmpty()) list.add(gameTiles[i][j]);
            }
        }

        return list;
    }

    public Model() {
        resetGameTiles();
    }

    protected void resetGameTiles() {
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }

        }
        addTile();
        addTile();
        score =0;
        maxTile = 0;
    }

}
