package com.example.admin.game;

        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
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
    Bullet bu = null;
    Spaceship ship;
    Alien[][] alien_list;
    boolean upward = false;
    boolean downward = false;
    boolean can_fire = true;
    float Dx, Dy;
    int i, j, k;
    int vx=10;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Construct game initial state .
        // Launch animator thread .
        // Allocating memory for alien called instance
        sp = new Space(getWidth(), getHeight());
        ship= new Spaceship(getWidth(), getHeight());
        alien_list = new Alien[5][4];
        for (i = 0; i < 5; i++) {
            for (k = 0; k < 4; k++) {
                alien_list[i][k] = new Alien(getWidth(), getHeight(), (i + 1) * 155, (k + 1) * 155, vx);
            }
        }
        st = new SpaceThread(this);
        st.start();
    }

    public void draw(Canvas c) {
        //---------------------------------Draw the canvas
        c.drawColor(Color.WHITE);
        //---------------------------------Draw the player
        Bitmap spaceship = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        sp.draw_player(c);
        sp.drag_player(Dx, downward);
        //--------------------------------Draw the aliens
        Bitmap alien = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        boolean switched = false;
        for (i = 0; i < 5; i++) {
            for (k = 0; k < 4; k++) {
                if (alien_list[i][k] != null) {
                    c.drawBitmap(alien, null, alien_list[i][k].alien_rect, null); //Draw the bitmap
                    alien_list[i][k].draw_alien(c);
                    alien_list[i][k].alien_move();
                    switched |= alien_list[i][k].must_change_direction();
                }
            }
        }
        if (switched) {
            for (i = 0; i < 5; i++) {
                for (k = 0; k < 4; k++) {
                    if (alien_list[i][k] != null) {
                        alien_list[i][k].alien_switch_direction();
                    }
                }
            }
        }
        //--------------------------------GAME OVER: Aliens collide with player
        for (i = 0; i < 5; i++) {
            for (k = 0; k < 4; k++) {
                if (alien_list[i][k] != null) {
                    if((alien_list[i][k].y + 150)>=1500){
                        System.out.println("GAME OVER !!!!");
                        sp = null;
                        for (i = 0; i < 5; i++) {
                            for (k = 0; k < 4; k++) {
                                alien_list[i][k] = null;
                                System.out.println("No more aliens on the screen");
                            }
                        }
                    }
                }
            }
        }

        //-------------------------------Draw the evil Spaceship
        c.drawBitmap(spaceship, null, ship.spaceship_rect, null); //Draw the bitmap
        ship.move_spaceship();
        ship.draw_spaceship(c);

        //--------------------------------Draw the bullet
        if(bu==null) {
            System.out.println("the Bullet is NULL before drawing the bullet");
        }
        bu.move_bullet();
        bu.draw_bullet(c);

        if (bu.out_of_screen()) {
            can_fire = true;
            bu = null;
        }
        if(bu==null) {
            System.out.println("the Bullet is NULL after drawing the bullet");
        }
        //-------------------------------Shooting an alien

        for (i = 0; i < 5; i++) {
            for (k = 0; k < 4; k++) {
                if (alien_list[i][k] != null) {
                    if ((bu.x >= (alien_list[i][k].x))
                            && (bu.x <= (alien_list[i][k].x + 150))
                            && (bu.y <= (alien_list[i][k].y + 150))) {
                        System.out.println("what is the value of can_fire during alien/bullet intersection:"+can_fire);
                        can_fire = true;
                        bu = null;
                        alien_list[i][k] = null;
                    }
                }
            }
        }

        //-----------------------------Create new batch of alien at the top when they all die, they move faster
        boolean empty = true;
        for (i = 0; i < 5; i++) {
            for (k = 0; k < 4; k++) {
                if (alien_list[i][k] != null) {
                    empty = false;
                    break;
                }
            }
        }
        if (empty) {
            //Increase the speed of the new alien batch:
            vx = vx + 10;
            for (i = 0; i < 5; i++) {
                for (k = 0; k < 4; k++) {
                    alien_list[i][k] = new Alien(getWidth(), getHeight(), (i + 1) * 155, (k + 1) * 155, vx);
                    System.out.println("vx1=" + vx);
                }
            }
        }
        //------------------------------- KILL THE EVIL SPACESHIP

        for (i = 0; i < 5; i++) {
            for (k = 0; k < 4; k++) {
                if (ship != null) {
                    if ((bu.x >= (ship.x))
                            && (bu.x <= (ship.x + 150))
                            && (bu.y <= (ship.y + 150))) {
                        can_fire = true;
                        System.out.println("what is the value of can_fire spaceship dies:"+can_fire);
                        bu = null;
                        ship = null;
                    }
                }
            }
        }

    }//END OF DRAWING


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

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        int action = e.getAction();
        switch (action) {

            case MotionEvent.ACTION_DOWN:
                System.out.println("CLICK DOWN");
                Dx = e.getX();
                Dy = e.getY();
                System.out.println("click down can_fire: "+can_fire);
                if(can_fire) {
                    if ( Dy < getHeight() / 2) {
                        System.out.println("CLICK DOWN to shoot");
                        bu = new Bullet(sp.x);
                        can_fire = false;

                        if(bu!=null) {
                            System.out.println("the Bullet exists DOWN");
                        }
                        }
                    }
                downward = true;
                upward= false;
                break;
            //(This case is not used for anything)
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                upward=true;
                downward=false;
                break;
        }
        if(bu==null) {
            System.out.println("the Bullet is NULL BELOWWWWWW");
        }
        return true;
    }
}
