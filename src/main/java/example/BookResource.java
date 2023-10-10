package example;

import example.pojos.Book;
import example.pojos.BookUpdate;
import jakarta.ws.rs.*;

import java.util.*;

@Path("/books")
public class BookResource {

    //public List<Book> books = new ArrayList<>();
    //private Set<Book> books = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
    public Map<Integer, Book> books = new HashMap<>();

    public BookResource() {
        /*
        this.books.add(new Book(1, "The Art of Programming", "John Smith", "023-01-15", 49.99F, "Computer Science", "9780123456789", 10));
        this.books.add(new Book(2, "The Power of Words", "Emily Johnson", "2022-11-30", 29.99F, "Self-Help", "9789876543210", 5));
        this.books.add(new Book(3, "A Journey Through Time", "David Thompson", "2023-03-22", 19.99F, "Fantasy", "9786543210987", 8));
        this.books.add(new Book(4, "The Hidden Secrets", "Sarah Roberts", "2023-02-10", 14.99F, "Mystery", "9783210987654", 12));
        this.books.add(new Book(5, "The Science of Nature", "Michael Anderson", "2023-04-18", 24.99F, "Science", "9785432109876", 3));
         */
        this.books.put(1, new Book("The Art of Programming", "John Smith", "023-01-15", 49.99F, "Computer Science", "9780123456789", 10));
        this.books.put(2, new Book("The Power of Words", "Emily Johnson", "2022-11-30", 29.99F, "Self-Help", "9789876543210", 5));
        this.books.put(3, new Book("A Journey Through Time", "David Thompson", "2023-03-22", 19.99F, "Fantasy", "9786543210987", 8));
        this.books.put(4, new Book("The Hidden Secrets", "Sarah Roberts", "2023-02-10", 14.99F, "Mystery", "9783210987654", 12));
        this.books.put(5, new Book("The Science of Nature", "Michael Anderson", "2023-04-18", 24.99F, "Science", "9785432109876", 3));
    }

    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    public Map<Integer, Book> getBooks() {
        return books;
    }

    @POST
    public Map<Integer, Book> addBook(Book post) {
        //if (!books.stream().filter(book -> book.getId() == post.getId()).findAny().isPresent()) {
        //if (!books.stream().anyMatch(book -> book.getId() == post.getId())) {
        //if (books.stream().noneMatch(book -> book.getId() == post.getId())) {
        //    books.add(post);
        //}
        if(!books.containsValue(post)) {
            Integer last = 0;
            for (Integer key : books.keySet()) {
                last = key;
            }
            books.put(last + 1, post);
        }
        return books;
    }

    @PUT
    public Map<Integer, Book> updateBook(BookUpdate upd) {
        //Optional<Book> old = books.stream().filter(book -> book.getId() == upd.getId()).findFirst();
        /*
        if (old.isPresent()) {
            books.set(books.indexOf(old.get()), upd);
        }
         */
        //old.ifPresent(book -> books.set(books.indexOf(book), upd));
        books.replace(upd.getId(), new Book(upd.getTitle(), upd.getAuthor(), upd.getPubDate(), upd.getPrice(), upd.getGenre(), upd.getIsbn(), upd.getQuantity()));
        return books;
    }

    @DELETE
    public Map<Integer, Book> deleteBook(BookUpdate del) {
        /*
        if (books.stream().filter(book -> book.getId() == del.getId()).findAny().isPresent()) {
            books.remove(del);
        }
         */
        //books.removeIf(book -> book.getId() == del.getId());
        books.remove(del.getId());
        return books;
    }

}
