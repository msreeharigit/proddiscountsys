package msh.productdiscountsystem.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import msh.productdiscountsystem.pojo.Product;
import msh.productdiscountsystem.pojo.UserInput;

/**
 * This class encapsulated with input functions that help to take input from console
 * @author sreehari
 *
 */
public class InputWrapper {
	
	private Set<String> brands;
	
	private Set<String> categories;
	
	public void setBrands(Set<String> brands) {
		this.brands = brands;
	}
	
	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}
	
	public InputWrapper(Set<String> brands,Set<String> categories){
		this.brands = brands;
		this.categories = categories;
	}

	/**
	 * Read integer value from  console
	 * @param reader
	 * @return
	 */
	private  int readInteger(BufferedReader reader) {
		int value = -1;
		while (value < 1) {
			Logger.info("Enter non-negative integer?");
			// Reading data using readLine
			try {
				String size = reader.readLine();
				value = Integer.parseInt(size);
			} catch (Exception e) {
				// Logger.error(e.getMessage());
				continue;
			}
		}

		return value;
	}

	/**
	 * Take inventory input from console
	 * ex:
	 * 2
	 * 1,Arrow,Shirts,800
	 * 2,Vero Moda,Dresses,1400
	 * 
	 * @param inventorySize
	 * @param reader
	 * @return
	 */
	private  List<Product> readInventory(int inventorySize, BufferedReader reader) {
		List<Product> products = new ArrayList<Product>();
		// take item input
		int count = 0;
		while (count < inventorySize) {
			String item;
			try {
				item = reader.readLine();
			} catch (IOException e1) {
				continue;
			}
			String[] tokens = item.split(",");
			int id = 0;
			double price = 0;
			try {
				id = Integer.parseInt(tokens[0].trim());
				price = Double.parseDouble(tokens[3].trim());
				if (StringUtils.isBlank(item) || tokens.length != 4 || id < 0 || price < 0) {
					Logger.error("Invalid input:Expected format: Id(non negative),Brand,Category,Price(non negative)");
					continue;
				}
				if(!this.brands.contains(tokens[1].trim())){
					Logger.error("Unknown brand["+tokens[1]+"], check inventory entered:Expected format: Id(non negative),Brand,Category,Price(non negative)");
					continue;
				}
				if(!this.categories.contains(tokens[2].trim())){
					Logger.error("Unknown category["+tokens[2]+"], check inventory entered:Expected format: Id(non negative),Brand,Category,Price(non negative)");
					continue;
				}
			} catch (Exception e) {
				Logger.error("Invalid input:Expected format: Id(non negative),Brand,Category,Price(non negative)");
				continue;
			}

			products.add(new Product(id, tokens[1].trim(), tokens[2].trim(), price));
			count++;
		}
		return products.size() == 0 ? null : products;
	}

	/**
	 * Read product ids in the cart from console
	 * @param orderCount
	 * @param reader
	 * @param productMap
	 * @return
	 */
	private  List<Set<Integer>> readProductIdsInTheCart(int orderCount, BufferedReader reader,Map<Integer, Product> productMap) {
		List<Set<Integer>> carts = new ArrayList<Set<Integer>>();
		// take product ids for each order
		int orderInputCount = 0;
		while (orderInputCount < orderCount) {
			String input;
			try {
				input = reader.readLine();
			} catch (IOException e1) {
				Logger.error("Unable to read product ids in the cart:"+e1.getMessage());
				return null;
			}
			String[] tokens = input.split(",");
			boolean error = false;
			Set<Integer> productIds = new TreeSet<Integer>();
			for (String id : tokens) {
				try {
					int productId = Integer.parseInt(id.trim());
					if (productId < 0 || !productMap.containsKey(productId)) {
						Logger.error(
								"Enter non negative number as a product id/verify given product id exists in the inventory");
						error = true;
						break;
					}
					productIds.add(productId);
				} catch (Exception e) {
					Logger.error("Enter non negative number as a product id");
					error = true;
					break;
				}
			}
			if (error) {
				continue;
			}
			carts.add(productIds);
			orderInputCount++;
		}
		
		return carts.size()==0?null:carts;
	}

	/**
	 * Main utility method  to get input from console
	 * @return
	 */
	public UserInput takeInput() {
		UserInput ui = new UserInput();

		// take input for inventory size

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

			int inventorySize = readInteger(reader);
			List<Product> products = readInventory(inventorySize, reader);
			ui.setProducts(products);

			// construct product map
			Map<Integer, Product> productMap = new HashMap<Integer, Product>();
			for (Product product : products) {
				productMap.put(product.getId(), product);
			}

			// take no.of orders input
			int orderCount = readInteger(reader);
			List<Set<Integer>> carts = readProductIdsInTheCart(orderCount, reader, productMap);
			ui.setCarts(carts);

		} catch (IOException e) {
			Logger.error("Unable to take user input:" + e.getMessage());
		}
		return ui;
	}
}
