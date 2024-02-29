import java.io.File;
import java.io.IOException;


public class ContactManagementSystem {
    public static void main(String[] args) throws IOException {
        TextInterface.clearConsole();
        System.out.println("==========WELCOME==========");
        File f = new File("users.csv");
        TextInterface textInterface = new TextInterface(f);
        textInterface.loginMenu(f);
    }

    
}
