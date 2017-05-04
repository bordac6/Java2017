package H3_2016;
import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;


public class Paddle extends Group {

    public double w;
    public double h;

    public double x;
    public double y;

    public double vx;
    public double vy;

    private double miny;
    private double maxy;

    private double speed;

    public Paddle(double width, double height, double miny, double maxy, double speed) {
        super();

        this.w     = width;
        this.h     = height;
        this.x     = 0;
        this.y     = 0;
        this.vx    = 0;
        this.vy    = 0;
        this.maxy  = maxy-h/2;
        this.miny  = miny+h/2;
        this.speed = speed;

        Line line = new Line(x, y-h/2, x, y+h/2);
        line.setStroke(Color.rgb(255, 255, 192));
        line.setStrokeWidth(w);
        getChildren().add(line);

        update(0);
        
        (new Thread() {
            public void run() {
                while (true) {
                    try { sleep(Main.tick); } catch (InterruptedException E) {}
                    update(0.001*Main.tick);
                    yield();
                }
            }
        }).start();
    }


    public void reset(double x, double y, double vx, double vy) {
        this.x    = x;
        this.y    = y;
        this.vx   = vx;
        this.vy   = vy;

        Platform.runLater(new Runnable() {
            public void run() {
                setTranslateX(x);
                setTranslateY(y);
            }
        });
    }


    public void update(double dt) {
        if (y < miny)  y = miny;
        if (y > maxy)  y = maxy;

        x += vx * dt;
        y += vy * dt;

        Platform.runLater(new Runnable() {
            public void run() {
                setTranslateX(x);
                setTranslateY(y);
            }
        });
    }


    public void pressUp() {
        vy = -speed;
    }


    public void pressDown() {
        vy = +speed;
    }


    public void releaseUp() {
        vy = 0;
    }


    public void releaseDown() {
        vy = 0;
    }

}
