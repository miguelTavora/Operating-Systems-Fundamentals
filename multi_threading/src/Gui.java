

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;

public class Gui {
	
	private App app;

	private JFrame frame;
	
	//botão para ligar o robo
	private JRadioButton turnOnBtn;
	
	//valores de distância e raio
	private JTextField distanceField;
	private JTextField radiusField;
	
	//botões das formas geometricas
	private JButton squareBtn;
	private JButton circleBtn;
	
	//textfield do outout
	private JTextField outputField;
	private JTextField robotName;
	
	
	
	public Gui(App app) {
		this.app = app;
		createLayout();
		
	}
	
	public void setVisible() {
		frame.setVisible(true);
	}
	
	//escrever os comandos no campo do output
	public void setTextToOutput(String txt) {
		outputField.setText(txt);
	}
	
	public String getDistance() {
		if(distanceField.getText().matches("[0-9]+") && Integer.parseInt(distanceField.getText()) < 1001)
			return distanceField.getText();
		
		else if(Integer.parseInt(distanceField.getText()) > 1001) {
			JOptionPane.showMessageDialog(frame,
					"O valor da distância não pode ser superior a 1000", "Valores errados",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		else {
			JOptionPane.showMessageDialog(frame,
					"O valor da distância não é um número", "Valores errados",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		
	}
	
	public String getRadius() {
		if(radiusField.getText().matches("[0-9]+") && Integer.parseInt(radiusField.getText()) < 1001)
			return radiusField.getText();
		
		else if(Integer.parseInt(radiusField.getText()) > 1001) {
			JOptionPane.showMessageDialog(frame,
					"O valor da distância não pode ser superior a 1000", "Valores errados",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		else {
			JOptionPane.showMessageDialog(frame,
					"O valor da distância não é um número", "Valores errados",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}
	
	public String getRobotName() {
		return robotName.getText();
	}
	
	public void setOpen(boolean flag) {
		turnOnBtn.setSelected(flag);
	}
	
	public void setButtonsActive(boolean flag) {
		squareBtn.setEnabled(flag);
		circleBtn.setEnabled(flag);
	}

	// cria o layout
	private void createLayout() {
		frame = new JFrame();
		frame.setBounds(100, 100, 406, 275);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(170, 170, 170));
		frame.setTitle("Aplicação");

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				app.exitApp();
				frame.dispose();
				System.exit(0);
			}
		});

		squareBtn = new JButton("Quadrado");
		squareBtn.setBounds(27, 123, 163, 38);
		frame.getContentPane().add(squareBtn);
		
		
		circleBtn = new JButton("Circulo");
		circleBtn.setBounds(225, 123, 148, 38);
		frame.getContentPane().add(circleBtn);
		
		
		
		turnOnBtn = new JRadioButton("Ligar");
		turnOnBtn.setBounds(326, 7, 58, 23);
		frame.getContentPane().add(turnOnBtn);
		
		
		JLabel outputTxt = new JLabel("Output");
		outputTxt.setFont(new Font("Tahoma", Font.PLAIN, 13));
		outputTxt.setBounds(27, 172, 46, 14);
		frame.getContentPane().add(outputTxt);
		
		outputField = new JTextField();
		outputField.setBounds(27, 197, 349, 28);
		outputField.setEditable(false);
		frame.getContentPane().add(outputField);
		
		distanceField = new JTextField();
		distanceField.setBounds(27, 92, 163, 20);
		frame.getContentPane().add(distanceField);
		
		JLabel lblNewLabel = new JLabel("Comprimento:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(27, 67, 86, 14);
		frame.getContentPane().add(lblNewLabel);
		
		radiusField = new JTextField();
		radiusField.setBounds(225, 92, 148, 20);
		frame.getContentPane().add(radiusField);
		
		
		JLabel lblRaio = new JLabel("Raio:");
		lblRaio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRaio.setBounds(225, 67, 33, 14);
		frame.getContentPane().add(lblRaio);
		
		robotName = new JTextField();
		robotName.setBounds(104, 8, 86, 20);
		robotName.setText("EV8");
		frame.getContentPane().add(robotName);
		
		JLabel lblRobotName = new JLabel("Robot Name");
		lblRobotName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRobotName.setBounds(27, 11, 76, 14);
		frame.getContentPane().add(lblRobotName);
		
		//listener para quando se liga
		actionOnTurnOn();
		
		//action Listeners on click of forms
		actionOnSquare();
		actionOnCircle();
	}
	
	
	//listener para o click do radiobutton ligar
	private void actionOnTurnOn() {
		turnOnBtn.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if(turnOnBtn.isSelected()) 
	        		app.openCommunication();
	        	
	        	else 
	        		app.closeCommunication();
	        	
	        }
	    });
	}
	
	//listener para o butão de desenhar quadrado
	private void actionOnSquare() {
		squareBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//app.drawSquare();
				app.stateMachine(app.STATE_SQUARE);
			}
		});
	}
	
	//listener para o butão de desenhar circulo
	private void actionOnCircle() {
		circleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//app.drawCircle();
				app.stateMachine(app.STATE_CIRCLE);
			}
		});
	}
	
	
}
