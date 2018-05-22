package Server;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Filter extends Remote {
    void filter() throws RemoteException;
    void saveImageToFile(String filename, String format) throws IOException;
    void loadImageFromFile(String filename) throws IOException;
}
