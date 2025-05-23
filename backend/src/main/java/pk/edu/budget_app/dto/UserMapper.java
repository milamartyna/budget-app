package pk.edu.budget_app.dto;

import pk.edu.budget_app.domain.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .totalIncome(user.getTotalIncome())
                .totalExpense(user.getTotalExpense())
                .build();
    }
}
