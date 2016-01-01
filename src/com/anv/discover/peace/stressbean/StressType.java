package com.anv.discover.peace.stressbean;

import java.io.Serializable;

public class StressType implements Serializable{
	 
	 private String stressGroup;
	 private String stressCause;
//	 private String stressSolution;

	/* public String getStressSolution() {
		return stressSolution;
	}


	public void setStressSolution(String stressSolution) {
		this.stressSolution = stressSolution;
	}

*/
	public StressType(String stressCause) {
	        this.stressCause = stressCause;
	    }
	 
	 
	    public String getStressGroup() {
	        return stressGroup;
	    }

	    public void setStressGroup(String stressGroup) {
	        this.stressGroup = stressGroup;
	    }

	   

	    public String getStressCause() {
	        return stressCause;
	    }

	    public void setStressCause(String stressCause) {
	        this.stressCause = stressCause;
	    }
	    
	    public String toString()
	    {
	    	return "The stress-class is " + this.getClass().getSimpleName();
	    }
}
