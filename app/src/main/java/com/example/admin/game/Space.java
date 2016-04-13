package com.example.admin.game;

        import android.graphics.Canvas;
        import android.graphics.Rect;
        import android.util.Log;

public class Space {
    int width;
    int height;
    Rect space_rect;
    public Space(int width, int height) {
        x = width / 2;
        y = 1500;
        space_rect= new Rect( width, height, width+100, height+100);

        this.width = width;
        this.height = height;
        Log.d("Log.DEBUG", "width=" + width + " height=" + height);
    }


    int x, y;

    void draw_spaceship(Canvas c) {
        space_rect= new Rect( x, y, x+100, y+100);
    }

    void drag_spaceship(float Dx, boolean downward) {
        if (downward == true && Dx > width / 2) {
            x = x + 10;
        }
        if (downward == true && Dx < width / 2) {
            x = x - 10;
        }

    }
}
