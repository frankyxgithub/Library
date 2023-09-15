package com.example.Library.Service;

import com.example.Library.Exception.BookNotFoundException;
import com.example.Library.Model.Books;
import com.example.Library.Model.BorrowedBooks;
import com.example.Library.Model.Users;
import com.example.Library.Repository.BookRepository;
import com.example.Library.Repository.BorrowedBookRepository;
import com.example.Library.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class BorrowedBooksService {
    @Autowired
    private BorrowedBookRepository borrowedBookRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

//    public List<BorrowedBooks> getAllBorrowedBooks(){
//        return borrowedBookRepository.getAllBorrowedBooks();
//    }

    public BorrowedBooks addBorrowedBook(@RequestBody BorrowedBooks book){
        return borrowedBookRepository.save(book);
    }

    public BorrowedBooks getByUserId(@PathVariable Users user_id){
        return borrowedBookRepository.getByUserId(user_id).orElseThrow(()-> new BookNotFoundException("Book with userId %s not found"));
    }

    public BorrowedBooks findByName(@PathVariable String name){
        return borrowedBookRepository.findByName(name).orElseThrow(()-> new BookNotFoundException("Book with name %s not found"));
    }

    public BorrowedBooks findByAuthor(@PathVariable String author){
        return borrowedBookRepository.findByAuthor(author).orElseThrow(()-> new BookNotFoundException("Book with with author %s not found"));
    }

}
