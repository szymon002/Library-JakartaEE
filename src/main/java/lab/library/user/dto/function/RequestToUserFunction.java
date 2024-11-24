package lab.library.user.dto.function;

import lab.library.user.dto.PutUserRequest;
import lab.library.user.entity.User;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToUserFunction implements BiFunction<UUID, PutUserRequest, User> {
    @Override
    public User apply(UUID uuid, PutUserRequest putUserRequest) {
        return User.builder()
                .id(uuid)
                .firstName(putUserRequest.getFirstName())
                .lastName(putUserRequest.getLastName())
                .email(putUserRequest.getEmail())
                .login(putUserRequest.getLogin())
                .password(putUserRequest.getPassword())
                .build();
    }
}
