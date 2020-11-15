/**
 * 
 */
package OMM;

import java.awt.EventQueue;

import javax.swing.UIManager;

import OMM.ui.windows.WindowMain;

/**
 * @author Brett Daniel Smith
 *
 */
public class Main {

	//https://www.youtube.com/watch?v=dQw4w9WgXcQ
	
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); // This line gives
																									// Windows Theme
					WindowMain frame = new WindowMain();
					frame.init();
					frame.setPanel(frame.selectorWindow);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
