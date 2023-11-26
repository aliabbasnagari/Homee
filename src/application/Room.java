package application;

public class Room {
	private int id;
	private String title;
	private boolean powerStatus;
	private boolean notificationStatus;
	private Statistics roomStats;

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

	public Statistics getRoomStats() {
		return roomStats;
	}

	public void setRoomStats(Statistics roomStats) {
		this.roomStats = roomStats;
	}
}
