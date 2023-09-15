package com.example.Library.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", unique = true)
    private int id;


    private String title;

    @NonNull
    @NotEmpty
    @NotBlank
    private String author;


//    @Max(value = 13)
//    @Min(value = 10)
//    @NonNull
//    @NotEmpty
//    @NotBlank
    private  long isbn;

//    @NonNull
//    @NotEmpty
//    @NotBlank
    @Column(name = "pub_year")
    private long publicationYear;

    private Boolean borrowed;

//    @NonNull
//    @NotEmpty
//    @NotBlank
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users borrowedBy;

    private String status;

    public boolean isBorrowed() {
        return borrowed;
    }

    public String setBorrowedBy(String none) {

        return none;
    }
}
