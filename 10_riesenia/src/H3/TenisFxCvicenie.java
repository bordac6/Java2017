package H3;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TenisFxCvicenie extends Application implements Runnable {
	static PlayGround pg;
	Thread thread;
	boolean gameOver;

	@Override
	public void start(Stage primaryStage) {
		pg = new PlayGround();

		Scene scene = new Scene(new Pane(pg));
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tenis");
		thread = new Thread(this);
		thread.start();
		primaryStage.show();


		/*Timeline anim = new Timeline(new KeyFrame(
										Duration.millis(50),
										e -> {
											pg.ball.move();
											handleCollisions();
											pg.paint();
										}));
		anim.setCycleCount(Timeline.INDEFINITE);
		anim.play();*/


		final EventHandler<KeyEvent> keyEventHandler =
		  new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				switch (keyEvent.getCode()){
					// player 1
					case W:
						pg.player1Paddle.moveUp();
						break;
					case S:
						pg.player1Paddle.moveDown();
					default:
						break;
				}
				keyEvent.consume();
			}
		};

		scene.setOnKeyPressed(keyEventHandler);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void stop() {
		gameOver = true;
		try {
			thread.join();
		} catch (InterruptedException e) { }
	}

	public void run() {
		System.out.println("started");
		while (!gameOver) {
			// simulacia
			// vykreslenie
			pg.ball.move();
			handleCollisions();
			pg.paint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleCollisions() {
		TheBall ball = pg.ball;

		// top collision
		if ( ((ball.posY - ball.size/2) <= 0) ||
				((pg.ball.posY + ball.size/2) >= pg.height)) {
			ball.deltaY *= -1;
		}

		// side collision
		if ((ball.posX - ball.size/2) <= 0) {
			ball.resetBall(pg.width/2, pg.height/2);
			pg.player2Paddle.score++;
		}

		if ((ball.posX + ball.size/2) >= pg.width) {
			ball.resetBall(pg.width/2, pg.height/2);
			pg.player1Paddle.score++;
		}
	}

	class PlayGround extends Canvas {
		double width;
		double height;

		public PlayerPaddle player1Paddle, player2Paddle;
		public TheBall ball;

		Font scoreFont;
		GraphicsContext gc = getGraphicsContext2D();

		public PlayGround() {
			width = 700.0;
			height = 500.0;
			setWidth(width);
			setHeight(height);

			scoreFont = new Font("Helvetica", 50);

			player1Paddle = new PlayerPaddle(50, height/2, 60);
			player2Paddle = new PlayerPaddle(width - 50, height/2, 60);
			ball = new TheBall(width/2, height/2, 30);
		}

		private void paintPaddle(PlayerPaddle paddle, GraphicsContext gc) {
			gc.moveTo(paddle.posX, paddle.posY - paddle.size/2);
			gc.lineTo(paddle.posX, paddle.posY + paddle.size/2);
			gc.stroke();
		}

		private void paintScore(GraphicsContext gc) {
			gc.setFont(scoreFont);
			// player 1
			gc.strokeText(Integer.toString(player1Paddle.score), width/4, 50);
			// player 2
			gc.strokeText(Integer.toString(player2Paddle.score), width - width/4, 50);
		}

		private void paintBall(GraphicsContext gc) {
			gc.setFill(Color.FIREBRICK);
			gc.fillOval(ball.posX, ball.posY, ball.size/2, ball.size/2);
		}

		public void paint() {
			// System.out.println("W: " + width + " H: " + height);
			gc.clearRect(0, 0, width, height);
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, width, height);

			gc.setStroke(Color.WHITE);
			gc.setLineWidth(5);
			gc.moveTo(width/2, 20);
			gc.lineTo(width/2, height-20);
			gc.stroke();

			paintPaddle(player1Paddle, gc);
			paintPaddle(player2Paddle, gc);
			paintBall(gc);
			paintScore(gc);
		}
	}
}
