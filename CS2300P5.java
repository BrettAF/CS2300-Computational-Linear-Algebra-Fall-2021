/**CS2300
 * Project 5
 * Due 12/2/2021
 * Brett Ford
 * This program has two parts:
 * First, we use a matrix given to produce a page rank algorithm. the matrix is tested to be sure it is stochastic and then  the power
 * 		Algorithm is used to find the eigenvector for each list item. the eigenvectors and the final rank are then printed. 
 * Second, 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.math.*;

public class CS2300P5 {

	public static void main(String[] args) throws FileNotFoundException {
		
		//this reads the file and saves it as a matrix
		Matrix matrixA = new Matrix("InputA.txt");
		
		//this tests if the matrix is stochastic
		if (matrixA.isStocastic() != true) {
			System.out.println("matrix is not stochastic ");
		} else {
			//this calls the method to calculate the power algorithim
			double[] eigenVector = matrixA.powerAlgorithim();
			double biggest = 0;
			int index = 0;
			double big = 0;
			System.out.println("*  *  *  Part A  *  *  *");
			
			//this for loop prints the power algorithim eigenvectors and prints the indexes in order
			for (int i = 0; i < eigenVector.length; i++) {
				System.out.println(eigenVector[i]);
				if (eigenVector[i] > biggest) {
					biggest = eigenVector[i];
					index = i;
				}
			}

			System.out.print((index + 1) + ", ");
			for (int i = 0; i < eigenVector.length - 1; i++) {
				for (int j = 0; j < eigenVector.length; j++) {
					if (eigenVector[j] < biggest && eigenVector[j] > big) {
						big = eigenVector[j];
						index = j;
					}
				}
				System.out.print((index + 1) + ", ");
				biggest = big;
				big = 0;
			}
		} // end of else
		
		//the start of part B
		System.out.println("\n*  *  *  Part B  *  *  *");

		LinearBinaryClassification classifier = new LinearBinaryClassification();
		//this reads the file
		String location = "InputB.txt";
		File fileName = new File(location);
		Scanner readFile = new Scanner(fileName);
		double[][] trainingMatrix = new double[5][6];
		double[] labels=new double[5];
		for (int c = 0; c < 5; c++) {
			labels[c]=readFile.nextDouble();
			for (int v = 0; v < 5; v++) {
				trainingMatrix[c][v] = readFile.nextDouble();
			}
		}
		readFile.close();
		
		//this trains the weights
		double[] weights=classifier.PerceptronTrainer(trainingMatrix,labels);

		//this reads the file to be tested
		location = "testInputB.txt";
		fileName = new File(location);
		readFile = new Scanner(fileName);
		double[][] analysisMatrix = new double[5][5];
		
		for (int c = 0; c < 5; c++) {
			for (int v = 0; v < 5; v++) {
				analysisMatrix[c][v] = readFile.nextDouble();
			}
		}
		readFile.close();
		
		//and this classifies the new data 
		classifier.perceptronAnalisys(analysisMatrix,weights);

	}// end of main()

}// end of class

//this class holds the matrix and the methods to perform the power algorithm 
class Matrix {
	double[][] matrix = new double[10][10];

	// this constructor reads the file
	public Matrix(String location) throws FileNotFoundException {
		File fileName = new File(location);
		Scanner readFile = new Scanner(fileName);
		int r = 0;
		while (readFile.hasNextDouble()) {
			for (int c = 0; c < 10; c++) {
				this.matrix[r][c] = readFile.nextDouble();
			}
			r++;
		}
		readFile.close();
	}

	//this methods tests is a matrix is stochastic
	public boolean isStocastic() {
		double columnSum;

		for (int c = 0; c < 10; c++) {
			columnSum = 0;
			for (int r = 0; r < 10; r++) {
				columnSum += matrix[r][c];
				if (matrix[r][c] < 0) {
					return false;
				}
			}
			if (Math.abs(columnSum - 1) > 0.0001) {
				System.out.println(Math.abs(columnSum - 1));
				return false;
			}
		}
		return true;
	}// end of isStocastic()

	//this methos preforms the power algorithim
	public double[] powerAlgorithim() {
		double x[] = { 1, 12, 1, 1, 1, 1, 1, 25, 1, 1 };
		double j;
		for (int k = 0; k <= 40; k++) {
			//this calls a method to multiply the matricies
			x = matrixVectorMultiply(x);
			
			//this finds the largest number in the vector
			j = infinateMag(x);
			
			//this tests if the eigenvectors are converged to zero
			if (Math.abs(j) < .0001) {
				System.out.println("has a zero eigenvector");
				x[1] = 5;
			}
			for (int i = 0; i < x.length; i++) {
				x[i] = x[i] / j;
			}
		}

		return x;
	}// end of powerAlgorithim

	//this multiplies a matrix by a vector and outputs a vector
	public double[] matrixVectorMultiply(double[] vector) {
		double[] newVector = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				newVector[c] += matrix[c][r] * vector[r];
			}
		}
		return newVector;
	}
	//this finds the largest number in a vector
	public double infinateMag(double[] vector) {
		double largest = 0;
		for (int i = 0; i < vector.length; i++) {
			if (Math.abs(vector[i]) > largest) {
				largest = Math.abs(vector[i]);
			}
		}
		return largest;
	}
}

//this class holds methods to do linear Binary classification
class LinearBinaryClassification {

	//this method finds dot products
	public double dotProduct(double[] vector1, double[] vector2) {
		double dotProduct = 0;
		for (int i = 0; i < vector1.length; i++) {
			dotProduct += vector1[i] * vector2[i];
		}
		return dotProduct;
	}

	//this scales a vector by a scaler, and then adds it to another vector
	public double[] addScaleVectors(double[] vector1, double[] vector2, double scale) {
		double[] sum = new double[vector1.length];

		for (int i = 0; i < vector1.length; i++) {
			sum[i] = vector1[i] + scale * vector2[i];
		}
		return sum;
	}

	//this finds the weights
	public double[] PerceptronTrainer(double[][] featuresMatrix,double[] labels) {
		double[] weights = { 5, 6, 4, 3, 4 };
		double[] features = new double[5];
		double F;
		double error = 0;

		// e=Y1-f(W,X1)
		// W = W + eX1
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 5; i++) {
				features = makeIntoVector(i, featuresMatrix);
				if (dotProduct(weights, features) >= 0) {
					F = 1;
				} else {
					F = 0;
				}
				error = labels[0] - F;
				weights = addScaleVectors(weights, features, error);
			}	
		}
		printVector(weights);
		return weights;
	}

	//this method prints vectors
	public void printVector(double[] vector) {
		for (int i = 0; i < vector.length; i++) {
			System.out.print(vector[i] + ", ");
		}
		System.out.println();
	}
	//this makes a row from a matrix into a vector
	public double[] makeIntoVector(int row, double[][] matrix) {
		int length=matrix[0].length;
		
		double[] vector = new double[length];
		for (int i = 0; i < length; i++) {
			vector[i] = matrix[row][i];
			
		}
		return vector;
	}// end of makeIntoVector

	//this takes in a matrix and applies the weights 
	public void perceptronAnalisys(double[][] matrix,double[]weight) {
		double[] vector;
		for(int i=0;i<5;i++) {
			vector=makeIntoVector(i,matrix);
			
			if(dotProduct(vector,weight)>=0) {
				System.out.print("1, ");
			}else {
				System.out.print("0, ");
			}
		}
	}
}// end of LinearBinaryClassification