package com.gcu.data;

import java.util.ArrayList;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.gcu.model.CredentialsModel;
import com.gcu.model.UserModel;
/**
 * All the Data layer logic for users
 */
@Service
public class UserDataService implements IDataAccess<UserModel> 
{
	//Autowired datasource injections
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	//Constructor
	/**
	 * Adds the data source to the data service when its instantiated
	 * @param dataSource (source of data)
	 */
	public UserDataService(DataSource dataSource) 
	{
		//set data source and template object
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	/**
	 * Gets all the users from the database and returns them in a list
	 * @return list of users
	 */
	@Override
	public List<UserModel> read() 
	{
		//sql statement to select all from users
		String sql = "SELECT * FROM USERS";
		//create structure to hold users
		List<UserModel> users = new ArrayList<UserModel>();
		try 
		{
			//Execute SQL query and loop over result set
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);	
			while(srs.next())
			{
				users.add(new UserModel(srs.getInt("id"),
										srs.getString("firstname"),
										srs.getString("lastname"),
										srs.getString("email"),
										srs.getString("number"),
										new CredentialsModel(srs.getString("username"),
													   srs.getString("password")),
										null));
			}
		}
		//catch exception and print stack trace
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		//return users data set 
		return users;
	}

	/**
	 * finds a user in the database by it's id
	 * @param id (user id)
	 * @return user model
	 */
	public UserModel findById(int id) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * create a user using a user model
	 * @param user (user model)
	 * @return true/false
	 */
	@Override
	public boolean create(UserModel user) 
	{
		//sql statement for inserting values
		String sql = "INSERT INTO users(id, firstname, lastname, username, password, email, number) VALUES(?,?,?,?,?,?,?)";
		try 
		{
			//execute SQL insert
			int rows = jdbcTemplateObject.update(sql,
												user.getId(),
												user.getFirstname(),
												user.getLastname(),
												user.getCredentials().getUsername(),
												user.getCredentials().getPassword(),
												user.getEmail(),
												user.getNumber());
			//return result of insertion
			return rows == 1 ? true : false;
					
		}
		//catch exception and print stack trace
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * updates the user model in the database
	 * @param oldt (original model)
	 * @param newt (updated model)
	 * @return true/false
	 */
	@Override
	public boolean update(UserModel oldt, UserModel newt) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * deletes a user from the database
	 * @param t (user model)
	 * @return true/false
	 */
	@Override
	public boolean delete(UserModel t) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Find user by username
	 * @param username (searched username)
	 * @return usermodel found/exception
	 */
	public UserModel findByUsername(String username) 
	{
		//SQL to select all from users where passed credentials match user's credentials
		String sql = "SELECT * FROM users WHERE username = '" + username + "'";
		//create structure to hold users
		List<UserModel> users = new ArrayList<UserModel>();
		try {
			//Execute SQL query and loop over result set
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);	
			while(srs.next())
			{
				users.add(new UserModel(srs.getInt("id"),
										srs.getString("firstname"),
										srs.getString("lastname"),
										srs.getString("email"),
										srs.getString("number"),
										new CredentialsModel(srs.getString("username"),
													   srs.getString("password")),
										null));
			}
			//there should only be one user with unique credentials
			if (users.size() == 1) {
				return users.get(0);
			}
			// else return false
			return null;
		}
		//catch exception and print stack trace
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
