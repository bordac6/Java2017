package H6_2016;

import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TenisFxCvicenie extends Application implements Runnable {
	static PlayGround pg;
	Thread thread;
	boolean gameOver;
	int height = 450;
	int width = 600;
	// suradnice stredu rakety
	static double x1;
	static double x2;
	static double y1;
	static double y2;
	static double raketaSize = 40;
	static double ballX;
	static double ballDX;
	static double ballDY;
	static double ballY;
	static double ballSize;
	static double delta =5;

	@Override
	public void start(Stage primaryStage) {
		pg = new PlayGround(width, height);
		pg.setFocusTraversable(true);
		pg.setOnKeyPressed(event -> {
//			System.out.println(event.getCode());
			switch (event.getCode()) {
			case W:
				y1 -= delta;
				break;
			case S:
				y1 += delta;
				break;
			case A:
				x1 -= delta;
				break;
			case D:
				x1 += delta;
				break;
			case UP:
				y2 -= delta;
				break;
			case DOWN:
				y2 += delta;
				break;
			case LEFT:
				x2 -= delta;
				break;
			case RIGHT:
				x2 += delta;
				break;

			default:
				break;
			}

		});


/*
		pg.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getX() < width/2){
					x1 = event.getX();
					y1 = event.getY();
				}else{
					x2 = event.getX();
					y2 = event.getY();
				}
			}
		});
	*/
		pg.setOnMouseClicked(event -> {
				if(event.getX() < width/2){
					x1 = event.getX();
					y1 = event.getY();
				}else{
					x2 = event.getX();
					y2 = event.getY();
				}
		});


		Scene scene = new Scene(new Pane(pg));
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tenis");
		Thread t= new Thread(this);
		t.start();
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
/*
	public void stop() {
		gameOver = true;
		try {
			thread.join();
		} catch (InterruptedException e) { }
	}
*/
	public void run() {
		System.out.println("started");
		while (!gameOver) {
			// simulacia
			ballX += ballDX;
			ballY += ballDY;

			try {
				thread.sleep(10);
			} catch (Exception e) {
				// TODO: handle exception
			}
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					pg.paint();
				}
			});

		}
		if(gameOver){
			System.out.println("game over");
		}
	}

	class PlayGround extends Canvas {
		public PlayGround(int width, int height) {
			this.setWidth(width);
			this.setHeight(height);
			x1 = width/4;
			y1 = height/2;
			x2 = 3*width/4;
			y2 = height/2;
			ballX = width/2;
			ballY = height/2;
			ballSize = 10;
			setFocusTraversable(true);
			Random rnd = new Random();
			do{
				ballDX = rnd.nextInt(3)-1;
				ballDY = rnd.nextInt(3)-1;
			}while(ballDX == 0 && ballDY == 0);
			System.out.println("je to tam");
		}

		public void paint() {
			//System.out.println("kreslim");
			GraphicsContext gc = getGraphicsContext2D();
			gc.setFill(Color.WHITE);
			gc.fillRect(0, 0, this.getWidth(), this.getHeight());
			gc.setLineWidth(10);
			gc.setStroke(Color.BLACK);
			gc.strokeRect(0, 0, this.getWidth(), this.getHeight());
			gc.strokeLine(this.getWidth()/2, 0, this.getWidth()/2, this.getHeight());
			gc.setStroke(Color.BLUE);
			gc.setLineWidth(10);
			gc.strokeLine(x1, y1-raketaSize/2, x1, y1+raketaSize/2);
			gc.setStroke(Color.RED);
			gc.setLineWidth(10);
			gc.strokeLine(x2, y2-raketaSize/2, x2, y2+raketaSize/2);
			gc.setFill(Color.ORANGE);
			gc.fillOval(ballX-ballSize, ballY-ballSize, ballSize*2, ballSize*2);
		}
	}
}
