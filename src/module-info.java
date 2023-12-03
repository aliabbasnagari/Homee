module Homee {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	requires javafx.graphics;
	
	opens HomeeInterface to javafx.graphics, javafx.fxml;
}
