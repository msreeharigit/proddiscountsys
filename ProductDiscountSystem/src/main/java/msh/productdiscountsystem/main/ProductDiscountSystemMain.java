package msh.productdiscountsystem.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import msh.productdiscountsystem.pojo.Brand;
import msh.productdiscountsystem.pojo.BrandRepository;
import msh.productdiscountsystem.pojo.Product;
import msh.productdiscountsystem.pojo.RootCategory;
import msh.productdiscountsystem.pojo.UserInput;
import msh.productdiscountsystem.utils.InputWrapper;
import msh.productdiscountsystem.utils.Logger;
import msh.productdiscountsystem.utils.ProductUtilities;

public class ProductDiscountSystemMain {

	public static void main(String[] args) {
		Logger.info("%%%%%____Welcome to Product Discount System___%%%%%");

		Runtime runtime = Runtime.getRuntime();
		runtime.addShutdownHook(new Thread() {
			@Override
			public void run() {
				Logger.info("Thanks for using the Product Discount System. Shutting down the system gracefully.");
			}
		});

		/// Loading required configuration
		// Load product categories
		RootCategory rootCategory = ProductUtilities.loadProductCategories("categories.json");
		if (rootCategory == null || rootCategory.getCategories() == null) {
			Logger.error("Unable to load categories");
			System.exit(1);
		}
		
		// Load brands
		BrandRepository brandRepo = ProductUtilities.loadBrands("brands.json");
		if (brandRepo == null || brandRepo.getBrands() == null) {
			Logger.error("Unable to load categories");
			System.exit(1);
		}

		// Make brand map
		Map<String, Brand> brandMap = new HashMap<String, Brand>();
		Set<Brand> brands = brandRepo.getBrands();

		for (Brand brand : brands) {
			brandMap.put(brand.getName(), brand);
		}

		// take input from user
		InputWrapper input  = new InputWrapper(brandMap.keySet(),rootCategory.getCategoryMap().keySet());
		UserInput ui = input.takeInput();
		
		// calculate discounted price  for each product entered part of inventory
		ProductUtilities.calculateDiscountForProduct(ui.getProducts(), brandMap, rootCategory.getCategoryMap());
		
		// make  product  id <-> product map
		Map<Integer,Product> productMap = new  HashMap<Integer,Product>();
		for(Product product:ui.getProducts()){
			productMap.put(product.getId(), product);
		}
		
		List<Set<Integer>> carts = ui.getCarts();
		for(Set<Integer> cart:carts){
			Logger.info(""+ProductUtilities.calculateCartTotalPrice(cart, productMap));
		}
	}
}
