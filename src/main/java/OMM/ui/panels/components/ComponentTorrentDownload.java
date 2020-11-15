package OMM.ui.panels.components;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Font;

public class ComponentTorrentDownload extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ComponentTorrentDownload() {
		setLayout(null);
		
		setPreferredSize(new Dimension(440, 70));
		setMinimumSize(new Dimension(440, 70));
		setMaximumSize(new Dimension(440, 70));
		
		JLabel lbl_TorrentName = new JLabel("Shrek 4");
		lbl_TorrentName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_TorrentName.setBounds(5, 5, 325, 20);
		add(lbl_TorrentName);
		
		JButton btn_RemoveTorrent = new JButton("");
		btn_RemoveTorrent.setIcon(new ImageIcon("C:\\Users\\Michael\\eclipse-workspace\\OMM\\src\\main\\resources\\delete.png"));
		btn_RemoveTorrent.setBounds(405, 5, 30, 30);
		add(btn_RemoveTorrent);
		
		JButton btn_StopTorrent = new JButton("");
		btn_StopTorrent.setIcon(new ImageIcon("C:\\Users\\Michael\\eclipse-workspace\\OMM\\src\\main\\resources\\stop.png"));
		btn_StopTorrent.setBounds(370, 5, 30, 30);
		add(btn_StopTorrent);
		
		JButton btn_ToggleTorrentDownload = new JButton("");
		btn_ToggleTorrentDownload.setIcon(new ImageIcon("C:\\Users\\Michael\\eclipse-workspace\\OMM\\src\\main\\resources\\play.png"));
		btn_ToggleTorrentDownload.setBounds(335, 5, 30, 30);
		add(btn_ToggleTorrentDownload);
		
		JProgressBar progressBar_DownloadProgress = new JProgressBar();
		progressBar_DownloadProgress.setValue(80);
		progressBar_DownloadProgress.setBounds(5, 45, 430, 20);
		add(progressBar_DownloadProgress);
		
		JLabel lbl_DownloadProgress = new JLabel("80%");
		lbl_DownloadProgress.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DownloadProgress.setBounds(145, 0, 140, 20);
		progressBar_DownloadProgress.add(lbl_DownloadProgress);
		
		JLabel lbl_TorrentInfo = new JLabel("Downloading: 3.9mbps | Uploading: 56.8kbps");
		lbl_TorrentInfo.setBounds(5, 25, 325, 20);
		add(lbl_TorrentInfo);

	}
}
