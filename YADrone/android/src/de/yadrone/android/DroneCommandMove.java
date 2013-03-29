package de.yadrone.android;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.shigeodayo.ardrone.ARDrone;

import android.util.Log;

public class DroneCommandMove extends DroneCommand {

	private DroneMovement targetLocation; // in the room

	DroneCommandMove(DroneCommandScheduler scheduler, float x, float y, float z, float orientation, float timespan) {
		super(scheduler);
		targetLocation = new DroneMovement(x, y, z, orientation, timespan);
	}

	public void execute() {
		Log.i("DroneCommandMove", String.format("x=%1$f y=%2$f z=%3$f orientation=%4$f timespan=%5$f\n",
				targetLocation.x, targetLocation.y, targetLocation.z, targetLocation.r, targetLocation.t));
		DroneMovement speed = scheduler.determineSpeedVector(targetLocation);

		ARDrone drone = scheduler.getDrone();

		// Date startTime = Date.Now;
		// Date endTime = startTime + speed.t;
		// Date currentTime = startTime;
		// while (currentTime < endTime) {
		drone.move3D((int) speed.x, (int) speed.y, (int) speed.z, (int) speed.r);
		try {
			Thread.sleep((long) speed.t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// currentTime = Date.Now;
		// }
		Log.i("DroneCommandMove", String.format("Stopped movement after %1$8.0f ms\n", speed.t));
		drone.stop();
	}
}
