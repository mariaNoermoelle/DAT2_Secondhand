package persistence;

import entities.User;
import exceptions.DatabaseException;
import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper
{
    public static User login(Context ctx) throws DatabaseException
    {
        User user;
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try(Connection connection = connectionPool.getConnection())
        {
            try(PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, ctx.formParam("username"));
                ps.setString(2, ctx.formParam("password"));
                ResultSet rs = ps.executeQuery();

                if(rs.next())
                {
                    int id = rs.getInt("user_id");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    user = new User(id, username, password, role);
                }
                else
                {
                    throw new DatabaseException("Fejl i input...");
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return user;
    }

}
