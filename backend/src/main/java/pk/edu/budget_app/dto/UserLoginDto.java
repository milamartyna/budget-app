package pk.edu.budget_app.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {

    @NonNull
    private String name;

    @NonNull
    private String password;

}
