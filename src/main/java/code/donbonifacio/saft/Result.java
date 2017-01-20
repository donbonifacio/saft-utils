package code.donbonifacio.saft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an arbitrary result from operations.
 * This class is immutable..*
 */
public final class Result {

    private final boolean succeeded;
    private final String reason;
    private final Exception exception;
    private final List<Result> results;
    private static final Result SUCCESS = new Result(true, "OK");

    /**
     * Creates a new result
     *
     * @param succeeded True if the result succeeded
     * @param reason a label string for the reason
     */
    private Result(boolean succeeded, String reason) {
        this.succeeded = succeeded;
        this.reason = reason;
        this.exception = null;
        this.results = null;
    }

    /**
     * Creates a Result
     *
     * @param succeeded true of false
     * @param reason a label string for the reason
     * @param exception an exception if available
     */
    private Result(boolean succeeded, String reason, Exception exception) {
        this.succeeded = succeeded;
        this.reason = reason;
        this.exception = exception;
        this.results = null;
    }

    /**
     * Creates a Result
     *
     * @param succeeded true of false
     * @param reason a label string for the reason
     * @param results an original source of results
     */
    private Result(boolean succeeded, String reason, List<Result> results) {
        this.succeeded = succeeded;
        this.reason = reason;
        this.exception = null;
        this.results = results;
    }

    /**
     * Always return a success result
     *
     * @return the result
     */
    public static Result success() {
        return SUCCESS;
    }

    /**
     * Creates a failure result
     *
     * @param reason the failed reason
     * @return the Result
     */
    public static Result failure(String reason) {
        return new Result(false, reason);
    }

    /**
     * Creates a failure result with an exception
     *
     * @param reason the string message
     * @param ex the exception
     * @return the result
     */
    public static Result failure(String reason, Exception ex) {
        return new Result(false, reason, ex);
    }

    /**
     * Returns a result that wraps an exception
     *
     * @param ex the exception
     * @return the result
     */
    public static Result exception(Exception ex) {
        return failure(ex.getMessage(), ex);
    }

    /**
     * Given a list of Results, checks to see if they all succeeded.
     * If True, then returns a success result.
     *
     * @param results the results to consider
     * @return a result that wraps the results
     */
    public static Result fromResults(List<Result> results) {
        if (results.size() == 1) {
            return results.get(0);
        }

        List<Result> failures = results.stream()
                .filter(Result::isFailed)
                .collect(Collectors.toList());

        if (failures.size() == 1) {
            return failures.get(0);
        }

        return new Result(failures.isEmpty(), "From list of results", failures);
    }

    /**
     * True if the result was succeeded
     *
     * @return true of false
     */
    public boolean isSucceeded() {
        return succeeded;
    }

    /**
     * True if the result has failed
     *
     * @return true or false
     */
    public boolean isFailed() {
        return !isSucceeded();
    }

    /**
     * Gets a text reason for this Result
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * String that represents this result
     *
     * @return a descriptive String
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Result{");
        builder.append("succeeded=");
        builder.append(isSucceeded());
        builder.append(", reason='");
        builder.append(getReason());
        builder.append("'");
        if(results != null) {
            builder.append(", results=[");
            for(Result result : results) {
                builder.append(result.toString());
                builder.append(", ");
            }
            builder.append("]");
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * True if this result contains an exception
     *
     * @return true or false
     */
    public boolean isException() {
        return exception != null;
    }


    /**
     * Converts this Result in a list that contains only
     * this result
     *
     * @return a list with one result
     */
    public List<Result> asList() {
        return new ArrayList<>(Arrays.asList(this));
    }
}
