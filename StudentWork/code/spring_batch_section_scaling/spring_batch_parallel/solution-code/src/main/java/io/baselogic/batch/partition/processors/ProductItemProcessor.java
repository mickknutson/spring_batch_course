package io.baselogic.batch.partition.processors;

import java.math.BigDecimal;

import io.baselogic.batch.partition.domain.Product;
import org.springframework.batch.item.ItemProcessor;

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
