package com.gcu.controller;

import java.util.List;




import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.business.ProductBusinessService;
import com.gcu.model.ProductModel;
/**
 * The Controller that handles all routing for product pages
 *
 */
@Controller
@RequestMapping("/transact")
public class TransactionController 
{
	@Autowired
	private ProductBusinessService service;
	/**
	 * displays the product page that shows all the products in the database
	 * @param model (page model)
	 * @return products page
	 */
	@GetMapping("/")
	public String display(Model model) 
	{
		//display method for landing on the all products page, passes services get all products in as 'products' attribute
		model.addAttribute("products", service.read());
		//return products view
		return "products";
	}
	/**
	 * displays the create product page and form
	 * @param model (page model)
	 * @return create products page
	 */
	@GetMapping("/addProduct")
	public String displayCreate(Model model) 
	{
		//display for landing on the create page, loads a new product as 'productModel' attribute
	    model.addAttribute("productModel", new ProductModel());
	    //return createProduct view
	    return "createProduct";
	}
	/**
	 * Handles the creating of the product and if valid routes back to the 
	 * products page - if errors, routes back to the create product form
	 * and displays validation errors
	 * @param productModel (product information)
	 * @param bindingResult (error logging)
	 * @param model (page model)
	 * @return either create product or products page based on if the data was valid
	 */
	@PostMapping("/addBlock")
	public String addBlock(@Valid ProductModel productModel, BindingResult bindingResult, Model model) 
	{
		//createProduct method for displayCreate submission, calls service's create method
		if (bindingResult.hasErrors()) 
		{
			return "createProduct";
		}
		//Prints the created product's name for debugging
		System.out.println(productModel.getProductNumber());
		//calling service to create the product and insert into database
		boolean result = service.create(productModel);
		//add success or failure attribute depending on the result of creation
		if(result == true) 
		{
			model.addAttribute("resultMessage", "Product Created Successfully");
		}
		else
		{
			model.addAttribute("resultMessage", "Something went wrong...");
		}
		//Create list of all retrieved products from service method
		List<ProductModel> productsList = service.read();	
		//Return to all products page after creation, with new updated list and success or failure pages
		model.addAttribute("title", "All Products");
		model.addAttribute("products", productsList);
		return "products";
		
	}
	@PostMapping("/saveChain")
	public String saveChain(@Valid ProductModel productModel, BindingResult bindingResult, Model model) 
	{
		//createProduct method for displayCreate submission, calls service's create method
		if (bindingResult.hasErrors()) 
		{
			return "createProduct";
		}
		//Prints the created product's name for debugging
		System.out.println(productModel.getProductNumber());
		//calling service to create the product and insert into database
		boolean result = service.create(productModel);
		//add success or failure attribute depending on the result of creation
		if(result == true) 
		{
			model.addAttribute("resultMessage", "Product Created Successfully");
		}
		else
		{
			model.addAttribute("resultMessage", "Something went wrong...");
		}
		//Create list of all retrieved products from service method
		List<ProductModel> productsList = service.read();	
		//Return to all products page after creation, with new updated list and success or failure pages
		model.addAttribute("title", "All Products");
		model.addAttribute("products", productsList);
		return "products";
		
	}
	/** deletes a product
	 * @param id (product id)
	 * @param productModel (product model)
	 * @param bindingResult (error log)
	 * @param model (page model)
	 * @return products with a success/fail message
	 */
	@PostMapping("/doDelete")
	public String deleteProduct(@RequestParam(name="id") int id, @Valid ProductModel productModel, BindingResult bindingResult, Model model) 
	{
		//deleteProduct method for button call in products page, first find the product with the ID passed in from the view
		productModel = service.findById(id);
		//once product is obtained, delete the product from the database by calling the service method
		//add success or failure attribute depending on result of deletion
		if(service.delete(productModel) != true || productModel.getProductId() == -1) 
		{
			//if service failed to delete object or objectID was not found in database, failure
			model.addAttribute("resultMessage", "Something went wrong...");
		}
		else
		{
			//else success
			model.addAttribute("resultMessage", "Product Deleted Successfully");
		}
		//retrieve updated list and return products page with updated list
		List<ProductModel> productsList = service.read();
		model.addAttribute("title", "All Products");
		model.addAttribute("products", productsList);
		return "products";
		
	}
	/** update product page routing
	 * @param id (product id)
	 * @param model (page model)
	 * @return update product page
	 */
	@PostMapping("/editView")
	public String editView(@RequestParam(name="id") int id, Model model) 
	{
		//editView method for landing on the edit page, requestparam ID for finding which user was clicked on
		//Add attributes and set attribute 'productModel' as the object that was clicked on
	    model.addAttribute("title", "Edit Product");
	    model.addAttribute("productModel", service.findById(id));
	    //return updateProduct view
	    return "updateProduct";
	}
	/** edit product in database
	 * @param id (product id)
	 * @param productModel (updated product model)
	 * @param bindingResult (error log)
	 * @param model (page model)
	 * @return products page with a success/fail message
	 */
	@PostMapping("/doEdit")
	public String editProduct(@RequestParam(name="id") int id, @Valid ProductModel productModel, BindingResult bindingResult, Model model) 
	{	
		//doEdit method for updating the object in the database
		//Find oldProduct by retrieving object with ID passed as requested parameter
		ProductModel oldProduct = service.findById(id);
		//productModel passed in as parameter is the new product, replace oldProduct with new Product by calling service
		boolean result  = service.update(oldProduct, productModel);
		//add success or failure attribute depending on result of replacement/update
		if(result == true) 
		{
			model.addAttribute("resultMessage", "Product Updated Successfully");
		}
		else
		{
			model.addAttribute("resultMessage", "Something went wrong...");
		}
		//retrieve updated list and return all products page with updated list
		List<ProductModel> productsList = service.read();
		model.addAttribute("title", "All Products");
		model.addAttribute("products", productsList);
		return "products";
	}
	
	
}
