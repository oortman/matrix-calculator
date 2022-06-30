import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This is the main class for my Matrix Calculator program.
 * The Matrix Calculator can be used to perform several operations on up to matrices.
 *
 * @author Alec Oortman
 */
public class MatrixCalculator {
    private static double[][] matrix;  // 2D array that represents a matrix

    /**
     * Creates a matrix based on the specified file.
     * @param filePath Path to the .txt file used for matrix creation
     * @param rows Number of rows in the matrix
     * @param cols Number of columns in the matrix
     */
    private static void createMatrix(String filePath, int rows, int cols) {
        /* Tries to create a File from the specified path and creates a Scanner to read it.
         * Catches exception if file is not found. */
        try {
            File file = new File(filePath);  // Text file that corresponds to specified file path
            Scanner fileScanner = new Scanner(file);  // Scanner for reading file

            matrix = new double[rows][cols];

            // Populates matrix with values from file
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = fileScanner.nextDouble();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Try entering another file path.");
        }
    }

    /**
     * Multiplies a matrix by a numerical value (scalar).
     * @param matrix Matrix to use in multiplication
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
     * Prints matrix to console.
     * @param matrix Matrix to be printed
     */
    private static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }

    /**
     * Main method for the Matrix Calculator program.
     * @param args Not used.
     */
    public static void main(String[] args) {
        createMatrix("C:\\Users\\aoort\\Desktop\\SampleMatrix1.txt", 2, 2);
        printMatrix(matrix);
        System.out.println();
        printMatrix(multiplyByScalar(matrix, -3));
    }
}
