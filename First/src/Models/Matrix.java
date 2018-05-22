package Models;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Matrix {
    private int[][] matrix;
    private int height;
    private int width;

    public Matrix(int height, int width) {
        this.height = height;
        this.width = width;
        this.matrix = new int[height][width];
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getElement(int height, int width) {
        Integer element = null;
        if (height >= 0 && width >= 0 && height < this.height && width < this.width) {
            element = this.matrix[height][width];
        }
        return element;
    }

    public boolean setElement(int height, int width, int value) {
        if (height >= 0 && width >= 0 && height < this.height && width < this.width) {
            this.matrix[height][width] = value;
            return true;
        }
        return false;
    }

    public static Matrix sum(Matrix a, Matrix b) {
        Matrix result = new Matrix(0, 0);
        if (a.getHeight() == b.getHeight() && a.getWidth() == b.getWidth()) {
            int height = a.getHeight();
            int width = a.getWidth();
            result = new Matrix(height, width);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    result.setElement(i, j, a.getElement(i, j) + b.getElement(i, j));
                }
            }
        }
        return result;
    }

    public static void writeMatrixToFile(Matrix matrix, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            String result = String.valueOf(matrix.getHeight()) + ' ' + String.valueOf(matrix.getWidth()) + '\n' + matrix.toString();
            writer.write(result);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Matrix readMatrixFromFile(File file) {
        Matrix result = null;
        try (Scanner scanner = new Scanner(new FileReader(file))) { // трай с ресурсами, штоб потом поток закрылся, как мой экз по тсп (а в т о м а т и ч е с к и), когда поток не закрывается - нехорошо, а когда экз - еще хуже
            String size = scanner.nextLine();
            String[] params = size.split(" ");//сплит - "1 2 3".split(" ") = ["1", "2", "3"]; "дурдур".split("у") = ["д", "рд", "р"]
            int height = Integer.parseInt(params[0]);
            int width = Integer.parseInt(params[1]);
            result = new Matrix(height, width);
            for (int i = 0; i < height; i++) {
                String[] row = scanner.nextLine().split(" ");
                for (int j = 0; j < width; j++) {
                    result.setElement(i, j, Integer.parseInt(row[j]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                sb.append(this.matrix[i][j]).append(' ');
            }
            sb.deleteCharAt(sb.length()-1).append('\n');
        }
        return sb.toString().trim(); //прост удаляет пробелы с начала и с конца
    }
}
