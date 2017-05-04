package H3;
public class PlayerPaddle {
	double posX, posY, size;
	int score;

	double delta = 10;

	public PlayerPaddle(double posX, double posY, double size) {
		this.posX = posX;
		this.posY = posY;
		this.size = size;
		score = 0;
	}

	public void moveUp() {
		posY -= delta;
	}
	public void moveDown() {
		posY += delta;
	}
}
