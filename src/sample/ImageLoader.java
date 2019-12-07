package sample;

import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import javafx.scene.image.Image;

/**
 * This class is for the image to load.
 *
 * @version 1.0
 * @author Romanov Andre
 * @author Shafi Mushfique
 * @author Stephen Aranda
 * @since 2019-09-21
 */
public class ImageLoader extends JPanel {

  public static ImageLoader roomA = new ImageLoader(1920, 1319, "Room A");
  public Image image;
  private int width, height;

  /**
   * ImageLoader constructor that has the properties for width, height, and the path.
   *
   * @param width Width of the image.
   * @param height Height of the image.
   * @param path The path of the image.
   */
  public ImageLoader(int width, int height, String path) {
    this.width = width;
    this.height = height;
  }
}
