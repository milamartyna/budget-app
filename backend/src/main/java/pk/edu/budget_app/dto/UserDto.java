package pk.edu.budget_app.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NonNull
    private String name;

    @NonNull
    private BigDecimal totalIncome;

    @NonNull
    private BigDecimal totalExpense;
    
}
