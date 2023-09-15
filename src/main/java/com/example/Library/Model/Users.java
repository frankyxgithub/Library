package com.example.Library.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private int userId;

    @NotEmpty
    @NotNull
    @NotBlank
    @Length(min = 6, max = 15, message = "name should be at least 6 character and at most 15 characters")
    @Column(name = "full_name", unique = true)
    private String fullName;

    @NotEmpty
    @NotNull
    @NotBlank
    @Column(unique = true, name = "email")
    @Email(message = "please enter a valid email")
    private String email;

//    @NotEmpty
//    @NotNull
//    @NotBlank
    @Min(value = 18, message = "user's age should not be less than 18")
    @Max(value = 70, message = "user's age should not be more than 70")
    private int age;

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = 100)
    private String address;

    private String book1Borrowed = "none";

    private String book2Borrowed = "none";

    private String book1returned = "none";

    private String book2returned = "none";

    private String status;


}
