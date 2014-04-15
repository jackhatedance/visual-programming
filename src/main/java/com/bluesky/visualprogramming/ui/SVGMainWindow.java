package com.bluesky.visualprogramming.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JDialog;
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
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.svg.SVGLocatable;
import org.w3c.dom.svg.SVGMatrix;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core.FieldType;
import com.bluesky.visualprogramming.core.ObjectLayout;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.SelectedStatus;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.ui.dialog.ObjectPropertyDialog;
import com.bluesky.visualprogramming.ui.svg.SVGUtils;
import com.bluesky.visualprogramming.ui.svg.SvgScene;
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
	// JScrollPane scrollDiagramPanel;

	JSplitPane splitPane;

	FieldSelectionClipboardOwner objectSelection = new FieldSelectionClipboardOwner();

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
		splitPane.setRightComponent(diagramPanel);

		splitPane.setDividerLocation(300);
		splitPane.setPreferredSize(new Dimension(500, 300));

		// Add the split pane to this panel.
		add(splitPane);

	}

	private void createTreePanel() {

		Field rootField = new Field(getVM().getObjectRepository()
				.getRootObject(), ObjectRepository.ROOT_OBJECT, true);

		// just create the root node at first.
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootField);

		treeModel = new DefaultTreeModel(rootNode);
		reloadChildNodes(rootNode);

		tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {

				DefaultMutableTreeNode theNode = getSelectedTreeNode();
				final Field field = getSelectedTreeField();
				if (theNode != null) {
					// refresh the child tree nodes(depth is 1)
					reloadChildNodes(theNode);

					// reload the diagram
					loadDiagram(field);
				}

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

			_Object target = field.getTarget();
			if (target != null) {
				target.drawInternal(diagramPanel, scene, new Point(0, 0));

				diagramPanel.setScene(scene);
				Element background = scene.getDocument().getElementById(
						"background");

				diagramPanel
						.addBackgroundPopupMenuListener((EventTarget) background);

				// use background node to capture mouse move event. otherwise
				// the
				// cursor could leave the object if you moving very fast.
				diagramPanel.addMouseMoveListener((EventTarget) background);
			} else {
				System.out.println("selected field is null");
			}
		}
	}

	public void reloadDiagram() {
		final Field field = getSelectedTreeField();

		loadDiagram(field);
	}

	/**
	 * refresh node children but not grand children.
	 * 
	 * @param owner
	 * @param field
	 * @return
	 */
	private void reloadChildNodes(DefaultMutableTreeNode node) {
		// clear existing child nodes

		// node.removeAllChildren();

		// to list
		Enumeration<DefaultMutableTreeNode> enumNodes = node.children();
		List<DefaultMutableTreeNode> nodeList = new ArrayList<DefaultMutableTreeNode>();
		while (enumNodes.hasMoreElements()) {
			nodeList.add(enumNodes.nextElement());
		}

		// remove all children
		for (DefaultMutableTreeNode child : nodeList)
			treeModel.removeNodeFromParent(child);

		// add new child
		createChildNodes(node, 2);

		// treeModel.nodeStructureChanged(node);
		// treeModel.nodeChanged(node);

	}

	private void createChildNodes(DefaultMutableTreeNode node, int depth) {
		if (depth == 0)
			return;

		Field field = (Field) node.getUserObject();
		_Object obj = field.getTarget();

		if (obj != null) {

			// create child nodes
			List<Field> fields = obj.getFields();
			for (int i = 0; i < fields.size(); i++) {
				Field childField = fields.get(i);
				// if (obj.owns(childField.target)) {

				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(
						childField);

				treeModel.insertNodeInto(childNode, node, node.getChildCount());
				// node.add(childNode);

				createChildNodes(childNode, depth - 1);
				// }
			}
		}
	}

	/**
	 * when right click on a child object
	 * 
	 * @return
	 */
	private JPopupMenu createObjectPopupMenu() {
		JPopupMenu menu = new JPopupMenu();

		JMenuItem eMenuItem = new JMenuItem("Property");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				ObjectPropertyDialog dialog = new ObjectPropertyDialog();
				dialog.setLocationRelativeTo(getOwner());
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

				Field activeChildField = getActiveChildField();
				dialog.setField(getActiveChildField());

				dialog.setVisible(true);

				if (dialog.isUpdated()) {
					TreeNode selectedChildNode = findChildNode(
							getSelectedTreeNode(), activeChildField);
					treeModel.valueForPathChanged(new TreePath(
							selectedChildNode), activeChildField);

					diagramPanel.reload();
				}

			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("Cut");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Point p = diagramPanel.getMousePosition();
				// updateSelectedChildObject(p, SelectedStatus.Selected);
				if (getActiveChildField() != null) {
					// save to clipboard
					objectSelection.setObject(getSelectedTreeField()
							.getTarget(), getActiveChildField().name);
				}
			}
		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("Delete");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Point p = diagramPanel.getMousePosition();
				// updateSelectedChildObject(p, SelectedStatus.Selected);
				if (getActiveChildField() != null) {
					int result = JOptionPane.showConfirmDialog(owner, String
							.format("Are you sure to delete '%s'?",
									getActiveChildField().getName()),
							"Confirmation", JOptionPane.YES_NO_OPTION);
					if (result == 0)// yes
					{
						// search
						DefaultMutableTreeNode selectedChildNode = findChildNode(
								getSelectedTreeNode(), getActiveChildField());

						// remove from tree
						treeModel.removeNodeFromParent(selectedChildNode);

						Field ownerField = (Field) getSelectedTreeNode()
								.getUserObject();
						_Object ownerObject = ownerField.getTarget();
						_Object childObject = getActiveChildField().getTarget();

						// remove field
						ownerObject.removeField(getActiveChildField().name);

						// destroy object if it owns it.
						if (ownerObject.owns(childObject)) {
							// remove from object repository
							getVM().getObjectRepository().destroyObject(
									getActiveChildField().getTarget());
						}

						diagramPanel.reload();
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
				if (getActiveChildField() != null) {
					int result = JOptionPane.showConfirmDialog(owner, String
							.format("Are you sure to execute '%s'?",
									getActiveChildField().getName()),
							"Confirmation", JOptionPane.YES_NO_OPTION);
					if (result == 0)// yes
					{

						getVM().getPostService().sendMessageFromNobody(
								getActiveChildField().getTarget().getOwner(),
								getActiveChildField().getName(), null, null);

					}
				}
			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("belong here");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Point p = diagramPanel.getMousePosition();
				// updateSelectedChildObject(p, SelectedStatus.Selected);
				if (getActiveChildField() != null) {
					Field ownerField = (Field) getSelectedTreeNode()
							.getUserObject();

					_Object childObject = getActiveChildField().getTarget();

					getActiveChildField().setType(FieldType.STRONG);
					childObject.attachTo(getActiveChildField());
				}
			}
		});
		menu.add(eMenuItem);

		return menu;
	}

	/**
	 * when right click on background (blank place)
	 * 
	 * @return
	 */
	private JPopupMenu createBackgroundPopupMenu() {
		JPopupMenu menu = new JPopupMenu();

		JMenuItem eMenuItem = new JMenuItem("New Object");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().getTarget(), ObjectType.NORMAL);

				addChildObjectToTree(obj);
			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("New Integer");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().getTarget(), ObjectType.INTEGER);

				addChildObjectToTree(obj);
			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("New String");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().getTarget(), ObjectType.STRING);

				addChildObjectToTree(obj);

			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("New Boolean");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().getTarget(), ObjectType.BOOLEAN);

				addChildObjectToTree(obj);

			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("New Link");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().getTarget(), ObjectType.LINK);

				addChildObjectToTree(obj);

			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("New Procedure");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object obj = getVM().getObjectRepository().createObject(
						getSelectedTreeField().getTarget(),
						ObjectType.PROCEDURE);

				addChildObjectToTree(obj);

			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("Paste");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				_Object newOwner = getSelectedTreeField().getTarget();

				FieldSelection selection = objectSelection.getObject();

				if (selection != null) {
					String fieldName = selection.fieldName;
					_Object oldOwner = selection.object;
					_Object child = oldOwner.getChild(fieldName);
					boolean ownership = oldOwner.owns(child);

					oldOwner.removeField(fieldName);
					newOwner.setField(fieldName, child, ownership);

					addChildObjectToTree(child);

				}

			}

		});
		menu.add(eMenuItem);

		eMenuItem = new JMenuItem("Show in list layout");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Field field = getSelectedTreeField();
				// set layout attribute
				StringValue sv = (StringValue) VirtualMachine
						.getInstance()
						.getObjectRepository()
						.createObject(ObjectType.STRING,
								ObjectScope.ExecutionContext);
				sv.setValue(ObjectLayout.List.toString());
				getSelectedTreeField().getTarget().setSystemField(
						_Object.OBJECT_LAYOUT, sv, true);

				// refresh UI
				loadDiagram(field);
			}

		});
		menu.add(eMenuItem);

		return menu;
	}

	private void initDiagramPanel() {
		JPopupMenu diagramObjectPopupMenu = createObjectPopupMenu();
		JPopupMenu backgroundPopupMenu = createBackgroundPopupMenu();

		diagramPanel = new SVGDiagramPanel(this, diagramObjectPopupMenu,
				backgroundPopupMenu);

		// diagram.setMinimumSize(new Dimension(1000, 1000));
		// addMouseListener();

		// diagramPanel;

		// Dimension minimumSize = new Dimension(200, 150);
		// scrollDiagramPanel.setMinimumSize(minimumSize);

		// diagramPanel.setComponentPopupMenu(diagramPopupMenu);

	}

	private Field addChildObjectToTree(_Object obj) {

		// add to tree
		// int index = obj.getOwner().getChildIndex(obj);
		Field f = obj.getOwnerField();

		Element background = diagramPanel.getScene().getDocument()
				.getElementById("background");
		SVGMatrix mat = ((SVGLocatable) background).getScreenCTM();
		Point screenPosition = diagramPanel.getPopupMenuPosition();

		SVGOMPoint elePosition = SVGUtils.screenToElement(mat, screenPosition);

		f.setStartPosition(elePosition.getX() / f.svgScale, elePosition.getY()
				/ f.svgScale);

		treeModel.insertNodeInto(new DefaultMutableTreeNode(f),
				getSelectedTreeNode(), getSelectedTreeNode().getChildCount());

		diagramPanel.reload();

		return f;
	}

	public void load(String runtimeFileName, String userFileName) {

		getVM().loadFromImage(runtimeFileName, userFileName);

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

		Field activeChildField = getActiveChildField();
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
		Point p = CanvasUtils.scaleBack(mousePos, getSelectedTreeField()
				.getTarget().scaleRate);

		_Object obj = field.getTarget();
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

			diagramPanel.reload();
		} else if ((sameActiveChildField && activeChildField != null && oldStatus != newStatus)) {
			if (logger.isDebugEnabled())
				logger.debug(String.format("status changed from %s to %s",
						oldStatus, newStatus));
			diagramPanel.repaint();
		}
	}

	public void setDiagramScaleRate(float diagramScaleRate) {
		// this.diagramScaleRate =diagramScaleRate;
		getSelectedTreeField().getTarget().scaleRate = diagramScaleRate;
		diagramPanel.reload();
	}

	private Field getActiveChildField() {
		return diagramPanel.getpopupTargetField();
	}

	public JFrame getOwner() {
		return owner;
	}
}
