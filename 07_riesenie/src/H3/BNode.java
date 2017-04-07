package H3;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class BNode< E extends Comparable< E>> implements Serializable {
    BNode< E> left;		// ¾avý podstrom
    E key;  			// hodnota vrchola
    BNode< E> right;		// pravý podstrom    
    public BNode(BNode< E> left, E key, BNode< E>right) {  // konštruktor  
    	this.left=left; this.key=key; this.right = right;
    	
    }
    
    public String toString(){
    	String result = "";
    	if(left != null){
    		result += "(" + left.toString() + ")";
    	}
    	result += " " + key + " ";
    	if (right != null){
    		result += "(" + right.toString() + ")";
    	}
    	
    	return result;
    }
    
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
    	File f = new File("subor.txt");
    	
    	
    	FileOutputStream fo = new FileOutputStream(f); 
    	ObjectOutputStream vypis = new ObjectOutputStream(fo);
    
    	BNode<Integer> strom = new BNode<Integer>(null, 5, null);
    	
    	BNode<Integer> strom2 = 
    			new BNode<Integer>(strom, 6, strom);
    	
    	BNode<Integer> strom3 = 
    			new BNode<Integer>(strom2, 3, strom);
    	
    	System.out.println(strom3.toString());
    	vypis.writeObject(strom);
    	vypis.writeObject(strom2);
    	vypis.writeObject(strom3);  	
    	
    	vypis.close();
    	
    	FileInputStream fi = new FileInputStream(f); 
    	ObjectInputStream fr = new ObjectInputStream(fi);
    	BNode<Integer> root = (BNode<Integer>)fr.readObject();
    	BNode<Integer> root2 = (BNode<Integer>)fr.readObject();
    	BNode<Integer> root3 = (BNode<Integer>)fr.readObject();
    	System.out.println(root.toString());
    	System.out.println(root2.toString());
    	System.out.println(root3.toString());
    	fi.close();
    }
}
