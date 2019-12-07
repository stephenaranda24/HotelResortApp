package sample;

import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import org.h2.engine.Database;

/**
 * This is class is the main class of the program which makes use of methods that create
 * error/info screens to appear when needed. This class also makes use of the start method
 * which is the main entry point for all JavaFX applications.
 *
 *
 * @version 1.0
 * @author Romanov Andre
 * @author Shafi Mushfique
 * @author Stephen Aranda
 * @since 2019-09-21
 */
public class Main extends Application {

  // null = no user logged in yet
  // static since only one user can be
  // logged-in at a time
  public static String loggedInUser = null;
  public static String Type = null;
  public static String tempName = null;

  /**
   * {@inheritDoc}
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   *This method creates an error message that will appear in the occurrence that the user
   * inputs something incorrect data.
   *
   * @param message A string that represents the error that needs to be shown.
   */
  public static void errorMessage(String message) {
    UIManager.put("OptionPane.minimumSize", new Dimension(600, 200));
    UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(
        "Arial", Font.BOLD, 40)));
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * This method creates an info message to appear when the user needs to be alerted of something.
   *
   * @param message A string that represents the message that needs to shown.
   */
  public static void infoMessage(String message) {
    UIManager.put("OptionPane.minimumSize", new Dimension(600, 200));
    UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(
        "Arial", Font.BOLD, 40)));
    JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * This met
   *
   * @param pressedButton
   */
  public static void logOutUser(Button pressedButton) {
    loggedInUser = null; // remove logged in user
    MainScreenController msc = new MainScreenController();
    msc.loadScene(pressedButton, "MainScreenSample.fxml", "Main Screen");

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    LocalDate todaysDate = LocalDate.now();
    String datePushed = String.valueOf(todaysDate);
    DatabaseManager db = new DatabaseManager();
    db.custodianDateValidation(datePushed);

    Parent root = FXMLLoader.load(getClass().getResource("MainScreenSample.fxml"));
    primaryStage.setTitle("Main Menu");
    primaryStage.setScene(new Scene(root, 900, 400));
    primaryStage.show();
  }

}
