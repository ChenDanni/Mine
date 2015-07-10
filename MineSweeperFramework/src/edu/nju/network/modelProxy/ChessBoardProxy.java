package edu.nju.network.modelProxy;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.DoubleClickOperation;
import edu.nju.controller.msgqueue.operation.LeftClickOperation;
import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.msgqueue.operation.RightClickOperation;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.network.client.ClientService;

/*
 * 代理棋盘
 */
public class ChessBoardProxy extends ModelProxy implements ChessBoardModelService{
	
//	ChessBoardProxy chess = (ChessBoardProxy) OperationQueue.getChessBoardModel();
	public boolean ClientSourse = true;
	
	public ChessBoardProxy(ClientService client){
		this.net = client;
	}

	@Override
	public boolean initialize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excavate(int x, int y) {
		// TODO Auto-generated method stub
		MineOperation op = new LeftClickOperation(x, y);
		op.setClientSourse(true);
		net.submitOperation(op);
//		chess.excavate(x, y);
	
		return true;
	}

	@Override
	public boolean mark(int x, int y) {
		// TODO Auto-generated method stub
		MineOperation op = new RightClickOperation(x, y);
		op.setClientSourse(true);
		net.submitOperation(op);
		return true;
	}

	@Override
	public boolean quickExcavate(int x, int y) {
		// TODO Auto-generated method stub
		MineOperation op = new DoubleClickOperation(x, y);
		op.setClientSourse(true);
		net.submitOperation(op);
		return true;
	}

	@Override
	public void setGameModel(GameModelService gameModel) {
		// TODO Auto-generated method stub
	}

}
