package Client;

import Server.Filter;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FilterClient {
    public FilterClient() {}

    public static void main(String[] args) {
        try {

            System.setProperty("java.security.policy", "D:\\zzz\\help\\Second\\src\\Client\\client.policy");
            System.out.println("Set property for security policy");
            System.setSecurityManager(new RMISecurityManager());//умная идеея
            System.out.println("Set security manager");

            Registry registry = LocateRegistry.getRegistry(null);
            System.out.println("Get RMI registry");
            Filter filter = (Filter) registry.lookup("filter");
            System.out.println("Get filter stub from RMI");

            String inputImagePath = args[0]; //"D:\\zzz\\help\\Second\\src\\DSP\\in.jpg"; //когда вспоминаешь о передаче параметров через аргументы в последний момент)))
            String outputImagePath = args[1]; //"D:\\zzz\\help\\Second\\src\\DSP\\out.png";

            filter.loadImageFromFile(inputImagePath);
            System.out.println("Load image: " + inputImagePath);
            System.out.println("Processing image...");
            filter.filter();
            System.out.println("Processing image complete!");
            filter.saveImageToFile(outputImagePath, "png");
            System.out.println("Save image: " + outputImagePath);
        } catch (IOException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}