package H3;

import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Jednoducha aplikacia s jednym namornikom, ktory chodi nahodne po kruhovom mole a
 * utopi sa
 */
public class NamornikAppFxCvicenie extends Application {

	@Override
	public void start(Stage primaryStage) {
		Pane pane = new Pane(new NamornikCanvas()); // do panelu vlozime Canvas
		Scene scene = new Scene(pane);				// vytvor scenu
		primaryStage.setTitle("Opity namornik"); 	// pomenuj okno aplikacie,
													// javisko
		primaryStage.setScene(scene); 	// vloz scenu do hlavneho okna, na javisko
		primaryStage.show(); 			// zobraz javisko
	}

	public static void main(String[] args) {
		launch(args);
	}
}

class NamornikCanvas extends Canvas {
	/** sirka*/
	int sizeX = 600;
	/** vyska */
	int sizeY = 600;
	/** suradnice stredu mola (x) */
	int centerX = sizeX / 2;
	/** suradnice stredu mola (y) */
	int centerY = sizeY / 2;
	/** skalovaci koeficient na prepocitavanie molovych a pixlovych suradnic */
	int scale = 25;
	/** velkost mola v molovych suradniciach */
	int moloSize = 10;
	/** na mole chodi jeden namornik */
	private NamornikFx namornik;
	private NamornikFx namornik2;


	public NamornikCanvas() {
		this.setWidth(sizeX);
		this.setHeight(sizeY);
		this.namornik = new NamornikFx(this);
		this.namornik.start();
		this.namornik2 = new NamornikFx(this);
		this.namornik2.start();

		(new Thread() {
			public void run() {
				while (namornik.alive || namornik2.alive) {
					paintCanvas();
					if(Math.pow(namornik.x-namornik2.x, 2) + Math.pow(namornik.y-namornik2.y, 2) < 9){
						if(namornik.wait < -5 && namornik2.wait < -5){
							namornik.wait = 10;
							namornik2.wait = 10;
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				paintCanvas();
			}
		}).start();
	}

	public void paintCanvas() {
		GraphicsContext gc = getGraphicsContext2D();	// kreslenie do canvasu
		gc.clearRect(0, 0, sizeX, sizeY);
		gc.setFill(Color.gray(0, 0.2));
		gc.fillOval(centerX - scale * moloSize, centerY - scale * moloSize,
					scale * 2 * moloSize, scale * 2 * moloSize);
		/**
		 * vykresli namornika na jeho aktualnych suradniciach do grafickej
		 * plochy
		 */
		if (namornik.alive) { // ak sa este neutopil, nakresli obrazok namornika
			gc.drawImage(namornik.img, namornik.getXPixel(true), namornik.getYPixel(true));
		}
		if (namornik2.alive) { // ak sa este neutopil, nakresli obrazok namornika
			gc.drawImage(namornik2.img, namornik2.getXPixel(true), namornik2.getYPixel(true));
		}
	}
}

/**
 * @author PP
 */

/**
 * Trieda reprezentuje opiteho namornika, ktory sa nahodne pohybuje po kruhovom
 * mole az kym nespadne do vody. Namornik je vizualizovany obrazkom zo suboru
 * namornik.gif alebo po utopeni sustrednym vlnenim na povrchu hladiny.
 */
class NamornikFx extends Thread {
	double x;
	/** poloha namornika - x */
	double y;
	/** poloha namornika - y */
	static Image img;
	/** obrazok namornika - je zdielany medzi vsetkymi namornikmi */
	double stepSize = 1.75;
	/** maximalna velkost kroku namornika v x-ovej a y-ovej zlozke */
	NamornikCanvas molo;
	/** referencia na canvas, kde sa namornik tacka */
	boolean alive = true;
	/** ci namornik este nespadol do vody */
	final int waves = 30;
	/** velkost najvacsieho kruhu vlniek po utopeni namornika */
	int nsteps = 0;

	int wait = 0;

	/** pocet krokov namornika */

	public NamornikFx(NamornikCanvas where) {
		/** vytvori namornika v bode (0,0) */
		molo = where; // zapamataj si referenciu na canvas s molom
		// pre istotu synchronizujeme, aby sme obrazok urcite nacitali iba raz
		if (img == null) // ak obrazok este nie je nacitany, nacitaj ho
			img = new Image("namornik.gif");
		// zaciname v bode (0,0)
		Random rand = new Random();
		x = rand.nextInt(molo.moloSize) - molo.moloSize / 2;
		y = rand.nextInt(molo.moloSize) - molo.moloSize / 2;
	}

	public void run() {
		/** main() metoda pre namornika - nahodny pohyb */

		Random rand = new Random();
		// kym sa drzi na mole
		while (x*x + y*y < molo.moloSize * molo.moloSize) {
			if (wait <= 0){
				double d = rand.nextDouble();
				double a = rand.nextDouble()*2*Math.PI;
				x += d * Math.sin(a);
				y += d * Math.cos(a);
			}
			wait--;
			try{
				this.sleep(200);
			}
			catch(Exception e){
			}
			nsteps++;
		}
		// ak spadol z mola, nie je nazive
		alive = false;
	}

	/**
	 * konvertuje molove suradnice na pixelove suradnice (x)
	 *
	 * @param center
	 *            - ci chceme stred namornika (true) alebo jeho lavy horny roh
	 *            (false)
	 */
	public double getXPixel(boolean center) {
		return molo.centerX + (int) (x * molo.scale)
				- (center ? 0 : (img.getWidth() / 2));
	}

	/**
	 * konvertuje molove suradnice na pixelove suradnice (y)
	 *
	 * @param center
	 *            - ci chceme stred namornika (true) alebo jeho lavy horny roh
	 *            (false)
	 */
	public double getYPixel(boolean center) {
		return molo.centerY + (int) (y * molo.scale)
				- (center ? 0 : (img.getHeight() / 2));
	}
}
