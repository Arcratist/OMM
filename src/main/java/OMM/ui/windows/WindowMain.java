/**
 * 
 */
package OMM.ui.windows;

import javax.swing.JFrame;
import javax.swing.JPanel;

import OMM.ui.panels.OMMPanel;
import OMM.ui.panels.PanelSelectorWindow;
import OMM.ui.panels.PanelTorrentDownloader;
import OMM.ui.panels.PanelVideoConverter;
import OMM.ui.panels.PanelYoutubeMp3Downloader;
import OMM.ui.panels.PanelYoutubeVideoDownloader;

/**
 * @author Brett Daniel Smith
 *
 */
public class WindowMain extends JFrame {
	private static final long serialVersionUID = 3283395982975081589L;

	private OMMPanel panel;

	///////////////////////////////////////////////////

	public OMMPanel selectorWindow;
	public OMMPanel youtubeMp3Downloader;
	public OMMPanel youtubeVideoDownloader;
	public OMMPanel torrentDownloader;
	public OMMPanel videoConverter;

	///////////////////////////////////////////////////

	/**
	 * Create the frame.
	 */
	public WindowMain() {
		setTitle("OMM");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init() {
		this.selectorWindow = new PanelSelectorWindow(this);
		this.youtubeMp3Downloader = new PanelYoutubeMp3Downloader(this);
		this.youtubeVideoDownloader = new PanelYoutubeVideoDownloader(this);
		this.torrentDownloader = new PanelTorrentDownloader(this);
		this.videoConverter = new PanelVideoConverter(this);
	}

	public void setPanel(OMMPanel panel) {
		if (this.panel != null) {
			panel.setPreviousPanel(this.panel);
			remove(this.panel);
		}
		this.panel = panel;
		getContentPane().add(panel);
		pack();
		repaint();
	}

	public JPanel getPanel() {
		return this.panel;
	}

}
