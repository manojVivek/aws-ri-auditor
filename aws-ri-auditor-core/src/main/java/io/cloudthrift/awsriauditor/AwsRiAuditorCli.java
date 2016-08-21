package io.cloudthrift.awsriauditor;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author manojvivek
 *
 */
public class AwsRiAuditorCli {

    private static Options options;
    private static CommandLineParser parser;
    static {
	options = new Options();
	options.addOption("accessKey", true, "AWS access key id to use for auditing");
	options.addOption("secretKey", true, "AWS secret key to use for auditing");
	parser = new DefaultParser();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	try {
	    CommandLine commandLine = parser.parse(options, args);

	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
