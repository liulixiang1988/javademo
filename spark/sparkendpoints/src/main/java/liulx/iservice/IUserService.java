package liulx.iservice;

import liulx.domain.User;

/**
 * Created by Liu Lixiang on 2017/5/3.
 */
public interface IUserService {
    User getUser(String username);
    String createUser(User user);
    boolean authentication(String username, String password);
}
