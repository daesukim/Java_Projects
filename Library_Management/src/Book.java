public abstract class Book implements Comparable{
    private String author;
    private String isbn;
    private String name;
    private String pages;

    public Book(String author, String isbn, String name, String pages){
        this.author = author;
        this.isbn = isbn;
        this.name = name;
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getPages() {
        return pages;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public abstract String toString();
}
