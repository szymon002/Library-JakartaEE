package lab.library.user.dto.function;

import lab.library.user.dto.GetUsersResponse;
import lab.library.user.entity.User;

import java.util.List;
import java.util.function.Function;

public class UsersToResponseFunction implements Function<List<User>, GetUsersResponse> {

    @Override
    public GetUsersResponse apply(List<User> users) {
        return GetUsersResponse.builder()
                .users(users.stream()
                        .map(user -> GetUsersResponse.User.builder()
                                .id(user.getId())
                                .login(user.getLogin())
                                .build())
                        .toList())
                .build();
    }

}
