package code.donbonifacio.saft;

import java.util.List;

/**
 * Represents an arbitrary result from operations.
 * This class is immutable..*
 */
public final class Result {

    private final boolean succeeded;
    private final String reason;
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
     * Creates a Result
     * @param succeeded true of false
     */
    private Result(boolean succeeded, String reason) {
        this.succeeded = succeeded;
        this.reason = reason;
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
}
