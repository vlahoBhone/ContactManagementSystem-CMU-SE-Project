import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TextInterface {
    private ContactList contactList;
    private Scanner input;
    private UserList userList;

    public TextInterface(File usersFile) throws FileNotFoundException {
        userList = new UserList(usersFile);
        input = new Scanner(System.in);
    }

    private void homeMenu(File contactsFile) throws FileNotFoundException, IOException {
        int cmd;
        while (true) {
            clearConsole();
            System.out.println("****Home Menu****");
            System.out.println("\t1. Add Contact");
            System.out.println("\t2. View Contact-List");
            System.out.println("\t3. Search Contact");
            System.out.println("\t0. Logout");
            System.out.print("Enter your command: ");
            cmd = readInt();
            switch(cmd) {
                case 1:
                    contactList.addContact(input);
                    break;
                case 2:
                    displayContactsMenu();
                    break;
                case 3:
                    searchMenu();
                    break;
                case 0:
                    saveContactList(contactsFile);
                    System.out.println("=======Logged Out=======");
                    return;
                default:
                    System.out.println("Invalid command");
            }
            System.out.println("------------------------------------------");
        }
    }

    private void searchMenu() throws IOException {
        System.out.print("Search: ");
        String keyword = input.nextLine();
        do {
            clearConsole();
            contactList.search(keyword);
        } while (contactsMenu());

    }

    private void displayContactsMenu() throws IOException {
        do {
            clearConsole();
            contactList.displayContacts();
        } while (contactsMenu());
    }

    private boolean contactsMenu() throws IOException {
        int cmd, index;
        System.out.println("****Contact Menu****");
        System.out.println("\t1. View Contact Detail");
        System.out.println("\t2. Remove Contact");
        System.out.println("\t3. Edit Contact Information");
        System.out.println("\t4. Sort");
        System.out.println("\t0. Back");
        System.out.print("Enter command: ");
        cmd = readInt();
        switch (cmd) {
            case 1: {
                index = chooseContactID();
                clearConsole();
                contactList.viewContact(index);
                input.nextLine();
                break;
            }
            case 2:
                index = chooseContactID();
                contactList.removeContact(index);
                break;
            case 3:
                index = chooseContactID();
                updateContactMenu(index);
                break;
            case 4:
                sortMenu();
                break;
            case 0:
                System.out.println("------------------------------------------");
                return false;
            default:
                System.out.println("Invalid Command");
        }
        System.out.println("------------------------------------------");
        return true;
    }

    private void sortMenu() throws IOException {
        clearConsole();
        System.out.println("1. By Name " + (contactList.sortedByName?"(current)":""));
        System.out.println("2. By Date Created " + (contactList.sortedByName?"":"(current)"));
        System.out.print("Sort by: ");
        int sortType = readInt();
        switch (sortType) {
            case 1:
                contactList.sortByName();
                break;
            case 2:
                contactList.sortById();
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    private int chooseContactID() {
        while (true) {
            System.out.print("Choose Contact ID: ");
            int id = readInt();
            if (id < 1 || id > contactList.size()) {
                System.out.println("Invalid ID");
            } else {
                return id;
            }
        }
        
    }

    private void updateContactMenu(int index) throws IOException {
        int field;
        do {
            clearConsole();
            System.out.println("1. Contact Name: " + contactList.get(index-1).getName());
            System.out.println("2. Phone Number: " + contactList.get(index-1).getPhoneNum());
            System.out.println("3. Email: " + contactList.get(index-1).getEmail());
            System.out.println("4. Address: " + contactList.get(index-1).getAddress());
            System.out.println("0. Finished updating");
            System.out.print("Choose the field to be updated: ");
            field = readInt();
            if (field == 0) break;
            contactList.updateContact(input, index, field);
            System.out.println("------------------------------------------");
        } while (true);
    }

    public static void clearConsole() throws IOException {
        System.out.print("\033[H\033[2J\033[H\033[2J\033[H\033[2J\033[H\033[2J");
        System.out.flush();
    }

    private int readInt() {
        String in = input.nextLine();
        try {
            return Integer.parseInt(in);
        } catch (NumberFormatException e) {
            System.out.println("Enter only numbers");
            input.nextLine();
            return -1;
        }
    }

    private static ContactList readContactList(File contactsFile) throws FileNotFoundException {
        Scanner input = new Scanner(contactsFile);
        ContactList contactList = new ContactList();
        while (input.hasNextLine()) {
            StringTokenizer s = new StringTokenizer(input.nextLine(), ",");
            Contact c = new Contact(s.nextToken(), s.nextToken(), s.nextToken(), s.nextToken());
            contactList.addContact(c);
            if (c.getName().equals("Null")) {
                contactList.remove(c);
            }
        }
        input.close();
        contactList.sortByName();
        return contactList;
    }

    private void saveContactList(File contactsFile) throws FileNotFoundException {
        contactList.sortById();
        PrintWriter out = new PrintWriter(contactsFile);
        for (Contact contact : contactList) {
            out.println(contact.getName()+","+contact.getPhoneNum()+","+contact.getEmail()+","+contact.getAddress());
        }
        out.close();
    }

    public void loginMenu(File usersFile) throws FileNotFoundException, IOException {
        int cmd;
        while (true) {
            clearConsole();
            System.out.println("==========WELCOME==========");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Enter command: ");

            cmd = readInt();

            switch (cmd) {
                case 1:
                    login();
                    break;
                case 2:
                    register(usersFile);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid input");
                    continue;
            }
        }
    }

    private File createContactsFile(String username) {
        String filename = "Users\\" + username + ".csv";
        File contactsFile = new File(filename);
        return contactsFile;
    }

    private void login() throws FileNotFoundException, IOException {
        clearConsole();
        System.out.println("-----------------Log In----------------");
        while (true) {
            System.out.println("Enter username: ");
            String name = input.nextLine();
            if (name.isEmpty()) {
                System.out.println("Username cannot be empty.");
                continue;
            }

            System.out.println("Enter password: ");
            String password = String.valueOf(System.console().readPassword());
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty.");
                continue;
            }

            if (userList.authenticateUser(name.trim(), password.trim())) {
                File contactsFile = createContactsFile(name);
                contactList = readContactList(contactsFile);
                homeMenu(contactsFile);
                break;
            } else {
                System.out.println("Incorrect Username or Password.");
            }
        }
    }

    private void register(File usersFile) throws FileNotFoundException, IOException {
        clearConsole();
        System.out.println("-----------------Register----------------");
        loop: while (true) {
            System.out.println("Enter username: ");
            String name = input.nextLine();
            if (name.isEmpty()) {
                System.out.println("Username cannot be empty.");
                continue;
            }

            System.out.println("Enter password: ");
            String password = String.valueOf(System.console().readPassword());
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty.");
                continue;
            }

            for (User user : userList) {
                if (name.equals(user.getUsername())) {
                    System.out.println("Username already exists.");
                    input.nextLine();
                    continue loop;
                }
            }

            User user = new User(name, password);
            userList.add(user);
            String filename = "Users\\" + name + ".csv";
            File contactsFile = new File(filename);
            PrintWriter out = new PrintWriter(contactsFile);
            out.println("Null,Null,Null,Null");
            out.close();
            saveUserList(usersFile);
            break;
        }
    }

    private void saveUserList(File usersFile) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(usersFile);
        for (User u : userList) {
            out.println(u.getUsername() + "," + u.getPassword());
        }
        out.close();
    }
}
