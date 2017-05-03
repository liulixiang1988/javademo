package liulx.service;

import com.mongodb.MongoClient;
import liulx.domain.User;
import liulx.iservice.IUserService;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by Liu Lixiang on 2017/4/30.
 */
public class UserService implements IUserService{
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    Datastore datastore = new Morphia().createDatastore(mongoClient, "users");

    public User getUser(String username) {
        User user = datastore.createQuery(User.class).field("userName").equal(username).get();
        if (user != null) {
            return user;
        }
        return null;
    }

    public String createUser(User user) {
        if (getUser(user.getUserName()) == null){
            datastore.save(user);
            return "User created";
        }
        return "User already exists";

    }

    public boolean authentication(String username, String password) {
        User user = getUser(username);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return true;
        }
        return false;
    }
}
