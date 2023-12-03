module Homee {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	
	opens HomeeInterface to javafx.graphics, javafx.fxml;
}
