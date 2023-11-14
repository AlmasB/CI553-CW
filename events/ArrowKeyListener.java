package events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class ArrowKeyListener implements KeyListener {
	
	public abstract void arrowUp();
	public abstract void arrowDown();
	
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Event");
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_KP_UP:
			arrowUp();
			break;
			
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_KP_DOWN:
			arrowDown();
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("Event released");
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("Event typed");
	}
}
