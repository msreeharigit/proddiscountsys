package msh.productdiscountsystem.pojo;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RootCategory {
	
	private Set<ProductCategory> categories;
	
	private Map<String,ProductCategory> categoryMap;
	
	public Map<String, ProductCategory> getCategoryMap() {
		return categoryMap;
	}
	
	public void setCategoryMap(Map<String, ProductCategory> categoryMap) {
		this.categoryMap = categoryMap;
	}

	/**
	 * @return the categories
	 */
	public Set<ProductCategory> getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(Set<ProductCategory> categories) {
		this.categories = categories;
	}
	
	public boolean addCategory(ProductCategory category){
		if(this.categories == null){
			this.categories = new HashSet<ProductCategory>();
		}
		return this.categories.add(category);
	}
	
}
