package liulx;

import liulx.domain.User;

import static liulx.util.JsonUtil.json;
import static liulx.util.JsonUtil.toObject;
import static spark.Spark.get;
import static spark.Spark.post;
/**
 * Created by Liu Lixiang on 2017/4/30.
 */
public class Api {
    public static void main(String[] args) {
        get("greeting", (req, res) -> {
            String name = req.queryParams("name");
            return "Hello " + name;
        });

        get("/:username", (req, res) -> {
            String name = req.params(":username");
            return "Your username is " + name;
        });

        //请求必须是json，返回响应设置头也是json
        post("/new-user", "application/json", (req, res) -> {
            res.type("application/json");
            User user = toObject(req.body(), User.class);
            return user;
        }, json());
    }
}
