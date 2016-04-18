package com.example.admin.game;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Alien {
    int width;
    int height;
    Rect alien_rect;
    int x, y;
    int vx;

    public Alien(int width, int height, int x, int y, int vx) {
        this.vx = vx;
        this.x = x;
        this.y = y;
        alien_rect = new Rect(x, y, x + 150, y + 150);

        this.width = width;//this is local
        this.height = height;
    }

    void draw_alien(Canvas c) {
        alien_rect = new Rect(x, y, x + 150, y + 150);
    }

    void alien_move() {
            x = x + vx;
    }

//Detecting if alien  should switch its direction or not
    boolean must_change_direction() {
        if (x >= 920 || x <= 0) {
            return true;
        } else {
            return false;
        }
    }

//Alien switches its direction of motion and shifts down
    void alien_switch_direction() {
        vx = -vx;
        y = y + 50;
    }
}
