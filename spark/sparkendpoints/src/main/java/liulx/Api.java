package liulx;

import liulx.domain.User;
import liulx.service.UserService;

import static liulx.util.JsonUtil.json;
import static liulx.util.JsonUtil.toObject;
import static spark.Spark.get;
import static spark.Spark.post;
/**
 * Created by Liu Lixiang on 2017/4/30.
 */
public class Api {
    public static UserService userService = new UserService();

    public static void main(String[] args) {
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
            return userService.createUser(user);
        }, json());
    }
}
