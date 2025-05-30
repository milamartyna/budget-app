package pk.edu.budget_app.dto;

import jakarta.annotation.Nullable;
import lombok.*;
import pk.edu.budget_app.domain.EntryType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    @Nullable
    private Long id;

    @NonNull
    private BigDecimal amount;

    @NonNull
    private EntryType transactionType;

    @NonNull
    private String accountName;

    @Nullable
    private String categoryName;

    private String description;

    private LocalDateTime date; // optional in POST, always filled in GET
}
