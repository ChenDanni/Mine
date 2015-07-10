/*
 *
 * TODO the view of MVC structure. to create user interface and show result
 * automatically when model data are changed
 */
package edu.nju.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;
import java.util.HashMap;
import java.util.Observable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
 


































import com.sun.java.swing.plaf.windows.resources.windows;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import edu.nju.controller.impl.MenuControllerImpl;
import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.service.MenuControllerService;
import edu.nju.model.impl.UpdateMessage;
import edu.nju.model.state.GameResultState;
import edu.nju.model.vo.BlockVO;
import edu.nju.model.vo.GameVO;
import edu.nju.view.listener.CoreListener;
import edu.nju.view.listener.MenuListener;

import java.util.Observer;

public class MainFrame implements Observer {
	
	//Variables declaration
	private JFrame mainFrame; 
	private JPanel head;
	private JMenuBar aJMenuBar;
	private JMenu game;
	private HashMap<String, JMenuItem> menuItemMap;//这里的用处是什么？
	private JMenuItem startItem;
	private JSeparator jSeparator;
	private JSeparator jSeparator1;
	private JSeparator jSeparator2;
	private ButtonGroup group;
	private JCheckBoxMenuItem easy;
	private JCheckBoxMenuItem hard;
	private JCheckBoxMenuItem hell;
	private JCheckBoxMenuItem custom;         //为什么不用jcheckbox呢
	private JMenuItem record;
	private JMenuItem exit;
	private JMenu online;
	private JMenuItem host;
	private JMenuItem client;
	private MineNumberLabel mineNumberLabel;
	private MarkedMineLabel markedMineNumLabel;//这里加了一个Label
	private JButton startButton;
	private JLabel time;
	private MineBoardPanel body;
	private final int buttonSize = 16;
	private final int bodyMarginNorth = 20;
	private final int bodyMarginOther = 12;
	private int defaultWidth = 9;
	private int defaultHeight = 9;
	private CoreListener coreListener;
	private MenuListener menuListener;
	//time
	private long numOfTime;
	private Thread updateTimeThread;
	private updateTime updateTime;
	//End of variables declaration
	public boolean clientSourse = false;

	public MainFrame() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//在哪个平台上运行，就显示哪个平台的外观
		} catch (Exception e) {
			e.printStackTrace();
		}
		componentsInstantiation();
		initComponents();
		mainFrame.setVisible(true);

	}

	//Instantiation of components
	private void componentsInstantiation() {
		mainFrame = new JFrame();
		head = new JPanel();
		mineNumberLabel = new MineNumberLabel();
		markedMineNumLabel = new MarkedMineLabel();
		startButton = new JButton();
		time = new JLabel();

		aJMenuBar = new JMenuBar();
		game = new JMenu();
		startItem = new JMenuItem();
		jSeparator = new JSeparator();
		jSeparator1 = new JSeparator();
		jSeparator2 = new JSeparator();
		easy = new JCheckBoxMenuItem();
		hard = new JCheckBoxMenuItem();
		hell = new JCheckBoxMenuItem();
		custom = new JCheckBoxMenuItem();
 		record = new JMenuItem();
		exit = new JMenuItem();
		online = new JMenu();
		host = new JMenuItem();
		client = new JMenuItem();
		
		menuItemMap = new HashMap<String,JMenuItem>();
		group = new ButtonGroup();
		
		body = new MineBoardPanel(defaultHeight,defaultWidth);
		coreListener = new CoreListener(this);
		menuListener = new MenuListener(this);
		
		numOfTime = 0;
	}
	
	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		mainFrame
				.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setBackground(Color.BLACK);
		game.setText("Game");

		//build menu bar
		group.add(easy);
		group.add(hard);
		group.add(hell);
		group.add(custom);

		startItem.setText("Start");
		game.add(startItem);
		startItem.addActionListener(menuListener);
		menuItemMap.put("start", startItem);

		game.add(jSeparator1);

		easy.setText("Easy");
		game.add(easy);
		easy.addActionListener(menuListener);
		menuItemMap.put("easy", easy);

		hard.setText("Hard");
		hard.addActionListener(menuListener);
		game.add(hard);
		menuItemMap.put("hard", hard);

		hell.setText("Hell");
		hell.addActionListener(menuListener);
		game.add(hell);
		menuItemMap.put("hell", hell);

		custom.setText("Custom");
		custom.addActionListener(menuListener);
		game.add(custom);
		menuItemMap.put("custom", custom);

		game.add(jSeparator2);

		record.setText("Record");
		game.add(record);
		menuItemMap.put("record", record);
		record.addActionListener(menuListener);

		game.add(jSeparator);

		exit.setText("Exit");
		exit.addActionListener(menuListener);
		game.add(exit);
		menuItemMap.put("exit", exit);

		aJMenuBar.add(game);
		
		online.setText("online");
		host.setText("registe as host");
		host.addActionListener(menuListener);
		online.add(host);
		menuItemMap.put("host", host);
		
		client.setText("registe as client");
		client.addActionListener(menuListener);
		online.add(client);
		menuItemMap.put("client", client);
		
		aJMenuBar.add(online);
		mainFrame.setJMenuBar(aJMenuBar);
		//build menu bar end

		mainFrame.getContentPane().setLayout(null);

		//build head panel
//		head.setBackground(Color.GRAY);/////////
		head.setBorder(new javax.swing.border.TitledBorder(null, "",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION));
		head.setLayout(null);

		startButton.setIcon(Images.START_BEGIN);
        startButton.addActionListener(coreListener);
		Font font = new Font("Serif", Font.BOLD, 12);
		mineNumberLabel.setFont(font);
//		mineNumberLabel.setOpaque(true);/////////////////
		markedMineNumLabel.setFont(font);
		time.setFont(font);

		mineNumberLabel.setHorizontalAlignment(JLabel.CENTER);
		markedMineNumLabel.setHorizontalAlignment(JLabel.CENTER);
        time.setHorizontalAlignment(JLabel.CENTER);
		
        mineNumberLabel.setText("剩余雷数");
        markedMineNumLabel.setText("已标雷数");
		time.setText("0");

		head.add(mineNumberLabel);
		head.add(markedMineNumLabel);
		head.add(startButton);
		head.add(time);
		mainFrame.getContentPane().add(head);	
		//build head panel end

		//build body panel
		mainFrame.getContentPane().add(body);
		//build body panel end
		
		mainFrame.setTitle("JMineSweeper");
		mainFrame.setIconImage(Images.FRAME_IMAGE);		
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		
		//build head, and body in detail  
//		body.setBackground(Color.GRAY);//////////
		head.setBounds(4, 5, body.getColumns() * buttonSize + bodyMarginOther * 2 - 4, 60);
		startButton.setBounds((head.getWidth() - 50) / 2,
				(head.getHeight() - 50) / 2, 50, 50);
		mineNumberLabel.setBounds(0, 0, head.getHeight(), head.getHeight()/2);//改了
		markedMineNumLabel.setBounds(0,head.getHeight()/2,head.getHeight(),head.getHeight()/2);
		time.setBounds(head.getWidth() - head.getHeight(), 0, head.getHeight(),
				head.getHeight());

		body.setBounds(2, head.getHeight(), body.getColumns() * buttonSize + 2
				* bodyMarginOther, body.getRows() * buttonSize + bodyMarginNorth
				+ bodyMarginOther);
		body.setBorder(new javax.swing.border.TitledBorder(
				new javax.swing.border.TitledBorder(""), "Challenge Yourself~",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION));
		mainFrame.getContentPane().add(body);
		mainFrame.setSize(body.getWidth() + 10, body.getHeight()
				+ head.getHeight() + 60);
		mainFrame.validate();
		mainFrame.repaint();
		easy.setSelected(true);
		mainFrame
				.setLocation((screenSize.width - head.getWidth()) / 2,
						(screenSize.height - aJMenuBar.getHeight()
								- head.getHeight() - body.getHeight()) / 2);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				
			}
		});
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public JMenuItem getMenuItem(String name) {
		if (menuItemMap == null)
			return null;
		return (JMenuItem) menuItemMap.get(name);
	}
	
	public MineBoardPanel getMineBoard(){
		
		return this.body;
	}
	public MineNumberLabel getMineNumberLabel(){
		return this.mineNumberLabel;
	}
	public MarkedMineLabel getMarkedMineLabel(){
		return this.markedMineNumLabel;
	}
	public JButton getStartButton(){
		return this.startButton;
	}
	/*
	 * (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 * 游戏gameModel发生变化体现在这里
	 */
//	updateTime updateTime = new updateTime();
//	Thread updateTimeThread = new Thread(updateTime);
	@Override
	public void update(Observable o, Object arg) {
		UpdateMessage notifingObject = (UpdateMessage)arg;
		if(notifingObject.getKey().equals("start")){
			GameVO newGame = (GameVO) notifingObject.getValue();
			int gameWidth = newGame.getWidth();
			int gameHeight = newGame.getHeight();
			String level = newGame.getLevel();		
			restart(gameHeight,gameWidth,level);
			startButton.setIcon(Images.START_RUN);
			//开始计时
			System.out.println("开始计时！");
			if(updateTimeThread!= null){
				updateTimeThread.stop();
				updateTimeThread = null;
			}
			updateTime = new updateTime();
			updateTimeThread = new Thread(updateTime);
		    updateTime.setrunning(true);
			updateTimeThread.start();
			
		}else if(notifingObject.getKey().equals("end")||notifingObject.getKey().equals("clientWin")){       ///////////////////////////////
			if (clientSourse) {
				new Win(mainFrame).newWin();
			}else {
				//这里加了图片     
				startButton.setIcon(Images.START_END);
				new Lose(mainFrame).newLose();
			}
			//停止计时
			updateTime.setrunning(false);
		}else if(notifingObject.getKey().equals("win")||notifingObject.getKey().equals("clientEnd")){
			if (clientSourse) {
				startButton.setIcon(Images.START_END);
				new Lose(mainFrame).newLose();
			}else {
				new Win(mainFrame).newWin();
			}
			updateTime.setrunning(false);
		}else if (notifingObject.getKey().equals("tie")) {
			
			new Tie(mainFrame).newTie();
		}
	}

	private void restart(int mineBoardHeight,int mineBoardWidth,String type) {

		mainFrame.getContentPane().remove(body);
		body = new MineBoardPanel(mineBoardHeight,mineBoardWidth);
		head.setBounds(4, 5, mineBoardWidth * buttonSize + bodyMarginOther * 2 - 4, 65);
		startButton.setBounds((head.getWidth() - 50) / 2,
				(head.getHeight() - 50) / 2, 50, 50);
		mineNumberLabel.setBounds(0, 0, head.getHeight(), head.getHeight()/2);
		markedMineNumLabel.setBounds(0,head.getHeight()/2,head.getHeight(),head.getHeight()/2);
		time.setBounds(head.getWidth() - head.getHeight(), 0, head.getHeight(),
				head.getHeight());

		body.setBounds(2, head.getHeight(), mineBoardWidth * buttonSize + 2
				* bodyMarginOther, mineBoardHeight * buttonSize + bodyMarginNorth
				+ bodyMarginOther);
		body.setBorder(new javax.swing.border.TitledBorder(
				new javax.swing.border.TitledBorder(""), "Challenge Yourself!",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION));
		mainFrame.getContentPane().add(body);
		mainFrame.setSize(body.getWidth() + 10, body.getHeight()
				+ head.getHeight() + 60);
		time.setText("0");
		
		if(type == null){
			//custom.setSelected(true);
			easy.setSelected(true);
		}
		else if(type.equals("小")){
			easy.setSelected(true);
		}
		else if(type.equals("中")){
			hard.setSelected(true);
		}
		else if(type.equals("大")){
			hell.setSelected(true);
		}
		else{
			custom.setSelected(true);
		}
//		switch (type) {
//		case "小":
//			easy.setSelected(true);
//			break;
//		case "中":
//			hard.setSelected(true);
//			break;
//		case "大":
//			hell.setSelected(true);
//			break;
//		default:
//			custom.setSelected(true);
//			break;
//		}
		mainFrame.validate();
		mainFrame.repaint();
	}
	
	public class updateTime implements Runnable{
		boolean running = false;
		public void setrunning(boolean running){
			this.running = running;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			numOfTime = 0;
			while(running){
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				numOfTime++;
				time.setText(String.valueOf(numOfTime));
			}
		}
		
	}

}

