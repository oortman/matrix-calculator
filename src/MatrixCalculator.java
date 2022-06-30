import java.io.File;
import java.io.FileNotFoundException;
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
     * Main method for the Matrix Calculator program.
     * @param args Not used.
     */
    public static void main(String[] args) {
        createMatrix("C:\\Users\\aoort\\Desktop\\SampleMatrix1.txt", 2, 2);
    }
}
