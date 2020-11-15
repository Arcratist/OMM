package OMM.ui.panels;

import javax.swing.JPanel;

import OMM.ui.windows.WindowMain;

public class OMMPanel extends JPanel{
	private static final long serialVersionUID = -1958491422608011167L;
	
	private WindowMain windowMain;
	
	private OMMPanel previousPanel;

	public OMMPanel(WindowMain windowMain) {
		this.setWindowMain(windowMain);
	}

	public WindowMain getWindowMain() {
		return windowMain;
	}

	public void setWindowMain(WindowMain windowMain) {
		this.windowMain = windowMain;
	}

	public OMMPanel getPreviousPanel() {
		return previousPanel;
	}

	public void setPreviousPanel(OMMPanel previousPanel) {
		this.previousPanel = previousPanel;
	}
	
}
