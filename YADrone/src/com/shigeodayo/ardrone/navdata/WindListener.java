package com.shigeodayo.ardrone.navdata;

import java.util.EventListener;


public interface WindListener extends EventListener {

	public void receivedEstimation(WindEstimationData d);

}
