package com.example.Library.Controller;

import com.example.Library.Model.Books;
import com.example.Library.Model.BorrowedBooks;
import com.example.Library.Model.Users;
import com.example.Library.Service.BorrowedBooksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bb/API/")
public class BorrowedBooksController {
    @Autowired
    private BorrowedBooksService borrowedBooksService;

//    @GetMapping("/allBorrowed")
//    public List<BorrowedBooks> getAllBorrowedBooks(){
//        return borrowedBooksService.getAllBorrowedBooks();
//    }

    @PostMapping("/borrowed-book")
    public BorrowedBooks addBorrowedBooks(@RequestBody BorrowedBooks book){
        return borrowedBooksService.addBorrowedBook(book);
    }


    @GetMapping("/byUserId/{user_id}")
    public BorrowedBooks getByUserId(@PathVariable @Valid Users userId){
        return borrowedBooksService.getByUserId(userId);
    }

    @GetMapping("/byBookName/{name}")
    public BorrowedBooks findByName(@PathVariable @Valid String name){
        return borrowedBooksService.findByName(name);
    }

    @GetMapping("/byAuthor/{author}")
    public BorrowedBooks findByAuthor(@PathVariable @Valid String author){
        return borrowedBooksService.findByAuthor(author);
    }

}
