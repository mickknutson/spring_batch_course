package io.baselogic.batch.file_input.process;

import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class ProductItemProcessor implements ItemProcessor<Product, Product> {

	@Override
	public Product process(Product product) throws Exception {
		
		if(product != null) {		
			product.setTotalAmount(product.getUnitPrice().multiply(new BigDecimal(product.getQuantity())));
			return product;
		}
		else
			return null;
	}

}
