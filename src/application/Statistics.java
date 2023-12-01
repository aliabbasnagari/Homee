package application;

public class Statistics {
	private double powerUsage;
	private double temperature;
	private double humidity;

	public Statistics() {
		powerUsage = 0;
		temperature = 0;
		humidity = 0;
	}

	public double getPowerUsage() {
		return powerUsage;
	}

	public void setPowerUsage(double powerUsage) {
		this.powerUsage = powerUsage;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

}
