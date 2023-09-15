package com.example.Library.Repository;

import com.example.Library.Model.Books;
import com.example.Library.Model.BorrowedBooks;
import com.example.Library.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBooks, Integer> {
//    List<BorrowedBooks> getAllBorrowedBooks();

    Optional<BorrowedBooks> getByUserId(Users userId);
    Optional<BorrowedBooks> findByName(String name);
    Optional<BorrowedBooks> findByAuthor(String author);
}
