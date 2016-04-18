package com.example.admin.game;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Spaceship {
    int x, y;
    int width;
    int height;
    Rect spaceship_rect;

    public Spaceship(int width, int height) {
        x =6000;
        y = 10;
        spaceship_rect = new Rect(width, height, width + 150, height + 150);
        this.width = width;
        this.height = height;
    }

    void draw_spaceship(Canvas c) {
        spaceship_rect = new Rect(x, y, x + 150, y + 150);
    }

    void move_spaceship(){
        x = x + 20;
        if(x > 7000) {
            x=-50;
        }
    }
}