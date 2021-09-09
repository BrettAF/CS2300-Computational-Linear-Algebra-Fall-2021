/*Brett Ford
 * CS2300 Assignment 1 Due Thursday 9/9 at 4:45
 * 
 * 
 */
import java.util.*;
import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CS2300P1BrettFord {

	public static void main(String[] args) throws FileNotFoundException {
		int lastname=4;//stores some information about my name
		int firstname=5;
		
		
		
		
		
		System.out.println("Matrix 1");//generates the matrices, using one of two methods.
		double[][] mat1 = martixColumnFirst(lastname,firstname,1,1);
		printMatricies(mat1,"BFordMat1.txt");
		
		System.out.println("\nMatrix 2");
		double[][] mat2 = martixRowFirst(firstname,lastname,3,3);
		printMatricies(mat2,"BFordMat2.txt");
		
		System.out.println("\nMatrix 3");
		double[][] mat3 = martixRowFirst(lastname,firstname,0.4,0.3);
		printMatricies(mat3,"BFordMat3.txt");
		
		System.out.println("\nMatrix 4");
		double[][] mat4 = martixRowFirst(6,13,2,2);
		printMatricies(mat4,"BFordMat4.txt");
		
		System.out.println("\nMatrix 5");
		double[][] mat5 = martixColumnFirst(13,6,-7,1);
		printMatricies(mat5,"BFordMat5.txt");

	}

	//this method creates a matrix of numbers which increment based on a argument step
	//it they will populate numbers moving across rows before moving on to the next row
	//It receives four arguments, row is how many rows it should have, Column is how many columns, 
	//start is the value in the top right corner, and step is the amount it increments on each iteration to fill the matrix.
	private static double[][] martixColumnFirst(int row,int column,double start,double step) {
		double[][] mat1 ;	//Number of Rows = Number of Letters in your first name; Number of Columns = Number of Letters in your last name
		mat1 = new double[row][column];
		double store;
		
			for(int c = 0 ; c<column ; c++) {//c should keep track of what column I am in
				for(int r=0 ; r<row ; r++) {//r should keep track of what row I am in
					store=Math.round(((c*row+r)*step+start)*10);
					mat1[r][c]=store/10;//stores the value to the matrix
					System.out.print(mat1[r][c]+" ");
				}
				System.out.print("\n");
			}
		return mat1;
	}//end of matrixColumnFirst()
	
	//this method creates a matrix of numbers which increment based on a argument step
	//it they will populate numbers moving down columns before moving on to the next column to the right
	//It receives four arguments, row is how many rows it should have, Column is how many columns, 
	//start is the value in the top right corner, and step is the amount it increments on each iteration to fill the matrix.
	private static double[][] martixRowFirst(int row,int column,double start,double step) {
		double[][] mat1 ;
		mat1 = new double[row][column];
		double store;
		
		for(int r=0 ; r<row ; r++) {//If i set this up correctly, c should keep track of what column I am in
			for(int c = 0 ; c<column ; c++) {//r should keep track of what row I am in
				store=Math.round(10*((c*row+r)*step+start));
				mat1[r][c]=store/10;//stores the value to the matrix
				System.out.print(mat1[r][c]+" ");
			}
			System.out.print("\n");
		}
		
		return mat1;
	}//end of matrixRowFirst()
	
	//this method prints the matrices to a text document
	private static void printMatricies(double[][] matrix,String name) throws FileNotFoundException {
		File fileName = new File(name);
		PrintWriter outputFile = new PrintWriter(fileName);//opens the text document
		System.out.print("matrix is saved to the file "+name+" at: "+fileName.getAbsolutePath()+"\n");
		outputFile.write(matrix[0].length+"  "+matrix.length+"   \n");//this stores the dimensions of the matrix on top of the text file
		
		
		for(int c = 0 ; c<matrix[0].length ; c++) {
			for(int r=0 ; r<matrix.length ; r++) {
				outputFile.write(matrix[r][c]+"  ");//prints the matrix elements, followed by a space
				
			}
			outputFile.write("\n");
		}
		outputFile.flush();
		outputFile.close();//closes the file
		
	}//end of print matrices
}//end of class
