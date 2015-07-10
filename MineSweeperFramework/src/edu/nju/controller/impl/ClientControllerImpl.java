package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.service.ClientControllerService;
import edu.nju.network.client.ClientInHandlerImpl;
import edu.nju.network.client.ClientServiceImpl;
import edu.nju.network.modelProxy.ChessBoardProxy;
import edu.nju.network.modelProxy.GameModelProxy;
import edu.nju.network.modelProxy.MarkedMineProxy;
import edu.nju.network.modelProxy.ParameterProxy;
import edu.nju.view.MainFrame;
import edu.nju.view.MarkedMineLabel;
import edu.nju.view.MineNumberLabel;

public class ClientControllerImpl implements ClientControllerService{
	
	MainFrame ui;
	
	public ClientControllerImpl(MainFrame ui){
		this.ui = ui;
	}

	@Override
	public boolean setupClient(String ip) {
		// TODO Auto-generated method stub
		try {
			ClientServiceImpl client = new ClientServiceImpl();
			ClientInHandlerImpl clientH = new ClientInHandlerImpl();
			
			GameModelProxy gameProxy = new GameModelProxy(client);
			ChessBoardProxy chessProxy = new ChessBoardProxy(client);
			MarkedMineProxy markedMineProxy = new MarkedMineProxy(client);
			ParameterProxy mineLabelProxy = new ParameterProxy(client);
			OperationQueue.gameModel = gameProxy;  //
			OperationQueue.chessBoard = chessProxy;
			
			ui.getMarkedMineLabel().setClientSourse(true);
			
			clientH.addObserver(gameProxy);
			clientH.addObserver(chessProxy); 
			clientH.addObserver(mineLabelProxy); //从客户端传到代理
		    clientH.addObserver(markedMineProxy);
			chessProxy.addObserver(ui.getMineBoard());
			gameProxy.addObserver(ui);
			mineLabelProxy.addObserver(ui.getMineNumberLabel());
			markedMineProxy.addObserver(ui.getMarkedMineLabel());
			
			OperationQueue.ifConnected = true;
			ui.clientSourse = true;
			
			client.init(ip, clientH);
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

}
