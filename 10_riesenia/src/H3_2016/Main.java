package H3_2016;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {

    public static final int tick   = 10;
    public static final int width  = 800;
    public static final int height = 600;
    public static final int border = 50;

    private Random R = new Random();
    private Ball B = new Ball(10, 0+border, height-border);
    private Paddle P1 = new Paddle(10, 70, 0+border, height-border, 300);
    private Paddle P2 = new Paddle(10, 70, 0+border, height-border, 300);

    private boolean frozen = false;
    private int score1 = 0;
    private int score2 = 0;


    private void freeze() {
        frozen = true;
        
        B.reset(-100, -100, 0, 0);

        P1.reset(border*2, height/2, 0, 0);
        P2.reset(width-2*border, height/2, 0, 0);
    }


    private void reset() {
        frozen = false;

        double d = 300 + 100 * R.nextDouble();
        double a = Math.PI * (R.nextDouble()*0.25 + R.nextInt(2)*1 + 0.375);
        double vx = Math.sin(a) * d;
        double vy = Math.cos(a) * d;

        B.reset(width/2, height/2, vx, vy);
    }


    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, width, height, Color.rgb(63, 63, 63));
        primaryStage.setScene(scene);

        Rectangle box = new Rectangle(width-border*2, height-border*2, Color.rgb(0, 0, 0));
        box.setX(border);
        box.setY(border);
        root.getChildren().add(box);

        Line line = new Line(width/2, border*2, width/2, height-border*2);
        line.setStroke(Color.rgb(127, 127, 127));
        line.setStrokeWidth(3);
        root.getChildren().add(line);

        root.getChildren().add(B);

        root.getChildren().add(P1);
        root.getChildren().add(P2);

        Text text1 = new Text(width/4, height/4, Integer.toString(score1));
        text1.setFont(new Font(36));
        text1.setFill(Color.rgb(192, 192, 192));
        root.getChildren().add(text1);

        Text text2 = new Text(3*width/4, height/4, Integer.toString(score2));
        text2.setFont(new Font(36));
        text2.setFill(Color.rgb(192, 192, 192));
        root.getChildren().add(text2);

        freeze();
        reset();

        primaryStage.show();

        scene.setOnKeyPressed(ev -> {
            if (!frozen) switch (ev.getCode().toString()) {
                case "W":    P1.pressUp();   break;
                case "S":    P1.pressDown(); break;
                case "UP":   P2.pressUp();   break;
                case "DOWN": P2.pressDown(); break;
            }
        });

        scene.setOnKeyReleased(ev -> {
            switch (ev.getCode().toString()) {
                case "W":    P1.releaseUp();   break;
                case "S":    P1.releaseDown(); break;
                case "UP":   P2.releaseUp();   break;
                case "DOWN": P2.releaseDown(); break;
            }
        });

        (new Thread() {
            public void run() {
                while (true) {
                    try { Thread.sleep(tick); } catch (Exception E) {}

                    if (B.x > width) {
                        score1++;
                        Platform.runLater(new Runnable() {
                            public void run() {
                                text1.setText(Integer.toString(score1));
                            }
                        });
                        freeze();
                        try { Thread.sleep(500); } catch (Exception E) {}
                        reset();
                    }

                    if (B.x < 0) {
                        score2++;
                        Platform.runLater(new Runnable() {
                            public void run() {
                                text2.setText(Integer.toString(score2));
                            }
                        });
                        freeze();
                        try { Thread.sleep(500); } catch (Exception E) {}
                        reset();
                    }

                    if ((B.y-B.r < P1.y+P1.h/2) &&
                        (B.y+B.r > P1.y-P1.h/2) &&
                        (B.x-B.r < P1.x+P1.w/2) &&
                        (B.x+B.r > P1.x-P1.w/2) &&
                        (B.vx < 0)) {
                        B.flip(P1.vy);
                    }

                    if ((B.y-B.r < P2.y+P2.h/2) &&
                        (B.y+B.r > P2.y-P2.h/2) &&
                        (B.x+B.r > P2.x+P2.w/2) &&
                        (B.x-B.r < P2.x-P2.w/2) &&
                        (B.vx > 0)) {
                        B.flip(P2.vy);
                    }

                    Thread.yield();
                }
            }
        }).start();
    }

}
