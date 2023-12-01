package application;

public class CollectiveStatistics extends Statistics {
	private static CollectiveStatistics _stats;
	private int id;
	private double powerUsage;
	private double powerSaved;

	private CollectiveStatistics() {
		id = -1;
		powerSaved = 0.0;
		powerUsage = 0.0;
	}

	public static CollectiveStatistics getInstance() {
		if (_stats == null) {
			_stats = new CollectiveStatistics();
		}
		return _stats;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPowerUsage() {
		return powerUsage;
	}

	public void setPowerUsage(Double powerUsage) {
		this.powerUsage = powerUsage;
	}

	public double getPowerSaved() {
		return powerSaved;
	}

	public void setPowerSaved(Double powerSaved) {
		this.powerSaved = powerSaved;
	}

}
