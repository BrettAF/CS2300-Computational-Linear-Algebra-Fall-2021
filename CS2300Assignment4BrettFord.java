/**CS2300 Fall 2021
 * Assignment 4
 * due 11/11/2021
 * the purpose of this program is to read a series of numbers from a matrix and then to perform many calculations on them.
 * the calculations are divided into three sections, and each section has a method associated with it. SectionA attempts to treat the numbers as points of triangles. 
 * The triangles are then culled if they are inviable, and the intensity of light shining on the triangles is calculated. 
 * Part B projects the lines onto a plane and returns the point it projects onto. It then does the same with perspective. finally, it calculates the distance to the plane.
 * this last part is in part C of the assignment, but it makes much more sense to do it in part B, because I am already making calculations with the points. 
 * finally in part C, the points are treated as a line and many triangles. weather a line passes through the triangle is calculated as well as the point it passes when it does hit.
 * 
 *Two classes hold many methods for doing calculations. Vector, which holds methods for doing vector math, and Matrix, which holds methods for doing matrix math as well as the constructor which reads the file.  
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CS2300Assignment4BrettFord {

	public static void main(String[] args) throws FileNotFoundException {
		Matrix mat=new Matrix("input_1.txt");
		partA(mat);
		partB(mat);
		partC(mat);
		
	}//end of main
	public static void partA(Matrix mat){
		//The first line of the input corresponds to the eye location point (Num11, Num12, and Num13) 
		//and the light direction (Num14, Num15, and Num16) 
		double e1=mat.get(1,1);
		double e2=mat.get(1, 2);
		double e3=mat.get(1, 3);
		double[] d = new double[3];
		d[0]=mat.get(1, 4);
		d[1]=mat.get(1, 5);
		d[2]=mat.get(1, 6);
		
		for(int col=2;col<11;col++) {
			double p1= mat.get(col, 1);
			double p2= mat.get(col, 2);
			double p3= mat.get(col, 3);
			double q1= mat.get(col, 4);
			double q2= mat.get(col, 5);
			double q3= mat.get(col, 6);
			double r1= mat.get(col, 7);
			double r2= mat.get(col, 8);
			double r3= mat.get(col, 9);
		
			//the centroid of the triangle
			double c1 = (p1+q1+r1)/3;
			double c2 = (p2+q2+r2)/3;
			double c3 = (p3+q3+r3)/3;
		
			//calculates the length of the vector e - c
			double lengthEMinusC=(e1-c1)*(e1-c1)+(e2-c2)*(e2-c2)+(e3-c3)*(e3-c3);
			lengthEMinusC=Math.sqrt(lengthEMinusC);
		
			// view vector, which is the direction from the eye location, e, to the triangle centroid, c
			double v[]=new double[3];
			v[0] = (c1 - e1)/lengthEMinusC;
			v[1] = (c2 - e2)/lengthEMinusC;
			v[2] = (c3 - e3)/lengthEMinusC;
			//Find the vector normal to the triangle:
			//Let u = q - p, w = r - p Then n = (u ^ w)/||u ^ w|| Compute the dot product of the view vector and the normal vector. If n · v < 0, then output 0 (cull), else output 1 (do not cull)
			double vu1 = q1-p1;
			double vu2 = q2-p2;
			double vu3 = q3-p3;
			double vw1 = r1-p1;
			double vw2 = r2-p2;
			double vw3 = r3-p3;
		
			Vector vector =new Vector();
			double n[]=vector.cross(vu1,vu2,vu3,vw1,vw2,vw3);
			n=vector.makeUnit(n[0], n[1], n[2]);

			double dotProduct = vector.dot(n, v);
			if (dotProduct<=0) {
				System.out.println("culled");
			}else {
				//The angle between d and n determines the intensity of the light, i.
            	// i = cos𝜃 = (d · n)/(||n|| ||d||)	
				double intensity = vector.dot(d, n) / (vector.length(n)*vector.length(d));
				if(intensity>0) {
					System.out.format("the light intensity is: %f\n",intensity);
				}else {
					System.out.format("the light intensity is: 0.0\n");
				}
			}
		
		}//end of for loop
		System.out.println("*   *   *   *   End of Part A   *   *   *   *");
	}//end of partA
	/* This is a projection problem. The first line of the input defines the plane and the projection direction (if it is parallel projection).
	 *  Num11, Num12, and Num13 corresponds to the point on the plane and Num14, Num15, 
	 *  and Num16 defines the normal to the plane before normalization. 
	 *  If it is a parallel projection, Num17, Num18, Num19 defines the projection direction.
	 *  The rest of the input lines define points where there are three points per line.
	 */
	
	public static void partB(Matrix matrix) {
		Vector vector=new Vector();
		//the point on the plane
		double[] q =new double[3];
		q[0]=matrix.get(1, 1);
		q[1]=matrix.get(1,2);
		q[2]=matrix.get(1, 3);
		
		//a vector normal to the plane
		double[] n =new double[3];
		n[0]=matrix.get(1, 4);
		n[1]=matrix.get(1, 5);
		n[2]=matrix.get(1, 6);
		
		//the parallel projection
		double[] v =new double[3];
		v[0]=matrix.get(1, 7);
		v[1]=matrix.get(1, 8);
		v[2]=matrix.get(1, 9);
		
		double[] x =new double[3];
		double[] x_ = new double[3];
		double[] qMinusX = new double[3];
		double distance;
		for(int r=1;r<10;r++) {
			for(int i=1;i<9;i=i+3) {
				//the points to be projected onto the plane
				x[0]=matrix.get(r, i);
				x[1]=matrix.get(r, i+1);
				x[2]=matrix.get(r, i+2);
				qMinusX=vector.subtract(q,x);
				//x′ = x + [([q - x]·n)/v·n]v
				x_[0]=x[0]+((vector.dot(qMinusX,n)/vector.dot(v, n)))*v[0];
				x_[1]=x[1]+((vector.dot(qMinusX,n)/vector.dot(v, n)))*v[1];
				x_[2]=x[2]+((vector.dot(qMinusX,n)/vector.dot(v, n)))*v[2];
				System.out.println("the point projects onto the plane at "+x_[0]+", "+x_[1]+", "+x_[2] );
				
				//The projection of each point, x′, is calculated as follows:
				//x′ = [q·n/x·n]x
				x_[0]=(vector.dot(q,n)/vector.dot(x,n) )*x[0];
				x_[1]=(vector.dot(q,n)/vector.dot(x,n) )*x[1];
				x_[2]=(vector.dot(q,n)/vector.dot(x,n) )*x[2];
				System.out.println("the perspective projection onto the plane is on point "+x_[0]+", "+x_[1]+", "+x_[2] );
				
				/*First, each line of the input defines a plane (first six numbers) and a point (the last three number). 
				 * For the J-th line on the input, NumJ1, NumJ2, and NumJ3 corresponds to the point on the plane and NumJ4, NumJ5, 
				 * and NumJ6 defines the normal to the plane before normalization.
				 *  NumJ7, NumJ8, NumJ9 are the point coordinates.
				 *  */
				
				//The distance, d, from the point to the plane is calculated as follows:
				//d = (c + n·x)/n·n
				//where c = -n·q
				distance = (vector.dot(vector.negative(n), q) +vector.dot(n, x))/(vector.dot(n, n));
				System.out.println("The distanc from the point to the plane is " +distance +"\n");
				
			
			}
		}//end of nested for loop	
		System.out.println("*   *   *   *   End of Part B   *   *   *   *");
	}//end of partB()
	public static void partC(Matrix matrix) {
	/*Second, the first line of the input defines a line defined by the two points, where Num11, Num12, and Num13 yields one point 
	 * and Num14, Num15, and Num16 provides the other point coordinates. 
	 * The rest of the lines on the input defines the three vertex points of a triangle plane (a bounded plane). */
		Vector vector=new Vector();
		double[] x =new double[3];
		double[] y =new double[3];
		double[] v =new double[3];
		double[] p1 =new double[3];
		double[] p2 =new double[3];
		double[] p3 =new double[3];
		double[] w =new double[3];
		double[] z =new double[3];
		double[] abc=new double[3];
		double[] intersection=new double[3];
		double[] u1u2t=new double[3];
		double[] minusV = new double[3];
		double[][] inverseMatrix= new double[3][3];
		x[0]=matrix.get(1, 1);
		x[1]=matrix.get(1, 2);
		x[2]=matrix.get(1, 3);
		y[0]=matrix.get(1, 4);
		y[1]=matrix.get(1, 5);
		y[2]=matrix.get(1, 6);
		v=vector.subtract(y, x);
		minusV=vector.negative(v);
		
		//collects all the points of the triangles.
		for(int col =2;col<10;col++) {
			p1[0]=matrix.get(col, 1);
			p1[1]=matrix.get(col, 2);
			p1[2]=matrix.get(col, 3);
			p2[0]=matrix.get(col, 4);
			p2[1]=matrix.get(col, 5);
			p2[2]=matrix.get(col, 6);
			p3[0]=matrix.get(col, 7);
			p3[1]=matrix.get(col, 8);
			p3[2]=matrix.get(col, 9);
			
			/* We then have the following equation that determines if the line intersects with the triangle:
			x + tv = p1 + u1(p2 - p1) + u2(p3 - p1)
			Let w = p2 - p1 and z = p3 - p1
			 */
			
			w=vector.subtract(p2, p1);
			z=vector.subtract(p3, p1);
			
			/*a = x1 - p1 , b = x2 - p2 ,  c = x3 - p3*/
			abc=vector.subtract(x, p1);
			inverseMatrix = matrix.inverseCalc(w,z,minusV);
			u1u2t=matrix.matrixVectorMultiply(inverseMatrix, abc);
			
			/*0  < u1 < 1,
			  0  < u2 < 1 and
			  u1 + u2 < 1, then the line intersects with the triangle and x + tv is the point of intersection.*/
			if((u1u2t[0]>0)&&(u1u2t[0]<1)&&(u1u2t[1]>0)&&(u1u2t[1]<1)&&(u1u2t[0]+u1u2t[1]<1) ) {
				intersection = vector.addVectors(	vector.scale(u1u2t[2], v)	,x	);

				System.out.println("the line intersects with the triangle at point "+intersection[0]+", "+intersection[1]+", "+intersection[2]+", ");
			}
			
			System.out.println("the line does not intersect with the triangle");
			
		}
			
		
	/* If the input had K lines, there are K-1 triangles for testing. 
	 * For each triangle, if it intersects with the line, find the point of intersection. 
	 * If it does not intersect, output "Does not intersect." 
	 * Generate a file for your output including K-1 lines for one triangle per line.
	 */
		
		
		
		
		System.out.println("*   *   *   *   End of Part C   *   *   *   *");
	}//end of partC()
}//end of class
class Matrix{
	double[][] matrix= new double[10][10];
	
	//this constructor reads the file
	public Matrix(String location) throws FileNotFoundException {
		File fileName = new File(location);
		Scanner readFile = new Scanner(fileName);	
		int r=0;
		while(readFile.hasNextDouble()) {
			for(int c=0;c<9;c++) {
			this.matrix[r][c]=readFile.nextDouble();
			}
			r++;
		}
		readFile.close();

	}
	public double get(int r,int c) {
		return matrix[r-1][c-1];
	}
	
	/*a method to calculate the determinate of a matrix made by 3 vectors*/
	public double by3determinate(double a[], double b[], double c[]) {
		double determinate=a[0]*(b[1]*c[2]-b[2]*c[1])-b[0]*(a[1]*c[2]-a[2]*c[1])+c[0]*(a[1]*b[2]-b[1]*a[2]);
		
		return determinate;
	}
	
	/*a method to find the inverse matrix*/
	public double[][] inverseCalc(double a[], double b[], double c[]){
		double[][] adj= new double[3][3];
		
		//calculates the determinate
		double detetminate=by3determinate(a,b,c);
		
		
		if(detetminate!=0) {
			adj[0][0]= (a[0]*b[1]-a[1]*b[0])*(1/detetminate);
			adj[1][0]=-(a[1]*b[2]-a[2]*b[1])*(1/detetminate);
			adj[2][0]=-(a[2]*b[0]-a[0]*b[2])*(1/detetminate);
			
			adj[0][1]=-(b[0]*c[1]-b[1]*c[0])*(1/detetminate);
			adj[1][1]= (b[1]*c[2]-b[2]*c[1])*(1/detetminate);
			adj[2][1]= (b[2]*c[0]-b[0]*c[2])*(1/detetminate);
			
			adj[0][2]= (c[0]*a[1]-c[1]*a[0])*(1/detetminate);
			adj[1][2]=-(c[1]*a[2]-c[2]*a[1])*(1/detetminate);
			adj[2][2]= (c[2]*a[0]-c[0]*a[2])*(1/detetminate);	
		}
		return adj;
	}
	//a method for multiplying a matrix by a vector
	public double[] matrixVectorMultiply(double matrix[][],double v[]){
	double[] product = new double[3];//holds the product of the multiplication
	product[0]=matrix[0][0]*v[0]+matrix[0][1]*v[1]+matrix[0][2]*v[2];
	product[1]=matrix[1][0]*v[0]+matrix[1][1]*v[1]+matrix[1][2]*v[2];
	product[2]=matrix[2][0]*v[0]+matrix[2][1]*v[1]+matrix[2][2]*v[2];
	
	return product;
	}
	
	
}//end of matrix
class Vector{
	public double[] cross(double a1,double a2,double a3,double b1,double b2, double b3) {
		double product[]=new double[3];
		product[0]=a2*b3-a3*b2;
		product[1]=a3*b1-a1*b3;
		product[2]=a1*b2-a2*b1;
		return product;
		
	}//end of crossProduct()
	public double[] negative(double[] a) {
		a[0]=-a[0];
		a[1]=-a[1];
		a[2]=-a[2];
		return a;
	}
	public double length(double a1,double a2,double a3) {
		double length=a1*a1+a2*a2+a3*a3;
		length=Math.sqrt(length);
		return length;
	}
	public double length(double a[]) {
		double length=a[0]*a[0]+a[1]*a[1]+a[2]*a[2];
		length=Math.sqrt(length);
		return length;
	}
	//a method to scale a vector by a constant
	public double[] scale(double sclaingFactor,double[] a) {
		a[0]=a[0]*sclaingFactor;
		a[1]=a[1]*sclaingFactor;
		a[2]=a[2]*sclaingFactor;
		return a;
	}
	
	public double[] addVectors(double[] b ,double[] a) {
		a[0]=a[0]+b[0];
		a[1]=a[1]+b[1];
		a[2]=a[2]+b[2];
		return a;
	}
	
	
	/**a method that subtracts two vectors. it returns a vector which is the first vector minus the second
	 * 
	 * @param a
	 * @param b
	 * @return the first argument minus the second argument
	 */
	public double[] subtract(double a[],double b[]) {
		double unit[]=new double[3];
		unit[0]=a[0]-b[0];
		unit[1]=a[1]-b[1];
		unit[2]=a[2]-b[2];
		return unit;
	}
	//a method to make a vector unit length, it receives three coordinates
	public double[] makeUnit(double a1,double a2,double a3) {
		double unit[]=new double[3];
		double l=length(a1,a2,a3);
		unit[0]=a1/l;
		unit[1]=a2/l;
		unit[2]=a2/l;
		return unit;
		
	}
	
	// a slightly different method to return a unit length vector, this on works on a single vector
	public double[] makeUnit(double a[]) {
		double unit[]=new double[3];
		double len=length(a[0],a[1],a[2]);
		unit[0]=a[0]/len;
		unit[1]=a[1]/len;
		unit[2]=a[2]/len;
		return unit;
		
	}
	public double dot(double[] vectorA,double[] vectorB) {
		return vectorA[0]*vectorB[0]+vectorA[1]*vectorB[1]+vectorA[2]*vectorB[2];
	}
}
