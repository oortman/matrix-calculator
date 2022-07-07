import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This is the main class for my Matrix Calculator program.
 * The Matrix Calculator can be used to perform the following operations:
 * 1. Matrix addition and subtraction
 * 2. Matrix multiplication
 * 3. Scalar multiplication
 * 4. Determinant calculation
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
     * Finds the sum or difference of two matrices by adding or subtracting them.
     * @param matrixA The first matrix to be used in calculation
     * @param matrixB The second matrix to be used in calculation
     * @param subtract True if subtracting, false if adding
     * @return The sum/difference of the two matrices
     */
    private static double[][] addSubtractMatrices(double[][] matrixA,
                                                  double[][] matrixB,
                                                  boolean subtract) {
        double[][] sum = new double[matrixA.length][matrixA[0].length];

        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                // Negates the entry in the second matrix if performing subtraction
                if (subtract) {
                    sum[i][j] = matrixA[i][j] + matrixB[i][j] * -1;
                } else {
                    sum[i][j] = matrixA[i][j] + matrixB[i][j];
                }
            }
        }

        return sum;
    }

    /**
     * Checks if two matrices can be added/subtracted.
     * Matrices must have the same dimensions to be added/subtracted.
     * @param matrixA The first matrix to be used in matrix addition
     * @param matrixB The second matrix to be used in matrix addition/subtraction
     * @return True if the matrices can be added, false otherwise.
     */
    private static boolean canAddAndSubtract(double[][] matrixA, double[][] matrixB) {
        return matrixA.length == matrixB.length && matrixA[0].length == matrixB[0].length;
    }

    /**
     * Finds the determinant of any square matrix recursively, using Laplace expansion.
     * Note: Only square matrices have determinants.
     * @param matrix The matrix used in determinant calculation
     * @return The determinant of the matrix
     */
    private static double findDeterminant(double[][] matrix) {
        double D = 0; // Determinant of the matrix
        int n = matrix.length; // Dimension of the matrix (number of rows/columns)

        // When the matrix is 1x1, the determinant is the same as the single value in the matrix
        if (n == 1) {
            return matrix[0][0];
        }

        // When the matrix is 2x2
        if (n == 2) {
            return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
        }

        double[][] subMatrix; // Sub-matrix used in calculation of minor (cofactor))
        int sign = 1; // The sign to be applied to each minor (cofactor) in the expansion

        // Finds each minor in the expansion and adds/subtracts it to/from the determinant
        for (int i = 0; i < n; i++) {
            subMatrix = createSubMatrix(matrix, n, i);
            D += sign * matrix[0][i] * findDeterminant(subMatrix);
            sign *= -1;
        }

        return D;
    }

    /**
     * Generates a sub-matrix for use in finding a minor of the Laplace expansion.
     * @param matrix The original matrix that the sub-matrix is derived from
     * @param n The dimension of the original matrix (number of rows/columns)
     * @param scalarCol The column index of the entry used as a scalar in calculating minor
     * @return The derived sub-matrix
     */
    private static double[][] createSubMatrix(double[][] matrix, int n, int scalarCol) {
        double[][] subMatrix = new double[n-1][n-1];

        // Determines the entries for this sub-matrix
        for (int i = 1; i < n; i++) {
            int subCol = 0; // The column index of the sub-matrix

            // Gets the entry values that are not in the same column or row as the scalar
            for (int j = 0; j < n; j++) {
                if (j != scalarCol) {
                    subMatrix[i-1][subCol] = matrix[i][j];
                    subCol++;
                }
            }
        }

        return subMatrix;
    }

    /**
     * Checks if the specified matrix is square.
     * @param matrix Matrix to be checked
     * @return True if the matrix is square, false otherwise.
     */
    private static boolean isSquare(double[][] matrix) {
        return matrix.length == matrix[0].length;
    }

    /**
     * Finds the transpose of a matrix.
     * @param matrix Matrix to be transposed
     * @return The transpose of the matrix
     */
    private static double[][] findTranspose(double[][] matrix) {
        double[][] transpose = new double[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }

        return transpose;
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
        printMatrix(findTranspose(listOfMatrices.get(0)));
    }
}
