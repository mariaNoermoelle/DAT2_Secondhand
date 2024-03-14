import config.ThymeleafConfig;
import controllers.ItemCreateController;
import controllers.ItemOverviewController;
import controllers.UserController;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.Map;

public class Main
{
    public static void main(String[] args)
    {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing

        app.get("/", ctx ->  ctx.render("index.html"));

        app.post("/login", ctx ->
        {
            UserController.login(ctx);
        });

       // app.get("/welcome", ctx -> ctx.render("welcome.html"));

        app.get("/welcome", ctx ->
        {
            var items = ItemOverviewController.loadItems(ctx);
            ctx.render("/welcome.html", Map.of("items", items));
        });

        app.get("/createitem", ctx ->
        {
            ctx.render("/createitem.html");
        });

        app.post("/submitItem", ctx ->
        {
            ItemCreateController.insertItem(ctx);
        });
    }
}
