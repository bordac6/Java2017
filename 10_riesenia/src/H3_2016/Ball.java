package H3_2016;
import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;


public class Ball extends Group {

    public double r;

    public double x;
    public double y;

    public double vx;
    public double vy;

    private double miny;
    private double maxy;

    public Ball(double radius, double miny, double maxy) {
        super();

        this.r    = radius;
        this.x    = 0;
        this.y    = 0;
        this.vx   = 0;
        this.vy   = 0;
        this.maxy = maxy-r;
        this.miny = miny+r;

        Circle body = new Circle(0, 0, r);
        body.setFill(Color.rgb(255, 255, 255));
        getChildren().add(body);

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


    public double getX() {
        return x;
    }


    public void flip(double v) {
        vx *= -1.00;
        vy += 0.5 * v;
    }


    public void update(double dt) {
        if (y < miny && vy < 0)  vy *= -1;
        if (y > maxy && vy > 0)  vy *= -1;

        x += vx * dt;
        y += vy * dt;

        Platform.runLater(new Runnable() {
            public void run() {
                setTranslateX(x);
                setTranslateY(y);
            }
        });
    }

}
