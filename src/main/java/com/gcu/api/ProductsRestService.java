package com.gcu.api;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.gcu.business.ProductBusinessService;

import com.gcu.model.ProductModel;


/**
 * Returns the JSON API
 */
@RestController
@RequestMapping("/service")
public class ProductsRestService 
{
	@Autowired
	ProductBusinessService service;
	
	/** Displays a list of products in JSON format
	 * @return JSON view of products
	 */
	@GetMapping(path="/products", produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getOrdersAsJSON()
	{
		try
		{
			//Create a new list of products and populate it with all products in the database
			List<ProductModel> products = service.read();
			//if products is null, return HTTP Not Found entity
			if(products == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			// else return HTTP Ok response entity and products in json format
			else
			{
				return new ResponseEntity<>(products, HttpStatus.OK);
			}
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	/** Displays an individual product in JSON format
	 * @param id - id of specific product
	 * @return JSON view of product with matching id
	 */
	//GetMapping with path that takes in argument passed through URL
	@GetMapping(path="/products/{id}", produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getOrdersAsJSON(@PathVariable("id") int id)
	{
		try
		{
			//Find product in database with ID passed in through URL
			ProductModel product = service.findById(id);
			// If internal server error, return HTTP Internal Server Error
			if (product == null)
			{
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			// Else if product isnt found, return HTTP Not Found error
			else if (product.getProductId() == -1)
			{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			//Else turn HTTP OK and return product in JSON format
			else 
			{
				return new ResponseEntity<>(product, HttpStatus.OK);
			}
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}

