package com.todo.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_token")
@Builder(toBuilder = true)
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class VerificationToken {
    private static final int ONE_DAY = 1;

    public VerificationToken(final User user, final String token) {
        this.token = token;
        this.user = user;
        this.expiryDate = LocalDateTime.now().plusDays(1);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Builder.Default
    private LocalDateTime expiryDate = LocalDateTime.now().plusDays(1);

    public boolean isExpired(){
        return expiryDate.compareTo(LocalDateTime.now()) < 0;
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = LocalDateTime.now().plusDays(1);
    }
}