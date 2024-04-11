import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuSolverGUI extends JFrame {
    private JTextField[][] cells;
    private JButton solveButton;
    private int[][] board;

    public SudokuSolverGUI() {
        setTitle("Sudoku Solver");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 9));

        cells = new JTextField[9][9];
        board = new int[9][9];

        // Initialize text fields and add to the frame
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new JTextField();
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                add(cells[i][j]);
            }
        }

        solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                readBoard();
                if (solve()) {
                    updateBoard();
                } else {
                    JOptionPane.showMessageDialog(SudokuSolverGUI.this,
                            "No solution exists for the given Sudoku puzzle.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);
        add(buttonPanel);

        setVisible(true);
    }

    private void readBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String value = cells[i][j].getText();
                if (!value.isEmpty()) {
                    board[i][j] = Integer.parseInt(value);
                } else {
                    board[i][j] = 0;
                }
            }
        }
    }

    private boolean solve() {
        return solveSudoku(0, 0);
    }

    private boolean solveSudoku(int row, int col) {
        if (row == 9) {
            row = 0;
            if (++col == 9) {
                return true;
            }
        }
        if (board[row][col] != 0) {
            return solveSudoku(row + 1, col);
        }
        for (int num = 1; num <= 9; num++) {
            if (isValid(row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(row + 1, col)) {
                    return true;
                }
                board[row][col] = 0;
            }
        }
        return false;
    }

    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num
                    || board[row - row % 3 + i / 3][col - col % 3 + i % 3] == num) {
                return false;
            }
        }
        return true;
    }

    private void updateBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setText(Integer.toString(board[i][j]));
            }
        }
    }

    public static void main(String[] args) {
        new SudokuSolverGUI();
    }
}
