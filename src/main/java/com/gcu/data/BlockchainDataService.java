package com.gcu.data;

import java.util.ArrayList;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.gcu.model.Block;
import com.gcu.model.CredentialsModel;
import com.gcu.model.UserModel;
/**
 * All the Data layer logic for users
 */
@Service
public class BlockchainDataService implements IDataAccess<Block> 
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
	public BlockchainDataService(DataSource dataSource) 
	{
		//set data source and template object
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	@Override
	public List<Block> read() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean create(Block t) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean update(Block oldt, Block newt) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean delete(Block t) {
		// TODO Auto-generated method stub
		return false;
	}

}
