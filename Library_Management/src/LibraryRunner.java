import javax.print.MultiDocPrintService;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Here is How-To-Use guideline:
 * First, you need to register using option1. The registration number is set to autoincrement by 1 starting from 1000.
 * So, the first user will get 1000 as a registration number.
 * If you wish, you can add more users.
 * You can, then, borrow and return books.
 * When borrowed and returned, the quantity of the book will be adjusted in chart as well as sorted order the book.
 * Also, whenever the action of borrowing or returning happens, the program updates borrowed_books.txt
 */

public class LibraryRunner {
    public LibraryRunner(){
        Library library = new Library("inventory.txt");
        Scanner in = new Scanner(System.in);
        boolean found = false;
        int regNumber = 1000;

        // to keep a record of borrowed_book
        HashMap<String, List<String>> borrowed_book = new HashMap<>();

        while(!found){
            System.out.println("Please select one of the followings:");
            System.out.println("1. Register");
            System.out.println("2. Sort Books");
            System.out.println("3. Search Books");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Show Inventory Stats");
            System.out.println("7. Exit");
            int n = in.nextInt();

            if (n == 1){
                System.out.print("Please enter your name: ");
                String name = in.next();
                Student student = new Student(name, Integer.toString(regNumber));
                library.registerStudent(student);
                System.out.println(student.getName() + ", your registration number is " + student.getRegistrationNumber());
                regNumber++;
            }
            else if (n == 2){
                System.out.println("How would you like to sort? ");
                System.out.println("1: Sort by ISBN");
                System.out.println("2: Sort by quantity");
                int useNum = in.nextInt();
                if (useNum == 1){
                    ArrayList<Book> sortedBooks = library.sort(useNum);
                    System.out.println("Here is the list of books sorted by ISBN");
                    for (Book book : sortedBooks){
                        String isbn = book.getIsbn();
                        String name = book.getName();
                        System.out.println(isbn + ", \"" + name + "\"" + " are currently available");
                    }
                }
                else if (useNum == 2){
                    ArrayList<Book> sortedBooks = library.sort(useNum);
                    System.out.println("Here is the list of books sorted by quantity");
                    for (Book book : sortedBooks){
                        String isbn = book.getIsbn();
                        String name = book.getName();
                        int quantity = library.getInventory().get(isbn);
                        System.out.println(quantity + " of \"" + name + "\"" + " with ISBN " + isbn + " are currently available");
                    }
                }
                else{
                    throw new IllegalArgumentException("Not a valid option");
                }
            }
            else if (n == 3){
                System.out.print("Please enter the ISBN of the book: ");
                String isbn = in.next();
                Book book = library.search(isbn);
                System.out.println(book.toString());
            }
            else if (n == 4){
                System.out.print("Please enter your registration number: ");
                String regNum = in.next();
                System.out.print("Please enter the ISBN of the book: ");
                String isbn = in.next();

                boolean studentFound = false;
                boolean isbnFound = false;

                for (Student student : library.getStudents()){
                    if (student.getRegistrationNumber().equals(regNum)){
                        studentFound = true;
                    }
                }

                for (String key : library.getInventory().keySet()) {
                    if (key.equals(isbn)){
                        isbnFound = true;
                    }
                }
                if (studentFound && isbnFound){
                    library.lend(isbn); // updating the quantity of the book

                    // HashMapping the key and list of isbn
                    if (borrowed_book.containsKey(regNum)){
                        borrowed_book.get(regNum).add(isbn);
                    }
                    else{
                        List<String> c = new ArrayList<>();
                        c.add(isbn);
                        borrowed_book.put(regNum,c);
                    }

                    // write out the file
                    try{
                        PrintWriter out = new PrintWriter("borrowed_books.txt");
                        for (String key : borrowed_book.keySet()){
                            List<String> book_number = borrowed_book.get(key);
                            for (String num : book_number){
                                out.println(key + ", " + num);
                            }
                        }
                        out.close();
                    }
                    catch (FileNotFoundException e){
                        e.printStackTrace();
                    }

                }
                else{
                    System.out.println("One of your registration number or the isbn does not exist in our system.");
                }
            }
            else if (n == 5){
                boolean regNum = false;
                while(!regNum){
                    System.out.print("Please enter your registration number: ");
                    String registrationNumber = in.next();
                    ArrayList<Student> students = library.getStudents();
                    for(Student student : students){
                        if (student.getRegistrationNumber().equals(registrationNumber)){
                            for (String key : borrowed_book.keySet()){
                                if (key.equals(registrationNumber)){
                                    List<String> listISBN= borrowed_book.get(key);
                                    System.out.println("Belows are the ISBN of the books you borrowed");
                                    for (String isbn : listISBN){
                                        System.out.println(isbn);
                                    }
                                    System.out.print("Please enter the ISBN number of the book you would like to return: ");
                                    String userNum = in.next();
                                    for (String isbn : listISBN){
                                        if (isbn.equals(userNum)){
                                            System.out.println(isbn + " is successfully returned");
                                            library.putBack(isbn);
                                            regNum = true;
                                        }
                                    }
                                    if(regNum){
                                        // if user's list is bigger than 1
                                        List<String> listOfISBN = borrowed_book.get(registrationNumber);
                                        listOfISBN.remove(userNum);
                                        try{
                                            PrintWriter out = new PrintWriter("borrowed_books.txt");
                                            for (String k : borrowed_book.keySet()){
                                                List<String> book_number = borrowed_book.get(k);
                                                for (String num : book_number){
                                                    out.println(k + ", " + num);
                                                }
                                            }
                                            out.close();
                                        }
                                        catch (FileNotFoundException e){
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else if (n == 6){
                int[] availableBooks = library.availableBook();
                JFrame frame = new JFrame();
                frame.setSize(400,500);
                frame.setTitle("Available Books");
                InventoryChart chart = new InventoryChart(availableBooks, "Available Books");
                frame.add(chart);
                frame.setVisible(true);
            }
            else if (n == 7){
                found = true;
            }
            else{
                System.out.println("Choose a valid option");
            }
        }
    }

    public static void main(String[] args) {
        LibraryRunner runner = new LibraryRunner();
    }
}
