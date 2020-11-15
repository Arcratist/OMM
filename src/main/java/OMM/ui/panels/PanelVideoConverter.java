/**
 * 
 */
package OMM.ui.panels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;

import OMM.ui.windows.WindowMain;

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

	/**
	 * Create the panel.
	 * 
	 * @param windowMain
	 */
	public PanelVideoConverter(WindowMain windowMain) {
		super(windowMain);

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
		txtField_Output.setText(System.getProperty("user.home") + "\\Videos\\Converted\\");
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
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setApproveButtonText("Select");
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
				Runnable runnable = new Runnable() {

					@Override
					public void run() {
						System.out.println(txtField_Input.getText().toString());

						IMediaReader reader = ToolFactory.makeReader(txtField_Input.getText().toString());
						// add a viewer to the reader, to see progress as the media is
						// transcoded
						reader.addListener(ToolFactory.makeViewer(true));
						IMediaWriter writer = ToolFactory
								.makeWriter(txtField_Output.getText() + "output." + comboBox.getSelectedItem(), reader);

						writer.addListener(ToolFactory.makeDebugListener());

						reader.addListener(ToolFactory.makeWriter(
								txtField_Output.getText() + "output." + comboBox.getSelectedItem(), reader));

						while (reader.readPacket() == null)
							do {
							} while (false);
					}
				};
				Thread thread = new Thread(runnable);
				thread.start();
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
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "mp4", "mkv", "avi" }));
		comboBox.setBounds(5, 110, 90, 24);
		add(comboBox);

	}
}
