package com.example.admin.game;



        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Rect;
        import android.view.MotionEvent;
        import android.view.SurfaceHolder;
        import android.view.SurfaceView;


// This is the ‘‘game engine ’ ’.
public class SpaceView extends SurfaceView implements SurfaceHolder.Callback {
    public SpaceView(Context context) {
        super(context);
        // Notify the SurfaceHolder that you’d like to receive // SurfaceHolder callbacks .
        getHolder().addCallback(this);
        setFocusable(true);
    }

    Space sp;
    SpaceThread st;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Construct game initial state .
        // Launch animator thread .
        sp = new Space(getWidth(), getHeight());
        st = new SpaceThread(this);
        st.start();
    }

    public void draw(Canvas c) {
        c.drawColor(Color.WHITE);
        sp.draw(c);
        //sp.updateright();
        //protected void onDraw(Canvas c) {
        //c.drawColor(Color.RED); // Set the background to black
        //Rect dst = new Rect();
        //dst.set(10, 30, 20, 40); //Set the window to place image from (10,30) to (20,
        //Bitmap mybitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        //c.drawBitmap(mybitmap, null, dst, null); //Draw the bitmap
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Respond to surface changes , e.g. ,

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // The cleanest way to stop a thread is by interrupting it.
        // SpaceThread regularly checks its interrupt flag.
        st.interrupt();
    }

    //float xprevious;
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float xcurrent;

        float Dx;
        float Ux;
        float Mx;

        int action = e.getAction();
        //xcurrent = e.getX();
        //xprevious= xcurrent;
        switch (action) {

            case MotionEvent.ACTION_DOWN:
                Dx = e.getX();
                //sp.down(Dx,xprevious);
                //       xprevious = Dx;
                break;
            case MotionEvent.ACTION_MOVE:
                Mx = e.getX();
                sp.drag(Mx);
                //        sp.drag(Mx,xprevious);
                //          xprevious=Mx;
                break;
            case MotionEvent.ACTION_UP:
                Ux = e.getX();
                //   sp.place(Ux);
                break;
        }

        return true;
    }
}
