package pt.ipvc;

import pt.ipvc.bll.RoleBLL;
import pt.ipvc.bll.UserBLL;
import pt.ipvc.dal.Role;
import pt.ipvc.dal.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<User> users = UserBLL.index();

        for(User user : users) {
            System.out.println(user.toString());
        }
    }
}