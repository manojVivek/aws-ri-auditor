package io.cloudthrift.awsriauditor.model;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.ReservedInstances;

/**
 * @author manojvivek
 *
 */
public class RIAuditorResult {
    private Exception exception;
    List<Instance> instancesWithEffectiveRI, instancesThatNeedRI;
    List<ReservedInstances> unUsedRI;

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

    /**
     * @return the instancesWithEffectiveRI
     */
    public List<Instance> getInstancesWithEffectiveRI() {
	return instancesWithEffectiveRI;
    }

    /**
     * @param instancesWithEffectiveRI
     *            the instancesWithEffectiveRI to set
     */
    public void setInstancesWithEffectiveRI(List<Instance> instancesWithEffectiveRI) {
	this.instancesWithEffectiveRI = instancesWithEffectiveRI;
    }

    public void addToInstancesWithEffectiveRI(Instance instanceWithEffectiveRI) {
	if (this.instancesWithEffectiveRI == null) {
	    this.instancesWithEffectiveRI = new ArrayList<>();
	}
	this.instancesWithEffectiveRI.add(instanceWithEffectiveRI);
    }

    /**
     * @return the instancesThatNeedRI
     */
    public List<Instance> getInstancesThatNeedRI() {
	return instancesThatNeedRI;
    }

    /**
     * @param instancesThatNeedRI
     *            the instancesThatNeedRI to set
     */
    public void setInstancesThatNeedRI(List<Instance> instancesThatNeedRI) {
	this.instancesThatNeedRI = instancesThatNeedRI;
    }

    public void addToInstancesThatNeedRI(Instance instanceThatNeedRI) {
	if (this.instancesThatNeedRI == null) {
	    this.instancesThatNeedRI = new ArrayList<>();
	}
	this.instancesThatNeedRI.add(instanceThatNeedRI);
    }

    /**
     * @return the unUsedRI
     */
    public List<ReservedInstances> getUnUsedRI() {
	return unUsedRI;
    }

    /**
     * @param unUsedRI
     *            the unUsedRI to set
     */
    public void setUnUsedRI(List<ReservedInstances> unUsedRI) {
	this.unUsedRI = unUsedRI;
    }

}
