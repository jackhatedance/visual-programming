package com.bluesky.visualprogramming.ui;

/**
 * This application that requires the following additional files:
 *   TreeDemoHelp.html
 *    arnold.html
 *    bloch.html
 *    chan.html
 *    jls.html
 *    swingtutorial.html
 *    tutorial.html
 *    tutorialcont.html
 *    vm.html
 */
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.SelectedStatus;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.ui.avatar.SvgScene;
import com.bluesky.visualprogramming.ui.diagram.SVGDiagramPanel;
import com.bluesky.visualprogramming.ui.selection.MouseOperation;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class SVGMainWindow extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	static Logger logger = Logger.getLogger(SVGMainWindow.class);

	private JFrame owner;

	public DefaultTreeModel treeModel;
	private JTree tree;
	JScrollPane scrollTreePanel;

	private SVGDiagramPanel diagramPanel;
	JScrollPane scrollDiagramPanel;

	JSplitPane splitPane;

	

	// the object that hovered by mouse
	private Field activeChildField;
	// either move or resize,depends on mouse position.
	private MouseOperation mouseOperation;

	protected VirtualMachine getVM() {
		return VirtualMachine.getInstance();
	}

	public SVGMainWindow(JFrame frame) {
		super(new GridLayout(1, 0));

		owner = frame;

		createTreePanel();

		initDiagramPanel();

		// Add the scroll panes to a split pane.
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(scrollTreePanel);
		splitPane.setRightComponent(scrollDiagramPanel);

		splitPane.setDividerLocation(300);
		splitPane.setPreferredSize(new Dimension(500, 300));

		// Add the split pane to this panel.
		add(splitPane);

	}

	private void createTreePanel() {

		Field rootField = new Field(getVM().getObjectRepository()
				.getRootObject(), "root");
		TreeNode rootNode = createTreeNode(null, rootField);

		treeModel = new DefaultTreeModel(rootNode);

		tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {

				// reload the diagram
				final Field field = getSelectedTreeField();

				
								loadDiagram(field);
				
			}
		});

		// Create the scroll pane and add the tree to it.
		scrollTreePanel = new JScrollPane(tree);

		Dimension minimumSize = new Dimension(200, 150);
		scrollTreePanel.setMinimumSize(minimumSize);

	}

	private void loadDiagram(Field field) {
		if (field != null) {

			if (logger.isDebugEnabled())
				logger.debug(String.format("draw field %s  status:%s",
						field.name, field.getSelectedStatus()));
			
			SvgScene scene = new SvgScene();			
			field.target.drawInternal(diagramPanel, scene, new Point(0, 0));
			
			diagramPanel.setScene(scene);			
		}
	}

	private DefaultMutableTreeNode createTreeNode(_Object owner, Field field) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(field);

		_Object obj = field.target;
		if (obj == null)
			throw new RuntimeException(String.format(
					"owner id %d field %s is null:", owner.getId(), field.name));

		if (field.target.getOwner() == owner) {
			for (int i = 0; i < obj.getFields().size(); i++) {

				Field childField = obj.getFields().get(i);

				DefaultMutableTreeNode childNode = createTreeNode(obj,
						childField);
				node.add(childNode);
			}
		}

		return node;
	}

	private JPopupMenu createPopupMenu() {
		JPopupMenu menu = new JPopupMenu();
		JMenuItem eMenuItem = new JMenuItem("New Object");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().target, ObjectType.NORMAL);

				addChildObjectToTree(obj);
			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("New Integer");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().target, ObjectType.INTEGER);

				addChildObjectToTree(obj);
			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("New String");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().target, ObjectType.STRING);
				addChildObjectToTree(obj);

			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("New Boolean");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().target, ObjectType.BOOLEAN);
				addChildObjectToTree(obj);

			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("New Procedure");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().target, ObjectType.PROCEDURE);
				addChildObjectToTree(obj);

			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("Delete");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Point p = diagramPanel.getMousePosition();
				// updateSelectedChildObject(p, SelectedStatus.Selected);
				if (activeChildField != null) {
					int result = JOptionPane.showConfirmDialog(owner, String
							.format("Are you sure to delete '%s'?",
									activeChildField.getName()),
							"Confirmation", JOptionPane.YES_NO_OPTION);
					if (result == 0)// yes
					{
						// search
						DefaultMutableTreeNode selectedChildNode = findChildNode(
								getSelectedTreeNode(), activeChildField);

						// remove tree model
						treeModel.removeNodeFromParent(selectedChildNode);

						// remove from object repository
						getVM().getObjectRepository().destroyObject(
								activeChildField.target);

						diagramPanel.repaint();
					}
				}
			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("Execute");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Point p = diagramPanel.getMousePosition();
				// updateSelectedChildObject(p, SelectedStatus.Selected);
				if (activeChildField != null) {
					int result = JOptionPane.showConfirmDialog(owner, String
							.format("Are you sure to execute '%s'?",
									activeChildField.getName()),
							"Confirmation", JOptionPane.YES_NO_OPTION);
					if (result == 0)// yes
					{

						getVM().getPostService().sendMessageFromNobody(
								activeChildField.target.getOwner(),
								activeChildField.getName());

					}
				}
			}

		});
		menu.add(eMenuItem);

		return menu;
	}

	private void initDiagramPanel() {

		diagramPanel = new SVGDiagramPanel(this,createPopupMenu());

		// diagram.setMinimumSize(new Dimension(1000, 1000));
		// addMouseListener();

		scrollDiagramPanel = new JScrollPane(diagramPanel);

		Dimension minimumSize = new Dimension(200, 150);
		scrollDiagramPanel.setMinimumSize(minimumSize);

		// diagram.setComponentPopupMenu(popupMenu);

	}

	private void addChildObjectToTree(_Object obj) {

		// add to tree
		int index = obj.getOwner().getChildIndex(obj);
		Field f = obj.getOwner().getField(index);

		f.getArea().setLocation(diagramPanel.getMousePosition());

		treeModel.insertNodeInto(new DefaultMutableTreeNode(f),
				getSelectedTreeNode(), getSelectedTreeNode().getChildCount());

		diagramPanel.repaint();
	}

	public void load(String fileName) {

		getVM().loadFromImage(fileName);

		createTreePanel();

		splitPane.setLeftComponent(scrollTreePanel);

	}

	public DefaultMutableTreeNode findChildNode(DefaultMutableTreeNode node,
			Field childField) {
		// search
		DefaultMutableTreeNode selectedChildNode = null;
		Enumeration en = node.children();
		while (en.hasMoreElements()) {
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) en
					.nextElement();
			Field curChildField = (Field) childNode.getUserObject();
			if (curChildField == childField) {
				selectedChildNode = childNode;
				break;
			}
		}
		return selectedChildNode;
	}

	public DefaultMutableTreeNode getSelectedTreeNode() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();
		return node;
	}

	protected Field getSelectedTreeField() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		if (node == null)
			return null;

		return (Field) node.getUserObject();
	}

	protected void updateSelectedChildObject(Point mousePos,
			SelectedStatus newStatus) {
		Field field = getSelectedTreeField();
		if (field == null)
			return;

		Field oldActiveChildField = activeChildField;

		if (activeChildField != null) {
			if (activeChildField.getName() != null
					&& activeChildField.getName().equals("helloWorld")
					&& activeChildField.getSelectedStatus() == SelectedStatus.NotSelected)
				System.out
						.println(String.format(
								"old active child field %s status %s",
								activeChildField,
								activeChildField.getSelectedStatus()));
		}
		activeChildField = null;
		Point p = CanvasUtils.scaleBack(mousePos,
				getSelectedTreeField().target.scaleRate);

		_Object obj = field.target;
		// step 1. find the hovering child field
		for (int i = 0; i < obj.getChildCount(); i++) {
			Field f = obj.getField(i);
			// keep the last hovered object, the last has highest z order
			if (f.getArea().contains(p))
				activeChildField = f;
		}

		boolean sameActiveChildField = (oldActiveChildField == activeChildField);

		// step 2: save old status
		SelectedStatus oldStatus = null;
		if (activeChildField != null)
			oldStatus = activeChildField.getSelectedStatus();

		// step 3: update status
		for (int i = 0; i < obj.getChildCount(); i++) {
			Field f = obj.getField(i);

			if (f == activeChildField)
				f.setSelectedStatus(newStatus);
			else
				f.setSelectedStatus(SelectedStatus.NotSelected);
		}

		// step 4: update status of new active field.
		if (activeChildField != null) {

			if (oldStatus != newStatus) {
				if (logger.isDebugEnabled())
					logger.debug(String.format(
							"field %s's status from %s to %s",
							activeChildField.getName(), oldStatus, newStatus));

			}
			// check if cursor is near borders, adjust to resize cursor
		}

		if ((oldActiveChildField != activeChildField)) {
			String oldField = oldActiveChildField == null ? ""
					: oldActiveChildField.name;
			String newField = activeChildField == null ? ""
					: activeChildField.name;

			if (logger.isDebugEnabled())
				logger.debug(String.format(
						"active child field changed from %s to %s", oldField,
						newField));

			diagramPanel.repaint();
		} else if ((sameActiveChildField && activeChildField != null && oldStatus != newStatus)) {
			if (logger.isDebugEnabled())
				logger.debug(String.format("status changed from %s to %s",
						oldStatus, newStatus));
			diagramPanel.repaint();
		}
	}

	public void setDiagramScaleRate(double diagramScaleRate) {
		// this.diagramScaleRate =diagramScaleRate;
		getSelectedTreeField().target.scaleRate = diagramScaleRate;
		diagramPanel.repaint();
	}

}
