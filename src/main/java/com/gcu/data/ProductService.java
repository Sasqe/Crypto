package com.gcu.data;

import java.util.ArrayList;


import java.util.List;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.lang.*;
import com.gcu.model.ProductModel;

/**
 * product data service
 */
@Service
public class ProductService implements IDataAccess<ProductModel> 
{
	//Autowired datasource injections
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	/** sets the datasource
	 * @param datasource (datasource)
	 */
	public ProductService(DataSource datasource) {
		//set data source and template object
				this.dataSource = datasource;
				this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	/**
	 * calls the database to get all products and returns them in a list
	 * @return List of products
	 */
	@Override
	public List<ProductModel> read() {
		//SQL query to select all from products
		String sql = "SELECT * FROM PRODUCTS";
		//create structure to hold products
		List<ProductModel> products = new ArrayList<ProductModel>();
		try 
		{
			//Execute SQL query and loop over result set, adding to products
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);	
			while(srs.next())
			{
				
		
				products.add(new ProductModel(srs.getInt("id"),
										srs.getString("productnumber"),
										srs.getDouble("productprice")));
			}
		}
		//catch exceptions and print stack trace
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		//return products list
		return products;
	}

	/** creates a product by inserting the model into the database
	 * @param model (Product model)
	 * @return true/false
	 */
	@Override
	public boolean create(ProductModel model) {
		//sql statement for inserting values
				String sql = "INSERT INTO products(id, productnumber,productprice) VALUES(?,?,?)";
				try 
				{
					//execute SQL insert to update values with following model attributes
					int rows = jdbcTemplateObject.update(sql,
														model.getProductId(),
														model.getProductNumber(),
														model.getProductPrice());
					//return result of insertion
					return rows == 1 ? true : false;
							
				}
				//catch and print stack trace
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				return false;
	}
	/** finds a model by id
	 * @param id (product id we are looking for)
	 * @return product model 
	 */
	public ProductModel findById(int id) 
	{
		//SQL query for selecting all from products with ID passed as parameter
		String sql = "SELECT * FROM products WHERE id = '" + id + "'";
		//create structure to hold users
		List<ProductModel> products = new ArrayList<ProductModel>();
		try {
			//Execute SQL query and loop over result set
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);	
			while(srs.next())
			{
				products.add(new ProductModel(srs.getInt("id"),
						srs.getString("productnumber"),
						srs.getDouble("productprice")));
			}
			//There should only be one product with unique ID, if ID is found return products[0] with the result
			if (products.size() == 1) {
				return products.get(0);
			}
			//else if there's multiple products with unique ID, return null for error handling
			else if (products.size() > 1) {
				return null;
			}
			//Else no object was found, return product with negative ID for error handling
			return new ProductModel(-1,
					"000",
					0.00);
		}
		//catch and print stack trace
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/** updates a model
	 * @param oldProduct (original model)
	 * @param newProduct (updated model)
	 * @return true/false
	 */
	@Override
	public boolean update(ProductModel oldProduct, ProductModel newProduct) {
		//sql statement for updating values
		String sql = "UPDATE products SET productNumber = '" + newProduct.getProductNumber() + "', productPrice = '" + newProduct.getProductPrice() + "' WHERE products.id = " + oldProduct.getProductId() + "";
		try 
		{
			//execute SQL update
			int rows = jdbcTemplateObject.update(sql);
			//return result of insertion
			return rows == 1 ? true : false;
					
		}
		//catch and print stack trace
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	/** deletes product
	 * @param model (product model to be deleted)
	 * @return true/false
	 */
	@Override
	public boolean delete(ProductModel model) 
	{
		//get the ID of the product that's being deleted
		int id = model.getProductId();
		//sql statement for deleting a value by id
		String sql = "DELETE FROM products WHERE id = '" + id + "'";
		try 
		{
			int rows = jdbcTemplateObject.update(sql);
			
			return rows == 1 ? true : false;
		}
		//catch and print stack trace
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}

}
