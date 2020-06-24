package com.todo.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "verification_token")
@Builder(toBuilder = true)
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class VerificationToken {
    private static final int ONE_DAY = 1;

    public VerificationToken(final String token, final User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(ONE_DAY);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "FK_VERIFY_USER"))
    private User user;

    private Date expiryDate;

    private Date calculateExpiryDate(int days) {
        return Date.valueOf(LocalDate.now().plusDays(days));
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(ONE_DAY);
    }
}