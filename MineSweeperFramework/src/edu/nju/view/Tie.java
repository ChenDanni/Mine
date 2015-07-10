package edu.nju.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tie {
	public JFrame frame;
	public JFrame ui;
	
	public Tie(JFrame ui){
		this.ui = ui;
	}
	
	public void newTie(){
		frame = new JFrame();
		JLabel label = new JLabel("   Tie~~");
		JButton button = new JButton("I see");
		
		button.addActionListener(new buttonListener());
		
		frame.getContentPane().add(BorderLayout.CENTER,label);
		frame.getContentPane().add(BorderLayout.SOUTH, button);
		
		frame.setSize(200, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(ui.getLocation().x+150,ui.getLocation().y+100);
	
	}
	
	public class buttonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			frame.dispose();
		}
		
	}



}
