package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.GameModelService;

public class StartGameOperation extends MineOperation{

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		GameModelService game = OperationQueue.getGameModel();
		
//		game.setGameLevel("小");
		game.startGame();
	}

	@Override
	public boolean setClientSourse(boolean ClientSourse) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getClientSourse() {
		// TODO Auto-generated method stub
		return false;
	}

}
