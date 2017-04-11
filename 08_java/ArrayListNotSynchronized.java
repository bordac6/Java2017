import java.util.*;

public class ArrayListNotSynchronized extends Thread {
	ArrayList<Integer> al = new ArrayList<Integer>();
	//List al = Collections.synchronizedList(new ArrayList());
	int counter = 0;
	//not 
	//synchronized 
	public void add() {
		//System.out.println("add "+counter);
		al.add(counter); counter++;
	}
	//not 
	//synchronized
	public void delete() {
		//System.out.println("index "+(counter-1));
		if (al.indexOf(counter-1) != -1) {
			//System.out.println("delete "+(counter-1));
			al.remove(counter-1); counter--;
		}
	}
}

