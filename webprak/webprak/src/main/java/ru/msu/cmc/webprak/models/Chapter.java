package ru.msu.cmc.webprak.models;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "chapter_")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Chapter implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "name_chapter", unique = true)
    @NonNull
    private String name;

    @Column(nullable = false, name = "date_chapter")
    @NonNull
    private Long date;

}
