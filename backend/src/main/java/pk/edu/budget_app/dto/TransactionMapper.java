package pk.edu.budget_app.dto;

import pk.edu.budget_app.domain.Transaction;

public class TransactionMapper {

    public static TransactionDto toDto(Transaction t) {
        return TransactionDto.builder()
                .amount(t.getAmount())
                .transactionType(t.getTransactionType())
                .accountName(t.getUser().getName())
                .categoryName(t.getCategory() != null ? t.getCategory().getName() : null)
                .date(t.getDate())
                .description(t.getDescription())
                .build();
    }

}
