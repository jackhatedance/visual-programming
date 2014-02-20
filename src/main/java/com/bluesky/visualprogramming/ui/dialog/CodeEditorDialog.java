package com.bluesky.visualprogramming.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;

public class CodeEditorDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	RSyntaxTextArea textArea;
	RTextScrollPane sp;
	
	private boolean updated=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CodeEditorDialog dialog = new CodeEditorDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CodeEditorDialog() {
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			 textArea = new RSyntaxTextArea(20, 60);
			  textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		      textArea.setCodeFoldingEnabled(true);
		      textArea.setAntiAliasingEnabled(true);
		      contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		        sp = new RTextScrollPane(textArea);
			contentPanel.add(sp);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						updated=true;
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						updated = false;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public void setCode(String code){
		textArea.setText(code);
		textArea.setCaretPosition(0);
		
	}
	public boolean isUpdated(){
		return updated;
	}
	public String getCode(){
		return textArea.getText();
	}

}
