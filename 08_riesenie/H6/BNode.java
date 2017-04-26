package H6;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class BNode<E extends Comparable<E>> implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	BNode<E> left; // ╛av² podstrom
	E key; // hodnota vrchola
	BNode<E> right; // prav² podstrom

	public BNode(BNode<E> left, E key, BNode<E> right)  { // konÜtruktor
		this.left = left;
		this.key = key;
		this.right = right;
	}

	public String toString(){
		String s = "";
		if(this.left != null){
			s += this.left.toString();
		}
		s += key.toString();
		if(this.right != null){
			s += this.right.toString();
		}
		return s;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, ClassNotFoundException{

/*
		BNode<Integer> L1 = new BNode<Integer>(null, 2, null);
		BNode<Integer> L2 = new BNode<Integer>(null, 3, null);
		BNode<Integer> koren = new BNode<Integer>(L1, 1, L2);
		System.out.println(koren.toString());
		File subor = new File("subor.txt");
		FileOutputStream fos = new FileOutputStream(subor);
		ObjectOutputStream ous = new ObjectOutputStream(fos);

		ous.writeObject(koren);
		ous.close();
		fos.close();
*/
		File file = new File("subor.txt");
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		BNode<Integer> node = (BNode<Integer>)ois.readObject();
		System.out.println(node);
		ois.close();
		}
}