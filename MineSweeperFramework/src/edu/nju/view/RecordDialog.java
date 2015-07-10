/*
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.nju.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import edu.nju.model.data.FileReaders;
import edu.nju.model.data.FileWriters;

public class RecordDialog {

	/**
	 *  
	 */
	public RecordDialog(JFrame parent) {
		super();
		initialization(parent);
	}

	public boolean show(String[] names, String[] score) {
		clear = false;
		this.names = names;
		this.score = score;
		dialog.setVisible(true);
		return clear;
	}
	
	public void show(){
		//dialog.setVisible(true);
		String[] percent = new String[4];
		int[] temp = new int[2];
		temp = new FileReaders().readFlie(new File("save小"));
		percent[0] = String.valueOf(temp[0])+"/"+String.valueOf(temp[1]);
		temp = new FileReaders().readFlie(new File("save中"));
		percent[1] = String.valueOf(temp[0])+"/"+String.valueOf(temp[1]);
		temp = new FileReaders().readFlie(new File("save大"));
		percent[2] = String.valueOf(temp[0])+"/"+String.valueOf(temp[1]);
		temp = new FileReaders().readFlie(new File("save自定义"));
		percent[3] = String.valueOf(temp[0])+"/"+String.valueOf(temp[1]);
		
		this.names = new String[]{"Easy","Hard","Hell","Custom"};
		this.score = new String[]{percent[0],percent[1],percent[2],percent[3]};
		show(names,score);
	}

	private void initialization(JFrame parent) {

		dialog = new JDialog(parent, "record", true);

		okBtn = new JButton("ok");
		okBtn.setFont(new Font("Monospaced", Font.PLAIN, 12));
		okBtn.setBounds(100, 155, 70, 23);
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});

		clearBtn = new JButton("clear");
		clearBtn.setFont(new Font("Monospaced", Font.PLAIN, 12));
		clearBtn.setBounds(192, 155, 70, 23);
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear = true;
				int length = names.length;
				for (int i = 0; i != length; ++i) {
					score[i] = "0/0";
					//把数据都清空
					new FileWriters().writeFile(new File("save小"), 0, 0);
					new FileWriters().writeFile(new File("save中"), 0, 0);
					new FileWriters().writeFile(new File("save大"), 0, 0);
					new FileWriters().writeFile(new File("save自定义"), 0, 0);
				}
				textPanel.repaint();
			}
		});

		line = new JSeparator();
		line.setBounds(20, 130, 240, 4);

		panel = new JPanel();
		panel.setLayout(null);

		textPanel = new DescribeTextPanel();
		panel.add(textPanel);

		panel.add(okBtn);
		panel.add(clearBtn);
		panel.add(line);

		dialog.setContentPane(panel);
		dialog.setBounds(parent.getLocation().x + 50,
				parent.getLocation().y + 50 + 50, 290, 240);

		clear = false;

	}

	private class DescribeTextPanel extends JPanel {

		DescribeTextPanel() {
			super();
			setBounds(0, 0, 290, 100);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setFont(new Font("Monospaced", Font.PLAIN, 12));
			int length = names.length;
//			System.out.println("!!!!!!!! length "+length);
			for (int i = 0; i != length; i++) {
				//System.out.println(names[i]);
				g.drawString(names[i], 20, 25 * (i + 1));
				g.drawString(String.valueOf(score[i]),150, 25 * (i + 1));
			}
		}
	}

  	private JDialog dialog;

	private JPanel panel;

	private JButton okBtn;

	private JButton clearBtn;

	private JSeparator line;

	private String names[];

	private String score[];

	private JPanel textPanel;

	boolean clear;
}