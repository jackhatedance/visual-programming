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
import java.awt.Cursor;
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

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.SelectedStatus;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.ui.diagram.DiagramPanel;
import com.bluesky.visualprogramming.ui.diagram.Painter;
import com.bluesky.visualprogramming.ui.dialog.ObjectPropertyDialog;
import com.bluesky.visualprogramming.ui.selection.MouseOperation;

public class MainWindow extends JPanel {

	private JFrame owner;

	private DefaultTreeModel treeModel;
	private JTree tree;
	JScrollPane scrollTreePanel;

	private DiagramPanel diagram;
	JScrollPane scrollDiagramPanel;
	// private double diagramScaleRate = 1;

	JSplitPane splitPane;

	JPopupMenu popupMenu;

	
	private Point cursorOffset;	
	//the object that hovered by mouse
	private _Object activeChildObject;
	//either move or resize,depends on mouse position.
	private MouseOperation mouseOperation;

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

		TreeNode rootNode = createTreeNode(ObjectRepository.getInstance()
				.getRootObject());

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

	private DefaultMutableTreeNode createTreeNode(_Object obj) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(obj);

		for (_Object childObj : obj.getChildrenList()) {
			DefaultMutableTreeNode childNode = createTreeNode(childObj);
			node.add(childNode);
		}

		return node;
	}

	private void initDiagramPanel() {
		diagram = new DiagramPanel();
		diagram.setPainter(new Painter() {

			@Override
			public void paint(Graphics g) {

				_Object obj = getSelectedTreeObject();
				if (obj != null)
					obj.drawInternal(g, new Point(0, 0));
			}
		});

		// diagram.setMinimumSize(new Dimension(1000, 1000));
		createMouseListener();

		scrollDiagramPanel = new JScrollPane(diagram);

		Dimension minimumSize = new Dimension(200, 150);
		scrollDiagramPanel.setMinimumSize(minimumSize);

		popupMenu = new JPopupMenu();
		JMenuItem eMenuItem = new JMenuItem("New Object");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = ObjectRepository.getInstance().createObject(
						getSelectedTreeObject());
				addChildObject(obj);
			}

		});
		popupMenu.add(eMenuItem);

		eMenuItem = new JMenuItem("New Integer");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				_Object obj = ObjectRepository.getInstance().createObject(
						getSelectedTreeObject(), ObjectType.INTEGER);

				addChildObject(obj);
			}

		});
		popupMenu.add(eMenuItem);

		eMenuItem = new JMenuItem("New String");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = ObjectRepository.getInstance().createObject(
						getSelectedTreeObject(), ObjectType.STRING);
				addChildObject(obj);

			}

		});
		popupMenu.add(eMenuItem);

		eMenuItem = new JMenuItem("New Procedure");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = ObjectRepository.getInstance().createObject(
						getSelectedTreeObject(), ObjectType.PROCEDURE);
				addChildObject(obj);

			}

		});
		popupMenu.add(eMenuItem);

		eMenuItem = new JMenuItem("Delete");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Point p = diagram.getMousePosition();
				// updateSelectedChildObject(p, SelectedStatus.Selected);
				if (activeChildObject != null) {
					int result = JOptionPane.showConfirmDialog(owner, String
							.format("Are you sure to delete '%s'?",
									activeChildObject.getName()),
							"Confirmation", JOptionPane.YES_NO_OPTION);
					if (result == 0)// yes
					{
						// search
						DefaultMutableTreeNode selectedChildNode = findChildNode(
								getSelectedTreeNode(), activeChildObject);

						// remove tree model
						treeModel.removeNodeFromParent(selectedChildNode);

						// remove from object repository
						ObjectRepository.getInstance().destroyObject(
								activeChildObject);

						diagram.repaint();
					}
				}
			}

		});
		popupMenu.add(eMenuItem);

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

		ObjectRepository.getInstance().load(fileName);

		createTreePanel();

		splitPane.setLeftComponent(scrollTreePanel);

	}

	private void createMouseListener() {
		diagram.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (!popupMenu.isVisible()) {
					// hovering, indicate the underlying object with red color.
					updateSelectedChildObject(e.getPoint(),
							SelectedStatus.Preselected);
					
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (activeChildObject == null)
					return;

				Point rawCursorPos = CanvasUtils.scaleBack(e.getPoint(),
						getSelectedTreeObject().scaleRate);

				activeChildObject.getArea().x = rawCursorPos.x
						- cursorOffset.x;
				activeChildObject.getArea().y = rawCursorPos.y
						- cursorOffset.y;

				diagram.repaint();
			}
		});

		diagram.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

				activeChildObject = null;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				updateSelectedChildObject(e.getPoint(), SelectedStatus.Selected);
				// save the offset for drag
				if (activeChildObject != null) {
					Point rawMousePt = CanvasUtils.scaleBack(e.getPoint(),
							getSelectedTreeObject().scaleRate);

					cursorOffset = CanvasUtils.getOffset(
							activeChildObject.area.getLocation(), rawMousePt);

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
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				} else {
					if (activeChildObject != null) {
						ObjectPropertyDialog dialog = new ObjectPropertyDialog();
						dialog.setLocationRelativeTo(owner);
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setObject(activeChildObject);

						dialog.setVisible(true);

						if (dialog.isUpdated()) {
							TreeNode selectedChildNode = findChildNode(
									getSelectedTreeNode(), activeChildObject);
							treeModel.valueForPathChanged(new TreePath(
									selectedChildNode), activeChildObject);
							// diagram.repaint();
						}
					}
				}
			}
		});
	}

	private DefaultMutableTreeNode findChildNode(DefaultMutableTreeNode node,
			_Object childObj) {
		// search
		DefaultMutableTreeNode selectedChildNode = null;
		Enumeration en = node.children();
		while (en.hasMoreElements()) {
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) en
					.nextElement();
			_Object curChildObj = (_Object) childNode.getUserObject();
			if (curChildObj == childObj) {
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

	protected _Object getSelectedTreeObject() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		if (node == null)
			return null;

		return (_Object) node.getUserObject();
	}

	protected void updateSelectedChildObject(Point mousePos,
			SelectedStatus newStatus) {
		_Object obj = getSelectedTreeObject();
		if (obj == null)
			return;

		_Object oldSelectedChildObject = activeChildObject;
		activeChildObject = null;
		Point p = CanvasUtils.scaleBack(mousePos,
				getSelectedTreeObject().scaleRate);

		for (_Object c : obj.getChildrenList()) {
			//set all to notSelected
			c.selectedStatus = SelectedStatus.NotSelected;
			
			//keep the last hovered object, the last has highest z order
			if (c.area.contains(p))
				activeChildObject = c;
		}

		if(activeChildObject!=null)
		{
			activeChildObject.selectedStatus = newStatus;
			
			//check if cursor is near borders, adjust to resize cursor
			
		
		}
		
		if (oldSelectedChildObject != activeChildObject)
			diagram.repaint();
	}

	public void setDiagramScaleRate(double diagramScaleRate) {
		// this.diagramScaleRate =diagramScaleRate;
		getSelectedTreeObject().scaleRate = diagramScaleRate;
		diagram.repaint();
	}
}
