package application;

public class Device {
	private int id;
	private String name;
	private boolean powerStatus;
	private boolean notificationStatus;
	private Statistics deviceStats;

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

	public boolean isPowerStatus() {
		return powerStatus;
	}

	public void setPowerStatus(boolean powerStatus) {
		this.powerStatus = powerStatus;
	}

	public boolean isNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(boolean notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public Statistics getDeviceStats() {
		return deviceStats;
	}

	public void setDeviceStats(Statistics deviceStats) {
		this.deviceStats = deviceStats;
	}

}
