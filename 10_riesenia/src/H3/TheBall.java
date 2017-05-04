package H3;
public class TheBall {
	double posX, posY, size;
	double deltaX, deltaY;

	public TheBall(double posX, double posY, double size) {
		this.posX = posX;
		this.posY = posY;
		this.size = size;

		deltaX = Math.random()*20 - 10;
		deltaY = Math.random()*20 - 10;
	}

	public void move() {
		this.posX += deltaX;
		this.posY += deltaY;
	}

	public void resetBall(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
		deltaX = Math.random()*20 - 10;
		deltaY = Math.random()*20 - 10;
	}

}
