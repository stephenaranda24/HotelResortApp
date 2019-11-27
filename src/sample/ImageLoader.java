package sample;

import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import javafx.scene.image.Image;

public class ImageLoader extends JPanel {

	public Image image;
	private int width, height;

	public static ImageLoader roomA = new ImageLoader(1920, 1319, "Room A");

	public ImageLoader(int width, int height, String path) {
		this.width = width;
		this.height = height;
	}
}
