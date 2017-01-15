package code.donbonifacio.saft;

/**
 * Represents an arbitrary result from operations.
 * This class is immutable..*
 */
public final class Result {

    private final boolean succceeded;
    private static final Result SUCCESS = new Result(true);

    /**
     * Always return a success result
     * @return the result
     */
    public static Result success() {
        return SUCCESS;
    }

    /**
     * Creates a Result
     * @param succeeded true of false
     */
    private Result(boolean succeeded) {
        this.succceeded = succeeded;
    }

    /**
     * True if the result was succeeded
     * @return true of false
     */
    public boolean isSuccceeded() {
        return succceeded;
    }
}
