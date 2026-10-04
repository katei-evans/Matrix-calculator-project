package matrixcalculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MatrixCalculator extends Application {

    private static final int SIZE = 2; // Default 2x2 Matrix
    private final TextField[][] inputA = new TextField[SIZE][SIZE];
    private final TextField[][] inputB = new TextField[SIZE][SIZE];
    private final Label[][] resultDisplay = new Label[SIZE][SIZE];
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        // UI Layout setups
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Grid Panes for Matrix A, Matrix B, and Result
        GridPane gridA = createMatrixGrid(inputA, "Matrix A");
        GridPane gridB = createMatrixGrid(inputB, "Matrix B");
        GridPane gridResult = createResultGrid("Result");

        HBox matricesBox = new HBox(20, gridA, gridB, gridResult);
        matricesBox.setAlignment(Pos.CENTER);

        // Control Buttons
        Button btnAdd = new Button("Add (A + B)");
        Button btnSub = new Button("Subtract (A - B)");
        Button btnMult = new Button("Multiply (A × B)");
        Button btnTranspose = new Button("Transpose A");
        Button btnDet = new Button("Det(A)");

        HBox buttonsBox = new HBox(10, btnAdd, btnSub, btnMult, btnTranspose, btnDet);
        buttonsBox.setAlignment(Pos.CENTER);

        // Status Label from your original starter code
        statusLabel = new Label("Matrix Calculator Initialized");
        statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");

        // Event Handlers for Buttons
        btnAdd.setOnAction(e -> calculate('+'));
        btnSub.setOnAction(e -> calculate('-'));
        btnMult.setOnAction(e -> calculate('*'));
        btnTranspose.setOnAction(e -> calculate('T'));
        btnDet.setOnAction(e -> calculate('D'));

        root.getChildren().addAll(matricesBox, buttonsBox, statusLabel);

        // Window scene and stage config (matching original window title)
        Scene scene = new Scene(root, 650, 350);
        primaryStage.setTitle("Object-Oriented Programming I-Matrix Project");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to create input grids
    private GridPane createMatrixGrid(TextField[][] fields, String title) {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);

        Label label = new Label(title);
        label.setStyle("-fx-font-weight: bold;");
        grid.add(label, 0, 0, SIZE, 1);

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                fields[r][c] = new TextField("0");
                fields[r][c].setPrefWidth(50);
                fields[r][c].setAlignment(Pos.CENTER);
                grid.add(fields[r][c], c, r + 1);
            }
        }
        return grid;
    }

    // Helper method to create non-editable display grid for output
    private GridPane createResultGrid(String title) {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);

        Label label = new Label(title);
        label.setStyle("-fx-font-weight: bold;");
        grid.add(label, 0, 0, SIZE, 1);

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                resultDisplay[r][c] = new Label("0");
                resultDisplay[r][c].setPrefWidth(50);
                resultDisplay[r][c].setPrefHeight(25);
                resultDisplay[r][c].setAlignment(Pos.CENTER);
                resultDisplay[r][c].setStyle("-fx-border-color: gray; -fx-background-color: #f0f0f0;");
                grid.add(resultDisplay[r][c], c, r + 1);
            }
        }
        return grid;
    }

    // Read values from input grid into Matrix object
    private Matrix parseMatrix(TextField[][] fields) throws NumberFormatException {
        double[][] values = new double[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                values[r][c] = Double.parseDouble(fields[r][c].getText().trim());
            }
        }
        return new Matrix(values);
    }

    // Render resulting Matrix object into output grid
    private void displayResult(Matrix res) {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                resultDisplay[r][c].setText(String.format("%.1f", res.getValue(r, c)));
            }
        }
    }

    // Main calculation dispatcher
    private void calculate(char operation) {
        try {
            Matrix matA = parseMatrix(inputA);
            Matrix matB = parseMatrix(inputB);
            Matrix res = null;

            switch (operation) {
                case '+':
                    res = matA.add(matB);
                    statusLabel.setText("Status: Addition Completed.");
                    break;
                case '-':
                    res = matA.subtract(matB);
                    statusLabel.setText("Status: Subtraction Completed.");
                    break;
                case '*':
                    res = matA.multiply(matB);
                    statusLabel.setText("Status: Multiplication Completed.");
                    break;
                case 'T':
                    res = matA.transpose();
                    statusLabel.setText("Status: Matrix A Transposed.");
                    break;
                case 'D':
                    double det = matA.determinant();
                    statusLabel.setText("Status: Determinant of Matrix A = " + det);
                    return;
            }

            if (res != null) {
                displayResult(res);
            }
        } catch (NumberFormatException ex) {
            statusLabel.setText("Error: Please enter valid numbers in all inputs.");
        } catch (Exception ex) {
            statusLabel.setText("Error: " + ex.getMessage());
        }
    }

    // Launching the application (matching your original main method)
    public static void main(String[] args) {
        launch(args);
    }
}