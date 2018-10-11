package io.baselogic.batch.file_input.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class ProductJdbcItemWriter implements ItemWriter<Product> {

	private Logger LOG = LoggerFactory.getLogger(ProductJdbcItemWriter.class);
	
	private static final String INSERT_PRODUCT = "insert into product (id,name,unit_price, quantity, total_amount) values(?,?,?,?,?)";
	
	private static final String UPDATE_PRODUCT = "update product set name=?, unit_price=?, quantity =?, total_amount=? where id = ?";
	
	private JdbcTemplate jdbcTemplate;
	
	public ProductJdbcItemWriter(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void write(List<? extends Product> items) throws Exception {
		LOG.info("Number of products getting committed : {}", items.size());

		for(Product item : items) {
			LOG.info("Saving product : {}", item);

			// Perform Database Logic:

			int updated = jdbcTemplate.update(UPDATE_PRODUCT,
				item.getName(),item.getUnitPrice(),item.getQuantity(), item.getTotalAmount(), item.getId());
			
			if(updated == 0) {
				jdbcTemplate.update(
					INSERT_PRODUCT,
					item.getId(),item.getName(),item.getUnitPrice(), item.getQuantity(), item.getTotalAmount());	
			}			
		}

	}

}
