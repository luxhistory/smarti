package io.redlink.smarti.model.config;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="_class")
public class ComponentConfiguration implements Cloneable {

    private String type;
    private boolean enabled;
    private boolean unbound;
    private final Map<String,Object> configuration = new LinkedHashMap<>(); //use the order of params as they are added
    
    /**
     * Getter for the component type
     * @return the type of the component
     */
    public String getType() {
        return type;
    }
    
    /**
     * The type of the component (e.g. the {@link Class#getName()}
     * @param type the component type
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * If this configuration is enabled. Setting this to <code>false</code> allows
     * for deactivating a configuration without deactivating it
     * @return if the configuration is enabled/disabled
     */
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * If this configuration is enabled. Setting this to <code>false</code> allows
     * for deactivating a configuration without deactivating it
     * @param enabled the state: enabled/disabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * If this configuration is bound to an active component.
     * @return <code>true</code> if this configuration is bound to an active component or
     * <code>false</code> if this configuration is not bound to a component
     */
    public boolean isUnbound() {
        return unbound;
    }
    
    public void setUnbound(boolean unbound) {
        this.unbound = unbound;
    }
    
    @JsonAnyGetter
    protected Map<String,Object> getConfigParams() {
        return configuration;
    }

    @JsonIgnore
    public void setConfiguration(String param, Object value){
        if(value == null){
            configuration.remove(param);
        } else {
            configuration.put(param, value);
        }
    }
    
    @JsonAnySetter
    protected void setConfigParam(String name, Object value) {
        configuration.put(name, value);
    }

    @JsonIgnore
    public final Object getConfiguration(String key){
        return configuration.get(key);
    }

    @JsonIgnore
    public final boolean isConfiguration(String key){
        return configuration.containsKey(key);
    }
    
    @JsonIgnore
    public final String getConfiguration(String key, String defaultValue) {
        return configuration.containsKey(key) ? configuration.get(key).toString() : defaultValue;
    }

    @JsonIgnore
    public final boolean getConfiguration(String key, boolean defaultValue) {
        Object value = configuration.get(key);
        if (value instanceof Boolean) {
            return ((Boolean) value);
        } else if (value instanceof String) {
            return Boolean.parseBoolean(value.toString());
        } else {
            return defaultValue;
        }
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    public final List<String> getConfiguration(String key, List<String> defaultValue) {
        return configuration.containsKey(key) && configuration.get(key) instanceof List ? (List<String>) configuration.get(key) : defaultValue;
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    public final Map<String, List<String>> getConfiguration(String key, Map<String, List<String>> defaultValue) {
        return configuration.containsKey(key) && configuration.get(key) instanceof Map ? (Map<String, List<String>>) configuration.get(key) : defaultValue;
    }

    @JsonIgnore
    public final long getConfiguration(String key, long defaultValue) {
        Object value = configuration.get(key);
        if (value instanceof Long || value instanceof Integer || value instanceof Short || value instanceof Byte) {
            return ((Number) value).longValue();
        } else if (value instanceof String || value instanceof Number) {
            try {
                //this will also convert things like 4.0 to 4 but fail for 4.1
                return new BigDecimal(value.toString()).toBigIntegerExact().longValueExact();
            } catch (NumberFormatException | ArithmeticException e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    @JsonIgnore
    public final int getConfiguration(String key, int defaultValue) {
        Object value = configuration.get(key);
        if (value instanceof Integer || value instanceof Short || value instanceof Byte) {
            return ((Number) value).intValue();
        } else if (value instanceof String || value instanceof Number) {
            try {
                //this will also convert things like 4.0 to 4 but fail for 4.1
                return new BigDecimal(value.toString()).toBigIntegerExact().intValueExact();
            } catch (NumberFormatException | ArithmeticException e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    @JsonIgnore
    public final double getConfiguration(String key, double defaultValue) {
        Object value = configuration.get(key);
        if (value instanceof Double || value instanceof Float) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String || value instanceof Number) {
            try {
                //this will also convert things like 4.0 to 4 but fail for 4.1
                return new BigDecimal(value.toString()).doubleValue();
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    @JsonIgnore
    public final float getConfiguration(String key, float defaultValue) {
        Object value = configuration.get(key);
        if (value instanceof Float) {
            return ((Number) value).floatValue();
        } else if (value instanceof String || value instanceof Number) {
            try {
                //this will also convert things like 4.0 to 4 but fail for 4.1
                return new BigDecimal(value.toString()).floatValue();
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }
    
    @Override
    public ComponentConfiguration clone() throws CloneNotSupportedException {
        ComponentConfiguration clone = new ComponentConfiguration();
        copyState(clone);
        return clone;
    }

    protected final void copyState(ComponentConfiguration clone) {
        clone.configuration.putAll(configuration);
        clone.enabled = enabled;
        clone.type = type;
        clone.unbound = unbound;
    }
}
