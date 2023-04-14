package pt.ipvc.bll;

import pt.ipvc.dal.User;
import pt.ipvc.utils.BCrypt;

import java.util.UUID;

public class SessionBLL {

    public static boolean login(String email, String password) {
        User user = UserBLL.getByEmail(email);

        if(user == null) return false;
        return BCrypt.checkpw(password, user.getPassword());
    }

    public static UUID register(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        if(user.getRole() == null)
            user.setRole(RoleBLL.getClientRole());

        UserBLL.create(user);
        return UserBLL
                .getByEmail(user.getEmail())
                .getId();
    }
}
