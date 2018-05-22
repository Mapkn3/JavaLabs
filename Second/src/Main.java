import java.io.File;
import java.io.IOException;
//ихихихих, ленивый запуск от Артура
public class Main {
    public static void main(String[] args) {
        try {
            String projectDir = "D:\\zzz\\help\\Second\\src\\";
            String destDir = projectDir + "target\\";
            String inputImagePath = "D:\\zzz\\help\\Second\\src\\DSP\\in.jpg";
            String outputImagePath = "D:\\zzz\\help\\Second\\src\\DSP\\out.png";
            String javacCmd = "javac -d target Server\\* Client\\FilterClient.java";
            String rmiCmd = "start rmiregistry";
            String serverCmd = "start java Server.FilterServer";
            String clientCmd = "start java Client.FilterClient " + inputImagePath + " " + outputImagePath;

            File img = new File(projectDir + "DSP\\out.png");
            if (img.exists()) {
                System.out.println("Delete old out.png: " + img.delete());
            }

            File target = new File(destDir);
            if (target.exists()) {
                recursiveDeleteDir(target);
            }
            System.out.println("Create folder for .class files: " + target.mkdir());
            System.out.println("Project updating...");
            Thread.sleep(1000);
            execCmd(javacCmd, projectDir);
            execCmd(rmiCmd, destDir);
            Thread.sleep(3000);
            execCmd(serverCmd, destDir);
            Thread.sleep(5000);
            execCmd(clientCmd, destDir);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void execCmd(String cmd, String execDir) throws IOException {//штука из великого и могучего Интернета
        String consoleCmd[] = {"cmd.exe", "/C", cmd};//с "/C" работает, это главное
        System.out.println(String.format("Start \"%s\"", cmd));
        Runtime.getRuntime().exec(consoleCmd, null, new File(execDir));
        System.out.println(String.format("\"%s\" completed!", cmd));
    }

    private static  void recursiveDeleteDir(File dir) { //очищение папки и ее удаление
        if (dir.isDirectory()) {
            for (File subdir : dir.listFiles()) {
                recursiveDeleteDir(subdir);
            }
        }
        System.out.println("Deleting " + dir.getAbsolutePath() + ": " + dir.delete());
    }
}
