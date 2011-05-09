package com.djavafactory.pttech.rrm.conf;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class extends PropertyPlaceHolderConfigurer to get the configuration from database
 *
 * @author Carine Leong
 */
public class JdbcPropertyPlaceHolderConfigurer extends PropertyPlaceholderConfigurer {
    private NamedParameterJdbcTemplate template;

    private String configurationQueryString = "select config_key, config_value from configuration";
    private String keyColumn = "config_key";
    private String valueColumn = "config_value";

    /**
     * Method to set the data source
     *
     * @param dataSource The data source
     */
    public void setDataSource(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Method to load properties from database
     *
     * @param props
     * @throws IOException
     */
    protected void loadProperties(final Properties props) throws IOException {
        try {
            this.template.query(this.configurationQueryString, new MapSqlParameterSource(), new RowCallbackHandler() {
                public void processRow(ResultSet rs) throws SQLException {
                    do {
                        props.setProperty(rs.getString(keyColumn), rs.getString(valueColumn));
                    } while (rs.next());
                }
            });
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Configuration data not available", e);
        } catch (BadSqlGrammarException e) {
            logger.warn("Table not available", e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("properties loaded : " + props.size());
        }
    }

    public void afterPropertiesSet() throws Exception {

    }
}
