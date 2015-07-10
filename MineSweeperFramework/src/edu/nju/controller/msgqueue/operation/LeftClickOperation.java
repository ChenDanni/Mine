package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.ChessBoardModelService;

public class LeftClickOperation extends MineOperation{
	private int x;
	private int y;
	public LeftClickOperation(int x ,int y){
		this.x = x;
		this.y = y;
	}
	
	private boolean ClientSourse;

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ChessBoardModelService chess = OperationQueue.getChessBoardModel(); //保护impl中的操作
		chess.excavate(x, y);
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
