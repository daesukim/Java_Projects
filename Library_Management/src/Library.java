import javax.management.Notification;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Library implements LibraryManagementSystem{
    private ArrayList<FictionBook> fictionBooks;
    private HashMap<String, Integer> inventory;
    private ArrayList<NonfictionBook> nonfictionBooks;
    private ArrayList<Student> students;

    public Library(String path){
        // Create Instance variables
        this.fictionBooks = new ArrayList<>();
        this.inventory = new HashMap<>();
        this.nonfictionBooks = new ArrayList<>();
        this.students = new ArrayList<>();
        inventory(path);
    }

    public int[] availableBook(){
        int[] result = new int[2];

        int numOfFictionBook = 0;
        int numOfNonFictionBook = 0;

        for (FictionBook fictionBook : fictionBooks){
            numOfFictionBook = numOfFictionBook + inventory.get(fictionBook.getIsbn());

        }

        for (NonfictionBook nonfictionBook : nonfictionBooks){
            numOfNonFictionBook = numOfNonFictionBook + inventory.get(nonfictionBook.getIsbn());
        }
        result[0] = numOfFictionBook;
        result[1] = numOfNonFictionBook;

        return result;
    }

    public ArrayList<FictionBook> getFictionBooks() {
        return fictionBooks;
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public ArrayList<NonfictionBook> getNonfictionBooks() {
        return nonfictionBooks;
    }

    public ArrayList<Student> getStudents(){
        return students;
    }

    @Override
    public void inventory(String input) {
        // try reading the file
        try{
            File file = new File(input);
            Scanner in = new Scanner(file);

            // skipping the first line
            String line = in.nextLine();

            while(in.hasNextLine()){
                line = in.nextLine();
                // split the line and store the words into parts
                String[] parts = line.split(",");
                String isbn = parts[0];
                String name = parts[1];
                String author = parts[2];
                String pages = parts[3];
                String quantity = parts[4];
                String fictionNonfiction = parts[parts.length-1].trim();

                // create HashMap
                int quantityToNumber = Integer.parseInt(quantity);
                inventory.put(isbn,quantityToNumber);

                // originally tried with "fiction", but since the data have two extra spaces at the end, it has to check "fiction  "
                if (fictionNonfiction.equals("fiction")){
                    FictionBook fb = new FictionBook(author, isbn, name, pages);
                    fictionBooks.add(fb);
                }

                else if(fictionNonfiction.equals("nonfiction")){
                    NonfictionBook nfb = new NonfictionBook(author, isbn, name, pages);
                    nonfictionBooks.add(nfb);
                }
            }


            //some rows are not read
//            int count = 0;
//            for (String key : inventory.keySet()){
//                System.out.print(key + " is ");
//                System.out.print(inventory.get(key) + " count is ");
//                System.out.print(count);
//                System.out.println();
//                count++;
//            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public void lend(String input) {
        boolean found = false;
        String isbn = "";
        int quantity = 0;
        for(String key : inventory.keySet()){
            if(key.equals(input)){
                isbn = key;
                quantity = inventory.get(key);
                found = true;
            }
        }
        if (found){
            if (inventory.get(isbn) < 1){
                System.out.println("The book is running out of stock");
            }
            else{
                System.out.println("Successfully borrowed");
                inventory.put(isbn, quantity-1);
            }
        }
        else{
            throw new IllegalArgumentException("Not found");
        }
    }
    @Override
    public void putBack(String input) {
        boolean found = false;
        String isbn = "";
        int quantity = 0;
        for(String key : inventory.keySet()){
            if(key.equals(input)){
                isbn = key;
                quantity = inventory.get(key);
                found = true;
            }
        }
        if (found){
            inventory.put(isbn, quantity+1);
            // System.out.println("Successfully returned");
        }
        else{
            throw new IllegalArgumentException("Not found");
        }
    }

    @Override
    public void registerStudent(Student student) {
        for (Student st : students){
            if (st.getRegistrationNumber().equals(student.getRegistrationNumber())){
                throw new IllegalArgumentException("The student is already in the system");
            }
        }
        students.add(student);
    }

    @Override
    public Book search(String input) {
        // first, check if the input (isbn) exists in keys of inventory
        if (inventory.containsKey(input)){

            int mid = inventory.size() / 2;

            for (FictionBook fb : fictionBooks){
                if (fb.getIsbn().equals(input)){
                    return fb;
                }
            }
            for (NonfictionBook nfb : nonfictionBooks){
                if (nfb.getIsbn().equals(input)){
                    return nfb;
                }
            }
        }
        else{
            throw new IllegalArgumentException("Book Not Found");
        }

        return null;
    }

    public void setFictionBooks(ArrayList<FictionBook> fictionBooks) {
        this.fictionBooks = fictionBooks;
    }

    public void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public void setNonfictionBooks(ArrayList<NonfictionBook> nonfictionBooks) {
        this.nonfictionBooks = nonfictionBooks;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    @Override
    public ArrayList<Book> sort(int n) {

        ArrayList<Book> listOfBook = new ArrayList<>();

        for (FictionBook fb : fictionBooks){
            listOfBook.add(fb);
        }

        for (NonfictionBook nfb : nonfictionBooks){
            listOfBook.add(nfb);
        }

        if (n == 1){
            Collections.sort(listOfBook);
        }

        if (n == 2){
            Comparator<Book> comparator = new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    if (inventory.get(o1.getIsbn()) > inventory.get(o2.getIsbn()))
                        return 1;
                    else if (inventory.get(o1.getIsbn()) < inventory.get(o2.getIsbn())){
                        return -1;
                    }
                    else{
                        return 0;
                    }

                }
            };
            Collections.sort(listOfBook, comparator);
        }
        return listOfBook;
    }
}
