package com.mewocz.storeeverything.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The category must be provided")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @Size(min = 3, max = 20, message = "The title should be between 3 and 20 characters")
    private String title;

    @NotNull
    @Size(min = 5, max = 500, message = "The content should be between 5 and 500 characters")
    private String content;

    private String Link;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate creationDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate reminderDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    private String uuid;
}
