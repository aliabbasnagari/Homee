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

	public boolean isON() {
		return powerStatus;
	}

	public void setPower(boolean powerStatus) {
		this.powerStatus = powerStatus;
	}

	public boolean isNotificationON() {
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

}
