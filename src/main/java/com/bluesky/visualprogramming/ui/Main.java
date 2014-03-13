package com.bluesky.visualprogramming.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.utils.Config;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class Main extends JFrame {
	static Logger logger = Logger.getLogger(Main.class);

	static String DEFAULT_RUNTIME_IMAGE_FILE_NAME = "runtime.xml";
	static String DEFAULT_USER_IMAGE_FILE_NAME = "users.xml";
	static String USER_IMAGE_ENVIRONMENT_NAME = "COOBY_USER_IMAGE";

	SVGMainWindow mainWindow = null;

	public Main() {

		initMenu();

		// Add content to the window.
		mainWindow = new SVGMainWindow(this);

		getContentPane().add(mainWindow);

		JToolBar toolBar = new JToolBar();
		GridLayout gl_toolBar = new GridLayout();
		gl_toolBar.setColumns(10);
		gl_toolBar.setRows(0);
		toolBar.setLayout(gl_toolBar);
		getContentPane().add(toolBar, BorderLayout.NORTH);

		JLabel lblScale = new JLabel("Scale:");
		toolBar.add(lblScale);

		JComboBox comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "1000",
				"500", "200", "100", "75", "50", "25", "10" }));
		comboBox.setSelectedIndex(3);
		toolBar.add(comboBox);
		comboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == e.SELECTED) {

					// System.out.println("scale changed to:"
					// + (String) e.getItem());
					float rate = 1f;
					rate = Integer.valueOf((String) e.getItem()) / 100f;
					mainWindow.setDiagramScaleRate(rate);
				}
			}
		});

		setMinimumSize(new Dimension(1200, 800));

		// Display the window.
		pack();
		setVisible(true);
	}

	public final void initMenu() {

		JMenuBar menubar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		// Load
		JMenuItem eMenuItem = null;

		// Load
		eMenuItem = new JMenuItem("Load");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainWindow.load(DEFAULT_RUNTIME_IMAGE_FILE_NAME,
						DEFAULT_USER_IMAGE_FILE_NAME);
			}

		});
		file.add(eMenuItem);

		// Save
		eMenuItem = new JMenuItem("Save");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				VirtualMachine vm = VirtualMachine.getInstance();

				vm.pause();
				vm.save();
				vm.resume();
			}

		});
		file.add(eMenuItem);

		menubar.add(file);

		eMenuItem = new JMenuItem("Exit");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}

		});
		file.add(eMenuItem);

		eMenuItem = new JMenuItem("Save&Exit");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				VirtualMachine vm = VirtualMachine.getInstance();

				vm.pause();
				vm.save();

				vm.resume();

				System.exit(0);
			}

		});
		file.add(eMenuItem);

		setJMenuBar(menubar);

		setTitle("Visual Programming");
		setSize(1600, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private static Map<String, String> processArguments(String[] args) {

		Map<String, String> map = new HashMap<String, String>();

		for (String kv : args) {
			String[] ss = kv.split("=");
			if (ss.length == 2)
				map.put(ss[0], ss[1]);
		}
		return map;
	}
	
	private static void printUsage(){
		System.out.println("Usage:");
		System.out.println("java -jar cooby.jar HomeDir=~/cooby/");
		System.out.println("You can set environment variable to set the user image file in another folder:");
		System.out.println("$export COOBY_USER_IMAGE=/home/jack/cooby/users.xml");
	}

	/**
	 * example: java -jar visual-programming.jar HomeDir=/cooby/
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if(args.length==1 && args[0].toLowerCase().contains("help")){
			printUsage();
			return;
		}
		
		Map<String, String> map = processArguments(args);
		Config config = new Config(map);

		String homeDir = config.getString("HomeDir", "images/");
		
		logger.debug("home directory: " + homeDir);
		
		//default value
		String runtimeImageFile = homeDir + DEFAULT_RUNTIME_IMAGE_FILE_NAME;
		String userImageFile = homeDir + DEFAULT_USER_IMAGE_FILE_NAME;

		//environment value has higher priority
		String userImageFileFromEnv = System
				.getenv(USER_IMAGE_ENVIRONMENT_NAME);
		if (userImageFileFromEnv != null && !userImageFileFromEnv.isEmpty())
		{
			logger.debug("user image file set by environment variable:"+userImageFileFromEnv);
			userImageFile = userImageFileFromEnv;
		}
				

		// init Object Repository
		VirtualMachine vm = new VirtualMachine();
		VirtualMachine.setInstance(vm);

		// vm.loadFromImage(DEFAULT_IMAGE_FILE_NAME);
		vm.loadFromImage(runtimeImageFile, userImageFile);

		vm.start();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main ex = new Main();
				ex.setVisible(true);
			}
		});
	}
}
