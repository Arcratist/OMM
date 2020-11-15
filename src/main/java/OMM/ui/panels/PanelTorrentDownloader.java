/**
 * 
 */
package OMM.ui.panels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import OMM.ui.panels.components.ComponentTorrentDownload;
import OMM.ui.windows.WindowMain;
import OMM.util.TorrentConfig;
import OMM.util.TorrentDownloader;

/**
 * @author Brett Daniel Smith
 *
 */
public class PanelTorrentDownloader extends OMMPanel {
	private static final long serialVersionUID = -698120514442597115L;

	private TorrentConfig config;
	private TorrentDownloader torrentDownloader;
	private JTextField textField;

	private JScrollPane vertical;

	/**
	 * Create the panel.
	 * 
	 * @param windowMain
	 */
	public PanelTorrentDownloader(WindowMain windowMain) {
		super(windowMain);

		config = new TorrentConfig();
		torrentDownloader = new TorrentDownloader();

		setLayout(null);
		setPreferredSize(new Dimension(470, 490));
		setMinimumSize(new Dimension(470, 490));
		setMaximumSize(new Dimension(470, 490));

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 470, 22);
		add(menuBar);

		JMenu mn_File = new JMenu("File");
		menuBar.add(mn_File);

		JMenuItem mntm_File_Back = new JMenuItem("Back");
		mntm_File_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowMain.setPanel(getPreviousPanel());
			}
		});
		mn_File.add(mntm_File_Back);

		JMenuItem mntm_File_Exit = new JMenuItem("Exit");
		mntm_File_Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenuItem mntm_Settings = new JMenuItem("Settings");
		mn_File.add(mntm_Settings);
		mn_File.add(mntm_File_Exit);

		JLabel lbl_Title = new JLabel("Torrent Downloader");
		lbl_Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_Title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title.setBounds(0, 30, 470, 20);
		add(lbl_Title);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				torrentDownloader.downloadTorrent(
						"magnet:?xt=urn:btih:24D1C58B22E240F0693732BD6FD85A43B273EDED&dn=Unreal+Tournament+1999+GOTY+-+HD+TEXTURES+INCLUDED&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2F9.rarbg.to%3A2920%2Fannounce&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337&tr=udp%3A%2F%2Ftracker.internetwarriors.net%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.pirateparty.gr%3A6969%2Fannounce&tr=udp%3A%2F%2Ftracker.cyberia.is%3A6969%2Fannounce",
						config);
			}
		});
		btnNewButton.setBounds(175, 85, 120, 20);
		add(btnNewButton);

		textField = new JTextField();
		textField.setBounds(5, 60, 355, 20);
		add(textField);
		textField.setColumns(10);

		JButton btnNewButton_1 = new JButton("Add Torrent");
		btnNewButton_1.setBounds(365, 60, 100, 20);
		add(btnNewButton_1);

		JPanel downloadsPanel = new JPanel();
		downloadsPanel.setLayout(new GridLayout(20, 1));

		for (int i = 0; i < 10; i++) {
			downloadsPanel.add(new ComponentTorrentDownload());
		}

		vertical = new JScrollPane(downloadsPanel);
		vertical.setSize(460, 300);
		vertical.setLocation(5, 185);
		vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(vertical);

	}
}
