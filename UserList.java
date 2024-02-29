import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class UserList extends ArrayList<User> {
    public  UserList(File file) throws FileNotFoundException {
        Scanner input =new Scanner(file);
        while (input.hasNextLine()){

            StringTokenizer s=new StringTokenizer(input.nextLine(),",");
            if(s.hasMoreTokens()){
            User user=new User(s.nextToken(),s.nextToken());
            this.add(user);}

            else {
                System.out.println("Register.");
            }
        }
        input.close();
    }
    
    public boolean authenticateUser(String username,String password){
        boolean found=true;
        for(User u:this){
            if(u.getUsername().equals(username)){
                if(u.getPassword().equals(password)){
                    return true;
                }
            }
            else {
                found=false;
            }
        }
        return found;

    }
}
