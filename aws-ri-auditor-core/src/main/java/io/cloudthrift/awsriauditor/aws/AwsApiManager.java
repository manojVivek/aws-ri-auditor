package io.cloudthrift.awsriauditor.aws;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeReservedInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.ReservedInstances;

import io.cloudthrift.awsriauditor.exception.AwsException;

/**
 * @author manojvivek
 *
 */
public class AwsApiManager {

    public List<ReservedInstances> getReserveInstances(AWSCredentials awsCredentials) throws AwsException {
	try {
	    AmazonEC2 ec2Client = new AmazonEC2Client(awsCredentials);
	    DescribeReservedInstancesResult result = ec2Client.describeReservedInstances();
	    return result.getReservedInstances();
	} catch (Exception e) {
	    throw new AwsException(e);
	}
    }

    public List<Instance> getAllInstances(AWSCredentials awsCredentials) throws AwsException {
	try {
	    AmazonEC2 ec2Client = new AmazonEC2Client(awsCredentials);
	    DescribeInstancesResult result = ec2Client.describeInstances();
	    List<Instance> instances = new ArrayList<Instance>();
	    List<Reservation> reservations = result.getReservations();
	    for (Reservation reservation : reservations) {
		instances.addAll(reservation.getInstances());
	    }
	    return instances;
	} catch (Exception e) {
	    throw new AwsException(e);
	}
    }

}
