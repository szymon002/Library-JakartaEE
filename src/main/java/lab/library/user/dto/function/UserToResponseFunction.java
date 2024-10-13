package lab.library.user.dto.function;

import lab.library.user.dto.GetUserResponse;
import lab.library.user.entity.User;

import java.util.function.Function;

public class UserToResponseFunction implements Function<User, GetUserResponse> {

    @Override
    public GetUserResponse apply(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .birthDate(user.getBirthDate())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

}
