package main;

import javafx.application.Application;
import javafx.stage.Stage;

import view_controller.*;


public class Main extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	ViewController loginVC = new ViewController(primaryStage);
        loginVC.getInstance(primaryStage).navigateToLogin();
    	
        primaryStage.setTitle("CalouselF");
        primaryStage.show();
    }
}

