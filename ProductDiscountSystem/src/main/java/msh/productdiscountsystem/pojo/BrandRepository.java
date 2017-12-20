package msh.productdiscountsystem.pojo;

import java.util.HashSet;
import java.util.Set;

public class BrandRepository {

	private Set<Brand> brands;

	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}
	
	public Set<Brand> getBrands() {
		return brands;
	}
	
	public boolean addBrand(Brand brand){
		if(this.brands == null){
			this.brands = new  HashSet<Brand>();
		}
		return this.brands.add(brand);
	}
}
