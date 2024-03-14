package controllers;

import persistence.ItemMapper;
import io.javalin.http.Context;

public class ItemCreateController
{
    public static void insertItem(Context ctx)
    {
        ItemMapper.createNewItem(ctx);
    }
}
