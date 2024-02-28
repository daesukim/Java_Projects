import java.util.ArrayList;
import java.util.List;

public class Practice {
    public static void main(String[] args) {
        ArrayList<Integer> ar = new ArrayList<>();
        ar.add(12);
        ar.add(13);

        // System.out.println(ar.get(0));

        Library library = new Library("inventory.txt");
        System.out.println(library.getFictionBooks().size());

        List<String> t = new ArrayList<>();

        t.add("Br");
        t.add("Hello");

        for (String a : t){
            System.out.println(a);
        }


    }
}
