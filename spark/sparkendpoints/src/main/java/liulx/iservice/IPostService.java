package liulx.iservice;

import liulx.domain.Post;
import liulx.domain.User;

import java.util.List;

/**
 * Created by Liu Lixiang on 2017/5/3.
 */
public interface IPostService {
    Post create(Post post, User user);
    List<Post> getPosts(User user);
    Post getPost(int id, User user);
}
