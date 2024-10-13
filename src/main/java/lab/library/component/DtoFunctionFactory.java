package lab.library.component;

import lab.library.user.dto.function.UserToResponseFunction;
import lab.library.user.dto.function.UsersToResponseFunction;

public class DtoFunctionFactory {

    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }
}
