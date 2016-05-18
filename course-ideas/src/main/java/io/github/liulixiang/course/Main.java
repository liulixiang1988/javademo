package io.github.liulixiang.course;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.get;
import static spark.route.RouteOverview.enableRouteOverview;

public class Main {
    public static void main(String[] args) {
        get("/", (req, res) -> new ModelAndView(null, "index.hbs"), new HandlebarsTemplateEngine());
        enableRouteOverview();
    }
}
