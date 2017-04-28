package H6;
 
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
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
	NamornikFx namornik;
	NamornikFx namornik2;

	public NamornikCanvas() {
		this.setWidth(sizeX);
		this.setHeight(sizeY);
		this.namornik = new NamornikFx(this);
		this.namornik2 = new NamornikFx(this);
		namornik.druhyNamornik = namornik2;
		namornik2.druhyNamornik = namornik;
		this.namornik.start();
		this.namornik2.start();
		// vytbor namornika
		// spusti jeho simulaciu
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
		if (namornik.alive) {
			gc.drawImage(namornik.actual, namornik.getXPixel(false), namornik.getYPixel(false) );
			// ak sa este neutopil, nakresli obrazok namornika
			//... kresli ziveho namornika
		} else {
			gc.setStroke(Color.BLUE);
			gc.strokeOval(namornik.getXPixel(true) - 25, namornik.getYPixel(true) - 25, 50, 50);
			gc.strokeOval(namornik.getXPixel(true) - 15, namornik.getYPixel(true) - 15, 30, 30);
			gc.strokeOval(namornik.getXPixel(true) - 5, namornik.getYPixel(true) - 5, 10, 10);
			// ak uz je utopeny, nakresli vlny
			//... kresli utopeneho namornika
		}
		if (namornik2.alive) {
			gc.drawImage(namornik2.actual, namornik2.getXPixel(false), namornik2.getYPixel(false) );
			// ak sa este neutopil, nakresli obrazok namornik2a
			//... kresli ziveho namornik2a
		} else {
			gc.setStroke(Color.BLUE);
			gc.strokeOval(namornik2.getXPixel(true) - 25, namornik2.getYPixel(true) - 25, 50, 50);
			gc.strokeOval(namornik2.getXPixel(true) - 15, namornik2.getYPixel(true) - 15, 30, 30);
			gc.strokeOval(namornik2.getXPixel(true) - 5, namornik2.getYPixel(true) - 5, 10, 10);
			// ak uz je utopeny, nakresli vlny
			//... kresli utopeneho namornik2a
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
	static Image rum;
	static Image actual;
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
	NamornikFx druhyNamornik;

	/** pocet krokov namornika */

	public NamornikFx(NamornikCanvas where) {
		/** vytvori namornika v bode (0,0) */
		molo = where; // zapamataj si referenciu na canvas s molom
		// pre istotu synchronizujeme, aby sme obrazok urcite nacitali iba raz
		if (img == null) // ak obrazok este nie je nacitany, nacitaj ho
			img = new Image("namornik.gif");
		if (rum == null) // ak obrazok este nie je nacitany, nacitaj ho
			rum = new Image("namornik_rum.gif");
		actual = img;
		// zaciname v bode (0,0)
		x = 0.0;
		y = 0.0;
	}

	public void run() {
		/** main() metoda pre namornika - nahodny pohyb */
		// kym sa drzi na mole
		Random rnd = new Random();
		long lastStrng = 0;
		while (alive) {
			//...alive sem pride simulacia pohybu namornika
			x += rnd.nextDouble() - 0.5;
			y += rnd.nextDouble() - 0.5;
			if (Math.pow(x, 2) + Math.pow(y, 2) > Math.pow(molo.moloSize,2)){
				alive = false;
			}
			if(Math.pow(x-druhyNamornik.x, 2) + Math.pow(y-druhyNamornik.y, 2)< 2 
					&& System.currentTimeMillis() -lastStrng > 1000){
				actual = rum;
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						molo.paintCanvas();

					}
				});
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lastStrng = System.currentTimeMillis();
				actual = img;
			}
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					molo.paintCanvas();

				}
			});
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
