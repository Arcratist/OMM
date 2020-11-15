/**
 * 
 */
package OMM.ui.panels;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import OMM.ui.panels.components.HintTextField;
import OMM.ui.windows.WindowMain;
import OMM.util.DownloadInfo;
import OMM.util.DownloadStatus;
import OMM.util.VideoDownloader;

/**
 * @author Brett Daniel Smith
 *
 */
public class PanelYoutubeVideoDownloader extends OMMPanel {
	private static final long serialVersionUID = 1067177770205308112L;

	private Image thumbnail = null;
	private JTextField txtField_Output;
	private HintTextField txtField_VideoURL;
	private Canvas canvas;

	private DownloadInfo currentDownload;

	private Thread thread;
	private Runnable runnable;
	private JProgressBar progressBar;
	private JLabel lbl_progressBar;
	private JButton btn_Download;
	private JButton btn_Browse;
	private JButton btn_Check;
	private JComboBox<String> comboBox_Format, comboBox_Resolution;

	private VideoDownloader videoDownloader;

	/**
	 * Create the panel.
	 * 
	 * @param windowMain
	 */
	public PanelYoutubeVideoDownloader(WindowMain windowMain) {
		super(windowMain);

		videoDownloader = new VideoDownloader();

		runnable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (currentDownload != null) {
						if (currentDownload.isNotify()) {
							progressBar.setValue((int) currentDownload.getPercentage());
							lbl_progressBar.setText(currentDownload.getPercentage() + "%");
							btn_Download.setEnabled(false);
							btn_Download.setText("Downloading...");
							btn_Check.setEnabled(false);
							btn_Browse.setEnabled(false);
							txtField_Output.setEnabled(false);
							txtField_VideoURL.setEnabled(false);

							if (currentDownload.getStatus().equals(DownloadStatus.COMPLETE)) {
								lbl_progressBar.setText("Complete");
								btn_Download.setEnabled(true);
								btn_Download.setText("Download");
								btn_Check.setEnabled(true);
								btn_Browse.setEnabled(true);
								txtField_Output.setEnabled(true);
								txtField_VideoURL.setEnabled(true);
							}
							currentDownload.setNotify(false);
						}
					}
				}
			}
		};

		thread = new Thread(runnable, "OMM_ytvideo_logic");
		thread.start();

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
		mn_File.add(mntm_File_Exit);

		txtField_Output = new JTextField();
		txtField_Output.setText(System.getProperty("user.home") + "\\Videos\\");
		txtField_Output.setToolTipText("Video Output Location.");
		txtField_Output.setBounds(5, 350, 365, 20);
		add(txtField_Output);
		txtField_Output.setColumns(10);

		btn_Browse = new JButton("Browse");
		btn_Browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(txtField_Output.getText()));
				chooser.setDialogTitle("Select destionation folder...");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setApproveButtonText("Select");
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					txtField_Output.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		btn_Browse.setBounds(375, 350, 90, 20);
		add(btn_Browse);

		btn_Download = new JButton("Download");
		btn_Download.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentDownload = videoDownloader.downloadVideo(txtField_VideoURL.getText(), txtField_Output.getText(), (String) comboBox_Format.getSelectedItem(), "1080");
			}
		});
		btn_Download.setBounds(175, 440, 120, 20);
		add(btn_Download);

		JLabel lbl_Title = new JLabel("YouTube Video Downloader");
		lbl_Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_Title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title.setBounds(0, 30, 470, 20);
		add(lbl_Title);

		txtField_VideoURL = new HintTextField("YouTube Video URL...");
		txtField_VideoURL.setToolTipText("YouTube Video URL.");
		txtField_VideoURL.setBounds(5, 60, 365, 20);
		add(txtField_VideoURL);
		txtField_VideoURL.setColumns(10);

		btn_Check = new JButton("Check");
		btn_Check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Checking " + txtField_VideoURL.getText() + "...");
				String videoID = txtField_VideoURL.getText().split("\\?v=")[1];
				String url = "http://img.youtube.com/vi/" + videoID + "/0.jpg";
				try {
					thumbnail = ImageIO.read(new URL(url));
					System.out.println("Found thumbnail: " + url + "...");
					canvas.repaint();
				} catch (Exception e1) {
					System.out.println("ERROR! Failed to aquire thumbnail!");
					e1.printStackTrace();
				}
			}
		});
		btn_Check.setBounds(375, 60, 90, 20);
		add(btn_Check);

		canvas = new Canvas() {
			private static final long serialVersionUID = 1265062518747501579L;

			@Override
			public void paint(Graphics g) {
				if (thumbnail != null)
					g.drawImage(thumbnail, 0, 0, getWidth(), getHeight(), null);
			}
		};
		canvas.setBackground(Color.BLACK);
		canvas.setBounds(5, 85, 460, 259);
		add(canvas);

		progressBar = new JProgressBar();
		progressBar.setBounds(5, 465, 460, 20);
		add(progressBar);

		lbl_progressBar = new JLabel("0%");
		lbl_progressBar.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_progressBar.setBounds(180, 0, 100, 20);
		progressBar.add(lbl_progressBar);

		comboBox_Resolution = new JComboBox<String>();
		comboBox_Resolution.setModel(new DefaultComboBoxModel<String>(new String[] { "8k", "4k", "1080p", "720p", "480p", "360p" }));
		comboBox_Resolution.setSelectedIndex(2);
		comboBox_Resolution.setBounds(130, 375, 120, 20);
		add(comboBox_Resolution);

		comboBox_Format = new JComboBox<String>();
		comboBox_Format.setModel(new DefaultComboBoxModel<String>(new String[] { "mp4", "mkv", "m4a", "avi" }));
		comboBox_Format.setBounds(5, 375, 120, 20);
		add(comboBox_Format);

	}
}
