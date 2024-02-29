import java.util.Scanner;

public class Contact {
    // data attributes
    private String name, phoneNum, email, address;
    private int id;
    // might add other fields later or change some fields to lists
//-------------------------------------------------------------------------------------

    // constructors
    public Contact() {}
    
    public Contact(String name, String phoneNum, String email, String address) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
        this.address = address;
    }
//-----------------------------------------------------------------------------------------

    // getters and setters
    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public int getId() {
        return id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setId(int id) {
        this.id = id;
    }
//------------------------------------------------------------------------------------------

    // read data from console
    private static String readData(Scanner input, String field, boolean optional) {
        String data;
        do {
            System.out.print(field + ": ");
            data = input.nextLine();
            if (data.isEmpty()) {
                if (optional) {
                    return null;
                }
                else {
                    System.out.println(field + " cannot be empty.");
                }
            }
        } while(data.isEmpty());
        return data;
    }

    public static String readName(Scanner input) {
        String name = readData(input, "Contact Name", false);
        return name;
    }

    public static String readPhoneNum(Scanner input) {
        String phoneNum = readData(input, "Phone Number", false);
        return phoneNum;
    }

    public static String readEmail(Scanner input) {
        String email = readData(input, "Email", true);
        return email;
    }

    public static String readAddress(Scanner input) {
        String address = readData(input, "Address", true);
        return address;
    }
//-----------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "Contact Name: " + name + "\nPhone Number: " + phoneNum + "\nEmail: " + email + "\nAddress: " + address;
    }
}