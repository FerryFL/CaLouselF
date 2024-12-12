package main;

import controller.ItemController;
import javafx.application.Application;
import javafx.stage.Stage;
import routes.ViewController;

public class Main extends Application {
    
    private final ItemController controller = new ItemController();
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ViewController viewController = ViewController.getInstance(primaryStage, controller);
        
        viewController.navigateToSellerHomePage(); 

        primaryStage.setTitle("CalouselF");
        primaryStage.show();
    }
}
