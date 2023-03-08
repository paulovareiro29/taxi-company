package pt.ipvc.bll;

import org.mindrot.jbcrypt.BCrypt;
import pt.ipvc.dal.User;

public class SessionBLL {

    public static boolean login(String email, String password) {
        User user = UserBLL.getByEmail(email);

        if(user == null) return false;
        return BCrypt.checkpw(password, user.getPassword());
    }

    public static Long register(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), "TAXI-COMPANY-SALT"));

        UserBLL.create(user);
        return UserBLL
                .getByEmail(user.getEmail())
                .getId();
    }
}
