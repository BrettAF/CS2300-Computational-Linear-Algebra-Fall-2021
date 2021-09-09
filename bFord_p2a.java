/*this program continues on from CS2300P1BrettFord
 * it reads the matrices from a text file, and then attempts to add them and multiply them in every combination. 
 * If it is able to add or multiply them, it it generates a file with the results. If not, it generates a file with an error message.
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
public class bFord_p2a {

	public static void main(String[] args) throws FileNotFoundException {
	
		double[][] max1=readMatrix("/Users/brett/eclipse-workspace/CS2300P1BrettFord/BFordMat1.txt");//these lines read the matrices
		double[][] max2=readMatrix("/Users/brett/eclipse-workspace/CS2300P1BrettFord/BFordMat2.txt");
		double[][] max3=readMatrix("/Users/brett/eclipse-workspace/CS2300P1BrettFord/BFordMat3.txt");
		double[][] max4=readMatrix("/Users/brett/eclipse-workspace/CS2300P1BrettFord/BFordMat4.txt");
		double[][] max5=readMatrix("/Users/brett/eclipse-workspace/CS2300P1BrettFord/BFordMat5.txt");
		
		
		writeMatSum(max1,max1,"1","1");//these lines submit the matrices in every combination to the methods which will add them and multiply them.
		writeMatSum(max1,max2,"1","2");
		writeMatSum(max1,max3,"1","3");
		writeMatSum(max1,max4,"1","4");
		writeMatSum(max1,max5,"1","5");
		writeMatSum(max2,max2,"2","2");
		writeMatSum(max2,max3,"2","3");
		writeMatSum(max2,max4,"2","4");
		writeMatSum(max2,max5,"2","5");
		writeMatSum(max3,max3,"3","3");
		writeMatSum(max3,max4,"3","4");
		writeMatSum(max3,max5,"3","5");
		writeMatSum(max4,max4,"4","4");
		writeMatSum(max4,max5,"4","5");
		writeMatSum(max5,max5,"5","5");
		
	}//end of main
	
	//this file receives the matrices and adds them together, or writes an error message instead
	//it recieves as arguments the two matrices that will be added, and their names. As matrix addition is commutative, it does not add them both ways.
	private static void writeMatSum(double[][] matrix1,double[][] matrix2,String max1name,String max2name) throws FileNotFoundException {
		File fileName = new File("bFordP2_"+max1name+max2name+".txt");//creates a new text file with a name that represents the matrices being added
		PrintWriter outputFile = new PrintWriter(fileName);
		
		if((matrix1.length==matrix2.length)&&(matrix1[0].length==matrix2[0].length)) {
			double[][] maxNew=new double[matrix1.length][matrix1[0].length];
			for(int c = 0 ; c<maxNew.length ; c++) {//c keeps track of what column it is in
				for(int r=0 ; r<maxNew[0].length ; r++) {//r keeps track of what row it is in
					maxNew[c][r]=(matrix1[c][r])+matrix2[c][r];
					outputFile.write(maxNew[c][r]+"  ");//outputs to the file

				}
				outputFile.write("\n");
				
			}
			System.out.println("the sum of matrix "+max1name+" and "+max2name+" can be found at: "+fileName.getAbsolutePath());
		}else {
			System.out.println(max1name+" and "+max2name+" cannot be added because they are are different sizes");
			outputFile.write("These two matricies cannot be added because their sizes are different.");//prints an error message
		}//end of else
		outputFile.flush();
		outputFile.close();//closes the file
		
		writeMatProd(matrix1,matrix2,max1name,max2name);//these pass the matrices and their names to the product method.Because multiplication is not commutative, it is done in both combinations
		writeMatProd(matrix2,matrix1,max2name,max1name);
	}//end of writeMatSum()
	
	private static void writeMatProd(double[][] matrix1,double[][] matrix2, String max1name, String max2name) throws FileNotFoundException {
		File fileName1 = new File("bFord_P3_"+max1name+max2name+".txt");
		PrintWriter outputFile1 = new PrintWriter(fileName1);
		if(matrix1.length==matrix2[0].length) {//the two matrices can be multiplied
			System.out.println("matrix "+max1name+" and "+max2name+" can be multiplied,\tTheir size will be "+matrix1.length+" by "+matrix2[0].length);
			double sum =0;
			double[][] maxNew=new double[matrix2.length][matrix1[0].length];//sets up a matrix to store the values
			for(int r = 0;r<matrix2[0].length;r++) {//r keeps track of the row we are currently populating
				for(int c=0;c<matrix1.length;c++) {// c keeps track of the column we are currently populating
					for(int i =0;i<maxNew.length;i++) {//i is used to add all the products to fill the matrix 
						sum=sum+(matrix1[c][i])*(matrix2[i][r]);//this computes the products to place in each cell of the output matrix
						
					}
					sum=(Math.round(sum*100)/100);
					outputFile1.write(sum+"  ");
					sum=0;
				}
				outputFile1.write("\n");
			}//end of the nested for loop that generates products
		}else {
			outputFile1.write("These two matricies are the wrong size to be multiplied");
		}
		outputFile1.flush();
		outputFile1.close();//closes the file
	}//end of writeMatProd()
	
	//this method reads the matrices from a text document
	private static double[][] readMatrix(String location) throws FileNotFoundException{
		File fileName = new File(location);
		Scanner readFile = new Scanner(fileName);
		int height=readFile.nextInt();//opens the file
		int width=readFile.nextInt();
		System.out.println("height= "+height+" , width= "+ width);
		double[][] matrix=new double[height][width];
		
		for(int c = 0 ; c<matrix.length ; c++) {//c keeps track of what column we are in
			for(int r=0 ; r<matrix[0].length ; r++) {//r keeps track of what row we are in
				matrix[c][r]=readFile.nextDouble();
			}
		}
		readFile.close();//closes the file
		return matrix;
		
	}//end of readMatrix
	
	//this method prints matrices to the standard output. It is unused in the program, but seemed important anyway
	private static void printMatricies(double[][] matrix) {
		
		for(int c = 0 ; c<matrix.length ; c++) {//c keeps track of what column we are in
			for(int r=0 ; r<matrix[0].length ; r++) {//r keeps track of what row we are in
				System.out.print(matrix[c][r]+" ");	
			}
			System.out.print("\n");
		}
		
	}//end of print matrices
	
}//end of class
