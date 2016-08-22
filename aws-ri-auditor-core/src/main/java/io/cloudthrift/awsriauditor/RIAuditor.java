package io.cloudthrift.awsriauditor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	Map<String, ReservedInstances> reservedInstancesMap = constructMap(reserveInstances);
	List<Instance> sortedInstances = sortInstancesBasedOnRunningTime(instances);
	for (Instance instance : sortedInstances) {
	    String key = constructKey(instance);
	    ReservedInstances reservedInstance = reservedInstancesMap.get(key);
	    if (reservedInstance != null && reservedInstance.getInstanceCount() > 0) {
		reservedInstance.setInstanceCount(reservedInstance.getInstanceCount() - 1);
		// TODO add these instances to the green list of result
	    }
	}
	// TODO add the remaining instances to the red & yellow list of the
	// result
	return result;
    }

    /**
     * @param instances
     * @return
     */
    private List<Instance> sortInstancesBasedOnRunningTime(List<Instance> instances) {
	// TODO removed the spot instances if any in the list
	List<Instance> sortedInstances = new ArrayList<>(instances);
	Collections.sort(sortedInstances, (i1, i2) -> i1.getLaunchTime().compareTo(i2.getLaunchTime()));
	return sortedInstances;
    }

    /**
     * @param reserveInstances
     * @return
     */
    private Map<String, ReservedInstances> constructMap(List<ReservedInstances> reserveInstances) {
	Map<String, ReservedInstances> reservedInstancesMap = new HashMap<>();
	for (ReservedInstances reservedInstance : reserveInstances) {
	    String key = constructKey(reservedInstance);
	    reservedInstancesMap.put(key, reservedInstance);
	}
	return reservedInstancesMap;
    }

    /**
     * @param reservedInstance
     * @return
     */
    private String constructKey(ReservedInstances reservedInstance) {
	return concat(reservedInstance.getInstanceType(), reservedInstance.getAvailabilityZone(),
		reservedInstance.getProductDescription());
    }

    /**
     * @param instance
     * @return
     */
    private String constructKey(Instance instance) {
	return concat(instance.getInstanceType(), instance.getPlacement().getAvailabilityZone(),
		instance.getPlatform());
    }

    private String concat(String type, String aZone, String platform) {
	return type + "|" + aZone + "|" + platform;
    }

}
