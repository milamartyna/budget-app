package pk.edu.budget_app.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    @NonNull
    String name;

    @Column(nullable = false)
    @Builder.Default
    BigDecimal totalIncome = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    BigDecimal totalExpense = BigDecimal.ZERO;

}
