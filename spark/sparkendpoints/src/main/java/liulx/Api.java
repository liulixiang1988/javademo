package liulx;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import liulx.domain.Post;
import liulx.domain.User;
import liulx.service.PostService;
import liulx.service.UserService;
import liulx.util.MongoConnector;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;

import static liulx.util.JsonUtil.json;
import static liulx.util.JsonUtil.toObject;
import static spark.Spark.*;

/**
 * Created by Liu Lixiang on 2017/4/30.
 */
public class Api {

    private static MongoConnector mongoDb = new MongoConnector();

    public static UserService userService = new UserService();
    public static PostService postService = new PostService();
    public static Logger logger = LoggerFactory.getLogger(Api.class);

    public static void main(String[] args) {

        DB db = mongoDb.getDb();
        DBCollection collection = db.getCollection("myvideos.files");


        before("/*", (req, res) -> {
            //halt("You can't come in");
            String authentication = req.headers("Authorization");

//            if (authentication != null && authentication.startsWith("Basic")) {
//                String credentials = authentication.substring("Basic".length()).trim();
//                byte[] decoded = DatatypeConverter.parseBase64Binary(credentials);
//                String decodedString = new String(decoded);
//                logger.info(decodedString);
//                String[] actualCredentials = decodedString.split(":");
//                String userName = actualCredentials[0];
//                String password = actualCredentials[1];
//
//                if (userService.authentication(userName, password)) {
//                    halt(401, "Sorry, Not authorized!");
//                }
//            } else {
//                halt(401, "Not authorized!");
//            }
        });

        post("/api/:username/videos/new", (req, res) -> {
            String location = "image";          // the directory location where files will be stored
            long maxFileSize = 100000000;       // the maximum size allowed for uploaded files
            long maxRequestSize = 100000000;    // the maximum size allowed for multipart/form-data requests
            int fileSizeThreshold = 1024;       // the size threshold after which files will be written to disk

            final File upload = new File(location);
            if (!upload.exists() && !upload.mkdirs()) {
                throw new RuntimeException("Failed to create directory " + upload.getAbsolutePath());
            }
            //jetty中上传文件必须指定进行设置
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                    location, maxFileSize, maxRequestSize, fileSizeThreshold);
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);

           String username = req.params(":username");

           //从请求中读取文件
            InputStream inputStream = req.raw().getPart("file").getInputStream();
            Part uploadFile = req.raw().getPart("file");

            //
            GridFS gridFS = new GridFS(db, "myvideos");

            GridFSInputFile gfsFile = gridFS.createFile(inputStream);
            gfsFile.put("username", username);
            gfsFile.put("contentType", req.raw().getContentType());
            gfsFile.put("filename", uploadFile.getSubmittedFileName());

            collection.insert(gfsFile);

            gfsFile.save();

            return 201;
        });

        get("greeting", (req, res) -> {
            String name = req.queryParams("name");
            return "Hello " + name;
        });

        get("/:username", (req, res) -> {
            res.type("application/json");
            String name = req.params(":username");

            User user = userService.getUser(name);
            if (user != null) {
                return user;
            } else {
                return "user was not found";
            }
        }, json());

        //请求必须是json，返回响应设置头也是json
        post("/new-user", "application/json", (req, res) -> {
            res.type("application/json");
            User user = toObject(req.body(), User.class);
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);
            return userService.createUser(user);
        }, json());

        post("/api/:username/newpost", (req, res) -> {
            res.type("application/json");
           String username = req.params(":username");
           User user = userService.getUser(username);
           Post post = toObject(req.body(), Post.class);
           postService.create(post, user);
           return post;
        }, json());

        get("/api/:username/posts", (req, res) -> {
            res.type("application/json");
            String username = req.params(":username");
            User user = userService.getUser(username);
            return postService.getPosts(user);
        }, json());

        get("/api/:username/posts/:postId", (req, res) -> {
            res.type("application/json");

            String username = req.params(":username");
            int id = Integer.parseInt(req.params(":postId"));

            return postService.getPost(id, userService.getUser(username));
        }, json());
    }
}
