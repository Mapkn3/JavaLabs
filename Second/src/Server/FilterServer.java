package Server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class FilterServer {
    public static void main(String[] args) {
        try {
            FilterImpl filter = new FilterImpl();
            System.out.println("Create filter object");

            Filter filterStub = (Filter) UnicastRemoteObject.exportObject(filter, 0);
            System.out.println("Create stub for filter object");
            Registry registry = LocateRegistry.getRegistry();
            System.out.println("Get RMI registry");
            registry.bind("filter", filterStub);
            System.out.println("Registry stub in RMI");

            System.err.println("Server ready");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
