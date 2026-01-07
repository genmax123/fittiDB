package de.genrichgolzsch.fittidb.dao;

import de.genrichgolzsch.fittidb.model.UserRoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDao {

    private final Connection conn;

    public UserRoleDao(Connection conn) {
        this.conn = conn;
    }

    public List<UserRoleView> findAll() throws Exception {

        String sql = """
                            SELECT
                    u.user_id,
                    u.username,
                    r.role_name,
                    r.description,
                    u.aktive
                FROM users u
                JOIN roles r ON r.role_id = u.role_id
                ORDER BY u.username;

                        """;

        List<UserRoleView> result = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(new UserRoleView(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("role_name"),
                        rs.getString("description"),
                        rs.getInt("aktive") == 1));
            }
        }
        return result;
    }
}
