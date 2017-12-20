package msh.productdiscountsystem.pojo;

import java.util.List;
import java.util.Set;

public class UserInput {
	private List<Product> products;
	
	private List<Set<Integer>> carts;
	
	public void setCarts(List<Set<Integer>> carts) {
		this.carts = carts;
	}
	
	public List<Set<Integer>> getCarts() {
		return carts;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public List<Product> getProducts() {
		return products;
	}
}	
