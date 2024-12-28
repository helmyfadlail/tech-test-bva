package helmyfadlail.technicaltestbva.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "members")

public class Member {
    @Id
    private String id;

    private String name;

    private String position;

    private String superior;

    @Column(name = "picture_url")
    private String pictureUrl;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;
}
