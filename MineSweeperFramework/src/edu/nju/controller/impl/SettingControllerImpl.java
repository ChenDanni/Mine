package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.service.SettingControllerService;
import edu.nju.model.service.GameModelService;

public class SettingControllerImpl implements SettingControllerService{
	
	@Override
	public boolean setEasyGameLevel() {
		// TODO Auto-generated method stub
		OperationQueue.getGameModel().setGameLevel("小");
		return true;
	}

	@Override
	public boolean setHardGameLevel() {
		// TODO Auto-generated method stub
		OperationQueue.getGameModel().setGameLevel("中");
		return true;
	}

	@Override
	public boolean setHellGameLevel() {
		OperationQueue.getGameModel().setGameLevel("大");
		return true;
	}

	@Override
	public boolean setCustomizedGameLevel(int height, int width, int nums) {
		// TODO Auto-generated method stub
		OperationQueue.getGameModel().setGameLevel("自定义");
		OperationQueue.getGameModel().setGameSize(width, height, nums);
		return true;
	}

}
