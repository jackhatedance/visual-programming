package com.bluesky.visualprogramming.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.vm.VirtualMachine;

import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class MainFrame extends JFrame {
	String DEFAULT_IMAGE_FILE_NAME = "visual-programming.txt";

	MainWindow mainWindow = null;

	public MainFrame() {

		initMenu();

		// Add content to the window.
		mainWindow = new MainWindow(this);

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
					Double rate = 1d;
					rate = Integer.valueOf((String) e.getItem()) / 100d;
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

		eMenuItem = new JMenuItem("Load Sample");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainWindow.load(DEFAULT_IMAGE_FILE_NAME);
			}

		});
		file.add(eMenuItem);

		// Load
		eMenuItem = new JMenuItem("Load");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainWindow.load(DEFAULT_IMAGE_FILE_NAME);
			}

		});
		file.add(eMenuItem);

		// Save
		eMenuItem = new JMenuItem("Save");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				VirtualMachine vm = VirtualMachine.getInstance();

				vm.pause();
				vm.getObjectRepository().save(DEFAULT_IMAGE_FILE_NAME);
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

		setJMenuBar(menubar);

		setTitle("Simple menu");
		setSize(1600, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		// init Object Repository
		VirtualMachine vm = new VirtualMachine();

		vm.getObjectRepository().loadSampleObjects();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame ex = new MainFrame();
				ex.setVisible(true);
			}
		});
	}
}
