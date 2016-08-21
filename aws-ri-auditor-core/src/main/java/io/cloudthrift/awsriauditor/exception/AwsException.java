package io.cloudthrift.awsriauditor.exception;

/**
 * @author manojvivek
 *
 */
public class AwsException extends Exception {

	private static final long serialVersionUID = 6580031618147015216L;

	public AwsException(Exception e) {
		super(e);
	}

}
