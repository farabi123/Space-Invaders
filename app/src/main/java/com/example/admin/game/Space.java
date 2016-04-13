package com.example.admin.game;



        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Rect;
        import android.util.Log;


        import java.util.logging.Logger;


public class Space {
    int width;
    int height;

    public Space(int width, int height) {
        x = width / 2;
        y = 1500;
        vx = 5;
        //  vy = 5;
        this.width = width;
        this.height = height;
        Log.d("Log.DEBUG", "width=" + width + " height=" + height);
    }


    float x, y;
    float vx, vy;


    void draw(Canvas c) {
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        c.drawCircle(x, y, 60, p);
        //Rect dst = new Rect();
        //dst.set(10, 30, 20, 40); //Set the window to place image from (10,30) to (20,
//        Bitmap mybitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
//        c.drawBitmap(mybitmap, null, dst, null); //Draw the bitmap
    }

    void drag(float Dx, boolean downward) {
        if (downward == true && Dx > width / 2) {
            x = x + 10;
        }
        if (downward == true && Dx < width / 2) {
            x = x - 10;
        }
    }
}
