package H6;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Jednoducha aplikacia s jednym namornikom, ktory chodi nahodne po kruhovom
 * mole a utopi sa
 */
public class NamornikAppFxCvicenie1 extends Application {

	@Override
	public void start(Stage primaryStage) {
		Pane pane = new Pane(new NamornikCanvas1()); // do panelu vlozime Canvas
		Scene scene = new Scene(pane); // vytvor scenu
		primaryStage.setTitle("Opity namornik"); // pomenuj okno aplikacie,
													// javisko
		primaryStage.setScene(scene); // vloz scenu do hlavneho okna, na javisko
		primaryStage.show(); // zobraz javisko
	}

	public static void main(String[] args) {
		launch(args);
	}
}

class NamornikCanvas1 extends Canvas {
	/** sirka */
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
	int tlelapsed = 0;
	int tlelapsed1 = 0;
	private NamornikFx1 namornik1, namornik2;
	long initTime = System.currentTimeMillis();

	public NamornikCanvas1() {
		this.setWidth(sizeX);
		this.setHeight(sizeY);
		Timeline tl = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			GraphicsContext gc = getGraphicsContext2D(); // kreslenie do canvasu
			tlelapsed++;
			gc.strokeText("Elapsed: " + tlelapsed, 20, 50);
		}));
		tl.setCycleCount(Timeline.INDEFINITE);
		tl.play();

		Thread timer = new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							GraphicsContext gc = getGraphicsContext2D(); // kreslenie
																			// do
																			// canvasu
							tlelapsed1++;
							gc.strokeText("Elapsed: " + tlelapsed1, 20, 80);
						}
					});
				}
			}
		};
		timer.start();

		namornik1 = new NamornikFx1(this, true);
		namornik1.start();
		namornik2 = new NamornikFx1(this, false);
		namornik2.start();
		
		
		
		this.setOnMouseClicked(mouseEvent -> {
			System.out.println("klikol som:" + mouseEvent.getX() + "," + mouseEvent.getY());
			namornik1.pause(mouseEvent.getX(), mouseEvent.getY());
			namornik2.pause(mouseEvent.getX(), mouseEvent.getY());
		});
	}

	public void paintCanvas() {
		GraphicsContext gc = getGraphicsContext2D(); // kreslenie do canvasu
		gc.clearRect(0, 0, sizeX, sizeY);
		gc.setFill(Color.gray(0, 0.2));
		gc.fillOval(centerX - scale * moloSize, centerY - scale * moloSize, scale * 2 * moloSize, scale * 2 * moloSize);
		/**
		 * vykresli namornika na jeho aktualnych suradniciach do grafickej
		 * plochy
		 */
		int elapsed = Math.round((System.currentTimeMillis() - initTime) / 1000);
		gc.strokeText("Elapsed: " + elapsed, 20, 20);
		namornik1.paint(this);
		namornik2.paint(this);
		if (Math.pow(namornik1.x - namornik2.x, 2) + Math.pow(namornik1.y - namornik2.y, 2) <= 10 * 10) {
			//namornik1.isSitting = true; namornik1.sittingTime = 10;
			//namornik2.isSitting = true; namornik2.sittingTime = 20;
		}
	}
}

/**
 * Trieda reprezentuje opiteho namornika, ktory sa nahodne pohybuje po kruhovom
 * mole az kym nespadne do vody. Namornik je vizualizovany obrazkom zo suboru
 * namornik.gif alebo po utopeni sustrednym vlnenim na povrchu hladiny.
 */
class NamornikFx1 extends Thread {
	double x;
	/** poloha namornika - x */
	double y;
	/** poloha namornika - y */
	Image img;
	Image imgSitting;
	/** obrazok namornika - je zdielany medzi vsetkymi namornikmi */
	double stepSize = 1.75;
	/** maximalna velkost kroku namornika v x-ovej a y-ovej zlozke */
	NamornikCanvas1 molo;
	/** referencia na canvas, kde sa namornik tacka */
	boolean alive = true;
	/** ci namornik este nespadol do vody */
	final int waves = 30;
	/** velkost najvacsieho kruhu vlniek po utopeni namornika */
	int nsteps = 0;
	boolean isPaused = false;
	boolean isSitting = false;
	boolean red;
	int sittingTime = 20;

	/** pocet krokov namornika */

	public NamornikFx1(NamornikCanvas1 where, boolean red) {
		/** vytvori namornika v bode (0,0) */
		this.red = red;
		molo = where; // zapamataj si referenciu na canvas s molom
		// pre istotu synchronizujeme, aby sme obrazok urcite nacitali iba raz
		if (img == null) { // ak obrazok este nie je nacitany, nacitaj ho
			if (red)
				img = new Image("namornik.gif");
			else 
				img = new Image("namornik2.gif");
		}
		if (imgSitting == null) { // ak obrazok este nie je nacitany, nacitaj ho
			if (red)
				imgSitting = new Image("namorniks.gif");
			else 
				imgSitting = new Image("namornik2s.gif");
		}
		
		// zaciname v bode (0,0)
		x = 0.0;
		y = 0.0;
	}

	public void run() {
		/** main() metoda pre namornika - nahodny pohyb */
		// kym sa drzi na mole
		while (x * x + y * y <= molo.moloSize * molo.moloSize) {
			// ...alive sem pride simulacia pohybu namornika
			try {
				sleep(200);
			} catch (InterruptedException e) {
			}
			if (isSitting) {
				System.out.println("namornik " + red + " sedi");
				if (sittingTime > 0) {
					sittingTime --;
				} else {
					isSitting = false;
					x += Math.random() * (2 * stepSize - stepSize); // urob nahodny
					// krok
					y += Math.random() * (2 * stepSize - stepSize);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							molo.paintCanvas();
						}
					});					
				}
			} else if (!isPaused) {
				System.out.println("namornik " + red + " zije");
				x += Math.random() * 2 * stepSize - stepSize; // urob nahodny
																// krok
				y += Math.random() * 2 * stepSize - stepSize;
				// a aktualizuj zobrazenie
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						molo.paintCanvas();
					}
				});
			}
			nsteps++;
		}
		System.out.println("spadol do mora");
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
		return molo.centerX + (int) (x * molo.scale) - (center ? 0 : (img.getWidth() / 2));
	}

	/**
	 * konvertuje molove suradnice na pixelove suradnice (y)
	 * 
	 * @param center
	 *            - ci chceme stred namornika (true) alebo jeho lavy horny roh
	 *            (false)
	 */
	public double getYPixel(boolean center) {
		return molo.centerY + (int) (y * molo.scale) - (center ? 0 : (img.getHeight() / 2));
	}
	
	public void pause(double x, double y) {
		if (
				Math.sqrt(
						Math.pow(getXPixel(true) - x,2) + Math.pow(getXPixel(true) - x,2)) < 20
						) {
			isPaused = !isPaused;
		}
	}
	
	public void paint(NamornikCanvas1 where) {
		GraphicsContext gc = where.getGraphicsContext2D();
		if (alive) { // ak sa este neutopil, nakresli obrazok namornika
			if (isSitting) {
				if (imgSitting != null)
					gc.drawImage(imgSitting, getXPixel(false), getYPixel(false));
				else
					System.out.println("null");				
			} else {
				if (img != null)
					gc.drawImage(img, getXPixel(false), getYPixel(false));
				else
					System.out.println("null");
			}
		} else { // ak uz je utopeny, nakresli vlny
			// ... kresli utopeneho namornika
			gc.setStroke(Color.BLACK);
			gc.strokeText("" + nsteps, getXPixel(true), getYPixel(true));
			for(int i = 3; i < 11; i++) {
				gc.setStroke(Color.BLUE);
				gc.strokeOval(
						getXPixel(true)-5*i,
						getYPixel(true)-5*i,
						10*i,
						10*i
						);
			}
		}
		
	}
}
