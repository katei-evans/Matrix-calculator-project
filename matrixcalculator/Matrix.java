package matrixcalculator;

public class Matrix {
    private final int rows;
    private final int cols;
    private final double[][] data;

    // Constructor to initialize an empty matrix of given dimensions
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    // Constructor to initialize with existing 2D array data
    public Matrix(double[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, cols);
        }
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }

    public double getValue(int r, int c) { return data[r][c]; }
    public void setValue(int r, int c, double val) { data[r][c] = val; }

    // Matrix Addition: C[i][j] = A[i][j] + B[i][j]
    public Matrix add(Matrix other) throws IllegalArgumentException {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions for addition.");
        }
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.setValue(i, j, this.data[i][j] + other.getValue(i, j));
            }
        }
        return result;
    }

    // Matrix Subtraction: C[i][j] = A[i][j] - B[i][j]
    public Matrix subtract(Matrix other) throws IllegalArgumentException {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions for subtraction.");
        }
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.setValue(i, j, this.data[i][j] - other.getValue(i, j));
            }
        }
        return result;
    }

    // Matrix Multiplication: C[i][j] = sum(A[i][k] * B[k][j])
    public Matrix multiply(Matrix other) throws IllegalArgumentException {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Columns of Matrix A must match rows of Matrix B.");
        }
        Matrix result = new Matrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                double sum = 0;
                for (int k = 0; k < this.cols; k++) {
                    sum += this.data[i][k] * other.getValue(k, j);
                }
                result.setValue(i, j, sum);
            }
        }
        return result;
    }

    // Transpose: Swaps rows and columns
    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.setValue(j, i, this.data[i][j]);
            }
        }
        return result;
    }

    // Determinant calculation for 2x2 and 3x3 matrices
    public double determinant() throws IllegalArgumentException {
        if (rows != cols) {
            throw new IllegalArgumentException("Determinant requires a square matrix.");
        }
        if (rows == 2) {
            return (data[0][0] * data[1][1]) - (data[0][1] * data[1][0]);
        } else if (rows == 3) {
            return data[0][0] * (data[1][1] * data[2][2] - data[1][2] * data[2][1])
                 - data[0][1] * (data[1][0] * data[2][2] - data[1][2] * data[2][0])
                 + data[0][2] * (data[1][0] * data[2][1] - data[1][1] * data[2][0]);
        } else {
            throw new UnsupportedOperationException("Determinant currently supported for 2x2 and 3x3 matrices.");
        }
    }
}