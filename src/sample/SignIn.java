package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @version 1.0
 * @ Romanov Andre
 * @ Shafi Mushfique
 * @ Stephen Aranda
 * @since 2019-09-21
 */
public class SignIn extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("SignInSample.fxml"));
    primaryStage.setTitle("Login Menu");
    primaryStage.setScene(new Scene(root, 600, 400));
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}
