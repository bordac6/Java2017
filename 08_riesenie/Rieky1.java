import java.util.Random;

public class Rieky1
{	
	public class Rieka extends Thread
	{
		private int index;		
		public Rieka(int indexRieky)
		{
			index = indexRieky;
		}
		
		private boolean jeZrazka()
		{
			if(this.index+1<pocetRiek)
				if(rieky[this.index+1]==rieky[this.index]){					
					return true;
				}
			return false;
		}
		
		@Override
		public void run(){			
			Random r = new Random();
			do{
				try {
					sleep(r.nextInt(500)+300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int tempPosun = rieky[this.index]+r.nextInt(3)-1;
				if((tempPosun<sirka) && (tempPosun>=0))
					rieky[this.index]= tempPosun;				
			} while(!jeZrazka() && (dozivaciCounter>0));
			
			prezivsieRieky[this.index] = false;
			
			while(dozivaciCounter>0){
				try {
					sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				rieky[this.index]=rieky[this.index+1];
			}
		}
	}
	
	private int[] rieky;
	private Rieka[] threadyRiek; 
	private int sirka;
	private int pocetRiek;
	private boolean[] prezivsieRieky;
	private int dozivaciCounter;
	
	public Rieky1(int Sirka, int pocetRiek, int pocetKol){
		this.dozivaciCounter = 10;
		this.sirka = Sirka;
		this.pocetRiek = pocetRiek;
		this.rieky = new int[this.pocetRiek];
		int posun = Sirka / pocetRiek;
		int offset = posun / 2;
		for(int i = 0; i < this.pocetRiek; ++i){			
			rieky[i]=offset;
			offset+=posun;
		}
		threadyRiek = new Rieka[pocetRiek];
		prezivsieRieky = new boolean[pocetRiek];
		for(int i = 0; i < pocetRiek; ++i){
			threadyRiek[i] = new Rieka(i);			
			prezivsieRieky[i] = true;
		}
	}
	
	public static void main(String[] args)  throws Exception 
	{
		new Rieky1(15, 3, 10).simuluj();
	}
		
	public void simuluj() throws Exception
	{		
		for(int i = 0; i < pocetRiek; ++i){
			threadyRiek[i].start();
		}
		
		StringBuilder riadok = new StringBuilder(this.sirka);
		riadok.setLength(this.sirka);		
		while(dozivaciCounter>0){
			for(int j = 0; j < this.sirka; ++j){				
				riadok.setCharAt(j, ' ');				
			}
			int pocetTecucich = this.pocetRiek; 
			for(int i = 0; i < pocetRiek; ++i){
				riadok.setCharAt(this.rieky[i],'~');
				if(!prezivsieRieky[i]) pocetTecucich--;				
			}	
			if(pocetTecucich==1){
				dozivaciCounter--;
			}
			System.out.println(riadok.toString());
			Thread.sleep(250);
			
		}
		for(int i = 0; i < pocetRiek; ++i){
			threadyRiek[i].join();
		}
		System.out.println("GG42");
	}
}
