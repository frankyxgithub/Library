package com.example.Library.Controller;

import com.example.Library.Model.Books;
import com.example.Library.Model.Users;
import com.example.Library.Service.BookService;
import com.example.Library.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/book/")
public class BooksController {
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @PostMapping("/book")
    public Books addBook(@RequestBody Books book) throws IOException {
        return bookService.addBook(book);
    }

    @GetMapping("/allBooks")
    public List<Books> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public Books findBookById(@PathVariable int id) {
        return bookService.findBookById(id);
    }

    @GetMapping("/books-title/{title}")
    public Books findBookByTitle(@PathVariable String title) {
        return bookService.findBookByTitle(title);
    }

    @GetMapping("/books-isbn/{isbn}")
    public Books findBookByIsbnNo(@PathVariable long isbn) {
        return bookService.findBookByIsbn(isbn);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteBook(@PathVariable int id) throws IOException {
        bookService.deleteBook(id);
        return ResponseEntity.ok("book successfully deleted");
    }


    @PostMapping("/borrow/{bookId}/{userId}")
    public ResponseEntity<Users> borrowBook(@PathVariable int id, @PathVariable int userId) {
        List<Users> user = userService.getAllUsers();
        List<Books> book = bookService.getAllBooks();

        Users borrowedBy = new Users();
        borrowedBy.setUserId(userId);

        book.forEach(books -> {
            if (books.getTitle().equals(id) && books.getStatus().equals("isPresent") && books.getBorrowed().equals("none")) {
                books.setId(userId);
                books.setStatus("isNotPresent");
                if (borrowedBy.getBook1Borrowed().equals("none"))
                    borrowedBy.setBook1Borrowed(books.getTitle());
                else if (borrowedBy.getBook2Borrowed().equals("none"))
                    borrowedBy.setBook2Borrowed(books.getTitle());
                else {
                    borrowedBy.setStatus("2 books already borrowed, you have exceeded limit to be borrowed per" + userId);
                }

            }
        });
        user.forEach(users -> {
            if (users.getFullName().equals(userId)) {
                borrowedBy.setFullName(users.getFullName());
                borrowedBy.setAddress(users.getAddress());
            }
        });
        return ResponseEntity.ok(borrowedBy);

    }

    @PostMapping("/return/{bookId}/{userId}")
    public ResponseEntity<List<Books>> returnBook(@PathVariable int id, @PathVariable int userId){
        List<Users> user = userService.getAllUsers();
        List<Books> book = bookService.getAllBooks();

        Users BorrowedBy = new Users();

        book.forEach(books -> {
            if (books.getTitle().equals(id) && books.getStatus().equals("isNotPresent") && books.getBorrowed().equals(userId)){
                BorrowedBy.setBook1returned(books.getTitle());
                books.setBorrowedBy("none");
                books.setStatus("isPresent");
            }
        });
        user.forEach(users -> {
            if (users.getFullName().equals(userId)){
                if (users.getBook1Borrowed().equals(BorrowedBy.getBook1Borrowed()))
                    users.setBook1returned("none");
                else if (users.getBook2Borrowed().equals(BorrowedBy.getBook1Borrowed()))
                    users.getBook2Borrowed();
                else {
                }

            }
        });
        return ResponseEntity.ok(book);
    }



}























//    public ResponseEntity<List<Books>> borrowBook(@PathVariable int id, @PathVariable int userId){
//        Books borrowedBook = bookService.borrowBook(id, userId);
//        if (borrowedBook != null){
//            return ResponseEntity.ok(borrowedBook.getBody());
//        }
//        return ResponseEntity.badRequest().build();
//    }

//    @PostMapping("/{bookId}/return/{userId}")
//    public ResponseEntity<ResponseEntity<List<Books>>> returnBook(@PathVariable int id, @PathVariable int userId){
//        ResponseEntity<List<Books>> returnedBook = bookService.returnBook(id, userId);
//        if (returnedBook != null){
//            return ResponseEntity.ok(returnedBook);
//        }
//        return ResponseEntity.badRequest().build();
//    }

