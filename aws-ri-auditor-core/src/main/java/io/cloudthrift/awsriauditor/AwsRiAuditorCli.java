package io.cloudthrift.awsriauditor;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.util.StringUtils;

/**
 * @author manojvivek
 *
 */
public class AwsRiAuditorCli {
    private static final String ACCESSKEY = "accessKey";
    private static final String SECRETKEY = "secretKey";
    private static Options options;
    private static CommandLineParser parser;
    static {
	options = new Options();
	options.addOption(ACCESSKEY, true, "AWS access key id to use for auditing");
	options.addOption(SECRETKEY, true, "AWS secret key to use for auditing");
	parser = new DefaultParser();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	try {
	    CommandLine commandLine = parser.parse(options, args);

	    String accessKey = commandLine.getOptionValue(ACCESSKEY), secretKey = commandLine.getOptionValue(SECRETKEY);
	    if (StringUtils.isNullOrEmpty(secretKey) || StringUtils.isNullOrEmpty(accessKey)) {
		HelpFormatter usageFormatter = new HelpFormatter();
		usageFormatter.printHelp("java AwsRiAuditorCli", options);
		return;
	    }
	    RIAuditor riAuditor = new RIAuditor();
	    riAuditor.runAuditing(new BasicAWSCredentials(accessKey, secretKey));
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (Exception e) {
	    // TODO: handle exception
	    e.printStackTrace();
	}
    }

}
