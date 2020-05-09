package com.magc.sensecane.framework.javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;

public abstract class AbstractController extends HBox implements Controller, Initializable {

	public URL fxml;
	public FXMLLoader loader;
	private Scene scene;
	
	public AbstractController(URL fxml) {
		this.fxml = fxml;
		this.loader = new FXMLLoader(fxml);
		this.loader.setRoot(this);
		this.loader.setController(this);
		
		HBox root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.scene = new Scene(root);
		}
	}

	public Scene get() {
		return scene;
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void destroy() {
		//Do nothing
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//DO nothing
	}
	
	public URL getFxml() {
		return this.fxml;
	}
}
