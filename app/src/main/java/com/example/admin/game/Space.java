package com.example.admin.game;

        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;

public class Space {
    int x,y;
    int r;
    int width;
    int height;

    public Space(int Width, int Height) {
        width = Width;
        height = Height;
        x = width/2;
        y = height - 70;
        r=60;
    }

    void draw_player(Canvas c)
    {
        Paint ball = new Paint();
        ball.setColor(Color.BLUE);
        c.drawCircle(x, y, r, ball);
    }


    void drag_player(float Dx, boolean downward) {
        //Player moves to the right if you press anywhere on the right half of the screen
        if (downward == true && Dx > width / 2) {
            if(x<=1000) {
                x = x + 30;
            }
        }
        //Player moves to the left if you press anywhere on the left half of the screen
        if (downward == true && Dx < width / 2) {
            if(x>=70) {
                x = x - 30;
            }
        }
    }
}
