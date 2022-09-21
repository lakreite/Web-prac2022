package ru.msu.cmc.webprak.models;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "user_")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "login", unique = true)
    private String login;

    @Column(nullable = false, name = "password")
    @NonNull
    private String password;

    @Column(nullable = false, name = "rights")
    @NonNull
    private Long rights;

    @Column(nullable = false, name = "date_user")
    @NonNull
    private Long date;
}
