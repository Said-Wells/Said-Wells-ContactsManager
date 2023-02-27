import com.sun.security.jgss.GSSUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactsManager {

    private static final String CONTACTS = "contacts.txt";


    public static void main(String[] args) {

        List<Contact> contacts = loadingContacts();

        boolean goOn = true;
        while (goOn) {
            int choice = mainMenu();
            switch (choice) {
                case 1:
                    showAllContacts(contacts);
                    break;
                case 2:
                    addContact(contacts);
                    break;
                case 3:
                    findContact(contacts);
                    break;
                case 4:
                    removeContact(contacts);
                    break;
                case 5:
                    goOn = false;
                    break;
                default:
                    System.out.println("Invalid choice, Enter a number between 1 - 5");
            }
        }
        writeContactsToFile(contacts);
    }

    private static List<Contact> loadingContacts() {
        List<Contact> contacts = new ArrayList<>();
        File file = new File(CONTACTS);
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    String phoneNumber = parts[1];
                    contacts.add(new Contact(name, phoneNumber));
                }
            }
        } catch (IOException iox) {
            System.out.println("Error: loading contacts from file" + iox.getMessage());
        }
        return contacts;
    }

    private static int mainMenu() {
        System.out.print("-----------------------------\nWelcome To Contacts Manager\nPlease, choose one of the following option:\nEnter (1) Show all contacts\nEnter (2) Add a new contact\nEnter (3) Search for a contact by name\nEnter (4) Delete a contact\nEnter (5) Exit. \n ---------------------------------\n");
        userInput input = new userInput();
        return input.getNumber();
    }

    private static void showAllContacts(List<Contact> contacts) {
        System.out.println("|==============> All contacts <=============|");
        System.out.print("|  Name               |  Phone number       |\n ------------------------------------------- \n");

        for (Contact contact : contacts) {
            String name = contact.getName();
            String formatPhone = formatPhoneNumber(contact.getPhoneNumber());
            System.out.printf("| %-20s| %-20s|\n", name, formatPhone);
            System.out.println("|-------------------------------------------|");
        }
    }

    private static String formatPhoneNumber(String phoneNumber) {
        String formatted = phoneNumber.replaceAll("\\D", "");

        if (formatted.length() == 10) {
            formatted = "+(1)" + formatted.substring(0, 3) + "-" + formatted.substring(3, 6) + "-" + formatted.substring(6);
        } else {
            formatted = "+(Int)" + formatted.substring(0, 2) + "-" + formatted.substring(3);
        }
        return formatted;
    }

    private static void addContact(List<Contact> contacts) {
        System.out.println("Enter the name of the new Contact: ");
        userInput input = new userInput();
        String name = input.getString();
        if (contactExist(contacts, name)) {
            System.out.println("The contact already exist!");
            return;
        }
        System.out.println("Enter the phone number (digits only)");
        String phoneNumber = input.getString();
        contacts.add(new Contact(name, phoneNumber));
        System.out.println("Success!");
    }

    private static boolean contactExist(List<Contact> contacts, String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private static void writeContactsToFile(List<Contact> contacts) {
        try (FileWriter writer = new FileWriter(CONTACTS)) {
            for (Contact contact : contacts) {
                writer.write(contact.getName() + "," + contact.getPhoneNumber() + "\n");
            }
        } catch (IOException iox) {
            System.out.println("Error: writing contact to file " + iox.getMessage());
        }
    }

    private static void removeContact(List<Contact> contacts) {
        System.out.println("Enter the name to remove: ");
        userInput input = new userInput();
        String name = input.getString();
        Contact contact = findContact(contacts, name);
        if (contact == null) {
            System.out.println("Contact does not exist!");
        } else {
            contacts.remove(contact);
            System.out.println("Contact Deleted Successfully!");
        }
    }

    private static Contact findContact(List<Contact> contacts, String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    private static void findContact(List<Contact> contacts) {
        System.out.println("Enter the name to find Contact: ");
        userInput input = new userInput();
        String name = input.getString();
        Contact contact = findContact(contacts, name);
        if (contact == null) {
            System.out.println("Contact does not exist!");
        } else {
            String formatPhone = formatPhoneNumber(contact.getPhoneNumber());
            System.out.println("|=============> Search Result <=============");
            System.out.print("| Name                |  Phone number       |\n ------------------------------------------- \n");
            System.out.printf("| %-20s| %-20s|\n", name, formatPhone);
            System.out.println("|-------------------------------------------|\n");
        }
    }

}
