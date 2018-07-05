import java.lang.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ANN_3
{
	
	static ArrayList <Double [] > Training_Data = new ArrayList <Double [] > ();
	static ArrayList <Double [] > Validation_Data = new ArrayList <Double [] > ();
	static ArrayList <Double [] > Test_Data = new ArrayList <Double [] > ();
	static String Train_Loc = "/home/hemvats/Desktop/ML_2/ML2 _A2/train.txt";
	static String Validation_Loc = "/home/hemvats/Desktop/ML_2/ML2 _A2/validation.txt";
	static String Test_Loc = "/home/hemvats/Desktop/ML_2/ML2 _A2/test.txt";
	static int batch_size = 100;
	//static int m;

	public static void main (String [] args)
	{
		
		Training_Data = Data_Fetcher1(Train_Loc);
		
		int i,j,k,n;
		Double [] arr;
		double acc;

		/*		
		for (i=0;i<Training_Data.size();i++)
		{
			arr=Training_Data.get(i);
			for (j=0;j<arr.length;j++)
				System.out.print(arr[j]+"  ");
			System.out.println();
		}
		*/

		Validation_Data = Data_Fetcher1(Validation_Loc);

		/*
		//int i,j,k,n;
		//Double [] arr;
		System.out.println(); System.out.println();
		System.out.println(); System.out.println();
		for (i=0;i<Validation_Data.size();i++)
		{
			arr=Validation_Data.get(i);
			for (j=0;j<arr.length;j++)
				System.out.print(arr[j]+"  ");
			System.out.println();
		}		
		for (i=0;i<Training_Data.size();i++)
		{
			arr=Training_Data.get(i);
			for (j=0;j<arr.length;j++)
				System.out.print(arr[j]+"  ");
			System.out.println();
		}
		*/

		Test_Data = Data_Fetcher1(Test_Loc);
		
		acc = make_ann(5);

		int m,f; double a,ak;
		ak = acc; f=5;
		for (m=6;m<=10;m++)
		{
			a = make_ann(m);
			System.out.println("The accuracy for  m =   "+m+"   is   "+ a);
			if(a>ak)
			{
				ak=a;
				f=m;
			}
		}

		acc = make_ann(f);
		System.out.println("The accuracy of final ANN is "+acc);
		System.out.println("The final ANN has "+f+"  nodes in the middle layer");

	}

	public static ArrayList <Double [] > Data_Fetcher1 (String File_Loc)
	{
		ArrayList <Double [] > arr = new ArrayList <Double [] > ();
		BufferedReader br = null;
		String [] lineVector;
		String line;
		Double [] DLineVector;
		int i,j,k,n;
		try
		{
			br = new BufferedReader(new FileReader(File_Loc));
            while((line = br.readLine())!=null)
            {
                lineVector = line.split(",");
                if(lineVector.length==65)
                {
                	DLineVector = new Double[65];
                	for (i=0;i<65;i++)
                	{
                		DLineVector[i]=Double.parseDouble(lineVector[i].trim());
                	}
                	arr.add(DLineVector);
                }
            }
		} 
		catch (FileNotFoundException e)
            {e.printStackTrace();}
        catch(IOException e)
            {e.printStackTrace();}
        finally 
        {
            if (br != null) 
            {
                try 
                {
                    br.close();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }
        return arr;
	}

	public static double make_ann (int m)
	{
		Double [][]arrw1,arrw2;
		arrw1 = new Double [64][m];
		arrw2 = new Double [m][10];
		Double [][]arrwt1,arrwt2;
		arrwt1 = new Double [64][m];
		arrwt2 = new Double [m][10];
		Double [][]grads1, grads2;
		grads1 = new Double [64][m];
		grads2 = new Double [m][10];
		Double []al1,al2,al3;
		al1 = new Double [64];
		al2 = new Double [m];
		al3 = new Double [10];
		Double []zl1,zl2,zl3;
		zl1 = new Double [64];
		zl2 = new Double [m];
		zl3 = new Double [10];
		Double []alt1,alt2,alt3;
		alt1 = new Double [64];
		alt2 = new Double [m];
		alt3 = new Double [10];
		Double []zlt1,zlt2,zlt3;
		alt1 = new Double [64];
		alt2 = new Double [m];
		alt3 = new Double [10];
		Double [] operror;
		operror = new Double [10];
		int i,j,k,n,p;

		//Initiliazing ANN
		Random r = new Random();
		double randomValue;
		for (i=0;i<64;i++)
		{
			for (j=0;j<m;j++)
			{
				randomValue = -1 + (2) * r.nextDouble();
				arrw1[i][j]=randomValue;
				System.out.print(arrw1[i][j]+"   ");
			}
			System.out.println();
		}
		for (i=0;i<m;i++)
		{
			for (j=0;j<10;j++)
			{
				randomValue = -1 + (2) * r.nextDouble();
				arrw2[i][j]=randomValue;
				System.out.print(arrw2[i][j]+"   ");
			}
			System.out.println();
		}


		/*
		for (i=0;i<64;i++)
		{
			for (j=0;j<m;j++)
			{
				//randomValue = -1 + (2) * r.nextDouble();
				System.out.print (arrw1[i][j]+"   ");//=randomValue;
			}
			System.out.println();
		}
		for (i=0;i<m;i++)
		{
			for (j=0;j<10;j++)
			{
				//randomValue = -1 + (2) * r.nextDouble();
				System.out.print (arrw2[i][j]+"   ");//=randomValue;
			}
			System.out.println();
		}
		*/

		Box b;
		
		b = get_verdict (arrw1 , arrw2, Training_Data.get(0), al2, al3, zl2, zl3, m);
		
		/*
		System.out.println();
		System.out.println(b.output);
		System.out.println(b.error);
		*/

		double a1,a2;
		Double [] arr;
		for (i=0;i<operror.length;i++) operror[i]=0.0;
		for (i=0;i<Training_Data.size();i=j+1)
		{
			for (p=0;p<grads1.length;p++)
			{
				for (k=0;k<grads1[0].length;k++)
					grads1[p][k]=0.0;
			}
			for (p=0;p<grads2.length;p++)
			{
				for (k=0;k<grads2[0].length;k++)
					grads2[p][k]=0.0;
			}

			for (j=i;j<Training_Data.size() && j<(i+batch_size);j++)
			{
				//System.out.println("The value of J is  "+j+"  and m is  "+m);
				arr = Training_Data.get(j);
				b = get_verdict(arrw1 , arrw2, arr, al2, al3, zl2, zl3, m);
				
				double dbl;// = myDouble.doubleValue();
				dbl = arr[arr.length-1];
				int intgr = (int) dbl;
				Integer val = Integer.valueOf(intgr);

				//operror[arr[arr.length-1]]+=b.error;
				operror[val]+=b.error;

				//add the gradients here
				for (p=0;p<grads1.length;p++)
				{
					for (k=0;k<grads1[0].length;k++)
						grads1[p][k]+=b.grad1[p][k];
				}
				for (p=0;p<grads2.length;p++)
				{
					for (k=0;k<grads2[0].length;k++)
						grads2[p][k]+=b.grad2[p][k];
				}				

			}
			get_new_weights(operror,arrw1, arrw2, arrwt1, arrwt2, grads1, grads2);
			a1 = get_accuracy(arrw1, arrw2, Validation_Data, al2, al3, zl2, zl3, m);
			a2 = get_accuracy(arrwt1, arrwt2, Validation_Data, al2, al3, zl2, zl3, m);
			

			////////////////////////////////////
			
			if(a2>=a1 || (a2+.05)>=a1)
			{
				update_weights(arrw1, arrw2, arrwt1, arrwt2);
			}
			else
				break;
			
			//update_weights(arrw1, arrw2, arrwt1, arrwt2);
			////////////////////////////////////



			System.out.println("The accuracy after "+i+"    traiing egs is    "+a2);
		}

		a1 = get_accuracy(arrw1, arrw2, Test_Data, al2, al3, zl2, zl3, m);

		return a1;
	}

	public static Box get_verdict (Double [][] arrw1, Double [][] arrw2, Double [] data_x, Double [] al2, Double [] al3, Double [] zl2, Double [] zl3, int m)
	{
		
		int i,j,k,n;

		/*
		System.out.println(arrw1.length);
		System.out.println(arrw1[0].length);
		for (i=0;i<data_x.length;i++)
			System.out.print(data_x[i]+"   ");
		*/

		Box b; b = new Box(m);

		//Hidden Layer 1 
		for (i=0;i<al2.length;i++) al2[i]=0.0;
		for (i=0;i<data_x.length-1;i++)
		{
			for (j=0;j<al2.length;j++)
			{
				al2[j]+=data_x[i]*arrw1[i][j];
			}
		}		
		for (i=0;i<zl2.length;i++) zl2[i]=sigmoid(al2[i]);
		//Output Layer
		for (i=0;i<al3.length;i++) al3[i]=0.0;
		for (i=0;i<zl2.length;i++)
		{
			for (j=0;j<al3.length;j++)
			{
				al3[j]+=zl2[i]*arrw2[i][j];
			}
		}
		for (i=0;i<zl3.length;i++) zl3[i]=sigmoid(al3[i]);

		//calculating output
		double d; d=zl3[0]; k=0;
		for (i=1;i<zl3.length;i++)
		{
			if(zl3[i]>d)
			{
				d=zl3[i];
				k=i;
			}
		}
		b.output = k;

		//calculating error


		double dbl;// = myDouble.doubleValue();
		dbl = data_x[data_x.length-1];
		int intgr = (int) dbl;
		Integer val = Integer.valueOf(intgr);

		//b.error = -1*Math.log(zl3[data_x[data_x.length-1]]);
		
		//b.error = -1*Math.log(zl3[val]);
		b.error = (1-zl3[val]);
		//b.error = (1-zl3[val])*zl3[val]; 

		//Updating gradients
		/*for (j=0;j<b.grad2.length;j++)
		{
			//for (k=0;k<grad2[0].length;k++)
			//{
				//b.grad2[j][b.output] = b.error * zl2[j] * diff_sigmoid(al3[b.output]);
				b.grad2[j][b.output] = b.error * zl2[j] * diff_sigmoid(al3[val]);
			//}
		}
		for (i=0;i<b.grad1.length;i++)
		{
			for (j=0;j<b.grad1[0].length;j++)
			{
				//b.grad1[i][j] = data_x[i] * b.error * arrw2[j][b.output] * diff_sigmoid(al2[j]);
				b.grad1[i][j] = data_x[i] * b.error * arrw2[j][val] * diff_sigmoid(al2[j]);
			}
		}*/

		//Updating gradients 2.0
		for (i=0;i<b.ev.length;i++)
		{
			if(i!=val)
			{
				//b.ev[i] = -1 * Math.log(1-zl3[i]);
				b.ev[i] = (0-zl3[val]);
				//b.ev[i] = (0-zl3[val])*zl3[val];
			}
			else if(i==val)
			{
				b.ev[i] = b.error;
			}
		}
		for (j=0;j<b.grad2.length;j++)
		{
			for (k=0;k<b.grad2[0].length;k++)
			{
				b.grad2[j][k]+=b.ev[k] * diff_sigmoid(al3[k]) * zl2[j];
			}
		}
		double dd;	
		for (i=0;i<b.grad1.length;i++)
		{
			for (j=0;j<b.grad1[0].length;j++)
			{
				dd=0.0;
				for (k=0;k<b.grad2[0].length;k++)
				{
					dd+=b.ev[k] * arrw2[j][k] * diff_sigmoid(al2[j]);
				}
				b.grad1[i][j]=data_x[i] * dd;
			}
		}

		return b;
	}

	public static void get_new_weights (Double [] operror, Double [][] arrw1, Double [][] arrw2, Double [][] arrwt1, Double [][] arrwt2, Double [][] grads1, Double [][] grads2)
	{
		int i,j,k;
		for (j=0;j<arrwt1.length;j++)
		{
			for (k=0;k<arrwt1[0].length;k++)
			{
				arrwt1[j][k]=arrw1[j][k]-0.001*(grads1[j][k]/batch_size);
				//arrwt1[j][k]=arrw1[j][k]-0.05*(grads1[j][k]);
			}
		}
		for (j=0;j<arrwt2.length;j++)
		{
			for (k=0;k<arrwt2[0].length;k++)
			{
				arrwt2[j][k]=arrw2[j][k]-0.001*(grads2[j][k]/batch_size);
				//arrwt2[j][k]=arrw2[j][k]-0.05*(grads2[j][k]);
			}
		}
	}

	public static double get_accuracy (Double [][] arrw1, Double [][] arrw2, ArrayList <Double [] > T_Data, Double [] al2, Double [] al3, Double [] zl2, Double [] zl3, int m)
	{
		int i,j,n; double pos,neg; 
		Box b; Double [] arr; pos=0.0; neg=0.0;
		for (i=0;i<T_Data.size();i++)
		{
			arr = T_Data.get(i);
			b = get_verdict(arrw1, arrw2, arr, al2, al3, zl2, zl3, m);
			
			double dbl;// = myDouble.doubleValue();
			dbl = arr[arr.length-1];
			int intgr = (int) dbl;
			Integer val = Integer.valueOf(intgr);

			/*if(b.output == arr[arr.length-1])
				pos++;
			else 
				neg++;*/
			if(b.output == val) pos=pos+1;
		}
		return (pos/(T_Data.size()));
	}

	public static void update_weights (Double [][] arrw1, Double [][] arrw2, Double [][] arrwt1, Double [][] arrwt2)
	{
		int i,j,k;
		for (i=0;i<arrw1.length;i++)
		{
			for (j=0;j<arrw1[0].length;j++)
				arrw1[i][j]=arrwt1[i][j];
		}
		for (i=0;i<arrw2.length;i++)
		{
			for (j=0;j<arrw2[0].length;j++)
				arrw2[i][j]=arrwt2[i][j];
		}
	}

	public static double sigmoid(double x) 
	{
    	return (1.0 / (1 + Math.exp(-x)));
	}

	public static double diff_sigmoid(double x)
	{
		return (Math.exp(-x) * (1/(1 + Math.exp(-x))) * (1/(1 + Math.exp(-x))));
	}

}

class Box
{
	int output;
	double error;
	Double [][]grad1, grad2;
	Double [] ev;
	int i,j,k;
	Box(int m)
	{
		//Double [][]grads1, grads2;
		grad1 = new Double [64][m];
		grad2 = new Double [m][10];

		for (j=0;j<grad1.length;j++)
		{
			for (k=0;k<grad1[0].length;k++)
				grad1[j][k]=0.0;
		}
		for (j=0;j<grad2.length;j++)
		{
			for (k=0;k<grad2[0].length;k++)
				grad2[j][k]=0.0;
		}

		ev = new Double [10];
		for (i=0;i<10;i++) ev[i]=0.0;
	}
}