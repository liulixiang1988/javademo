package liulx;

import liulx.domain.User;
import liulx.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;

import static liulx.util.JsonUtil.json;
import static liulx.util.JsonUtil.toObject;
import static spark.Spark.*;

/**
 * Created by Liu Lixiang on 2017/4/30.
 */
public class Api {
    public static UserService userService = new UserService();
    public static Logger logger = LoggerFactory.getLogger(Api.class);

    public static void main(String[] args) {

        before("/*", (req, res) -> {
            //halt("You can't come in");
            String authentication = req.headers("Authorization");

            if (authentication != null && authentication.startsWith("Basic")) {
                String credentials = authentication.substring("Basic".length()).trim();
                byte[] decoded = DatatypeConverter.parseBase64Binary(credentials);
                String decodedString = new String(decoded);
                logger.info(decodedString);
                String[] actualCredentials = decodedString.split(":");
                String userName = actualCredentials[0];
                String password = actualCredentials[1];

                if (userService.authentication(userName, password)) {
                    halt(401, "Sorry, Not authorized!");
                }
            } else {
                halt(401, "Not authorized!");
            }
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
    }
}
