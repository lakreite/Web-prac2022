package ru.msu.cmc.webprak.models;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "theme_")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Theme implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id_theme")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_chapter")
    @ToString.Exclude
    @NonNull
    private Chapter chapter;

    @Column(nullable = false, name = "name_theme")
    @NonNull
    private String name;

    @Column(nullable = false, name = "date_theme")
    private Long date;
}
