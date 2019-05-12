package com.example.demo;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

@Component
public class Screen1Controller implements Initializable {

	@FXML Button button1;
	@FXML Button button2;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		button1.setOnAction(event -> System.out.println("button1 Clicked"));
		button2.setOnAction(event -> System.out.println("button2 Clicked"));
	}
	
}
