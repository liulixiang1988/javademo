package liulx.service;

import com.mongodb.MongoClient;
import liulx.domain.Post;
import liulx.domain.User;
import liulx.iservice.IPostService;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

/**
 * Created by Liu Lixiang on 2017/5/2.
 */
public class PostService implements IPostService {
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    Datastore datastore = new Morphia().createDatastore(mongoClient, "users");

    UpdateOperations<User> ops;

    public Post create(Post post, User user) {
        int id = user.getPosts().size() + 1;
        post.setId((long) id);
        //更新，见morphia文档
        Query<User> updateQuery = datastore.createQuery(User.class).field("_id").equal(user.getId());
        ops = datastore.createUpdateOperations(User.class).addToSet("posts", post);
        datastore.update(updateQuery, ops);

        return post;
    }

    public List<Post> getPosts(User user) {
        return user.getPosts();
    }

    public Post getPost(int id, User user) {
        return user.getPosts().get(id - 1);
    }
}
