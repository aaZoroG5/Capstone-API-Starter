package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {

        //create shopping cart object
        ShoppingCart cart = new ShoppingCart();

        //create sql String
        String sql = """
                SELECT
                    S.user_id,
                    S.product_id,
                    S.quantity,
                    P.*
                FROM
                    shopping_cart S
                JOIN
                    products P ON (P.product_id = S.product_id)
                WHERE
                    S.user_id = ?
                """;
        //try/catch with resources
        try(//create connection and preparedStatement
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql))
        {//set userId and run the query
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();

            while(result.next())
            {//create new product
                Product product = new Product();

                //set all values for product
                product.setProductId(result.getInt("product_id"));
                product.setName(result.getString("name"));
                product.setPrice(result.getBigDecimal("price"));
                product.setCategoryId(result.getInt("category_id"));
                product.setDescription(result.getString("description"));
                product.setSubCategory(result.getString("subcategory"));//MISTAKE: misspelled label
                product.setImageUrl(result.getString("image_url"));
                product.setStock(result.getInt("stock"));
                product.setFeatured(result.getBoolean("featured"));

                //get quantity of product
                int quantity = result.getInt("quantity");

                //create cart item
                ShoppingCartItem cartItem = new ShoppingCartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);

                cart.add(cartItem);
            }
        }catch(SQLException e){
            throw new RuntimeException("There was an error retrieving order by userId" + e);
        }
        return cart;
    }

    @Override
    public void addToCart(int productId, int userId) {
        String sql = """
                INSERT INTO
                    shopping_cart (user_id, product_id, quantity)
                VALUES
                    (?,?,1)
                ON DUPLICATE KEY UPDATE quantity = quantity + 1
                """;//the quantity is set as 1 by default
                    //the last statement adds the quantity of products
        try(//create connection and prepared statement
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql))
        {   //set user and product Id
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.executeUpdate();

        }catch(SQLException e){
            throw new RuntimeException("There was an error adding to cart" + e);
        }
    }

    @Override
    public void clearCart(int userId) {

    }

    @Override
    public void editCart(int productId, int userId, int quantity) {

    }
}
