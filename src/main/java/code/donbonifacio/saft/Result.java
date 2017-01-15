package code.donbonifacio.saft;

import code.donbonifacio.saft.exceptions.SaftLoaderException;

import java.util.List;

/**
 * Represents an arbitrary result from operations.
 * This class is immutable..*
 */
public final class Result {

    private final boolean succeeded;
    private final String reason;
    private final Exception exception;
    private static final Result SUCCESS = new Result(true, "OK");

    /**
     * Always return a success result
     * @return the result
     */
    public static Result success() {
        return SUCCESS;
    }

    /**
     * Creates a failure result
     * @param reason the failed reason
     * @return the Result
     */
    public static Result failure(String reason) {
        return new Result(false, reason);
    }

    /**
     * Creates a failure result with an exception
     * @param reason the string message
     * @param ex the exception
     * @return the result
     */
    public static Result failure(String reason, Exception ex) {
        return failure(ex.getMessage(), ex);
    }

    /**
     * Returns a result that wraps an exception
     * @param ex the exception
     * @return the result
     */
    public static Result exception(Exception ex) {
        return failure(ex.getMessage(), ex);
    }

    /**
     * Given a list of Results, checks to see if they all succeeded.
     * If True, then returns a success result.
     * @param results the results to consider
     * @return a result that wraps the results
     */
    public static Result fromResults(List<Result> results) {
        if(results.size() == 1) {
            return results.get(0);
        }

        boolean success = results.stream()
                .map(Result::isSucceeded)
                .reduce(true, (a, s) -> a && s);

        return new Result(success, "From list of results");
    }

    /**
     * Creates a new result
     * @param succeeded True if the result succeeded
     * @param reason a label string for the reason
     */
    private Result(boolean succeeded, String reason) {
        this.succeeded = succeeded;
        this.reason = reason;
        this.exception = null;
    }

    /**
     * Creates a Result
     * @param succeeded true of false
     * @param reason a label string for the reason
     * @param exception an exception if available
     */
    private Result(boolean succeeded, String reason, Exception exception) {
        this.succeeded = succeeded;
        this.reason = reason;
        this.exception = exception;
    }

    /**
     * True if the result was succeeded
     * @return true of false
     */
    public boolean isSucceeded() {
        return succeeded;
    }

    /**
     * True if the result has failed
     * @return true or false
     */
    public boolean isFailed() {
        return !isSucceeded();
    }

    /**
     * Gets a text reason for this Result
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * String that represents this result
     * @return a descriptive String
     */
    @Override
    public String toString() {
        return "Result[succeeded=" + isSucceeded() + ", reason='" + getReason() +"']";
    }

}
