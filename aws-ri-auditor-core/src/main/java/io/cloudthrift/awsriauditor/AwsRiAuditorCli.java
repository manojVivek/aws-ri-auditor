package io.cloudthrift.awsriauditor;

import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.ReservedInstances;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.util.StringUtils;

import io.cloudthrift.awsriauditor.model.RIAuditorResult;

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
	    RIAuditorResult result = riAuditor.runAuditing(new BasicAWSCredentials(accessKey, secretKey));
	    prettyPrintResult(result);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (Exception e) {
	    // TODO: handle exception
	    e.printStackTrace();
	}
    }

    /**
     * @param result
     */
    private static void prettyPrintResult(RIAuditorResult result) {
	System.out.println("=================================================");
	System.out.println("Result:");
	System.out.println("=================================================");
	if (result.getUnUsedRI() != null && result.getUnUsedRI().size() > 0) {
	    System.out.println("Unused RI:");
	    for (int i = 0; i < result.getUnUsedRI().size(); i++) {
		ReservedInstances reservedInstances = result.getUnUsedRI().get(i);
		System.out.println((i + 1) + ".\t" + reservedInstances.getInstanceType() + "\t"
			+ reservedInstances.getInstanceCount() + "\t" + reservedInstances.getAvailabilityZone());
	    }
	    System.out.println("=================================================");
	}
	if (result.getInstancesThatNeedRI() != null && result.getInstancesThatNeedRI().size() > 0) {
	    System.out.println("Instances that need RI:");
	    for (int i = 0; i < result.getInstancesThatNeedRI().size(); i++) {
		Instance instance = result.getInstancesThatNeedRI().get(i);
		System.out.println((i + 1) + ".\t" + getName(instance) + "\t"
			+ instance.getPlacement().getAvailabilityZone() + "\t" + getRunningTime(instance));
	    }
	    System.out.println("=================================================");
	}

	if (result.getInstancesWithEffectiveRI() != null && result.getInstancesWithEffectiveRI().size() > 0) {
	    System.out.println("Instances with effective RI:" + result.getInstancesWithEffectiveRI().size());
	    System.out.println("=================================================");
	}


    }

    /**
     * @param instance
     * @return
     */
    private static String getRunningTime(Instance instance) {
	long current = new Date().getTime();

	return ((current - instance.getLaunchTime().getTime()) / (1000 * 60 * 60 * 24)) + "days";
    }

    /**
     * @param instance
     * @return
     */
    private static String getName(Instance instance) {
	for (Tag tag : instance.getTags()) {
	    if ("Name".equals(tag.getKey())) {
		return tag.getValue();
	    }
	}
	return "";
    }

}
