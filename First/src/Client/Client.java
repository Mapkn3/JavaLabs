package Client;

import Models.Matrix;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    //C:\\Users\\Kondrat\\Desktop\\first.txt C:\\Users\\Kondrat\\Desktop\\second.txt C:\\Users\\Kondrat\\Desktop\\result.txt
    public static void main(String[] args) {
        int serverPort = 18896;
        String serverAddress = "127.0.0.1";

        try {
            for (int i = 0; i < args.length; i++ ) {
                System.out.println(args[i]);
            }
            InetAddress ipAddress = InetAddress.getByName(serverAddress); //ИНЕТАДРЭСС=АйПИ
            Socket socket = new Socket(ipAddress, serverPort);
            System.out.println("Connect to server...");

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Create output stream for object");
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Create input stream for object");

            Matrix a = Matrix.readMatrixFromFile(new File(args[0]));
            System.out.println("First:\n" + a);
            Matrix b = Matrix.readMatrixFromFile(new File(args[1]));
            System.out.println("Second:\n" + b);

            out.writeObject(a.getMatrix());
            out.flush();
            System.out.println("Send first.");

            out.writeObject(b.getMatrix());
            out.flush();
            System.out.println("Send second.");

            int[][] result = (int[][]) in.readObject();//цитаты для самых маленьких: "о, что-то вы долго, я уже устал ждать!"
            System.out.println("Read result.");

            if (result.length != 0) {
                Matrix m = new Matrix(result.length, result[0].length);
                m.setMatrix(result);
                System.out.println(m);
                Matrix.writeMatrixToFile(m, new File(args[2]));
            } else {
                try (FileOutputStream fis = new FileOutputStream(new File(args[2]))) {
                    fis.write("Error! Different dimensions of matrices.".getBytes());
                } catch (IOException  e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Save result");

            in.close();
            out.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
