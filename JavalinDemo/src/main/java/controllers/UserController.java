package controllers;

import entities.User;
import exceptions.DatabaseException;
import io.javalin.http.Context;
import persistence.UserMapper;

public class UserController
{
    public static void login(Context ctx)
    {
        try
        {
            User user = UserMapper.login(ctx);
            ctx.sessionAttribute("currentUser", user);

            ctx.redirect("/welcome");
        }
        catch (DatabaseException e)
        {
            ctx.attribute("Database fejl", e.getMessage());
        }

    }
}
