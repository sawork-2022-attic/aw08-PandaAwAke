package com.example.webpos.db;

import com.example.webpos.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//@Repository
public class AmazonMysqlDB implements PosDB {

    private LinkedList<Product> products = new LinkedList<>();

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Amazon?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "123456";
    static final String DATABASE_TABLE_NAME = "meta_Gift_Cards";

    private Connection connection = null;
    private int fetchedItems = 0;
    private static final int ITEMS_TO_FETCH_ONCE = 40;


    public void fetchFromDatabase() throws Exception {
        if (connection == null) {
            try {
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        PreparedStatement stmt = connection.prepareStatement(
                "SELECT main_cat, title, asin, category, imageURLHighRes FROM " + DATABASE_TABLE_NAME +
                        " LIMIT ?,?"
        );

        stmt.setInt(1, fetchedItems);
        stmt.setInt(2, ITEMS_TO_FETCH_ONCE);

        fetchedItems += ITEMS_TO_FETCH_ONCE;

        ResultSet result = stmt.executeQuery();

        while (result.next()) {
            Product product = new Product();
            product.setId(String.valueOf(result.getString("asin")));
            product.setName(result.getString("title"));
            product.setPrice(233.3);

            String imageURLHighResStr = result.getString("imageURLHighRes");
            ArrayList<String> imageURLs;
            ObjectMapper objectMapper = new ObjectMapper();
            imageURLs = objectMapper.readValue(imageURLHighResStr, ArrayList.class);

            if (imageURLs.size() > 0) {
                product.setImage(imageURLs.get(0));
            }
            products.offer(product);
        }
        // 完成后关闭
        result.close();
        stmt.close();
    }

    @Override
    public List<Product> getProducts() {
        try {
            if (products.isEmpty()) {
                fetchFromDatabase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product getProduct(String productId) {
        for (Product p : getProducts()) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

}
