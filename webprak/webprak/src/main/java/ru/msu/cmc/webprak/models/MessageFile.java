package ru.msu.cmc.webprak.models;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "message_file_")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class MessageFile implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id_message_file")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_message")
    @ToString.Exclude
    @NonNull
    private Message message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_file")
    @ToString.Exclude
    @NonNull
    private File file;
}
