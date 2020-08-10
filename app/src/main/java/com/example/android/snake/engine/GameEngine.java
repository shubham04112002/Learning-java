package com.example.android.snake.engine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

import com.example.android.snake.classes.Coordinate;
import com.example.android.snake.enums.Direction;
import com.example.android.snake.enums.GameState;
import com.example.android.snake.enums.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {

    /**public void SizeOfScreen(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
    }*/

    public static final int GameWidth = 28;
    public static final int GameHeight =42;
    private List<Coordinate> walls = new ArrayList<>();
    private Random random = new Random();
    private boolean increaseTail = false;
    private List<Coordinate> snake = new ArrayList<>();
    private  List<Coordinate> apples = new ArrayList<>();
    private Direction currentDirection = Direction.East;
    private GameState currentGameState = GameState.Running;
    private Coordinate getSnakeHead(){
        return snake.get(0);
    }

    public GameEngine(){

    }

    public void initGame(){
        AddWalls();
        AddSnake();
        AddApple();
    }

    private void AddApple() {
        Coordinate coordinate = null;
        boolean added = false;
        while (!added){
            int x = 1 + random.nextInt(GameWidth-2);
            int y = 1 + random.nextInt(GameHeight - 2);

            coordinate = new Coordinate(x,y);
            boolean collision = false;
            for(Coordinate s : snake){
                if(s.equals(coordinate)){
                    collision = true;
                  //  break;
                }
            }
            if(collision == true){
                continue;
            }
            for(Coordinate a : apples){
                if(a.equals(coordinate)){
                    collision = true;
                  //  break;
                }
            }
            added = !collision;
        }
          apples.add(coordinate);

    }

    public void updateDirection(Direction newDirection){
        if(Math.abs(newDirection.ordinal()-currentDirection.ordinal()) % 2 == 1){
            currentDirection = newDirection;
        }
    }
    public void update(){
        // update the snake direction
        switch (currentDirection) {
            case North:
                updateSnake(0,-1);
                break;
            case East:
                updateSnake(1,0);
                break;
            case South:
                updateSnake(0,1);
                break;
            case West:
                updateSnake(-1,0);
                break;
        }
        //check the collision with walls
        for(Coordinate w : walls){
            if(snake.get(0).equals(w)){
                currentGameState=GameState.Lost;
            }
        }
        //Check snake collision with itself
        for(int i = 1;i<snake.size();i++ ){

            if (getSnakeHead().equals(snake.get(i))){
                currentGameState = GameState.Lost;
                return;
            }
        }
        // Check apples
        Coordinate appleToRemove = null;
        for (Coordinate apple: apples ){
            if(getSnakeHead().equals(apple)){
                appleToRemove = apple;
                increaseTail = true;
            }
        }
        if (appleToRemove!=null){
            apples.remove(appleToRemove);
            AddApple();
        }
      /**  if(currentGameState == GameState.Lost){
            Restart();
        }*/

    }

    /**private void Restart() {
        initGame();
        getMap();
        apples.remove();
    }*/


    public TileType[][] getMap(){
        TileType[][] map = new TileType[GameWidth][GameHeight];
        for(int x = 0 ;x<GameWidth;x++){
            for(int y = 0 ; y<GameHeight;y++)
                map[x][y]=TileType.Nothing;
        }
       for (Coordinate s: snake)
            map[s.getX()][s.getY()]= TileType.SnakeTail;
       for (Coordinate a: apples)
           map[a.getX()][a.getY()] = TileType.Apple;

        map[(snake.get(0).getX())][snake.get(0).getY()] = TileType.SnakeHead;
        for (Coordinate wall:walls)
            map[wall.getX()][wall.getY()]=TileType.Wall;

        return map;
    }
    private void updateSnake(int x,int y){
        int newX = snake.get(snake.size()-1).getX();
        int newY = snake.get(snake.size()-1).getY();
        for(int i = snake.size()-1;i > 0;i--){
            snake.get(i).setX(snake.get(i-1).getX());
            snake.get(i).setY(snake.get(i-1).getY());
        }
        snake.get(0).setX(snake.get(0).getX()+x);
        snake.get(0).setY(snake.get(0).getY()+y);
        //Increasing the size of the snake after it ate the apple
        if(increaseTail){
            snake.add(new Coordinate(newX,newY));
            increaseTail = false;
        }
    }

    private void AddSnake(){
        snake.clear();
        snake.add(new Coordinate(7,7));
        snake.add(new Coordinate(6,7));
        snake.add(new Coordinate(5,7));
        snake.add(new Coordinate(4,7));
        snake.add(new Coordinate(3,7));
        snake.add(new Coordinate(2,7));
        snake.add(new Coordinate(1,7));

    }

    private void AddWalls() {
        //Top and Bottom Walls
        for (int x = 0;x<GameWidth;x++){
        walls.add(new Coordinate(x,0));// Top wall
        walls.add(new Coordinate(x,GameHeight-1));//y= GameHeight-1 because the overall height of screen
                                                     // has decreased because of Top border #BOTTOM WALL

        }

        //Left and Right Walls
        for(int y = 1;y<GameHeight-1;y++){
            walls.add(new Coordinate(0,y));
            walls.add(new Coordinate(GameWidth-1,y));
        }

    }
    public GameState getCurrentGameState(){
        return currentGameState;    }
}
