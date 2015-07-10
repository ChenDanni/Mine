package edu.nju.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import edu.nju.model.impl.UpdateMessage;

public class MarkedMineLabel extends JLabel implements Observer{

	private int markedMineNumber;
	public boolean clientSourse = false;
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		UpdateMessage updateMessage = (UpdateMessage) arg;
		if (updateMessage.getKey().equals("markedMine")) {
			if (!clientSourse) {
				int markedNums = (Integer) updateMessage.getValue();
				this.setMarkedMineNum(markedNums);
				this.setText(markedNums + "");
//				System.out.println("====================================================add one");
			}
		}
		if (updateMessage.getKey().equals("clientMarkedMine")) {
			if (clientSourse) {
				int clientMarkedMines = (Integer) updateMessage.getValue();
				this.setMarkedMineNum(clientMarkedMines);
//				System.out.println("=======================================client add one");
				this.setText(clientMarkedMines + "");
			}
			
		}
		
		////////////////
		//System.out.println(updateMessage.getKey()+"...............");
		
	}
	public int getMarkedMineNum(){
		return markedMineNumber;
	}
	public void setMarkedMineNum(int markedMineNum){
		this.markedMineNumber = markedMineNum;
	}
	
	public void setClientSourse(boolean clientSourse){
		this.clientSourse = clientSourse;
	}
}
