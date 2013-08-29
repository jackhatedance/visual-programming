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
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			lblNewLabel = new JLabel("ID");
			contentPanel.add(lblNewLabel);
		}
		{
			textFieldId = new JTextField();
			textFieldId.setEditable(false);
			contentPanel.add(textFieldId);
			textFieldId.setColumns(10);
		}
		{
			lblName = new JLabel("Name");
			contentPanel.add(lblName);
		}
		{
			textFieldName = new JTextField();
			textFieldName.setColumns(10);
			contentPanel.add(textFieldName);
		}
		{
			lblValue = new JLabel("Type");
			contentPanel.add(lblValue);
		}
		{
			textFieldType = new JTextField();
			textFieldType.setEditable(false);
			textFieldType.setColumns(10);
			contentPanel.add(textFieldType);
		}
		{
			label = new JLabel("Value");
			contentPanel.add(label);
		}
		{
			textAreaValue = new JTextArea();
			textAreaValue.setLineWrap(true);
			contentPanel.add(textAreaValue);
		}
		{
			lblColor = new JLabel("Color");
			contentPanel.add(lblColor);
		}
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
			contentPanel.add(btnSetBorderColor);
		}
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
