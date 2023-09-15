package com.example.Library.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "borrowedBooks")
public class BorrowedBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @NonNull
//    @NotEmpty
//    @NotBlank
    @ManyToOne
    private Users userId;

//    @NonNull
//    @NotEmpty
//    @NotBlank
    @JoinColumn(name = "author")
    private String name;

//    @NonNull
//    @NotEmpty
//    @NotBlank
    private String author;

}

