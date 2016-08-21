package io.cloudthrift.awsriauditor;

import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.ReservedInstances;

import io.cloudthrift.awsriauditor.aws.AwsApiManager;
import io.cloudthrift.awsriauditor.exception.AwsException;
import io.cloudthrift.awsriauditor.model.RIAuditorResult;

/**
 * @author manojvivek
 *
 */
public class RIAuditor {

    public RIAuditorResult runAuditing(AWSCredentials awsCredentials) {
	AwsApiManager manager = new AwsApiManager();
	try {
	    List<ReservedInstances> reserveInstances = manager.getReserveInstances(awsCredentials);
	    List<Instance> instances = manager.getAllInstances(awsCredentials);
	    return runAuditing(reserveInstances, instances);
	} catch (AwsException e) {
	    RIAuditorResult result = new RIAuditorResult();
	    result.setException(e);
	    return result;
	}

    }


    public RIAuditorResult runAuditing(List<ReservedInstances> reserveInstances, List<Instance> instances) {
	RIAuditorResult result = new RIAuditorResult();
	
	return result;

    }

}
