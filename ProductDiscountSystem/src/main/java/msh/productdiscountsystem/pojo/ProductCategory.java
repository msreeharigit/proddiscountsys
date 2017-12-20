package msh.productdiscountsystem.pojo;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory {
	
	private String categoryName;
	
	private double discount;
	
	private transient ProductCategory parent;
	
	private List<ProductCategory> children;
	
	//this property will be set MAX(this.discount,parent.discount)
	private double bestDiscount;
	
	public ProductCategory() {
	}
	

	public ProductCategory(String categoryName, double discount, ProductCategory parent,
			List<ProductCategory> children) {
		super();
		this.categoryName = categoryName;
		this.discount = discount;
		this.parent = parent;
		this.children = children;
	}
	

	public ProductCategory(String categoryName, double discount) {
		super();
		this.categoryName = categoryName;
		this.discount = discount;
	}

	public void setBestDiscount(double bestDiscount) {
		this.bestDiscount = bestDiscount;
	}
	
	public double getBestDiscount() {
		return bestDiscount;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}


	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	/**
	 * @return the discount
	 */
	public double getDiscount() {
		return discount;
	}


	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}


	/**
	 * @return the parent
	 */
	public ProductCategory getParent() {
		return parent;
	}


	/**
	 * @param parent the parent to set
	 */
	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}


	/**
	 * @return the children
	 */
	public List<ProductCategory> getChildren() {
		return children;
	}


	/**
	 * @param children the children to set
	 */
	public void setChildren(List<ProductCategory> children) {
		this.children = children;
	}
	
	/**
	 * @param child
	 */
	public boolean addChild(final ProductCategory child){
		if(this.children == null){
			this.children = new  ArrayList<ProductCategory>();
		}
		return this.children.add(child);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductCategory other = (ProductCategory) obj;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductCategory [categoryName=" + categoryName + ", discount=" + discount + ", children=" + children
				+ "]";
	}
}
