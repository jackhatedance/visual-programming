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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.utils.Config;
import com.bluesky.visualprogramming.vm.AppProperties;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class Main extends JFrame {
	static Logger logger = Logger.getLogger(Main.class);

	public static String DEFAULT_RUNTIME_IMAGE_FILE_NAME = "runtime.xml";
	public static String DEFAULT_USER_IMAGE_FILE_NAME = "users.xml";
	public static String USER_IMAGE_ENVIRONMENT_NAME = "COOBY_USER_IMAGE";

	JComboBox cmbZoom;

	SVGMainWindow mainWindow = null;

	/**
	 * periodic timer save objects to file
	 */
	private static ScheduledExecutorService autoSaveService = Executors
			.newScheduledThreadPool(1);

	private static void startAutoSaveService() {
		logger.debug("start auto-saving service");
		Config appConfig = AppProperties.getInstance().getAsConfig();
		if (appConfig.containsKey(AppProperties.AUTO_SAVE_INTERVAL)) {

			int autoSaveInterval = appConfig.getInteger(
					AppProperties.AUTO_SAVE_INTERVAL, 1000 * 60 * 10);
			int minDelay = 1000 * 10;
			int delay = autoSaveInterval < minDelay ? minDelay
					: autoSaveInterval;

			autoSaveService.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					VirtualMachine vm = VirtualMachine.getInstance();

					vm.pause();

					logger.debug("start auto saving");
					vm.save();
					logger.debug("finish auto saving");
					vm.resume();

				}
			}, delay, autoSaveInterval, TimeUnit.MILLISECONDS);
		}
	}

	private static void stopAutoSaveService() {
		logger.debug("stoping auto-saving service");

		autoSaveService.shutdown();
		try {
			logger.debug("waiting for auto-saving service to stop.");
			autoSaveService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
			logger.debug("auto-saving service stopped.");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

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

		JLabel lblZoom = new JLabel("Zoom:");
		toolBar.add(lblZoom);

		cmbZoom = new JComboBox();
		cmbZoom.setEditable(true);
		cmbZoom.setModel(new DefaultComboBoxModel(new String[] { "1000", "500",
				"200", "100", "75", "50", "25", "10" }));
		cmbZoom.setSelectedIndex(3);
		toolBar.add(cmbZoom);
		cmbZoom.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == e.SELECTED) {

					float rate = getZoomRate();

					mainWindow.setDiagramScaleRate(rate);
				}
			}
		});

		setMinimumSize(new Dimension(1200, 800));

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				int choice = JOptionPane.showConfirmDialog(Main.this,
						"Save the world?", "Exit", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (choice == JOptionPane.YES_OPTION)
					saveAndExit();
				else
					exit();
			}
		});

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

				stopAutoSaveService();

				vm.pause();
				vm.save();
				vm.resume();

				startAutoSaveService();
			}

		});
		file.add(eMenuItem);

		menubar.add(file);

		eMenuItem = new JMenuItem("Exit");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				exit();
			}

		});
		file.add(eMenuItem);

		eMenuItem = new JMenuItem("Save&Exit");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				saveAndExit();
			}

		});
		file.add(eMenuItem);

		setJMenuBar(menubar);

		setTitle("Visual Programming");
		setSize(1600, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void saveAndExit() {
		VirtualMachine vm = VirtualMachine.getInstance();

		stopAutoSaveService();

		vm.pause();
		vm.save();
		vm.resume();

		vm.stop();

		System.exit(0);
	}

	private void exit() {
		VirtualMachine vm = VirtualMachine.getInstance();

		stopAutoSaveService();

		vm.stop();

		System.exit(0);
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

	private static void printUsage() {
		System.out.println("Usage:");
		System.out.println("java -jar cooby.jar HomeDir=~/cooby/");
		System.out
				.println("You can set environment variable to set the user image file in another folder:");
		System.out
				.println("$export COOBY_USER_IMAGE=/home/jack/cooby/users.xml");
	}

	/**
	 * example: java -jar visual-programming.jar HomeDir=/cooby/
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length == 1 && args[0].toLowerCase().contains("help")) {
			printUsage();
			return;
		}

		Map<String, String> map = processArguments(args);
		Config config = new Config(map);

		String homeDir = config.getString("HomeDir", "images/");

		logger.debug("home directory: " + homeDir);

		// default value
		String runtimeImageFile = homeDir + DEFAULT_RUNTIME_IMAGE_FILE_NAME;
		String userImageFile = homeDir + DEFAULT_USER_IMAGE_FILE_NAME;

		// environment value has higher priority
		String userImageFileFromEnv = System
				.getenv(USER_IMAGE_ENVIRONMENT_NAME);
		if (userImageFileFromEnv != null && !userImageFileFromEnv.isEmpty()) {
			logger.debug("user image file set by environment variable:"
					+ userImageFileFromEnv);
			userImageFile = userImageFileFromEnv;
		}

		// init Object Repository
		VirtualMachine vm = new VirtualMachine();
		VirtualMachine.setInstance(vm);

		// vm.loadFromImage(DEFAULT_IMAGE_FILE_NAME);
		vm.loadFromImage(runtimeImageFile, userImageFile);

		vm.start();

		startAutoSaveService();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main ex = new Main();
				ex.setVisible(true);
			}
		});
	}

	public float getZoomRate() {
		float rate = Integer.valueOf((String) cmbZoom.getSelectedItem()) / 100f;
		return rate;
	}
}
