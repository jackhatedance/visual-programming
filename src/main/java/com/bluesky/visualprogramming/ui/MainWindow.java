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
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.SelectedStatus;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.ui.diagram.DiagramPanel;
import com.bluesky.visualprogramming.ui.diagram.Painter;
import com.bluesky.visualprogramming.ui.dialog.ObjectPropertyDialog;
import com.bluesky.visualprogramming.ui.selection.MouseOperation;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class MainWindow extends JPanel {

	static Logger logger = Logger.getLogger(MainWindow.class);

	private JFrame owner;

	private DefaultTreeModel treeModel;
	private JTree tree;
	JScrollPane scrollTreePanel;

	private DiagramPanel diagram;
	JScrollPane scrollDiagramPanel;
	// private double diagramScaleRate = 1;

	JSplitPane splitPane;

	JPopupMenu parentPopupMenu;

	private Point cursorOffset;
	// the object that hovered by mouse
	private Field activeChildField;
	// either move or resize,depends on mouse position.
	private MouseOperation mouseOperation;

	protected VirtualMachine getVM() {
		return VirtualMachine.getInstance();
	}

	public MainWindow(JFrame frame) {
		super(new GridLayout(1, 0));

		owner = frame;

		createTreePanel();

		initDiagramPanel();

		// Add the scroll panes to a split pane.
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(scrollTreePanel);
		splitPane.setRightComponent(scrollDiagramPanel);

		splitPane.setDividerLocation(200);
		splitPane.setPreferredSize(new Dimension(500, 300));

		// Add the split pane to this panel.
		add(splitPane);

	}

	private void createTreePanel() {

		Field rootField = new Field(getVM().getObjectRepository()
				.getRootObject(), "root");
		TreeNode rootNode = createTreeNode(rootField);

		treeModel = new DefaultTreeModel(rootNode);

		tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {

				diagram.repaint();
			}
		});

		// Create the scroll pane and add the tree to it.
		scrollTreePanel = new JScrollPane(tree);

		Dimension minimumSize = new Dimension(200, 150);
		scrollTreePanel.setMinimumSize(minimumSize);

	}

	private DefaultMutableTreeNode createTreeNode(Field field) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(field);

		_Object obj = field.target;
		if (obj == null)
			throw new RuntimeException("field is null:" + field.name);

		for (int i = 0; i < obj.getFields().size(); i++) {

			Field childField = obj.getFields().get(i);

			DefaultMutableTreeNode childNode = createTreeNode(childField);
			node.add(childNode);
		}

		return node;
	}

	private void initDiagramPanel() {
		diagram = new DiagramPanel();
		diagram.setPainter(new Painter() {

			@Override
			public void paint(Graphics g) {

				Field field = getSelectedTreeField();

				if (field != null) {
					System.out.println(String.format(
							"draw field %s  status:%s", field.name,
							field.selectedStatus));
					field.target.drawInternal(g, new Point(0, 0));
				}
			}
		});

		// diagram.setMinimumSize(new Dimension(1000, 1000));
		createMouseListener();

		scrollDiagramPanel = new JScrollPane(diagram);

		Dimension minimumSize = new Dimension(200, 150);
		scrollDiagramPanel.setMinimumSize(minimumSize);

		parentPopupMenu = new JPopupMenu();
		JMenuItem eMenuItem = new JMenuItem("New Object");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().target, ObjectType.NORMAL);
				addChildObject(obj);
			}

		});
		parentPopupMenu.add(eMenuItem);

		eMenuItem = new JMenuItem("New Integer");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().target, ObjectType.INTEGER);

				addChildObject(obj);
			}

		});
		parentPopupMenu.add(eMenuItem);

		eMenuItem = new JMenuItem("New String");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().target, ObjectType.STRING);
				addChildObject(obj);

			}

		});
		parentPopupMenu.add(eMenuItem);

		eMenuItem = new JMenuItem("New Procedure");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().target, ObjectType.PROCEDURE);
				addChildObject(obj);

			}

		});
		parentPopupMenu.add(eMenuItem);

		eMenuItem = new JMenuItem("Delete");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Point p = diagram.getMousePosition();
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

						diagram.repaint();
					}
				}
			}

		});
		parentPopupMenu.add(eMenuItem);

		eMenuItem = new JMenuItem("Execute");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Point p = diagram.getMousePosition();
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
								activeChildField.target.getName());

					}
				}
			}

		});
		parentPopupMenu.add(eMenuItem);

		// diagram.setComponentPopupMenu(popupMenu);

	}

	private void addChildObject(_Object obj) {
		obj.getArea().setLocation(diagram.getMousePosition());

		// add to tree
		treeModel.insertNodeInto(new DefaultMutableTreeNode(obj),
				getSelectedTreeNode(), getSelectedTreeNode().getChildCount());

		diagram.repaint();
	}

	public void load(String fileName) {

		getVM().loadFromImage(fileName);

		createTreePanel();

		splitPane.setLeftComponent(scrollTreePanel);

	}

	private void createMouseListener() {
		diagram.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (!parentPopupMenu.isVisible()) {
					// hovering, indicate the underlying object with red color.
					updateSelectedChildObject(e.getPoint(),
							SelectedStatus.Preselected);

				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (activeChildField == null)
					return;

				Point rawCursorPos = CanvasUtils.scaleBack(e.getPoint(),
						getSelectedTreeField().target.scaleRate);

				activeChildField.target.getArea().x = rawCursorPos.x
						- cursorOffset.x;
				activeChildField.target.getArea().y = rawCursorPos.y
						- cursorOffset.y;

				diagram.repaint();
			}
		});

		diagram.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

				activeChildField = null;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				updateSelectedChildObject(e.getPoint(), SelectedStatus.Selected);
				// save the offset for drag
				if (activeChildField != null) {
					Point rawMousePt = CanvasUtils.scaleBack(e.getPoint(),
							getSelectedTreeField().target.scaleRate);

					cursorOffset = CanvasUtils.getOffset(
							activeChildField.target.area.getLocation(),
							rawMousePt);

				}
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {

				updateSelectedChildObject(e.getPoint(), SelectedStatus.Selected);

				if (SwingUtilities.isRightMouseButton(e)) {
					parentPopupMenu.show(e.getComponent(), e.getX(), e.getY());
				} else {
					if (activeChildField != null) {
						ObjectPropertyDialog dialog = new ObjectPropertyDialog();
						dialog.setLocationRelativeTo(owner);
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setField(activeChildField);

						dialog.setVisible(true);

						if (dialog.isUpdated()) {
							TreeNode selectedChildNode = findChildNode(
									getSelectedTreeNode(), activeChildField);
							treeModel.valueForPathChanged(new TreePath(
									selectedChildNode), activeChildField);
							// diagram.repaint();
						}
					}
				}
			}
		});
	}

	private DefaultMutableTreeNode findChildNode(DefaultMutableTreeNode node,
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

	protected DefaultMutableTreeNode getSelectedTreeNode() {
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
		activeChildField = null;
		Point p = CanvasUtils.scaleBack(mousePos,
				getSelectedTreeField().target.scaleRate);

		_Object obj = field.target;
		// step 1. find the hovering child field
		for (int i = 0; i < obj.getChildCount(); i++) {
			Field f = obj.getField(i);
			// keep the last hovered object, the last has highest z order
			if (f.target.area.contains(p))
				activeChildField = f;
		}

		// step 2: save old status
		SelectedStatus oldStatus = null;
		if (activeChildField != null)
			oldStatus = activeChildField.selectedStatus;

		// step 3: clear all status
		for (int i = 0; i < obj.getChildCount(); i++) {
			Field f = obj.getField(i);
			// set all to notSelected
			f.selectedStatus = SelectedStatus.NotSelected;
		}

		// step 4: update status of new active field.
		if (activeChildField != null) {

			if (oldStatus != newStatus) {
				if (logger.isDebugEnabled())
					System.out.println(String.format(
							"field %s's status from %s to %s",
							activeChildField.getName(), oldStatus, newStatus));

				activeChildField.selectedStatus = newStatus;
			}

			// check if cursor is near borders, adjust to resize cursor

		}

		if ((oldActiveChildField != activeChildField)
				|| (oldActiveChildField == activeChildField && activeChildField!=null && oldStatus != newStatus)) {
			String oldField = oldActiveChildField == null ? ""
					: oldActiveChildField.name;
			String newField = activeChildField == null ? ""
					: activeChildField.name;
			System.out.println(String.format(
					"active child field changed from %s to %s", oldField,
					newField));
			System.out.println(String.format(
					"status changed from %s to %s", oldStatus,
					newStatus));
			diagram.repaint();
		}
	}

	public void setDiagramScaleRate(double diagramScaleRate) {
		// this.diagramScaleRate =diagramScaleRate;
		getSelectedTreeField().target.scaleRate = diagramScaleRate;
		diagram.repaint();
	}
}
