import java.util.ArrayList;
import java.util.Scanner;

public class ContactList extends ArrayList<Contact> {
    boolean sortedByName;

    public ContactList() {
        sortedByName = true;
    }

    // adding contacts (might need to adjust with data attributes)
    public void addContact(Scanner input) {
        Contact contact = new Contact();
        contact.setName(Contact.readName(input));
        contact.setPhoneNum(Contact.readPhoneNum(input));
        contact.setEmail(Contact.readEmail(input));
        contact.setAddress(Contact.readAddress(input));

        addContact(contact);
    }

    public void addContact(Contact contact) {
        for (Contact c : this) {
            if (contact.getName().equals(c.getName())) {
                System.out.println("This contact name has already existed.");
                System.out.println("Update existing contact or create a new contact with different name.");
                new Scanner(System.in).nextLine();
                return;
            }
        }
        this.add(contact);
        contact.setId(this.size());
        if (sortedByName) {
            sortByName();
        }
    }

    public void removeContact(int index) {
        this.remove(index-1);
        for (int i = index - 1; i < this.size(); i++) {
            this.get(i).setId(i+1);
        }
        System.out.println("Contact Deleted");
    }

    public void viewContact(int index) {
        System.out.println(this.get(index-1));
    }

    public void updateContact(Scanner input, int index, int field) {
        Contact contact = this.get(index-1);
        input.nextLine();
        switch(field) {
            case 1:
                contact.setName(Contact.readName(input));
                break;
            case 2:
                contact.setPhoneNum(Contact.readPhoneNum(input));
                break;
            case 3:
                contact.setEmail(Contact.readEmail(input));
                break;
            case 4:
                contact.setAddress(Contact.readAddress(input));
                break;
            default:
                System.out.println("Invalid input");
        }
    }

    public void search(String keyword) {
        System.out.println("----------Search Result : " + keyword + "----------");
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getName().toLowerCase().startsWith(keyword.toLowerCase())) {
                System.out.println((i+1) + ". " + this.get(i).getName());
            }
        }
        System.out.println("--------------------------------------------------");
    }

    public ArrayList<Contact> getContacts() {
        return this;
    }

    // Display the contacts
    public void displayContacts() {
        System.out.println("----------------Contacts--------------------");
        for (int i = 0; i < this.size(); i++) {
            System.out.println((i+1) + ". " + this.get(i).getName());
        }
        System.out.println("--------------------------------------------------");
    }

    private boolean lessThan(String a, String b) {
        return a.toLowerCase().compareTo(b.toLowerCase()) < 0;
    }

    public void sortByName() {
        sortedByName = true;
        for (int i = 1; i < this.size(); i++) {
            Contact current = this.get(i);
            int j = i;
            while (j > 0) {
                if (lessThan(current.getName(), this.get(j-1).getName())) {
                    this.set(j, this.get(j-1));
                } else {
                    break;
                }
                j--;
            }
            this.set(j, current);
        }
    }

    public void sortById() {
        sortedByName = false;
        for (int i = 1; i < this.size(); i++) {
            Contact current = this.get(i);
            int j = i;
            while (j > 0) {
                if (current.getId() < this.get(j-1).getId()) {
                    this.set(j, this.get(j-1));
                } else {
                    break;
                }
                j--;
            }
            this.set(j, current);
        }
    }

}
