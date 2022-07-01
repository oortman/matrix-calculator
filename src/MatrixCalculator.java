import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This is the main class for my Matrix Calculator program.
 * The Matrix Calculator can be used to perform several operations on up to matrices.
 *
 * @author Alec Oortman
 */
public class MatrixCalculator {
    private static ArrayList<double[][]> listOfMatrices;

    /**
     * Creates a matrix based on the specified file.
     * @param filePath Path to the .txt file used for matrix creation
     * @param rows Number of rows in the matrix
     * @param cols Number of columns in the matrix
     */
    private static void createMatrix(String filePath, int rows, int cols) {
        /* Tries creating a File from the specified path and creates a Scanner to read it.
         * Catches exception if file is not found. */
        try {
            File file = new File(filePath);  // Text file that corresponds to specified file path
            Scanner fileScanner = new Scanner(file);  // Scanner for reading file

            double[][] matrix = new double[rows][cols];

            // Populates matrix with values from file
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = fileScanner.nextDouble();
                }
            }

            listOfMatrices.add(matrix);
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Try entering another file path.");
        }
    }

    /**
     * Finds the product of a matrix and a numerical value (scalar).
     * @param matrix Matrix to be used in multiplication
     * @param scalar Numerical value to be used in multiplication
     * @return The resulting matrix of the multiplication
     */
    private static double[][] multiplyByScalar(double[][] matrix, double scalar) {
        double[][] result = new double[matrix.length][matrix[0].length];  // Resulting matrix

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                result[row][col] = scalar * matrix[row][col];
            }
        }

        return result;
    }

    /**
     * Finds the product of two matrices.
     * Note: Order matters. Matrix A times Matrix B does not have the same product as Matrix B times
     * Matrix A.
     * @param matrixA The first matrix in the multiplication
     * @param matrixB The second matrix in the multiplication
     * @return The product of the two matrices
     */
    private static double[][] multiplyByMatrix(double[][] matrixA, double[][] matrixB) {
        double[][] product = new double[matrixA.length][matrixB[0].length];

        // Finds each entry in the matrix product by calling helper method findEntry
        for (int i = 0; i < product.length; i++) {
            for (int j = 0; j < product[0].length; j++) {
                product[i][j] = findEntry(matrixA, matrixB, i, j);
            }
        }

        return product;
    }

    /**
     * Calculates the value for a specific entry of a matrix product.
     * @param matrixA The first matrix in the multiplication
     * @param matrixB The second matrix in the multiplication
     * @param row The row of the first matrix used in entry calculation
     * @param col The column of the second matrix used in entry calculation
     * @return The calculated value for the entry
     */
    private static double findEntry(double[][] matrixA, double[][] matrixB, int row, int col) {
        double entryValue = 0;

        for (int i = 0; i < matrixB.length; i++) {
            entryValue += matrixA[row][i] * matrixB[i][col];
        }

        return entryValue;
    }

    /**
     * Checks if two matrices can be multiplied.
     * The number of columns in the first matrix must be equal to the number of rows in the second
     * matrix in order for them to be multiplied.
     * @param matrixA The first matrix to be used in matrix multiplication
     * @param matrixB The second matrix to be used in matrix multiplication
     * @return True if the matrices can be multiplied, false otherwise.
     */
    private static boolean canBeMultiplied(double[][] matrixA, double[][] matrixB) {
        return matrixA[0].length == matrixB.length;
    }

    /**
     * Finds the sum of two matrices.
     * @param matrixA The first matrix to be used in matrix addition
     * @param matrixB The second matrix to be used in matrix addition
     * @return The sum of the two matrices
     */
    private static double[][] addMatrices(double[][] matrixA, double[][] matrixB) {
        double[][] sum = new double[matrixA.length][matrixA[0].length];

        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                sum[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }

        return sum;
    }

    /**
     * Checks if two matrices can be added.
     * Matrices must have the same dimensions (same number of rows and columns) to be added.
     * @param matrixA The first matrix to be used in matrix addition
     * @param matrixB The second matrix to be used in matrix addition
     * @return True if the matrices can be added, false otherwise.
     */
    private static boolean canBeAdded(double[][] matrixA, double[][] matrixB) {
        return matrixA.length == matrixB.length && matrixA[0].length == matrixB[0].length;
    }

    /**
     * Prints a matrix to the console.
     * @param matrix Matrix to be printed
     */
    private static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    /**
     * Main method for the Matrix Calculator program.
     * @param args Not used.
     */
    public static void main(String[] args) {
        listOfMatrices = new ArrayList<>();
        createMatrix("C:\\Users\\aoort\\Desktop\\Matrices\\2x3_Matrix.txt", 2, 3);
        printMatrix(listOfMatrices.get(0));
        System.out.println();
        createMatrix("C:\\Users\\aoort\\Desktop\\Matrices\\2x2_Matrix.txt", 2, 2);
        printMatrix(listOfMatrices.get(1));
        System.out.println();

        if (canBeMultiplied(listOfMatrices.get(1), listOfMatrices.get(0))) {
            printMatrix(multiplyByMatrix(listOfMatrices.get(1), listOfMatrices.get(0)));
        } else {
            System.out.println("These matrices cannot be multiplied");
        }

        System.out.println();

        if (canBeMultiplied(listOfMatrices.get(0), listOfMatrices.get(1))) {
            printMatrix(multiplyByMatrix(listOfMatrices.get(1), listOfMatrices.get(0)));
        } else {
            System.out.println("These matrices cannot be multiplied");
        }

        createMatrix("C:\\Users\\aoort\\Desktop\\Matrices\\3x3_Matrix1.txt", 3, 3);
        createMatrix("C:\\Users\\aoort\\Desktop\\Matrices\\3x3_Matrix2.txt", 3, 3);
        printMatrix(addMatrices(listOfMatrices.get(2), listOfMatrices.get(3)));
    }
}
