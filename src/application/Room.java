package application;

public class Room {
	private int id;
	private String title;
	private boolean powerStatus;
	private boolean notificationStatus;
	private Device[] devices = new Device[9];

	public Room() {
		for (int i = 0; i < devices.length; i++) {
			devices[i] = new Device(i);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Device[] getDevices() {
		return devices;
	}

	public void setDevices(Device[] devices) {
		this.devices = devices;
	}

}
