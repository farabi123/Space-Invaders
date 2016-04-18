package com.example.admin.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bullet {
    int x,y;
    int r;

    public Bullet(int x_pos) {
        y = 1500;
        x = x_pos;
        r=15;
    }

    void draw_bullet(Canvas c) {
        Paint ball = new Paint();
        ball.setColor(Color.BLACK);
        c.drawCircle(x, y, r, ball);
    }

    void move_bullet() {
        y = y - 50;
    }

    boolean out_of_screen(){
        if (y <= 0) {
            return true;
        } else {
            return false;
        }
    }
}
