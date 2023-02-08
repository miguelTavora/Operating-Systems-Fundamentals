

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DesignerGui {

	private JFrame frame;
	private JTextArea receivedMessagesArea;

	
	public DesignerGui(String windownName) {
		createLayout(windownName);
	}
	

	private void createLayout(String windowName) {
		frame = new JFrame();
		frame.setBounds(100, 350, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle(windowName);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		});
		
		// adiciona uma textArea com scroll
		JScrollPane sbrText = new JScrollPane();
		sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sbrText.setBounds(20, 20, 395, 220);
		receivedMessagesArea = new JTextArea();
		receivedMessagesArea.setEditable(false);
		sbrText.setViewportView(receivedMessagesArea);
		frame.getContentPane().add(sbrText);
	}
	
	public void setVisible() {
		frame.setVisible(true);
	}
	
	public void setTextToTextArea(String txt) {
		receivedMessagesArea.setText(txt);
	}
	
	public String getTextFromTextArea() {
		return receivedMessagesArea.getText();
	}
}
