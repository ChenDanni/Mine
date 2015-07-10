package edu.nju.model.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.ParameterModelService;

public class ParameterModelImpl extends BaseModel implements ParameterModelService{
	
	private int maxMine;
	public int mineNum;
	private int markedmines;
	private int clientMarkedMines;
	public boolean clientSourse = false;
	private int marked;
	public static int temp;
	String message;

	@Override
	public boolean setMineNum(int num) {
		// TODO Auto-generated method stub
		mineNum = num;
		maxMine = num;
		markedmines = 0;
		clientMarkedMines = 0;
		//clientMarkedMines = 0;
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		super.updateChange(new UpdateMessage("markedMine", markedmines));
		super.updateChange(new UpdateMessage("clientMarkedMine", clientMarkedMines));
		return true;
	}

	@Override
	public boolean addMineNum() {
		// TODO Auto-generated method stub
		mineNum++;
	
		if(mineNum>maxMine){
			mineNum--;
			if (OperationQueue.nowOperation.getClientSourse()) {
				clientMarkedMines++;
			}else {
				markedmines++;
			}
			return false;
		}
		if (OperationQueue.nowOperation.getClientSourse()) {
			clientMarkedMines--;
			message = "clientMarkedMine";
			marked = clientMarkedMines;
		}else {
			markedmines--;
			message = "markedMine";
			marked = markedmines;
		}
		temp = mineNum;
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		super.updateChange(new UpdateMessage(message, marked));
		return true;
	}

	@Override
	public boolean minusMineNum() {
		// TODO Auto-generated method stub
		mineNum--;
		
		if(mineNum<0){
			mineNum++;
			if (OperationQueue.nowOperation.getClientSourse()) {
				clientMarkedMines--;
			}else {
				markedmines--;
			}
			return false;
		}
		if (OperationQueue.nowOperation.getClientSourse()) {
			clientMarkedMines++;
			message = "clientMarkedMine";
			marked = clientMarkedMines;
		}else {
			markedmines++;
			message = "markedMine";
			marked = markedmines;
		}
		
		temp = mineNum;
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		super.updateChange(new UpdateMessage(message, marked));
		return true;
	}
	
	public int getMineNum(){
		return mineNum;
	}
	public int getClientMine(){
		return clientMarkedMines;
	}

}
