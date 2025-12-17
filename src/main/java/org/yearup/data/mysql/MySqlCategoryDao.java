package org.yearup.data.mysql;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        try(Connection connection = getConnection();
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
    {
        // create a new category
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
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
