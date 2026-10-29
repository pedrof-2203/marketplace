package dio.marketplace.registration.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@RequiredArgsConstructor
public class Customer {
    @Id
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String emailAddress;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @OneToOne(cascade =  CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
