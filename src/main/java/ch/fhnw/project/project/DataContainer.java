package ch.fhnw.project.project.DataModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds data from a specific variable of a file
 */
public class DataContainer {
    private String variableName;
    private List<Double> values;

    /**
     * Creates a new Data Container
     * @param variableName The name of the variable.
     */
    public DataContainer(final String variableName) {
        this.values = new ArrayList<>();
        this.variableName = variableName;

    }

    /**
     * Add a value to the variable.
     * @param value The value to add.
     */
    public void addValue(Double value) {
        this.values.add(value);
    }

    /**
     * Returns the name of the variable.
     * @return The name of the variable.
     */
    public String getVariableName() {
        return variableName;
    }

    /**
     * Returns the values of the variable.
     * @return The values of the variable.
     */
    public List<Double> getValues() {
        return values;
    }
}
