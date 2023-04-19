package pt.ipvc.bll;

import pt.ipvc.dal.User;
import pt.ipvc.utils.BCrypt;

import java.util.UUID;

public class SessionBLL {
    private static User authenticatedUser = null;

    public static boolean login(String email, String password) {
        User user = UserBLL.getByEmail(email);

        if(user == null) return false;

        boolean isPasswordCorrect = BCrypt.checkpw(password, user.getPassword());

        if(isPasswordCorrect) {
            authenticatedUser = user;
        }

        return isPasswordCorrect;
    }

    public static void logout() {
        authenticatedUser = null;
    }

    public static UUID register(String name, String email, String phone, String password) {
        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));

        if(user.getRole() == null)
            user.setRole(RoleBLL.getClientRole());

        UserBLL.create(user);
        return UserBLL
                .getByEmail(user.getEmail())
                .getId();
    }

    public static User getAuthenticatedUser() {
        return authenticatedUser;
    }
}
