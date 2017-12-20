package msh.productdiscountsystem.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import msh.productdiscountsystem.pojo.Brand;
import msh.productdiscountsystem.pojo.BrandRepository;
import msh.productdiscountsystem.pojo.Product;
import msh.productdiscountsystem.pojo.ProductCategory;
import msh.productdiscountsystem.pojo.RootCategory;

/**
 * This class provides Product utility methods
 * 
 * @author sreehari
 *
 */
public class ProductUtilities {
	
	/**
	 * To calculate  Cart total price
	 * @param cart
	 * @param productMap
	 * @return
	 */
	public static double calculateCartTotalPrice(Set<Integer> cart,Map<Integer,Product> productMap){
		if(cart == null || productMap == null){
			Logger.error("Unable to calculate Cart total price");
			return 0;
		}
		
		double totalPrice = 0;
		for(Integer productId:cart){
			totalPrice += productMap.get(productId).getDiscountedPrice();
		}
		
		return totalPrice;
	}
	
	/**
	 * Calculate discount price for  each product  of inventory
	 * @param products
	 * @param brandsMap
	 * @param categoryMap
	 */
	public static void calculateDiscountForProduct(List<Product> products,Map<String,Brand> brandsMap,
			Map<String,ProductCategory> categoryMap){
		if(products == null || brandsMap == null || categoryMap == null){
			Logger.error("Unable  to calculate  discount price  for the product due to invalid  input");
			return;
		}
		
		for(Product product:products){
			try{
				Double brandDiscount = brandsMap.get(product.getBrandName()).getDiscount();
				Double categoryBestDiscount  = categoryMap.get(product.getCategoryName()).getBestDiscount();
				Double bestDiscount = brandDiscount>categoryBestDiscount?brandDiscount:categoryBestDiscount;
				Double discountedPrice = product.getPrice() - (product.getPrice()*bestDiscount/100);
				product.setDiscountedPrice(discountedPrice);
			}catch(Exception e){
				Logger.error("Unable to calculate discount price for the product:"+product.getId());
			}
		}
		
	}
	
	/**
	 * @param fileName
	 * @return
	 */
	public static RootCategory loadProductCategories(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			Logger.error("Unable to load categories as fileName is passed as null");
			return null;
		}
		RootCategory rootCategory = null;
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		try {
			rootCategory = gson.fromJson(
					readFromInputStream(ProductUtilities.class.getClassLoader().getResourceAsStream(fileName)),
					RootCategory.class);
			if (rootCategory == null || rootCategory.getCategories() == null) {
				Logger.error("Unable to load categories from filename:" + fileName);
				return null;
			}
			Map<String,ProductCategory> categoryMap = new  HashMap<String,ProductCategory>();
			for (ProductCategory pc : rootCategory.getCategories()) {
				pc.setBestDiscount(pc.getDiscount());
				categoryMap.put(pc.getCategoryName(), pc);
				setParentRecursively(pc, pc.getChildren(),categoryMap);
			}
			rootCategory.setCategoryMap(categoryMap);

		} catch (JsonSyntaxException e) {
			Logger.error(e.getMessage());
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
		return rootCategory;
	}

	public static void setParentRecursively(ProductCategory current, List<ProductCategory> children,Map<String,ProductCategory> categoryMap) {
		if (children == null) {
			return;
		}
		ProductCategory parent = current;
		for (ProductCategory child : children) {
			child.setBestDiscount(child.getDiscount() > parent.getBestDiscount() ? child.getDiscount() : parent.getBestDiscount());
			setParentRecursively(child, child.getChildren(),categoryMap);
			categoryMap.put(child.getCategoryName(), child);
			child.setParent(parent);
		}
	}

	/**
	 * @param fileName
	 * @return BrandRepository contains all the brand details configured
	 */
	public static BrandRepository loadBrands(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			Logger.error("Unable to load brands as fileName is passed as null");
			return null;
		}
		BrandRepository brandRepository = null;
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		try {
			brandRepository = gson.fromJson(
					readFromInputStream(ProductUtilities.class.getClassLoader().getResourceAsStream(fileName)),
					BrandRepository.class);
			if (brandRepository != null && brandRepository.getBrands() == null) {
				Logger.error("Unable to load brands from filename:" + fileName);
				return null;
			}
		} catch (JsonSyntaxException e) {
			Logger.error(e.getMessage());
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
		return brandRepository;
	}
	
	private static String readFromInputStream(InputStream inputStream) throws IOException {
		if(inputStream  ==  null){
			Logger.debug("input stream is null");
			return null;
		}
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}

}
