package edu.nju.model.impl;

import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.bind.v2.TODO;

import sun.security.util.Length;
import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.po.BlockPO;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.ParameterModelService;
import edu.nju.model.state.BlockState;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.BlockVO;
import edu.nju.view.MineNumberLabel;

public class ChessBoardModelImpl extends BaseModel implements ChessBoardModelService{
	
	private GameModelService gameModel;
	public ParameterModelService parameterModel;
	
	private BlockPO[][] blockMatrix;
	
	private boolean gameover = false; 
	
	public boolean ClientSourse = false;
	
	public int clientMark = 0;    ///////加了一个
	public int mineNum = 0;
	
	public ChessBoardModelImpl(ParameterModelService parameterModel){
		this.parameterModel = (ParameterModelImpl)parameterModel;
	}
	
	public void getMineNum() {
		for (int i = 0; i < blockMatrix.length; i++) {
			for (int j = 0; j < blockMatrix[0].length; j++) {
				if (blockMatrix[i][j].isMine()) {
					mineNum++;
				}
			}
		}
	}

	@Override
	public boolean initialize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		/********************简单示例初始化方法，待完善********************/
		blockMatrix = new BlockPO[width][height];
		setBlock(mineNum);
		
		this.parameterModel.setMineNum(mineNum);
		/***********请在删除上述内容的情况下，完成自己的内容****************/
		
//		this.printBlockMatrix();
		
		return false;
	}

	@Override
	public boolean excavate(int x, int y) {
		// TODO Auto-generated method stub
		int open = 0;
		
		/********************简单示例挖开方法，待完善********************/
		if(blockMatrix == null)
			return false;
		
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		
		//不能挖开标记为雷的地方
		if ((block.getState()==BlockState.FLAG)||gameover) {
			return false;
		}
		
		block.setState(BlockState.CLICK);
		blocks.add(block);
		
		GameState gameState = GameState.RUN;
		if(block.isMine()){
			gameState = GameState.OVER;
			gameover = true;
			this.gameModel.gameOver(GameResultState.FAIL);
			//失败时要把雷全部挖开
			for (int i = 0; i < blockMatrix.length; i++) {
				for (int j = 0; j < blockMatrix[0].length; j++) {
					if (blockMatrix[i][j].isMine() || blockMatrix[i][j].getState() ==  BlockState.FLAG) {
						//blockMatrix[i][j].setState(BlockState.CLICK);
						blocks.add(blockMatrix[i][j]);
					}
				}
			}
		}
		
		if ((!block.isMine())&&(block.getMineNum()<1)) {
			
			openZeroArea(blocks,blockMatrix,x,y);
				
		}
		
		//if win?
		for (int i = 0; i < blockMatrix.length; i++) {
			for (int j = 0; j < blockMatrix[0].length; j++) {
				if (blockMatrix[i][j].getState() == BlockState.CLICK) {
					open++;
				}
			}
		}
		mineNum = 0;
		this.getMineNum();
		
//		System.out.println("mmmmmmmmaaaaaaaaaaaaaaaaaa open :"+open);
//		System.out.println(",,,,,,,,,,,,aaaaaaaaaaaaaa mine :"+mineNum);
		
		if (!OperationQueue.ifConnected) {
			if ((open == (blockMatrix.length*blockMatrix[0].length)-mineNum)&&(gameState!=GameState.OVER)){
				gameState = GameState.OVER;
				gameover = true;
				this.gameModel.gameOver(GameResultState.SUCCESS);
			}
		}
		
		
		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, gameState)));			
		/***********请在删除上述内容的情况下，完成自己的内容****************/
		return true;
	}
	//挖开的区域是0
	public void openZeroArea(List<BlockPO> blocks, BlockPO[][] blockMatrix2, int x, int y){
		//挖开mineNum为0的区域
				boolean[][] searched = new boolean[blockMatrix.length][blockMatrix[0].length];
				for (int i = 0; i < searched.length; i++) {
					for (int j = 0; j < searched[0].length; j++) {
						searched[i][j] = false;
					}
				}//初始化searched
				int head2 = blocks.size()-1;
				int head = blocks.size()-1;
				int tail = blocks.size();
				searched[x][y] = true;
				while(head<=tail){
				for (int i = (-1); i < 2; i++) {
					for (int j = (-1); j < 2; j++) {
						if(((x+i)>=0)&&((x+i)<blockMatrix.length)&&((y+j)>=0)&&((y+j)<blockMatrix[0].length)&&(blockMatrix[x+i][y+j].getState()!=BlockState.FLAG)){
						if ((blockMatrix[x+i][y+j].getMineNum()<1)&&(searched[x+i][y+j]==false)) {
							blockMatrix[x+i][y+j].setState(BlockState.CLICK);
							blocks.add(blockMatrix[x+i][y+j]);
							searched[x+i][y+j] = true;
							tail++;
						}
						}
					}
				}
				head++;
				if(head>=blocks.size()) continue;
			    x = blocks.get(head).getX();
			    y = blocks.get(head).getY();
			    }//接下来把周围的挖开
				head--;
//				System.out.println("head2:"+head2+" head:"+head);
				for (int j2 = head2; j2 < head; j2++) {
					x = blocks.get(j2).getX();
					y = blocks.get(j2).getY();
					for (int i = (-1); i < 2; i++) {
						for (int j = (-1); j < 2; j++) {
							if(((x+i)>=0)&&((x+i)<blockMatrix.length)&&((y+j)>=0)&&((y+j)<blockMatrix[0].length)&&(blockMatrix[x+i][y+j].getState()!=BlockState.FLAG)){
								blockMatrix[x+i][y+j].setState(BlockState.CLICK);
								blocks.add(blockMatrix[x+i][y+j]);
								searched[x+i][y+j] = true;
							}
						}
					}
				}
				

		
	}
	
	@Override
	public boolean mark(int x, int y) {
		// TODO Auto-generated method stub
		/********************简单示例标记方法，待完善********************/
		clientMark = parameterModel.getClientMine();
		System.out.println("---------------------enter this method"+clientMark);
		if((blockMatrix == null)||gameover)
			return false;
		
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		BlockPO block = blockMatrix[x][y];
		
		//如果是在联机转状态时,标记失败
		if (OperationQueue.ifConnected) {
			if (!blockMatrix[x][y].isMine()) {
				block.setState(BlockState.FLAG);
				gameover = true;
				this.gameModel.gameOver(GameResultState.FAIL);
				for (int i = 0; i < blockMatrix.length; i++) {
					for (int j = 0; j < blockMatrix[0].length; j++) {
						if (blockMatrix[i][j].isMine() || blockMatrix[i][j].getState() ==  BlockState.FLAG) {
							//blockMatrix[i][j].setState(BlockState.CLICK);
							blocks.add(blockMatrix[i][j]);
						}
					}
				}
				
				super.updateChange(new UpdateMessage("excute", this.getDisplayList(blocks, GameState.OVER)));
				return true;
			}
		}
		//标记完了不能标记
		BlockState state = block.getState();
		if(state == BlockState.UNCLICK){
			if (this.parameterModel.minusMineNum()) {
				if (OperationQueue.nowOperation.getClientSourse()) {
				}
				block.setState(BlockState.FLAG);
			}
			//block.setState(BlockState.FLAG);
			//this.parameterModel.minusMineNum();
		}
		else if(state == BlockState.FLAG){
			block.setState(BlockState.UNCLICK);
			if (OperationQueue.nowOperation.getClientSourse()) {
			}
			this.parameterModel.addMineNum();
		}
		
		
//		System.out.println("clientMarked issssssssssssssssssssssssssssssssssssssss:"+clientMark);
		blocks.add(block); 
		
		if (OperationQueue.ifConnected) {
			if (parameterModel.getMineNum() == 0) {
				System.out.println("ininininin");
				//////////标的雷多的胜
				gameover = true;
			if (clientMark>(mineNum-clientMark)) {
				if (OperationQueue.nowOperation.getClientSourse()) {
					this.gameModel.gameOver(GameResultState.SUCCESS);
				}else {
					this.gameModel.gameOver(GameResultState.FAIL);
				}
			}else if(clientMark<(mineNum - clientMark)){
				if (OperationQueue.nowOperation.getClientSourse()) {
					this.gameModel.gameOver(GameResultState.FAIL);
				}else {
					this.gameModel.gameOver(GameResultState.SUCCESS);
				}
			}else {
					this.gameModel.gameOver(GameResultState.TIE);
			}
			gameover = true;
			super.updateChange(new UpdateMessage("excute", this.getDisplayList(blocks, GameState.OVER)));
			return true;
			}
		}
		//TODO
		super.updateChange(new UpdateMessage("excute",this.getDisplayList(blocks, GameState.RUN)));
		/***********请在删除上述内容的情况下，完成自己的内容****************/
		
		return true;
	}

	@Override
	public boolean quickExcavate(int x, int y) {
		// TODO Auto-generated method stub
		/***********请在此处完成快速挖开方法实现****************/
		List<BlockPO> blocks = new ArrayList<BlockPO>();
		boolean reallyOver = false;
		int numOfFlag = 0;
		//统计周围一共有几个标记
		for (int i = (-1); i < 2; i++) {
			for (int j = (-1); j < 2; j++) {
				if(((x+i)>=0)&&((x+i)<blockMatrix.length)&&((y+j)>=0)&&((y+j)<blockMatrix[0].length)){
					if (blockMatrix[x+i][y+j].getState()==BlockState.FLAG) {
						numOfFlag++;
				}
			}
		}
		}
		System.out.println(numOfFlag);
		System.out.println(blockMatrix[x][y].getMineNum());
		//判断挖不挖
		if (blockMatrix[x][y].getMineNum()==numOfFlag) {
			System.out.println("ininin");
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if(((x+i)>=0)&&((x+i)<blockMatrix.length)&&((y+j)>=0)&&((y+j)<blockMatrix[0].length)){
						excavate(x+i, y+j);
						if (gameover) {
							gameover = false;
							reallyOver = true;
						}
					}
				}
			}
			if (reallyOver) {
				gameover = true;
			}
			
			super.updateChange(new UpdateMessage("excute", this.getDisplayList(blocks, GameState.RUN)));
		}else {
			return false;
		}
		
		return true;
	}

	/**
	 * 示例方法，可选择是否保留该形式
	 * 
	 * 初始化BlockMatrix中的Block，并随机设置mineNum颗雷
	 * 同时可以为每个Block设定附近的雷数
	 * @param mineNum
	 * @return
	 */
	private boolean setBlock(int mineNum){
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		
		int index = 0;
		
		gameover = false;
		
		//初始化及布雷
		for(int i = 0 ; i<width; i++){
			for (int j = 0 ; j< height; j++){
				blockMatrix[i][j] = new BlockPO(i,j);
				//放置雷，并设定block附近雷数，现有放置方法为固定方法，请添加随机实现
//				index ++;
//				if(index == 2){
//					if(mineNum>0){
//						if(i>3&&j>3){
//							blockMatrix[i-1][j-1].setMine(true);
//						
//							addMineNum(i-1,j-1);
//							mineNum--;
//						}
//					}
//					index = 0;
//				}
			}
		}
		//随机放置雷
		int randomX = 0;
		int randomY = 0;
		for (int i = 0; i < mineNum; i++) {
			randomX = (int)(Math.random()*width);
			randomY = (int)(Math.random()*height);
			if (!blockMatrix[randomX][randomY].isMine()) {
				blockMatrix[randomX][randomY].setMine(true);
				addMineNum(randomX,randomY);
			}else {
				i--;
			}
		}
		
		
		return false;
	}
	
	
	/**
	 * 示例方法，可选择是否保留该形式
	 * 
	 * 将(i,j)位置附近的Block雷数加1
	 * @param i
	 * @param j
	 */
	private void addMineNum(int i, int j){
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		
		int tempI = i-1;		
		
		for(;tempI<=i+1;tempI++){
			int tempJ = j-1;
			for(;tempJ<=j+1;tempJ++){
				if((tempI>-1&&tempI<width)&&(tempJ>-1&&tempJ<height)){
//					System.out.println(i+";"+j+":"+tempI+";"+tempJ+":");
					blockMatrix[tempI][tempJ].addMine();
				}
			}
		}
		
	}
	
	/**
	 * 将逻辑对象转化为显示对象
	 * @param blocks
	 * @param gameState
	 * @return
	 */
	private List<BlockVO> getDisplayList(List<BlockPO> blocks, GameState gameState){
		List<BlockVO> result = new ArrayList<BlockVO>();
		for(BlockPO block : blocks){
			if(block != null){
				BlockVO displayBlock = block.getDisplayBlock(gameState);//(位置和状态)
				if(displayBlock.getState() != null)
				result.add(displayBlock);
			}
		}
		return result;
	}

	@Override
	public void setGameModel(GameModelService gameModel) {
		// TODO Auto-generated method stub
		this.gameModel = gameModel;
	}
	
	private void printBlockMatrix(){
		for(BlockPO[] blocks : this.blockMatrix){
			for(BlockPO b :blocks){
				String p = b.getMineNum()+"";
				if(b.isMine())
					p="*";
				System.out.print(p);
			}
			System.out.println();
		}
	}

}
