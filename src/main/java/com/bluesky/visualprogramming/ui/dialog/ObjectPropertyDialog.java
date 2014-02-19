package com.bluesky.visualprogramming.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.vm.VirtualMachine;
import javax.swing.JScrollPane;

public class ObjectPropertyDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private Field field;
	private _Object object;

	private boolean updated = false;

	private JTextField textFieldId;
	private JTextField textFieldName;
	private JTextArea textAreaValue;
	private JScrollPane scrollPaneValue;
	private JTextField textFieldType;
	private JLabel lblNewLabel;
	private JLabel lblFieldName;
	private JLabel lblValue;
	private JLabel label;
	private JLabel lblColor;
	private JButton btnSetBorderColor;
	private JLabel lblName;
	private JTextField textFieldFieldName;
	private JLabel lblOwner;
	private JTextField textFieldOwner;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ObjectPropertyDialog dialog = new ObjectPropertyDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ObjectPropertyDialog() {
		setModal(true);
		setTitle("Object Property");
		setBounds(100, 100, 747, 706);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {107};
		gbl_contentPanel.rowHeights = new int[] { 0, 45, 45, 49, 45, 68, 45, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0 };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			textFieldName = new JTextField();
			textFieldName.setColumns(10);
		}
		{
			lblNewLabel = new JLabel("ID");
		}
		{
			lblOwner = new JLabel("Owner");
			GridBagConstraints gbc_lblOwner = new GridBagConstraints();
			gbc_lblOwner.fill = GridBagConstraints.VERTICAL;
			gbc_lblOwner.insets = new Insets(0, 0, 5, 5);
			gbc_lblOwner.gridx = 0;
			gbc_lblOwner.gridy = 0;
			contentPanel.add(lblOwner, gbc_lblOwner);
		}
		{
			textFieldOwner = new JTextField();
			textFieldOwner.setEditable(false);
			textFieldOwner.setColumns(10);
			GridBagConstraints gbc_textFieldOwner = new GridBagConstraints();
			gbc_textFieldOwner.insets = new Insets(0, 0, 5, 0);
			gbc_textFieldOwner.fill = GridBagConstraints.BOTH;
			gbc_textFieldOwner.gridx = 1;
			gbc_textFieldOwner.gridy = 0;
			contentPanel.add(textFieldOwner, gbc_textFieldOwner);
		}
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		{
			textFieldId = new JTextField();
			textFieldId.setEditable(false);
			textFieldId.setColumns(10);
		}
		GridBagConstraints gbc_textFieldId = new GridBagConstraints();
		gbc_textFieldId.fill = GridBagConstraints.BOTH;
		gbc_textFieldId.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldId.gridx = 1;
		gbc_textFieldId.gridy = 1;
		contentPanel.add(textFieldId, gbc_textFieldId);
		{
			lblFieldName = new JLabel("Name");
		}
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.fill = GridBagConstraints.VERTICAL;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 2;
		contentPanel.add(lblFieldName, gbc_lblName);
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.fill = GridBagConstraints.BOTH;
		gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 2;
		contentPanel.add(textFieldName, gbc_textFieldName);
		{
			lblValue = new JLabel("Type");
		}
		{
			lblName = new JLabel("Field Name");
			GridBagConstraints gbc_lblFieldName = new GridBagConstraints();
			gbc_lblFieldName.insets = new Insets(0, 0, 5, 5);
			gbc_lblFieldName.gridx = 0;
			gbc_lblFieldName.gridy = 3;
			contentPanel.add(lblName, gbc_lblFieldName);
		}
		{
			textFieldFieldName = new JTextField();
			textFieldFieldName.setColumns(10);
			GridBagConstraints gbc_textFieldFieldName = new GridBagConstraints();
			gbc_textFieldFieldName.insets = new Insets(0, 0, 5, 0);
			gbc_textFieldFieldName.fill = GridBagConstraints.BOTH;
			gbc_textFieldFieldName.gridx = 1;
			gbc_textFieldFieldName.gridy = 3;
			contentPanel.add(textFieldFieldName, gbc_textFieldFieldName);
		}
		GridBagConstraints gbc_lblValue = new GridBagConstraints();
		gbc_lblValue.fill = GridBagConstraints.VERTICAL;
		gbc_lblValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblValue.gridx = 0;
		gbc_lblValue.gridy = 4;
		contentPanel.add(lblValue, gbc_lblValue);
		{
			textFieldType = new JTextField();
			textFieldType.setEditable(false);
			textFieldType.setColumns(10);
		}
		GridBagConstraints gbc_textFieldType = new GridBagConstraints();
		gbc_textFieldType.fill = GridBagConstraints.BOTH;
		gbc_textFieldType.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldType.gridx = 1;
		gbc_textFieldType.gridy = 4;
		contentPanel.add(textFieldType, gbc_textFieldType);
		{
			label = new JLabel("Value");
		}
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.VERTICAL;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 5;
		contentPanel.add(label, gbc_label);
		{
			lblColor = new JLabel("Color");
		}
		{
			textAreaValue = new JTextArea();
			textAreaValue.setLineWrap(true);
			scrollPaneValue = new JScrollPane(textAreaValue);
		}
		GridBagConstraints gbc_textAreaValue = new GridBagConstraints();
		gbc_textAreaValue.fill = GridBagConstraints.BOTH;
		gbc_textAreaValue.insets = new Insets(0, 0, 5, 0);
		gbc_textAreaValue.gridx = 1;
		gbc_textAreaValue.gridy = 5;
		contentPanel.add(scrollPaneValue, gbc_textAreaValue);
		GridBagConstraints gbc_lblColor = new GridBagConstraints();
		gbc_lblColor.fill = GridBagConstraints.VERTICAL;
		gbc_lblColor.insets = new Insets(0, 0, 0, 5);
		gbc_lblColor.gridx = 0;
		gbc_lblColor.gridy = 6;
		contentPanel.add(lblColor, gbc_lblColor);
		{
			btnSetBorderColor = new JButton("Change Color");
			btnSetBorderColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Color newColor = JColorChooser
							.showDialog(ObjectPropertyDialog.this,
									"Choose Background Color",
									field.target.borderColor);
					if (newColor != null) {
						btnSetBorderColor.setForeground(newColor);
					}
				}
			});
		}
		GridBagConstraints gbc_btnSetBorderColor = new GridBagConstraints();
		gbc_btnSetBorderColor.fill = GridBagConstraints.BOTH;
		gbc_btnSetBorderColor.gridx = 1;
		gbc_btnSetBorderColor.gridy = 6;
		contentPanel.add(btnSetBorderColor, gbc_btnSetBorderColor);
		// contentPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new
		// Component[]{lblNewLabel, textFieldId, lblName, textFieldName,
		// lblValue, textFieldType, label, textAreaValue}));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						updateObject();
						updated = true;
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton btnDelete = new JButton("Delete");
				btnDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						VirtualMachine.getInstance().getObjectRepository()
								.destroyObject(field.target);
						dispose();
					}
				});
				buttonPane.add(btnDelete);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});

				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public void setField(Field field) {
		this.field = field;
		this.object = field.target;

		String ownerPath ="";
		if(object.hasOwner())
			ownerPath= object.getOwner().getPath();
		
		textFieldOwner.setText(ownerPath);
		textFieldId.setText(String.valueOf(object.getId()));
		textFieldName.setText(object.getName());
		textFieldFieldName.setText(field.name);
		textFieldType.setText(object.getType().toString());
		textAreaValue.setText(object.getValue());
		btnSetBorderColor.setForeground(object.borderColor);
	}

	private void updateObject() {

		String oldFieldName = field.name;
		String newFieldName = textFieldFieldName.getText();

		if (!oldFieldName.equals(newFieldName))
			object.getOwner().renameField(oldFieldName, newFieldName);

		object.setName(textFieldName.getText());
		object.setValue(textAreaValue.getText());
		object.borderColor = btnSetBorderColor.getForeground();

	}

	public boolean isUpdated() {
		return updated;
	}
}
