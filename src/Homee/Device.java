package Homee;

public class Device {
	private int id;
	private int deviceNo;
	private String name;
	private boolean powerStatus;
	private boolean notificationStatus;
	private Statistics deviceStats;

	public Device(int dNo) {
		id = -1;
		deviceNo = dNo;
		name = "Empty Slot";
		powerStatus = false;
		notificationStatus = false;
		deviceStats = new Statistics();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getPower() {
		return powerStatus;
	}

	public void setPower(boolean powerStatus) {

		this.powerStatus = powerStatus;

	}

	public boolean getNotification() {
		return notificationStatus;
	}

	public void setNotification(boolean notificationStatus) {

		this.notificationStatus = notificationStatus;

	}

	public Statistics getDeviceStats() {
		return deviceStats;
	}

	public void setDeviceStats(Statistics deviceStats) {
		this.deviceStats = deviceStats;
	}

	public int getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(int deviceNo) {
		this.deviceNo = deviceNo;
	}

	public void simulate() {
		if (powerStatus) {
			if (deviceStats.getTemperature() < 110) {
				deviceStats.setTemperature(deviceStats.getTemperature() + 0.3);
			}
			deviceStats.setPowerUsage(deviceStats.getPowerUsage() + Math.random() * 5);
		} else {
			if (deviceStats.getTemperature() >= 0.15) {
				deviceStats.setTemperature(deviceStats.getTemperature() - 0.15);
			}
		}
		deviceStats.setHumidity(Math.random() * 105);
	}

	@Override
	public String toString() {
		if (id != -1)
			return String.format("%s\n Temp(%.2f⁰C)   Humid(%.2f)", name, deviceStats.getTemperature(),
					deviceStats.getHumidity());
		else
			return String.format("%s", name);
	}

}
