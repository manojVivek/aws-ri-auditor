package io.cloudthrift.awsriauditor.model;

/**
 * @author manojvivek
 *
 */
public class RIAuditorResult {
    private Exception exception;

    /**
     * @return the exception
     */
    public boolean hasException() {
	if (exception != null) {
	    return true;
	}
	return false;
    }

    /**
     * @param exception
     *            the exception to set
     */
    public void setException(Exception exception) {
	this.exception = exception;
    }

    /**
     * @return the exception
     */
    public Exception getException() {
	return exception;
    }

}
