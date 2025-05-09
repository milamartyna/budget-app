package pk.edu.budget_app.dto;

import lombok.*;
import pk.edu.budget_app.domain.EntryType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data // @Value would provide immutability (maybe change?)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    @NonNull
    private BigDecimal amount;

    @NonNull
    private EntryType transactionType;

    @NonNull
    private String accountName;

    private String categoryName;

    private String description;

    private LocalDateTime date; // optional in POST, always filled in GET
}
