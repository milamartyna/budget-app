package pk.edu.budget_app.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 Core object that can either represent income or expense
**/
@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private BigDecimal amount;

    @NonNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EntryType transactionType;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Nullable
    private Category category;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Nullable
    private String description;

}
