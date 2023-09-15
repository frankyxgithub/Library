package com.example.Library.Repository;

import com.example.Library.Model.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Books, Integer> {
    default List<Books> getAllBooks(){
        return getAllBooks();
    }
    Optional<Books> findBookById(int id);
    Optional<Books> findBookByIsbn(long isbn);
    Optional<Books> findBookByTitle(String title);


}
