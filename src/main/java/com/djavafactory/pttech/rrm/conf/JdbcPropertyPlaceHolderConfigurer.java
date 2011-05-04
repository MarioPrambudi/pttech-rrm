package com.djavafactory.pttech.rrm.conf;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.dao.EmptyResultDataAccessException;
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

public class JdbcPropertyPlaceHolderConfigurer extends PropertyPlaceholderConfigurer {
    private NamedParameterJdbcTemplate template;
    private List<String> configKey;

    private String configurationQueryString = "select config_key, config_value from configuration where config_key in (:config_key)";
    private String keyColumn = "config_key";
    private String valueColumn = "config_value";

    public List<String> getConfigKey() {
        return configKey;
    }

    public void setConfigKey(List<String> configKey) {
        this.configKey = configKey;
    }

    public void setDataSource(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    protected void loadProperties(final Properties props) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("load properties from " + this.configKey);
        }
        try {
            for (String propKey : getConfigKey()) {
                props.setProperty(propKey, "");
            }
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
        }
        if (logger.isDebugEnabled()) {
            logger.debug("properties loaded : " + props.size());
        }
    }

    public void afterPropertiesSet() throws Exception {

    }
}
