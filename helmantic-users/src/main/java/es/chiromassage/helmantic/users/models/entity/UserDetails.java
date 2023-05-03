package es.chiromassage.helmantic.users.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="user_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String firstSurname;

    private String secondSurname;

    @Column(unique = true)
    private String fiscalId;

    @OneToOne(mappedBy = "userDetails")
    @JsonIgnore
    private User user;
}
