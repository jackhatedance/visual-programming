package com.bluesky.visualprogramming.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.vm.VirtualMachine;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

public class ObjectPropertyDialog extends JDialog {
	
	
	private final JPanel contentPanel = new JPanel();

	private _Object object;
	private boolean updated = false;

	private JTextField textFieldId;
	private JTextField textFieldName;
	private JTextArea textAreaValue;
	private JTextField textFieldType;
	private JLabel lblNewLabel;
	private JLabel lblName;
	private JLabel lblValue;
	private JLabel label;
	private JLabel lblColor;
	private JButton btnSetBorderColor;

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
		setBounds(100, 100, 583, 420);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{107, 219, 0};
		gbl_contentPanel.rowHeights = new int[] {45, 45, 45, 68, 45, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			textFieldName = new JTextField();
			textFieldName.setColumns(10);
		}
		{
			lblNewLabel = new JLabel("ID");
		}
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
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
		gbc_textFieldId.gridy = 0;
		contentPanel.add(textFieldId, gbc_textFieldId);
		{
			lblName = new JLabel("Name");
		}
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.fill = GridBagConstraints.BOTH;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 1;
		contentPanel.add(lblName, gbc_lblName);
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.fill = GridBagConstraints.BOTH;
		gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 1;
		contentPanel.add(textFieldName, gbc_textFieldName);
		{
			lblValue = new JLabel("Type");
		}
		GridBagConstraints gbc_lblValue = new GridBagConstraints();
		gbc_lblValue.fill = GridBagConstraints.BOTH;
		gbc_lblValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblValue.gridx = 0;
		gbc_lblValue.gridy = 2;
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
		gbc_textFieldType.gridy = 2;
		contentPanel.add(textFieldType, gbc_textFieldType);
		{
			label = new JLabel("Value");
		}
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 3;
		contentPanel.add(label, gbc_label);
		{
			lblColor = new JLabel("Color");
		}
		{
			textAreaValue = new JTextArea();
			textAreaValue.setLineWrap(true);
		}
		GridBagConstraints gbc_textAreaValue = new GridBagConstraints();
		gbc_textAreaValue.fill = GridBagConstraints.BOTH;
		gbc_textAreaValue.insets = new Insets(0, 0, 5, 0);
		gbc_textAreaValue.gridx = 1;
		gbc_textAreaValue.gridy = 3;
		contentPanel.add(textAreaValue, gbc_textAreaValue);
		GridBagConstraints gbc_lblColor = new GridBagConstraints();
		gbc_lblColor.fill = GridBagConstraints.BOTH;
		gbc_lblColor.insets = new Insets(0, 0, 0, 5);
		gbc_lblColor.gridx = 0;
		gbc_lblColor.gridy = 4;
		contentPanel.add(lblColor, gbc_lblColor);
		{
			btnSetBorderColor = new JButton("New button");
			btnSetBorderColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Color newColor = JColorChooser.showDialog(
							ObjectPropertyDialog.this,
							"Choose Background Color", object.borderColor);
					if (newColor != null) {
						btnSetBorderColor.setForeground(newColor);
					}
				}
			});
		}
		GridBagConstraints gbc_btnSetBorderColor = new GridBagConstraints();
		gbc_btnSetBorderColor.fill = GridBagConstraints.BOTH;
		gbc_btnSetBorderColor.gridx = 1;
		gbc_btnSetBorderColor.gridy = 4;
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
								.destroyObject(object);
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

	public void setObject(_Object obj) {
		this.object = obj;

		textFieldId.setText(String.valueOf(obj.getId()));
		textFieldName.setText(obj.getName());
		textFieldType.setText(obj.getType().toString());
		textAreaValue.setText(obj.getValue());
		btnSetBorderColor.setForeground(obj.borderColor);
	}

	private void updateObject() {

		String oldName = object.getName();
		String newName = textFieldName.getText();

		if (!oldName.equals(newName))
			object.getOwner().renameField(oldName, newName);

		object.setName(newName);
		object.setValue(textAreaValue.getText());
		object.borderColor = btnSetBorderColor.getForeground();

	}

	public boolean isUpdated() {
		return updated;
	}
}
