package io.vaku.app.domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAO {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/edu-otus-coursework";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "mysecretpassword";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public List<Item> getAll() {
        try (Statement stmt = getConnection().createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery("SELECT * FROM item")) {
                List<Item> items = new ArrayList<>();
                while (resultSet.next()) {
                    items.add(createItem(resultSet));
                }

                return items;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Item> getById(long id) {
        try (PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM item WHERE id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(createItem(resultSet));
                }

                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Item create(Item item) {
        return null;
    }

    public Item update(Item item) {
        return null;
    }

    public void delete(long id) {
        try (PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM item WHERE id = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Item createItem(ResultSet resultSet) throws SQLException {
        return new Item(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getBigDecimal(3)
        );
    }
}
