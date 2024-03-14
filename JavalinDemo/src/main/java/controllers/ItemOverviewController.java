package controllers;

import entities.Item;
import persistence.ItemMapper;

import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class ItemOverviewController
{
    public static List<Item> loadItems(Context ctx)
    {
        List<Item> itemlist = new ArrayList<>();
        try
        {
            ItemMapper.getItems(ctx);

        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return itemlist;
    }
}

