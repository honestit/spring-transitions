package pl.honestit.demos.spring.model.entities.user;

import lombok.*;
import pl.honestit.demos.spring.model.entities.base.ParentEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "example_user_details")
@Getter @Setter @ToString(exclude = "owner") @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDetailsEntity extends ParentEntity {

    @OneToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
    /*
        Możemy mieć pole encji i pole kolumny na tą samą kolumnę.
        Wtedy pracujemy w kodzie na encji, a pole z kolumną jest polem
        tylko do odczytu. Można je wykorzystać np. w metodzie toString
     */
    @Column(name = "owner_id", insertable = false, updatable = false)
    private Long owner_id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "pesel")
    private String pesel;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
}
