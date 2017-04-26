import java.util.ArrayList;
import java.util.HashSet;

public class Obrazkovy {

	public static void main(String[] args) {
		{ // kod 1
			int[][]A = {{0,1,2}, null};
		}
		
		{ // kod 2 neexistuje, lebo int nemoze byt null
		}
		{ // kod 3
			int[] B = {0,0,0};
			int[][]A = {B,B,B};
		}		
		{ // kod 4
			String[][]A = { {null, null, "Midterm"}, { null, null}, {null} };
		}
		
		{ // kod 5
			Integer[][]p ={{0,1},{2,3},null,{4,5}};
			Integer[][] clon =
			             (Integer[][])p.clone();
			p[0][0] = 4; 
			System.out.println(16 / clon[0][0]);  // 4
		}
		{ // kod 7
			Integer[] pole1 = new Integer[]{1,2,null,null};
			//for(Integer p:pole1) {
			//	System.out.println(p+p);  // NPE null+null .. R
			//}

		}
		{  // kod 8
			Integer[] pole3 = { 0 };
			Integer[] pole4 = pole3;

			pole4 = new Integer[]{1,2};
			//System.out.println(pole3[1]);  // Array index of of bounds .. R
			pole3 = pole4;
			pole4[1] = 99;
					
			if (pole3[1] == 99)
			  System.out.println(pole3[1]); // 99

		}
		{ // kod 9
			//MySet ms = new HashSet<String>();  // cannot convert HashSet to MySet ...S
			HashSet<String> hs = new MySet();
		}
		{ // kod 10
			// MySet[] pole5 = new HashSet<String>[5];  // cannot create generic array... S
			HashSet<String>[] pole6 = new MySet[6];
		}
		{ // kod 11
			//ArrayList<MySet> al1 = new ArrayList<HashSet<String>>(); // cannot convert ArrayList<HashSet> to ArrayList<MySet> ...S
			//ArrayList<HashSet<String>> al2 = new ArrayList<MySet>(); // cannot convert ArrayList<MySet> to ArrayList<HashSet> ...S
		}
		{ // kod 12
			int a = 123456;
			if ((a & 1) != 0) a++;
			int b = (a >> 4) & 0b1111;		
		}
	}
}
class MySet extends HashSet<String> {}
