package cz.cvut.kbe.crypthelper;

import javax.swing.UIManager;


/**
 * @author Radek Ježdík
 */
public class Main {

	public static void main(String[] args) {
		try {
			String className = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(className);
		} catch(Exception e) {
		}
		new MainController().run();
	}

}
