import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactsManager {

    //Variable containing our TXT file.
    private static final String CONTACTS = "contacts.txt";

    //Custom class to get user input.
    public static userInput input = new userInput();

    public static void main(String[] args) {

        //Variable that loads our programs list of contacts on start up.
        List<Contact> contacts = loadingContacts();

        //User selection menu.
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

        //Saves the changes to the file.
        writeContactsToFile(contacts);
    }


    //This method creates an ArrayList of parsed Contact objects based up on the TXT file passed into it and returns that new list.
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

    //This method creates the UI for the program.
    private static int mainMenu() {
        System.out.print("----------------------------------\n⭐️⭐️⭐️ Welcome To The Contacts Manager App 📱 ⭐️⭐️⭐\nPlease choose from one of the following options:\nEnter 1️⃣ to show all contacts 📖\nEnter 2️⃣ to add a new contact ✅\nEnter 3️⃣ to search for a contact by name 🔍\nEnter 4️⃣ to delete a contact 🗑️ \nEnter 5️⃣ to exit the app 🚫 \n ---------------------------------\n");
        return Integer.parseInt(input.getString());
    }


    //This method takes in a list of Contact objects and writes to the TXT file the object to information to be saved.
    private static void writeContactsToFile(List<Contact> contacts) {
        try (FileWriter writer = new FileWriter(CONTACTS)) {
            for (Contact contact : contacts) {
                writer.write(contact.getName() + "," + contact.getPhoneNumber() + "\n");
            }
        } catch (IOException iox) {
            System.out.println("Error: writing contact to file " + iox.getMessage());
        }
    }


    //This method takes in a list of Contact objects and displays all the objects' information to the user.
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

    //This method takes in a phoneNumber and formats it into a 3-digit-3digit-4digit pattern separated by "-". It also differentiates between U.S. and International phone numbers.
    private static String formatPhoneNumber(String phoneNumber) {
        String formatted = phoneNumber.replaceAll("\\D", "");

        if (formatted.length() == 10) {
            formatted = "+(1)" + formatted.substring(0, 3) + "-" + formatted.substring(3, 6) + "-" + formatted.substring(6);
        } else {
            formatted = "+(Int)" + formatted.substring(0, 2) + "-" + formatted.substring(3);
        }
        return formatted;
    }


    //This method takes in a list of Contact objects and allows the user add a new contact. If that contact already exist, it alerts the users and prevents them from adding that user again.
    private static void addContact(List<Contact> contacts) {
        System.out.println("Enter the name of the new Contact: ");
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


    //This method takes in a list of Contact objects and a String name and checks to see of that contact currently exist in the list.
    private static boolean contactExist(List<Contact> contacts, String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    //This method takes in a list of Contact objects and allows the user to input a contact to be deleted if the contact does exist within the list. If it doesn't exist, then it will alert the user.
    private static void removeContact(List<Contact> contacts) {
        System.out.println("Enter the name to remove: ");
        String name = input.getString();
        Contact contact = findContact(contacts, name);
        if (contact == null) {
            System.out.println("Contact does not exist!");
        } else {
            contacts.remove(contact);
            System.out.println("Contact Deleted Successfully!");
        }
    }

    //This method takes in a list of Contact objects and a String name and returns that contact if the contact is in the list, else it returns null.
    private static Contact findContact(List<Contact> contacts, String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    //This method takes in a list of Contact objects and allows the user to search for a contact in the list. If the contact exist, it returns a formatted result of thr contact name and phone number. If it doesn't exist, the program alerts the user that no such contact exists in the list.
    private static void findContact(List<Contact> contacts) {
        System.out.println("Enter the name to find Contact: ");
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

