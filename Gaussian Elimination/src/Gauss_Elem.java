import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
public class Gauss_Elem {

	public static void main(String[]args) throws FileNotFoundException{
		FileInputStream FIS = new FileInputStream("matrix.txt");
		@SuppressWarnings("resource")
		Scanner in = new Scanner(FIS);

		ArrayList<String> NewList = new ArrayList<>();
		ArrayList<Double> intList = new ArrayList<>();
		ArrayList<ArrayList<Double>> fullList = new ArrayList<>();

		while(in.hasNextLine())
			NewList.add(in.nextLine());

		fullList = createList(fullList, NewList, intList, 0);
		
		ArrayList<Double> rx = new ArrayList<>();
		ArrayList<Double> ry = new ArrayList<>();
		switchrows(fullList, fullList.size());
		fullList = ReducedEchelon(Echelon(AugmentMatrix(fullList, rx, ry, 0, 1), rx, 0), rx, ry, fullList.size()-1, fullList.size()-2);
		for(int i = 0; i < fullList.size(); i++)
		System.out.println(fullList.get(i));

	}
	
	public static ArrayList<ArrayList<Double>> ReducedEchelon(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> rx, ArrayList<Double> ry, int i, int j){
		
		if(i <= 0) {
			return matrix;
		}
		
		double divisor = 0;
		
		rx = matrix.get(i);
		
		ry = matrix.get(j);
		
		for(int a = 0; a < rx.size(); a++) {
			if(rx.get(a) == 1 && a <= rx.size()-2) {
				divisor = -ry.get(a);
				break;
			}
		}
		
		for(int a = 0; a < rx.size(); a++) {
			ry.set(a, ry.get(a)+(rx.get(a)*divisor));
		}
		
		if(j <= 0) {
			return ReducedEchelon(matrix, rx, ry, i-1, i-2);
		}
		else {
			return ReducedEchelon(matrix, rx, ry, i, j-1);
		}
		
	}
	
	public static ArrayList<ArrayList<Double>> Echelon(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> rx, int i){
		if(i >= matrix.size()) {
			return matrix;
		}
		
		rx = matrix.get(i);
		double divisor = 0;
		
		for(int a = 0; a < rx.size(); a++) {
			if(rx.get(a) != 0) {
				divisor = 1/rx.get(a);
				break;
			}
		}
		
		for(int a = 0; a < rx.size(); a++) {
			rx.set(a, divisor*rx.get(a));
		}
		
		return Echelon(matrix, rx, i+1);
		
	}
	
	public static ArrayList<ArrayList<Double>> AugmentMatrix(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> rx, ArrayList<Double> ry, int i, int j){
		
		if(i >= matrix.size()-1) {
			switchrows(matrix, matrix.size());
			return matrix;
		}
		
		double divisor = 0;
		
		rx = matrix.get(i);
		ry = matrix.get(j);
		
		for(int a = 0; a < rx.size(); a++) {
			if(rx.get(a) != 0) {
				divisor = -ry.get(a)/rx.get(a);
				break;
			}
		}
		
		//System.out.println(ry);
		
		for(int a = 0; a < rx.size(); a++) {
			ry.set(a, ry.get(a)+(divisor*rx.get(a)));
		}
		
		if(j >= matrix.size()-1) {
			switchrows(matrix, matrix.size());
			return AugmentMatrix(matrix, rx, ry, i+1, i+2);
		}
		else {
			return AugmentMatrix(matrix, rx, ry, i, j+1);
		}
		
	}

	//switch the rows least amount of zeros at the top and most amount of zeros at the bottom.
	public static ArrayList<ArrayList<Double>> switchrows(ArrayList<ArrayList<Double>>matrix, int rows){
		int[] rowcountzero = new int[rows];

		int counter = 0;

		for(int i = 0; i < matrix.size(); i++) {
			counter = 0;
			for(int j = 0; j < matrix.get(i).size(); j++) {
				if(matrix.get(i).get(j)==0) {	
					counter++;
					rowcountzero[i] = counter;
				}
				else {
					break;
				}
			}
		}
		return sortelems(rowcountzero, matrix);
	}


	//sorting the array
	public static ArrayList<ArrayList<Double>>  sortelems(int[] rowcountzero, ArrayList<ArrayList<Double>>  Arrays) {
		//sorting
		for (int i = 0; i < rowcountzero.length; i++) {
			// min is the index of the smallest element with an index greater or equal to i
			int min = i;

			for (int j = i + 1; j < rowcountzero.length; j++) {
				if (rowcountzero[j] < rowcountzero[min]) {
					min = j;

				}
			}
			// Swapping i-th and min-th elements
			int swap = rowcountzero[i];
			ArrayList<Double> wap = Arrays.get(i);
			rowcountzero[i] = rowcountzero[min];
			Arrays.set(i, Arrays.get(min));
			rowcountzero[min] = swap;
			Arrays.set(min, wap);

		}

		return Arrays;

	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<ArrayList<Double>> createList(ArrayList<ArrayList<Double>> fullList, ArrayList<String> NewList, ArrayList<Double> intList, int i){
		String temp[];
		if(i >= NewList.size()) {
			return fullList;
		}
		else {
			intList.clear();
			temp = NewList.get(i).split("\\s+");

			for(int j = 0; j < temp.length; j++)
				intList.add(Double.parseDouble(temp[j]));

			fullList.add((ArrayList<Double>) intList.clone());

			return createList(fullList, NewList, intList, i+1);
		}
	}

}
