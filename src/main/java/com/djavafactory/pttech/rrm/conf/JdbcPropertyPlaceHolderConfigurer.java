package com.djavafactory.pttech.rrm.conf;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Class extends PropertyPlaceHolderConfigurer to get the configuration from database
 *
 * @author Carine Leong
 */
public class JdbcPropertyPlaceHolderConfigurer extends PropertyPlaceholderConfigurer {
    private NamedParameterJdbcTemplate template;
    private List<String> configKey;

    private String configurationQueryString = "select config_key, config_value from configuration where config_key in (:config_key)";
    private String keyColumn = "config_key";
    private String valueColumn = "config_value";

    /**
     * Method to get the configuration key
     *
     * @return List of keys
     */
    public List<String> getConfigKey() {
        return configKey;
    }

    /**
     * Method to set the configuration key
     *
     * @param configKey The configuration key
     */
    public void setConfigKey(List<String> configKey) {
        this.configKey = configKey;
    }

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
        if (logger.isDebugEnabled()) {
            logger.debug("load properties from " + this.configKey);
        }
        for (String propKey : getConfigKey()) {
            props.setProperty(propKey, "");
        }
        try {
            SqlParameterSource namedParameters = new MapSqlParameterSource("config_key", getConfigKey());
            this.template.query(this.configurationQueryString, namedParameters, new RowCallbackHandler() {
                public void processRow(ResultSet rs) throws SQLException {
                    do {
                        props.setProperty(rs.getString(keyColumn), rs.getString(valueColumn));
                    } while (rs.next());
                }
            });
        } catch (EmptyResultDataAccessException e) {
            logger.warn(configKey + " not available", e);
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
