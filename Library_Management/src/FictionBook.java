public class FictionBook extends Book {
    public FictionBook(String author, String isbn, String name, String pages) {
        super(author, isbn, name, pages);
    }


    @Override
    public String toString(){
        return "Fiction Book" +
                "\nAuthor Name: " + getAuthor() +
                "\nISBN: " + getIsbn() +
                "\nName of the book: " + getName() +
                "\nNumber of pages: " + getPages();
    }

    @Override
    public int compareTo(Object o) {
        Book other = (Book) o;

        if (Integer.parseInt(this.getIsbn()) > Integer.parseInt(other.getIsbn())){
            return 1;
        }
        else if (Integer.parseInt(this.getIsbn()) < Integer.parseInt(other.getIsbn())){
            return -1;
        }
        else{
            return 0;
        }
    }
}
