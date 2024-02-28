import java.util.ArrayList;

public interface LibraryManagementSystem {
    public void inventory(String input);
    public void lend(String input);
    public void putBack(String input);
    public void registerStudent(Student student);
    public Book search(String input);
    public ArrayList<Book> sort(int n);
}
