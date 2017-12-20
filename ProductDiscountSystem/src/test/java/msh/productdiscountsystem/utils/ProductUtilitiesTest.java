package msh.productdiscountsystem.utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import msh.productdiscountsystem.pojo.BrandRepository;
import msh.productdiscountsystem.pojo.RootCategory;

public class ProductUtilitiesTest {

	@DataProvider(name = "filenames")
	public Object[][] getFileNames() {
		String[][] fileNames = { { null }, { "" }, { " " }, { "brands.json" }, { "categories.json" }, { "test.json" } };
		return fileNames;
	}

	@Test(dataProvider = "filenames")
	public void testLoadProductCategories(String fileName) {
		RootCategory root = ProductUtilities.loadProductCategories(fileName);
		if (StringUtils.isNotBlank(fileName) && fileName.equals("categories.json")) {
			assertNotNull(root.getCategories());
		} else {
			assertEquals(null, root);
		}

	}

	@Test(dataProvider = "filenames")
	public void testloadBrands(String fileName) {
		BrandRepository brands = ProductUtilities.loadBrands(fileName);
		if(StringUtils.isNotBlank(fileName)&& fileName.equals("brands.json")){
			assertNotNull(brands.getBrands());
		}else{
			assertNull(brands);
		}
	}
}
