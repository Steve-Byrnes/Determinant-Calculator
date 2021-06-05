import java.util.ArrayList;
import java.util.Scanner;

public class DeterminantCalculatorDouble {

    //Exceptions
    public static boolean isNotDouble(String string) {
        if (string == null) {
            return true;
        } try {
            Double.parseDouble(string);
        } catch (NumberFormatException exception) {
            System.out.println("Error: Matrix rows must be of double-type");
            return true;
        }
        return false;
    }

    //User-input matrix method
    public static double[][] matrixForge() {
        System.out.println("Enter the rows of matrix (Ex. 1 2.3 4.56 7.0): ");
        Scanner userInput = new Scanner (System.in);
        String[] rowOne = userInput.nextLine().split(" ");
        double[] rowOneDouble = new double[rowOne.length];
        for (int i = 0; i < rowOne.length; i++) {
            if (isNotDouble(rowOne[i])) {
                return null;
            }
            rowOneDouble[i] = Double.parseDouble(rowOne[i]);
        }

        double[][] matrixBig = new double[rowOneDouble.length][rowOneDouble.length];
        matrixBig[0] = rowOneDouble;
        int i = 1;
        while (i < rowOne.length) {
            String[] rowMore = userInput.nextLine().split(" ");
            double[] rowMoreDouble = new double[rowMore.length];
            for (int j = 0; j < rowMore.length; j++) {
                if (isNotDouble(rowMore[j])) {
                    return null;
                } else {
                    rowMoreDouble[j] = Double.parseDouble(rowMore[j]);
                    if (rowMoreDouble.length != rowOneDouble.length) {
                        System.out.println("Error: Rows must be the same size");
                        return null;
                    }
                    matrixBig[i] = rowMoreDouble;
                }
            }
            i++;
        }
        userInput.close();
        return matrixBig;
    }

    //Creating matrix for staggered 1's and -1's method
    public static int[][] matrixSign(double[][] matrix) {
        int[][] matrixOfSigns = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrixOfSigns.length; i++) {
            for (int j = 0; j < matrixOfSigns.length; j++) {
                if ((i+j) % 2 == 0) {
                    matrixOfSigns[i][j] = 1;
                } else {
                    matrixOfSigns[i][j] = -1;
                }
            }
        }
        return matrixOfSigns;
    }

    //Shrinking Matrix method
    public static double[][] matrixShrink(double[][] matrix, int column) {
        ArrayList<ArrayList<Double>> matrixSmaller = new ArrayList<>();
        for (int i = 1; i < matrix.length; i++) {
            ArrayList<Double> tempArray = new ArrayList<>();
            for (int j = 0; j < matrix[0].length; j++) {
                if (j != column) {
                    tempArray.add(matrix[i][j]);
                }
            }
            matrixSmaller.add(tempArray);
        }
        double[][] matrixSmallerDouble = new double[matrix.length - 1][matrix.length - 1];
        for (int i = 0; i < matrixSmaller.size(); i++) {
            for (int j = 0; j < matrixSmaller.size(); j++) {
                matrixSmallerDouble[i][j] = matrixSmaller.get(i).get(j);
            }
        }
        return matrixSmallerDouble;
    }

    //Determinant method
    public static double deter(double[][] matrix) {
        double deterSum = 0;
        if (matrix == null) {
            return 0;
        } else if (matrix.length == 1) {
            return matrix[0][0];
        } else if (matrix.length == 2) {
            return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
        } else {
            int[][] matrixOfSigns = matrixSign(matrix);
            for (int i = 0; i < matrix.length; i++) {
                //Summing determinant of regions
                deterSum += (matrixOfSigns[0][i] * matrix[0][i]) * deter(matrixShrink(matrix, i));
            }
            return deterSum;
        }
    }

    public static void main(String[] args) {
        double[][] matrix = matrixForge();
        if (matrix != null) {
            System.out.print("The determinant of your matrix is: " + deter(matrix));
        }
    }
}
