package edu.nju.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Lose {
	JFrame frame;
	JFrame ui;
	
	public Lose(JFrame ui){
		this.ui = ui;
	}
	
	public void newLose(){
		frame = new JFrame();
		JLabel label = new JLabel("Sorry,you lose.");
		JButton button = new JButton("I see");
		
		frame.getContentPane().add(BorderLayout.CENTER,label);
		frame.getContentPane().add(BorderLayout.SOUTH, button);
		
		button.addActionListener(new buttonListener());
		
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(ui.getLocation().x+150,ui.getLocation().y+100);
		
		frame.setSize(200, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	
	}
	
	public class buttonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			frame.dispose();
		}
	}
}
