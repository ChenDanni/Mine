package edu.nju.network.modelProxy;

import edu.nju.model.service.ParameterModelService;
import edu.nju.network.client.ClientService;

public class MarkedMineProxy extends ModelProxy implements ParameterModelService{
	
	public MarkedMineProxy(ClientService client){
		this.net = client;
	}
	
	
	@Override
	public boolean setMineNum(int num) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean minusMineNum() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addMineNum() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int getMineNum() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getClientMine() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
