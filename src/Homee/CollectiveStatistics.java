package Homee;

import java.util.ArrayList;

public class CollectiveStatistics extends Statistics {
	private static CollectiveStatistics _stats;
	private int id;
	private double solarEnergy;
	private double gridEnergy;

	private CollectiveStatistics() {
		id = -1;
		super.setPowerUsage(0);
		super.setHumidity(0);
		super.setTemperature(0);
	}

	public static CollectiveStatistics getInstance() {
		if (_stats == null) {
			_stats = new CollectiveStatistics();
		}
		return _stats;
	}

	public double getSolarEnergy() {
		return solarEnergy;
	}

	public void setSolarEnergy(double solarEnergy) {
		this.solarEnergy = solarEnergy;
	}

	public double getGridEnergy() {
		return gridEnergy;
	}

	public void setGridEnergy(double gridEnergy) {
		this.gridEnergy = gridEnergy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void refresh(ArrayList<Room> rooms) {
		super.setPowerUsage(0);
		this.setTemperature(Math.random() * 100);
		this.setHumidity(Math.random() * 100);
		this.gridEnergy = (Math.random() * 25);
		this.solarEnergy = (Math.random() * 10);
		for (Room rm : rooms) {
			for (Device dv : rm.getDevices()) {
				super.setPowerUsage(super.getPowerUsage() + dv.getDeviceStats().getPowerUsage());
			}
		}
	}
}
