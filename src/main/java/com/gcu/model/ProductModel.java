package com.gcu.model;

import java.text.NumberFormat;
import java.util.Locale;

import javax.validation.constraints.NotNull;

/**
 *The model for product details
 */
public class ProductModel 
{
	private int ProductId;
	@NotNull(message="Product Number is a required field")
	private String ProductNumber;
	@NotNull(message="Price is a required field")
	private double ProductPrice;

	
	/**
	 * Constructor with all parameters
	 * @param productId (product ID #)
	 * @param productNumber (product Number)
	 * @param price (Product Price)
	 */
	public ProductModel(int productId, String productNumber, double price) 
	{
		this.ProductId = productId;
		// Pad Format Product Number
		int pad = Integer.valueOf(productNumber);
		String padded = String.format("%03d", pad);
		this.ProductNumber = padded;
		this.ProductPrice = price;
	}
	/**
	 * Empty copy constructor
	 */
	public ProductModel() {
		
	}
	/** product id getter
	 * @return product id #
	 */
	public int getProductId() {
		return ProductId;
	}
	/** product id setter
	 * @param productId (product id #)
	 */
	public void setProductId(int productId) {
		ProductId = productId;
	}
	/** product name getter
	 * @return product name
	 */
	public String getProductNumber() {
		return ProductNumber;
	}
	/** product name setter
	 * @param productName (Product name)
	 */
	public void setProductNumber(String productNumber) {
		ProductNumber = productNumber;
	}
	/** product price getter
	 * @return product price
	 */
	public double getProductPrice() {
		return ProductPrice;
	}
	/** product price setter
	 * @param productPrice (product price)
	 */
	public void setProductPrice(double productPrice) {
		ProductPrice = productPrice;
	}
	public String displayPrice(double price) {
		Locale locale = new Locale("en", "US");
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
		return currencyFormatter.format(price);
	}
	
}
