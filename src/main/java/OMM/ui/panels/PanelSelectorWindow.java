/**
 * 
 */
package OMM.ui.panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import OMM.ui.windows.WindowMain;

/**
 * @author Brett Daniel Smith
 *
 */
public class PanelSelectorWindow extends OMMPanel {
	private static final long serialVersionUID = -8300933255618257016L;

	/**
	 * Create the panel.
	 * 
	 * @param windowMain
	 */
	public PanelSelectorWindow(WindowMain windowMain) {
		super(windowMain);

		setLayout(null);
		setPreferredSize(new Dimension(470, 490));
		setMinimumSize(new Dimension(470, 490));
		setMaximumSize(new Dimension(470, 490));

		JButton btn_op1 = new JButton("Youtube To MP3");
		btn_op1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowMain.setPanel(windowMain.youtubeMp3Downloader);
			}
		});
		btn_op1.setBounds(5, 25, 150, 150);
		add(btn_op1);

		JButton btn_op2 = new JButton("YouTube Downloader");
		btn_op2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowMain.setPanel(windowMain.youtubeVideoDownloader);
			}
		});
		btn_op2.setBounds(160, 25, 150, 150);
		add(btn_op2);

		JButton btn_op3 = new JButton("Torrent Downloader");
		btn_op3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowMain.setPanel(windowMain.torrentDownloader);
			}
		});
		btn_op3.setBounds(315, 25, 150, 150);
		add(btn_op3);

		JButton btn_op4 = new JButton("Video Converter");
		btn_op4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowMain.setPanel(windowMain.videoConverter);
			}
		});
		btn_op4.setBounds(5, 180, 150, 150);
		add(btn_op4);

		JButton btn_op5 = new JButton("5");
		btn_op5.setBounds(160, 180, 150, 150);
		add(btn_op5);

		JButton btn_op6 = new JButton("6");
		btn_op6.setBounds(315, 180, 150, 150);
		add(btn_op6);

		JButton btn_op7 = new JButton("7");
		btn_op7.setBounds(5, 335, 150, 150);
		add(btn_op7);

		JButton btn_op8 = new JButton("8");
		btn_op8.setBounds(160, 335, 150, 150);
		add(btn_op8);

		JButton btn_op9 = new JButton("9");
		btn_op9.setBounds(315, 335, 150, 150);
		add(btn_op9);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 470, 20);
		add(menuBar);

		JMenu mn_File = new JMenu("File");
		menuBar.add(mn_File);

		JMenuItem mntm_File_Exit = new JMenuItem("Exit");
		mntm_File_Exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		mn_File.add(mntm_File_Exit);

		JMenu mn_About = new JMenu("About");
		menuBar.add(mn_About);

	}
}
