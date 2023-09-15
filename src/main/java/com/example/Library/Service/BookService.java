package com.example.Library.Service;

import com.example.Library.Controller.LogFile;
import com.example.Library.Exception.BookNotFoundException;
import com.example.Library.Model.Books;
import com.example.Library.Model.Users;
import com.example.Library.Repository.BookRepository;
import com.example.Library.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Cacheable("/AllBook")
    public List<Books> getAllBooks(){
        return bookRepository.findAll();
    }
    public Books findBookById(@PathVariable int id){
        return bookRepository.findBookById(id).orElseThrow(()-> new BookNotFoundException("Book not found"));
    }
    public Books findBookByTitle(@PathVariable String title){
        return bookRepository.findBookByTitle(title).orElseThrow(()-> new BookNotFoundException("Book with title %s not found"));
    }
    public Books findBookByIsbn(@PathVariable long isbn){
        return bookRepository.findBookByIsbn(isbn).orElseThrow(()-> new BookNotFoundException("Book with not found"));
    }

    @CacheEvict
    public Books addBook(@RequestBody Books book) throws IOException {
        String log = String.format("Book with name %s is added successfully\n",book.getAuthor());
        LogFile.writeFile(log);
        return bookRepository.save(book);
    }


    @CacheEvict(value = "borrowedBook", allEntries = true, key = ("#id, #userId"))
    public ResponseEntity<Users> borrowBook(@PathVariable int id, @PathVariable int userId){
        List<Users> user = userRepository.findAll();
        List<Books> book = bookRepository.getAllBooks();

        Users borrowedBy = new Users();
        borrowedBy.setUserId(userId);

        book.forEach(books -> {
            if (books.getTitle().equals(id) && books.getStatus().equals("isPresent") && books.getBorrowed().equals("none")){
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
            if (users.getFullName().equals(userId)){
                borrowedBy.setFullName(users.getFullName());
                borrowedBy.setAddress(users.getAddress());
            }
        });
        return ResponseEntity.ok(borrowedBy);

    }

    @CacheEvict(value = "returnedBook", allEntries = true, key = "#id")
    public ResponseEntity<List<Books>> returnBook(@PathVariable int id, @PathVariable int userId){
        List<Users> user = userRepository.findAll();
        List<Books> book = bookRepository.getAllBooks();

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

    @CacheEvict(value = "deletedBook", key = "#id", allEntries = true)
    public ResponseEntity<String> deleteBook(@PathVariable int id) throws IOException {
        String log = String.format("Book with name %s is deleted successfully\n", id);
        LogFile.writeFile(log);
        bookRepository.deleteById(id);
        return ResponseEntity.ok("book successfully deleted");
    }



}
























//    public Books borrowBook(@PathVariable int id, @PathVariable int userId){
//        Books book = findBookById(id);
//        Users user = userRepository.findUserByUserId(userId);
//        if (book != null && !book.isBorrowed() && user != null){
//            book.setBorrowedBy(user);
//            book.setBorrowed(true);
//            return addBook(book);
//        }
//        return null;
//    }

//    @CacheEvict(value = "returnedBook", allEntries = true, key = "#id")
//    public Books returnBook(@PathVariable int id){
//        Books book = findBookById(id);
//        if (book != null && book.isBorrowed()){
//            book.setBorrowedBy(null);
//            book.setBorrowed(false);
//            return addBook(book);
//        }
//        return null;
//    }
