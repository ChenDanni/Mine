package edu.nju.model.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sun.swing.UIAction;
import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.data.FileReaders;
import edu.nju.model.data.FileWriters;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.GameVO;

public class GameModelImpl extends BaseModel implements GameModelService{
	
	private StatisticModelService statisticModel;
	private ChessBoardModelService chessBoardModel; //！
	
	private List<GameLevel> levelList;
	
	private GameState gameState;
	private int width;
	private int height;
	private int mineNum;
	private String level = "小";
	
	private GameResultState gameResultStae;
	private int time;
	
	private long startTime;

	public GameModelImpl(StatisticModelService statisticModel, ChessBoardModelService chessBoardModel){
		this.statisticModel = statisticModel;
		this.chessBoardModel = chessBoardModel;
		gameState = GameState.OVER;
		
		chessBoardModel.setGameModel(this);
		
		levelList = new ArrayList<GameLevel>();
		levelList.add(new GameLevel(0,"大",30,16,99));
		levelList.add(new GameLevel(1,"中",16,16,40));
		levelList.add(new GameLevel(2,"小",9,9,10));
	}

	@Override
	public boolean startGame() {
		// TODO Auto-generated method stub
		gameState = GameState.RUN;
		startTime = Calendar.getInstance().getTimeInMillis();
		
		
//		System.out.println("!!!!!!!!!!!!!!!!!!!!"+level);
		
		GameLevel gl = null;
		for(GameLevel tempLevel : levelList){
			if(tempLevel.getName().equals(level)){
				gl = tempLevel;
				break;
			}
		}
		if(gl == null&&width==0&&height == 0)
			gl = levelList.get(2);
		
		if(gl != null){
			height = gl.getWidth();
			width = gl.getHeight();
			mineNum = gl.getMineNum();
		}
		
		this.chessBoardModel.initialize(width, height, mineNum);
		
		super.updateChange(new UpdateMessage("start",this.convertToDisplayGame()));
		return true;
	}
	
	@Override
	public boolean gameOver(GameResultState result) {
		// TODO Auto-generated method stub
		
		this.gameState = GameState.OVER;
		this.gameResultStae = result;
		this.time = (int)(Calendar.getInstance().getTimeInMillis() - startTime)/1000;//时间在这里
		
		this.statisticModel.recordStatistic(result, time);
		
		File file = new File("save小");
		int[] winAtotal = new int[2];
		switch (level) {
		case "大":
			file = new File("save大");
			break;
		case "中":
			file = new File("save中");
			break;
		case "小":
			file = new File("save小");
			break;
		case "自定义":
			file = new File("save自定义");
			break;
		default:
			break;
		}
		
		if (gameResultStae==GameResultState.FAIL) {
			if (!OperationQueue.ifConnected) {
//				System.out.println("m===========================================in");
				System.out.println(file.getName());
				
				winAtotal = new FileReaders().readFlie(file);
				winAtotal[1]++;
				new FileWriters().writeFile(file, winAtotal[0], winAtotal[1]);
			}
			if (OperationQueue.nowOperation.getClientSourse()) {
				super.updateChange(new UpdateMessage("clientEnd", this.convertToDisplayGame()));
			}else {
				super.updateChange(new UpdateMessage("end",this.convertToDisplayGame()));
			}	
		}else if(gameResultStae==GameResultState.SUCCESS){
			if (!OperationQueue.ifConnected) {
				winAtotal = new FileReaders().readFlie(file);
				winAtotal[0]++;
				winAtotal[1]++;
				new FileWriters().writeFile(file, winAtotal[0], winAtotal[1]);
			}
			if (OperationQueue.nowOperation.getClientSourse()) {
				super.updateChange(new UpdateMessage("clientWin", this.convertToDisplayGame()));
			}else {
				super.updateChange(new UpdateMessage("win",this.convertToDisplayGame()));	
			}
		}else if (gameResultStae == GameResultState.TIE) {
			super.updateChange(new UpdateMessage("tie", this.convertToDisplayGame()));
		}
			
		return false;
	}

	@Override
	public boolean setGameLevel(String level) {
		// TODO Auto-generated method stub
		//输入校验
		this.level = level;
		return true;
	}

	@Override
	public boolean setGameSize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		//输入校验
		this.width = width;
		this.height = height;
		this.mineNum = mineNum;
		return true;
	}
	
	private GameVO convertToDisplayGame(){
		return new GameVO(gameState, width, height,level, gameResultStae, time);
	}

	@Override
	public List<GameLevel> getGameLevel() {
		// TODO Auto-generated method stub
		return this.levelList;
	}
}