package Server;

import Models.Matrix;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 18896;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                System.out.println("Waiting for a client...");
                Socket socket = serverSocket.accept();//ждет запрос соединения и возвр нью сокет с клайент
                System.out.println("Client connected.");

                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());//на клиенте ща берем out и обертываем, ибо почему нет
                System.out.println("Create input stream for object");
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());// невероятно, но на клиенте in
                System.out.println("Create output stream for object");

                int[][] m_a = (int[][]) in.readObject();//читаем объект (десериализуем ееее)
                System.out.println("Read matrix a");
                Matrix a = new Matrix(m_a.length, m_a[0].length);
                System.out.println("Create Matrix a object");
                a.setMatrix(m_a);
                System.out.println("Read first:\n" + a);//когда Артур молодец и предложил переопреписать тустринг

                int[][] m_b = (int[][]) in.readObject();
                System.out.println("Read matrix b");
                Matrix b = new Matrix(m_b.length, m_b[0].length);
                System.out.println("Create Matrix b object");
                b.setMatrix(m_b);
                System.out.println("Read second:\n" + b);

                Matrix c = Matrix.sum(a, b);
                System.out.println("Calculate result.");

                out.writeObject(c.getMatrix()); //отправим-ка(сериализуем)
                out.flush();
                System.out.println("Send result.");

                in.close();
                out.close();
                socket.close();
                System.out.println("Close connection with client.");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
