package com.example.android.snake.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.Nullable;

import com.example.android.snake.enums.TileType;

public class SnakeView extends View {
    private Paint paint = new Paint();
    private TileType  snakeViewMap[][];
    public SnakeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public void setSnakeViewMap(TileType[][] map){this.snakeViewMap = map;}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(snakeViewMap !=null) {
            float TileSizeX = canvas.getWidth() / snakeViewMap.length;
            float TileSizeY = canvas.getHeight() / snakeViewMap[0].length;
            float circleSize = Math.min(TileSizeX, TileSizeY) /2;
            for(int x = 0 ;x<snakeViewMap.length;x++){
                for(int y = 0; y < snakeViewMap[x].length;y++){
                    switch (snakeViewMap[x][y]) {

                        case Nothing:
                            paint.setColor(Color.WHITE);
                            break;
                        case Wall:
                            paint.setColor(Color.GRAY);
                            break;
                        case SnakeHead:
                            paint.setColor(Color.BLACK);
                            break;
                        case SnakeTail:
                            paint.setColor(Color.MAGENTA);
                            break;
                        case Apple:
                            paint.setColor(Color.RED);
                            break;
                    }
                canvas.drawCircle(x*TileSizeX + TileSizeX/2f +circleSize/2,y*TileSizeY + TileSizeY/2f +circleSize/2,circleSize,paint);
            }

            }

        }
    }
}
