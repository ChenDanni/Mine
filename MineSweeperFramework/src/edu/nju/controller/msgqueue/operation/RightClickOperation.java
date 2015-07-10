package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.ChessBoardModelService;

public class RightClickOperation extends MineOperation {
	private int x;
	private int y;
	
	public RightClickOperation(int x,int y){
		this.x = x;
		this.y = y;
	}
	private boolean ClientSourse;
	
	public void execute(){
		ChessBoardModelService chess = OperationQueue.getChessBoardModel();
//		System.out.println("enter rightclickoperation");
		chess.mark(x, y);
	}

	@Override
	public boolean setClientSourse(boolean ClientSourse) {
		// TODO Auto-generated method stub
		this.ClientSourse = ClientSourse;
		return ClientSourse;
	}

	@Override
	public boolean getClientSourse() {
		// TODO Auto-generated method stub
		return ClientSourse;
	}

}
