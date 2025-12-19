package org.yearup.data.mysql;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories()
    {
        // get all categories
        List<Category> categories = new ArrayList<>();

        String sql = """
                SELECT
                    category_id,
                    name,
                    description
                FROM
                    categories
                """;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();)
        {
            while (results.next()) {
                //create a new category object
                Category category = new Category();
                //set the categories id
                category.setCategoryId(results.getInt("category_id"));
                //set the categories name
                category.setName(results.getString("name"));
                //set the categories description
                category.setDescription(results.getString("description"));
                //add the category object to our list
                categories.add(category);
            }
        }catch(SQLException e){
            System.out.println("There was an issue retrieving the data");
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category getById(int categoryId)
    {
        // get category by id
        //arrayList is not needed for this method because categoryId is a primary key with 0 or 1 value
        String sql = """
                SELECT
                    category_id,
                    name,
                    description
                FROM
                    categories
                WHERE
                    category_id = ?
                """;
        try(//establish connection and create a prepared statement
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);)
        {
            statement.setInt(1,categoryId);

            try (ResultSet results = statement.executeQuery())
            {
                if (results.next()){
                    //create a new category object
                    Category category = new Category();
                    //set the categories id
                    category.setCategoryId(results.getInt("category_id"));
                    //set the categories name
                    category.setName(results.getString("name"));
                    //set the categories description
                    category.setDescription(results.getString("description"));

                    return category;

                }
            }
        }catch(SQLException e ){
            System.out.println("There was an error retrieving the data");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category create(Category category)
    {// create an insert query
       String sql = """
               INSERT INTO
                    categories (name, description)
               VALUES
                    (?,?)
               """;

       try (//establish connection and create a prepared statement
            Connection connection = getConnection();
            //the second parameter gives me the values of any auto-generated columns
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
       {    //construct the  new category
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.executeUpdate();
            //this resultSet retrieves the generated category Id
            try (ResultSet keys = statement.getGeneratedKeys())
            {
                if (keys.next())//moves cursor to first row and returns true if row exists
                {
                    category.setCategoryId(keys.getInt(1));//sets category id
                }
            }
            return category;

       }catch(SQLException e)
       {
           e.printStackTrace();
           throw new RuntimeException("Error creating category", e);
       }

    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
        String sql = """
                UPDATE
                    categories
                SET
                    name = ?,
                    description = ?
                WHERE
                    category_id = ?
                """;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, categoryId);

            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("There was an error updating the category");
        }
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
        String sql = """
                DELETE FROM
                    categories
                WHERE
                    category_id = ?
                """;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, categoryId);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Error deleting category", e);
        }
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
