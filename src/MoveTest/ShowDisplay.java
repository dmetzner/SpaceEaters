package MoveTest;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class ShowDisplay {
	public static void main(String[] args) {
		GraphicsEnvironment enviroment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = enviroment.getDefaultScreenDevice();
		
		DisplayMode[] ds = device.getDisplayModes();
		
		for(int i = 0; i<ds.length; i++){
			System.out.println(ds[i].getWidth() + " " + ds[i].getHeight() + " " + ds[i].getBitDepth() + " " + ds[i].getRefreshRate());
		}
	}

}
