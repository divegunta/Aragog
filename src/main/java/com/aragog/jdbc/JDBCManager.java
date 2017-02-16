package com.aragog.jdbc;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Collection;

import com.aragog.datamodel.GetItemsRequest;
import com.aragog.datamodel.Item;

public class JDBCManager {

    private static final String INSERT_QUERY_ITEMS = "INSERT INTO items (item_title,item_price) VALUES (?,?)";

    private static final String SELECT_ITEMS_BY_CRITERIA = "SELECT * FROM items WHERE item_title LIKE ?";

    private Connection getConnection(String url) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public boolean insertItems(Collection<Item> items) throws SQLException {
        boolean result = false;
        PreparedStatement preparedStatement = null;
        Savepoint savepoint = null;
        Connection connection = getConnection("jdbc:oracle:thin:username/password@amrood:1521:EMP");
        for (Item item : items) {
            try {
                savepoint = connection.setSavepoint("insertItemTuple");
                preparedStatement = connection.prepareStatement(INSERT_QUERY_ITEMS);
                preparedStatement.setString(1, item.getTitle());
                preparedStatement.setDouble(2, item.getPrice());
                result = preparedStatement.executeUpdate() == 1 ? true : false;
                connection.commit();
            } catch (SQLException e) {
                connection.rollback(savepoint);
                throw new SQLException(e);
            } finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                connection.releaseSavepoint(savepoint);
            }
        }
        return result;
    }

    public Collection<Item> getItemsByCriteria(GetItemsRequest request) throws SQLException {
        Collection<Item> items = new ArrayList<Item>();
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection("jdbc:oracle:thin:username/password@amrood:1521:EMP");
        try {
            preparedStatement = connection.prepareStatement(SELECT_ITEMS_BY_CRITERIA);
            StringBuilder searchKey = new StringBuilder();
            searchKey.append('%').append(request.getSearchKey()).append('%');
            preparedStatement.setString(1, searchKey.toString());
            
            if (preparedStatement != null) {
                ResultSet results = preparedStatement.executeQuery();
                while (results.next()) {
                    System.out.println("Found item title: " + results.getString("item_title")
                            + "and item price: " + results.getDouble("item_price"));

                    Item item = new Item(results.getString("item_title"),
                            results.getDouble("item_price"));
                    items.add(item);
                }
            }
        } catch (final SQLException e) {
            throw new SQLException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return items;
    }
}
