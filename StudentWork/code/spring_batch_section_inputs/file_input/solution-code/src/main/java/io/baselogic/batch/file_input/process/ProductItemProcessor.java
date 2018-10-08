package io.baselogic.batch.file_input.process;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
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
