package ru.msu.cmc.webprak.models;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "file_")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class File implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id_file")
    private Long id;

    @Column(nullable = false, name = "name_file")
    @NonNull
    private String name;

    @Column(nullable = false, name = "content")
    @NonNull
    private String content;
}
