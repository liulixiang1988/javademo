package io.github.liulixiang.course;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.route.RouteOverview.enableRouteOverview;

public class Main {
    public static void main(String[] args) {
        get("/", (req, res) -> new ModelAndView(null, "index.hbs"), new HandlebarsTemplateEngine());
        post("/sign-in", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", req.queryParams("username"));
            return new ModelAndView(model, "sign-in.hbs");
        }, new HandlebarsTemplateEngine());
        enableRouteOverview();
    }
}
