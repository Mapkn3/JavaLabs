import Models.Matrix;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Matrix m = new Matrix(2, 3);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                m.setElement(i, j, i+j);
            }
        }
        File firstFile = new File("C:\\Users\\Kondrat\\Desktop\\first.txt");
        File secondFile = new File("C:\\Users\\Kondrat\\Desktop\\second.txt");
        Matrix.writeMatrixToFile(m, firstFile);
        Matrix n = Matrix.readMatrixFromFile(firstFile);
        System.out.println(n);
        System.out.println(Matrix.sum(m, n));
        Matrix.writeMatrixToFile(Matrix.sum(m, n), secondFile);
    }
}
