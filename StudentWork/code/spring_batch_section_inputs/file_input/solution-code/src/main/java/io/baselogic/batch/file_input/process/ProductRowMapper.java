package io.baselogic.batch.file_input.process;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

//@Component
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class ProductRowMapper implements RowMapper<Product> {

    public Product mapRow(ResultSet rs, int rowNum)
            throws SQLException {
        Product product = new Product();
        product.setId(rs.getString("id"));
        product.setName(rs.getString("name"));
        product.setUnitPrice(rs.getBigDecimal("price"));
        return product;
    }
} // The End...