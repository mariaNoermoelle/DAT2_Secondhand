package persistence;

import entities.Item;
import entities.User;
import exceptions.DatabaseException;

import io.javalin.http.Context;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemMapper
{
    public static List<Item> getItems(Context ctx)
    {
        List<Item> itemlist = new ArrayList<>();
        // fetch data from database (same procedure as we did in UserMapper.login())

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        String sql = "SELECT * FROM items WHERE author = ? AND title = ? AND body = ? AND price = ? AND author_address = ?";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, ctx.formParam("author"));
                ps.setString(2, ctx.formParam("title"));
                ps.setString(3, ctx.formParam("body"));
                ps.setString(4, ctx.formParam("price"));
                ps.setString(5, ctx.formParam("author_address"));
                ResultSet rs = ps.executeQuery();

                // instead of if(rs.next()) then use a while loop; while(rs.next())
                // inside the while loop, you create a new Item() using data from the db and add it to an arraylist.

                while (rs.next())
                {
                    int itemId = rs.getInt("item_id");
                    String author = rs.getString("author");
                    String title = rs.getString("title");
                    String body = rs.getString("body");
                    int price = rs.getInt("price");
                    String authorAddress = rs.getString("author_address");
                    itemlist.add(new Item(itemId, author, title, body, price, authorAddress));
                }
            } catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return itemlist;
    }

    public static void createNewItem(Context ctx)
    {

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        String sql = "INSERT INTO items (author, title, body, price, author_address) VALUES (?,?,?,?,?)";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                User user = ctx.sessionAttribute("currentUser");

                String author = user.getUsername();

                String title = ctx.formParam("title");
                String body = ctx.formParam("body");
                int price = Integer.parseInt(ctx.formParam("price"));
                String authorAddress = ctx.formParam("author_address");

                ps.setString(1, author);
                ps.setString(2, title);
                ps.setString(3, body);
                ps.setInt(4, price);
                ps.setString(5, authorAddress);

                ps.executeUpdate();

            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
