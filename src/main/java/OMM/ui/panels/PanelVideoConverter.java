/**
 * 
 */
package OMM.ui.panels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import OMM.ui.windows.WindowMain;
import OMM.util.DownloadInfo;
import OMM.util.DownloadStatus;
import OMM.util.VideoConverter;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.job.TwoPassFFmpegJob;
import net.bramp.ffmpeg.progress.ProgressListener;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.JRadioButton;

/**
 * @author Brett Daniel Smith
 *
 */
public class PanelVideoConverter extends OMMPanel {
	private static final long serialVersionUID = 388920866059268253L;
	private JTextField txtField_Output;
	private JTextField txtField_Input;

	private JProgressBar progressBar;
	private JLabel lbl_progressBar;
	private JButton btn_Convert;
	private JButton btn_BrowseOutput;
	private JButton btn_BrowseSource;
	private JComboBox<String> comboBox;
	private Thread thread;
	private Runnable runnable;
	private VideoConverter videoConverter;

	private DownloadInfo currentDownload;
	private JLabel lbl_info;
	private JComboBox<String> comboBox_Quality;
	private JComboBox<String> comboBox_Resolution;
	private JFormattedTextField formattedTextField_FPS;
	private JFormattedTextField formattedTextField_Threads;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JSpinner spinner_volume;
	private JLabel lblNewLabel_6;
	private JRadioButton rdbtn_MultiPass;

	/**
	 * Create the panel.
	 * 
	 * @param windowMain
	 */
	public PanelVideoConverter(WindowMain windowMain) {
		super(windowMain);

		videoConverter = new VideoConverter();

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
							progressBar.setValue(currentDownload.getPercentage());
							lbl_progressBar.setText(currentDownload.getPercentage() + "%");
							lbl_info.setText(currentDownload.getOut());

							if (currentDownload.getStatus().equals(DownloadStatus.COMPLETE)) {
								lbl_progressBar.setText("Complete");
								btn_Convert.setText("Convert");
								btn_Convert.setEnabled(true);
							}
							currentDownload.setNotify(false);
						}
					}
				}
			}
		};

		thread = new Thread(runnable, "OMM_videoconverter_logic");
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
		txtField_Output.setText(".");
		txtField_Output.setToolTipText("Video Output Location.");
		txtField_Output.setBounds(5, 85, 365, 20);
		add(txtField_Output);
		txtField_Output.setColumns(10);

		btn_BrowseOutput = new JButton("Browse");
		btn_BrowseOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(txtField_Output.getText()));
				chooser.setDialogTitle("Select destionation folder...");
				chooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
				chooser.setApproveButtonText("Save");
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					txtField_Output.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		btn_BrowseOutput.setBounds(375, 85, 90, 20);
		add(btn_BrowseOutput);

		btn_Convert = new JButton("Convert");
		btn_Convert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentDownload = videoConverter.convertVideo(txtField_Input.getText(), txtField_Output.getText(),
						comboBox.getSelectedItem().toString(), Integer.parseInt(formattedTextField_Threads.getText()),
						comboBox_Quality.getSelectedIndex() + 1, Integer.parseInt(formattedTextField_FPS.getText()),
						(comboBox_Resolution.getSelectedIndex() == 0) ? new Dimension(1920, 1080)
								: new Dimension(1280, 720), calcVolume((String) spinner_volume.getValue()), rdbtn_MultiPass.isSelected());
				btn_Convert.setText("Converting...");
				btn_Convert.setEnabled(false);
			}

			private float calcVolume(String value) {
				if (value.equals("Normal"))
					return 1.0f;
				else
					return (1.0f + Float.parseFloat(value));
			}
		});
		btn_Convert.setBounds(175, 440, 120, 20);
		add(btn_Convert);

		JLabel lbl_Title = new JLabel("Video Converter");
		lbl_Title.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_Title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title.setBounds(0, 30, 470, 20);
		add(lbl_Title);

		txtField_Input = new JTextField("Source Video File...");
		txtField_Input.setToolTipText("Source Video File.");
		txtField_Input.setText(".");
		txtField_Input.setBounds(5, 60, 365, 20);
		add(txtField_Input);
		txtField_Input.setColumns(10);

		btn_BrowseSource = new JButton("Browse");
		btn_BrowseSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(txtField_Input.getText()));
				chooser.setDialogTitle("Select source video file...");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setApproveButtonText("Select");
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					txtField_Input.setText(chooser.getSelectedFile().getAbsolutePath());
					txtField_Output.setText(txtField_Input.getText().substring(0, txtField_Input.getText().length() - 3)
							+ comboBox.getSelectedItem().toString());
				}
			}
		});
		btn_BrowseSource.setBounds(375, 60, 90, 20);
		add(btn_BrowseSource);

		progressBar = new JProgressBar();
		progressBar.setBounds(5, 465, 460, 20);
		add(progressBar);

		lbl_progressBar = new JLabel("0%");
		lbl_progressBar.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_progressBar.setBounds(180, 0, 100, 20);
		progressBar.add(lbl_progressBar);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "mp4", "mkv", "avi", "divx" }));
		comboBox.setSelectedIndex(2);
		comboBox.setBounds(380, 345, 80, 20);
		add(comboBox);

		lbl_info = new JLabel("[0%] frame:0 time:00:00:00.00 ms fps:00.00 speed:0.00x");
		lbl_info.setBounds(10, 415, 450, 20);
		add(lbl_info);

		JLabel lblNewLabel = new JLabel("Threads:");
		lblNewLabel.setBounds(10, 370, 55, 20);
		add(lblNewLabel);

		formattedTextField_Threads = new JFormattedTextField(NumberFormat.getNumberInstance());
		formattedTextField_Threads.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_Threads.setText("4");
		formattedTextField_Threads.setBounds(70, 370, 80, 20);
		add(formattedTextField_Threads);

		JLabel lblNewLabel_1 = new JLabel("Resolution:");
		lblNewLabel_1.setBounds(10, 345, 55, 20);
		add(lblNewLabel_1);

		comboBox_Resolution = new JComboBox<String>();
		comboBox_Resolution.setModel(new DefaultComboBoxModel<String>(new String[] { "1920x1080", "1280x720" }));
		comboBox_Resolution.setSelectedIndex(1);
		comboBox_Resolution.setBounds(70, 345, 80, 20);
		add(comboBox_Resolution);

		JLabel lblNewLabel_2 = new JLabel("FPS:");
		lblNewLabel_2.setBounds(10, 395, 55, 20);
		add(lblNewLabel_2);

		formattedTextField_FPS = new JFormattedTextField(NumberFormat.getNumberInstance());
		formattedTextField_FPS.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_FPS.setText("24");
		formattedTextField_FPS.setBounds(70, 395, 80, 20);
		add(formattedTextField_FPS);

		JLabel lblNewLabel_3 = new JLabel("Quality:");
		lblNewLabel_3.setBounds(160, 345, 55, 20);
		add(lblNewLabel_3);

		comboBox_Quality = new JComboBox<String>();
		comboBox_Quality.setModel(new DefaultComboBoxModel<String>(new String[] { "HIGH", "MEDIUM", "LOW" }));
		comboBox_Quality.setSelectedIndex(1);
		comboBox_Quality.setBounds(225, 345, 80, 20);
		add(comboBox_Quality);

		lblNewLabel_4 = new JLabel("Format:");
		lblNewLabel_4.setBounds(315, 345, 55, 20);
		add(lblNewLabel_4);
		
		Canvas canvas = new Canvas();
		canvas.setBackground(Color.BLACK);
		canvas.setBounds(35, 110, 400, 225);
		add(canvas);
		
		lblNewLabel_5 = new JLabel("Volume:");
		lblNewLabel_5.setBounds(160, 370, 55, 20);
		add(lblNewLabel_5);
		
		spinner_volume = new JSpinner();
		spinner_volume.setModel(new SpinnerListModel(new String[] {"-1.0", "-0.75", "-0.50", "-0.25", "Normal", "+0.25", "+0.50", "+0.75", "+1.0"}));
		spinner_volume.setValue("Normal");
		spinner_volume.setBounds(225, 370, 80, 20);
		add(spinner_volume);
		
		lblNewLabel_6 = new JLabel("Multi-Pass:");
		lblNewLabel_6.setBounds(160, 395, 55, 20);
		add(lblNewLabel_6);
		
		rdbtn_MultiPass = new JRadioButton("");
		rdbtn_MultiPass.setSelected(true);
		rdbtn_MultiPass.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_MultiPass.setBounds(225, 395, 80, 20);
		add(rdbtn_MultiPass);

	}

	public FFmpegJob createTwoPassJob(FFmpeg ffmpeg, FFmpegBuilder builder, ProgressListener listener) {
		return new TwoPassFFmpegJob(ffmpeg, builder, listener);
	}
}
