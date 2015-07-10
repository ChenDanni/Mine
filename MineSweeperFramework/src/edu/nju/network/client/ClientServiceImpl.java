package edu.nju.network.client;

import edu.nju.controller.msgqueue.operation.MineOperation;

public class ClientServiceImpl extends ClientService{

	@Override
	public void submitOperation(MineOperation op) {
		// TODO Auto-generated method stub
		ClientAdapter.write(op); //客户端传的是操作
//		System.out.println("here!!!!!!!!!!!!!!!!!!3");
	}

}
