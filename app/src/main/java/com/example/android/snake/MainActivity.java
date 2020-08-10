package com.example.android.snake;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.snake.engine.GameEngine;
import com.example.android.snake.enums.Direction;
import com.example.android.snake.enums.GameState;
import com.example.android.snake.views.SnakeView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private GameEngine gameEngine;
    private SnakeView snakeView;
    private final long updateDelay = 200;
    private final Handler handler = new Handler();
    private float prevX, prevY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameEngine = new GameEngine();
        gameEngine.initGame();
        snakeView = findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);
        startUpdateHandler();
    }

    private void startUpdateHandler() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEngine.update();
                if (gameEngine.getCurrentGameState() == GameState.Running)
                    handler.postDelayed(this, updateDelay);

                if (gameEngine.getCurrentGameState() == GameState.Lost)
                    gameLost();

                snakeView.setSnakeViewMap(gameEngine.getMap());
                snakeView.invalidate();

            }
        }, updateDelay);
    }

    private void gameLost() {
        Toast.makeText(this, "YOU LOST", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float newX = event.getX();
                float newY = event.getY();

                //Calculate where we swiped
                if (Math.abs(newX - prevX) > Math.abs(newY - prevY)) {
                    //Left Or Right Direction
                    if (newX > prevX) {
                        //Right
                        gameEngine.updateDirection(Direction.East);
                    } else {
                        //Left
                        gameEngine.updateDirection(Direction.West);
                    }

                } else { //Up - Down Direction
                    if (newY > prevY)
                        gameEngine.updateDirection(Direction.South);
                    else
                        gameEngine.updateDirection(Direction.North);

                }
                break;

        }
        return true;
    }
}