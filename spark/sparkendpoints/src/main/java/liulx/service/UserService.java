package liulx.service;

import com.mongodb.MongoClient;
import liulx.domain.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by Liu Lixiang on 2017/4/30.
 */
public class UserService {
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    Datastore datastore = new Morphia().createDatastore(mongoClient, "users");

    public User getUser(String username) {
        User user = datastore.createQuery(User.class).field("userName").endsWith(username).get();
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
}
