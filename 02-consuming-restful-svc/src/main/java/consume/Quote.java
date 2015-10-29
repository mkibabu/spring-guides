package consume;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Domain object for quote received from the remote web service.
 */

/*
 * This annotation asks JACKSON to ignore any property not bound in this type.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    
    private String type;
    private Value value;
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Value getValue() {
        return value;
    }
    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Quote{" + "type='" + type + "', value=" + value + "}";
    }
}