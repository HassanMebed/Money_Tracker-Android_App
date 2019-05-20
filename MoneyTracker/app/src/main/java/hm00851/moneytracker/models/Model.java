package hm00851.moneytracker.models;

/**
 * Every model should inherit from this class in order to be identified as a model in this application and
 * utilize the main model variables and methods.
 */
public abstract class Model
{
    /** errors related to the model or database related errors */
    protected String modelErrors = null;

    public Model()
    {
        this.modelErrors = "";
    }

    /**
     * sets modelErrors back to default values.
     */
    public final void clearErrors()
    {
        this.modelErrors = "";
    }

    /**
     * @return the model errors
     */
    public final String getModelErrors()
    {
        return this.modelErrors;
    }
}
