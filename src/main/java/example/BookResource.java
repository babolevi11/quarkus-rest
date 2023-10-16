package example;

import example.pojos.Book;
import example.pojos.BookUpdate;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.*;

@Path("/books")
public class BookResource {

    public Map<Integer, Book> books = new HashMap<>();

    public BookResource() {
        this.books.put(1, new Book("The Art of Programming", "John Smith", "2023-01-15", 49.99F, "Computer Science", "9780123456789", 10));
        this.books.put(2, new Book("The Power of Words", "Emily Johnson", "2022-11-30", 29.99F, "Self-Help", "9789876543210", 5));
        this.books.put(3, new Book("A Journey Through Time", "David Thompson", "2023-03-22", 19.99F, "Fantasy", "9786543210987", 8));
        this.books.put(4, new Book("The Hidden Secrets", "Sarah Roberts", "2023-02-10", 14.99F, "Mystery", "9783210987654", 12));
        this.books.put(5, new Book("The Science of Nature", "Michael Anderson", "2023-04-18", 24.99F, "Science", "9785432109876", 3));
    }

    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        return Response.ok(books).build();
    }

    @POST
    public Response addBook(Book post) {
        if(!books.containsValue(post)) {
            Integer last = 0;
            for (Integer key : books.keySet()) {
                last = key;
            }
            books.put(last + 1, post);
            return Response.ok(post).status(201).build();
        } else {
            //return books;
            return Response.ok("Duplicate Record!").status(409).build();
        }
    }

    @PUT
    public Response updateBook(BookUpdate upd) {
        if (books.replace(upd.getId(), new Book(upd.getTitle(), upd.getAuthor(), upd.getPubDate(), upd.getPrice(), upd.getGenre(), upd.getIsbn(), upd.getQuantity())) != null) {
            return Response.ok(books).build();
        } else {
            return  Response.ok("Id(" + upd.getId() + ") not found!").status(409).build();
        }
    }

    @DELETE
    public Response deleteBook(BookUpdate del) {
        if (books.remove(del.getId()) != null) {
            return Response.ok(books).build();
        } else {
            return  Response.ok("Id(" + del.getId() + ") not found!").status(409).build();
        }
    }

}
