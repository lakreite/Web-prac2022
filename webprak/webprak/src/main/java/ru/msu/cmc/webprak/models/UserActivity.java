package ru.msu.cmc.webprak.models;

import lombok.*;
import javax.persistence.*;

//@Entity
@Table(name = "user_activity_")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class UserActivity implements CommonEntity<String> {

    @Column(nullable = false, name = "login")
    @NonNull
    private String id;

    @Column(nullable = false, name = "password")
    @NonNull
    private String password;

    @Column(nullable = false, name = "rights")
    @NonNull
    private Long rights;

    @Column(nullable = false, name = "date_user")
    @NonNull
    private Long date;

    @Column(nullable = false, name = "activity")
    @NonNull
    private Long activity;
}
