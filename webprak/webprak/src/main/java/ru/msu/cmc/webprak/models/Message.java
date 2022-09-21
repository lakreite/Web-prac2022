package ru.msu.cmc.webprak.models;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "message_")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Message implements CommonEntity<Long> {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_theme")
    @ToString.Exclude
    @NonNull
    private Theme theme;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_chapter")
    @ToString.Exclude
    @NonNull
    private Chapter chapter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ToString.Exclude
    @NonNull
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id_message")
    private Long id;

    @Column(nullable = false, name = "content_message")
    @NonNull
    private String content;

    @Column(nullable = false, name = "date_message")
    @NonNull
    private Long date;
}
