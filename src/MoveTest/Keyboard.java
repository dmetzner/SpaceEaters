package MoveTest;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	private static boolean[] keys = new boolean[1024];
	
	public static boolean isKeyDown(int keycode){
		if(keycode>=0&&keycode<keys.length) return keys[keycode];
		else return false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		if(keycode>=0&&keycode<keys.length) keys[keycode]= true;
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keycode = e.getKeyCode();
		if(keycode>=0&&keycode<keys.length) keys[keycode]= false;
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//unnötig: :D
	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}
	

}
