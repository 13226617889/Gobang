package 五子棋.test1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * 需求：实现五子棋（两种游玩模式）
 * 模式1：电脑 vs 玩家  -- 
 * 模式2：玩家 vs 玩家 
 * 游戏规则：谁先5个棋子连成线谁就获胜。
 * 界面构件：
 * 1.开始游戏界面
 * 2.游戏开始时候的界面，左边为棋局，右边实现游戏说明等内容。
 * 玩家 vs 玩家分析：
 * 1.五子棋棋盘规格15 * 15(18 * 18)，需要创建一个二位网格作为游戏版。
 * 2.每次落棋都要进行判断（判断是否获胜）
 * 电脑 vs 玩家
 * 1.电脑需要判定当前棋局的各种可能性。
 *  - 1.自己是否将要获胜
 *  - 2.玩家是否将要获胜（1为false）
 *  - 3.自己是否有旗形（2为false）
 *  - 4.玩家是否有旗形（3为false）
 * 实现：
 * 1.创建游戏版
 * 2.实现键盘和鼠标监控
 * 
 * 创建游戏版时候需要建造围墙如【17】【17】则需要变成【18】【18】；
 * 
 */
public class Test1 extends JPanel implements KeyListener, MouseMotionListener,MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5675428124962825363L;
	private static BufferedImage bg;
	private static BufferedImage white;
	private static BufferedImage black;
	private static BufferedImage Loading1;
	private static BufferedImage Loading2;
	private static boolean isdown;
	private static int[][] board = createBoard();
	private static int ID;
	private static ArrayList<ArrayList<Integer>> arraylist = new ArrayList<>();
	private static boolean trB;
	private static boolean condition;
	static boolean qd = true;
	private int cc;
	private int initpoth;
	private Timer time;
	static boolean startGui = true;
	private mypenal2 mypenal;
	static {
		try {
			bg = ImageIO.read(new File("image\\background.jpg"));
			white = ImageIO.read(new File("image\\白棋.png"));
			black = ImageIO.read(new File("image\\黑棋.png"));
			Loading1 = ImageIO.read(new File("image\\Loading1.jpg"));
			Loading2 = ImageIO.read(new File("image\\Loading2.jpg"));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Random r = new Random();
		int a = r.nextInt(2);
		if(a == 0) {
			System.out.println("电脑先出");
			trB = true;
			ID = 1;
		}else {
			System.out.println("玩家先出");
			ID = 2;
		}
		/**
		 * 用多线程执行吧
		 * 线程1：玩家唤醒电脑（条件符合唤醒电脑）
		 * 线程2：电脑操作（执行完后等待条件唤醒）
		 * 通过trB来决定线程到谁
		 */
		
	}
	/*public static void main(String[] args) {
		Test1 t = new Test1();
		//t.createBoard();
	}*/
	public Test1(mypenal2 my) {
		this.mypenal = my;
	}
	public static int[][] createBoard(){
		int[][] board = new int[17][17];
		/*board[9][4] = 1;
		board[10][4] = 1;
		board[1][4] = 2;
		board[2][4] = 2;
		board[3][4] = 2;
		board[4][4] = 2;
		board[6][4] = 2;*/
		return board;
	}
	/**
	 * 16*16的点击范围  
	 * 默认开始范围40,69
	 * 48 - 77
	 * >= 0.8 则 count +1；
	 * [0] = 列y
	 * [1] = 行x
	 */
	public void print() {
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board[i].length;j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void down(int x, int y) {
		//0.33范围在0.2这个之间
		if(x < 40 || x > 625 || y < 68 || y > 666) {
			return;
		}
		float numberX = (float)((float)x / 36 - 0.22);
		int countX = (int)numberX;
		float fnX = (float)((int)((numberX - countX) * 100)) / 100;	
		float numbery = (float)((float)(y - 29) / 36 - 0.33);
		int county = (int)numbery;
		float fny = (float)((int)((numbery - county) * 100)) / 100;
		if(fnX >= 0.4 && fnX < 0.8 ||fny >= 0.25 && fny < 0.8) {
			return ;
		}
		
		if(fnX >= 0.8) {
			countX++;
		}
		if(fny >= 0.8) {
			county++;
		}
		//如果当前位置没有棋子，则可以下。【0为空，1为白棋，2为黑棋】
		/*System.out.println(countX + "," + county);
		System.out.println(fnX + "," + fny);*/
		if(!examine(countX,county)) {
			board[county - 1][countX - 1] = 2;
			addList(county - 1, countX - 1);
			isdown = true;
			ID = 1;
			ConditionWin(county - 1,countX - 1,2);
			repaint();
		}
	}
	
	public void addList(int x, int y) {
		ArrayList<Integer> list = new ArrayList<>();
		list.add(y);
		list.add(x);
		arraylist.add(list);
	}
	/**
	 * 初始默认位置X = 40， Y = 50
	 * x = -3,y=-17
	 * 高度间隔 = 35 
	 * 宽度为间隔 = 31
	 */
	/**
	 * boolean examine()
	 * 检查鼠标落下位置有没有棋子
	 * true 有
	 * false 空1056109491
	 */
	/**
	 * 返回上一步则，删除自己上一步下的棋子，如果电脑也下了棋子，则电脑的棋子也删除
	 * 
	 */
	/**
	 *返回上一步不管电脑是不是赢了，都有权利返回上一步的操作。 
	 */
	public void rup() {
		/*如果返回的时候是玩家则返回两步
		如果返回的时候是电脑则返回一步
		*/
		if(ID == 1 && arraylist.size() > 0) {
			System.out.println("我是电脑");
		}
		if(ID == 2 && arraylist.size() >= 2) {
			board[arraylist.get(arraylist.size() - 1).get(1)][arraylist.get(arraylist.size() - 1).get(0)] = 0;
			board[arraylist.get(arraylist.size() - 2).get(1)][arraylist.get(arraylist.size() - 2).get(0)] = 0;
			arraylist.remove(arraylist.size()-1);
			arraylist.remove(arraylist.size()-1);
			isdown = true;
			repaint();
		}
	}
	public boolean examine(int n1, int n2) {
		if(board[n2 - 1][n1 - 1] == 0) {
			return false;
		}
		return true;
	}
	public int[][] copyBoard() {
		int[][] Board = new int[17][17];
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board[i].length;j++) {
				Board[i][j] = board[i][j];
			}
		}
		return Board;
	}
	public int[][] RandomMove(ArrayList<ArrayList<Integer>> list){
		//System.out.println(list);
		int[][] rxy = new int[1][2];
		if(list != null) {
			Random r = new Random();
			int rn = r.nextInt(list.size());
			rxy[0][0] = list.get(rn).get(0);
			rxy[0][1] = list.get(rn).get(1);
			return rxy;
		}
		return null;
	}
	
	public void ConditionWin(int j, int i,int whoID) {
		if(win(board,j,i,whoID)) {
			System.out.println("Win");
			ID = -1;
		}
	}
	
	public void RightComputerMove() {
		print();
		int[][] set = MoveComputer();
		if(set != null) {
			board[set[0][0]][set[0][1]] = 1;
			addList(set[0][0], set[0][1]);
			ID = 2;
			ConditionWin(set[0][0],set[0][1],1);
		}else {
			board[9][11] = 1;  //9是高度 11是宽度
			System.out.println("异常");
			addList(9, 11);
			ID = 2;
			ConditionWin(9,11,1);
		}
		isdown = true;
		repaint();
	}
	/**
	 *第一步：判断自己能否获胜
	 *	 		true:下棋 
	 *第二步:false 判定玩家是否能获胜
	 *			true:下棋
	 *第三步:false 判定自己是否有1的优先级
	 *			true:下棋
	 *第四步:false 判定玩家是否有1的优先级
	 *			true:下棋
	 *第五步:false 也添加到随机move去。
	 * 
	 * 创建2个数组。
	 * 创建一个方法用来判定是否满足以上条件
	 */
	
	public int[][] MoveComputer() {
		ArrayList<ArrayList<Integer>> computerL1 = new ArrayList<>();
		ArrayList<ArrayList<Integer>> computerL2 = new ArrayList<>();
		ArrayList<ArrayList<Integer>> computerL3 = new ArrayList<>();
		
		ArrayList<ArrayList<Integer>> userL1 = new ArrayList<>();
		ArrayList<ArrayList<Integer>> userL2 = new ArrayList<>();
		ArrayList<ArrayList<Integer>> userL3 = new ArrayList<>();
		
		ArrayList<ArrayList<Integer>> gx = new ArrayList<>();
		cont2(computerL1,computerL2,computerL3,userL1,userL2,userL3,gx);
		
		System.out.println("computerL1: " + computerL1);
		System.out.println("computerL2: " + computerL2);
		System.out.println("computerL3: " + computerL3);
		System.out.println("userL1: " + userL1);
		System.out.println("userL2: " + userL2);
		System.out.println("userL3: " + userL3);
		System.out.println("gx: " + gx);
		if(computerL1.size() !=0 ) {
			return RandomMove(computerL1);
		}else if(userL1.size() != 0) {
			return RandomMove(userL1);
		}else if(userL2.size() != 0) {
			System.out.println(userL2);
			return RandomMove(userL2);
		}else if(computerL2.size() != 0) {
			return RandomMove(computerL2);
		}else if(computerL3.size() != 0) {
			return RandomMove(computerL3);
		}else if(userL3.size() != 0) {
			return RandomMove(userL3);
		}else {
			if(gx.size() == 0) {
				FirstRun(gx);
			}
			return RandomMove(gx);
		}
		/**
		 *第一步：判断自己能否获胜
		 *	 		true:下棋 
		 *第二步:false 判定玩家是否能获胜
		 *			true:下棋
		 *第三步:false 判定自己是否有1的优先级
		 *			true:下棋
		 *第四步:false 判定玩家是否有1的优先级
		 *			true:下棋
		 *第五步:false 也添加到随机move去。
		 * 
		 * 创建2个数组。
		 * 创建一个方法用来判定是否满足以上条件
		 */
		
	}
	public void FirstRun(ArrayList<ArrayList<Integer>> gx) {
		ArrayList<Integer> li = new ArrayList<>();
		for(int i=(board.length - 1) / 2;i<board.length;i++) {
			for(int j=(board[i].length - 1) / 2;j<board[i].length;j++) {
				if(board[i][j] == 0) {
					li.add(i);
					li.add(j);
					gx.add(li);
				}
			}
		}
	}
	public int[][] cont2(ArrayList<ArrayList<Integer>> cp1,ArrayList<ArrayList<Integer>> cp2
			,ArrayList<ArrayList<Integer>> cp3,ArrayList<ArrayList<Integer>> us1,ArrayList<ArrayList<Integer>> us2,ArrayList<ArrayList<Integer>> us3
			,ArrayList<ArrayList<Integer>> g1){
		int[][] direction = new int [][]{{0,1},{1,0},{1,1},
			{-1,1}};
		int[][] copyboard = copyBoard();
		for(int id = 1;id<=2;id++) {
			for(int i=0;i<copyboard.length;i++) {
				for(int j=0;j<copyboard[i].length;j++) {
					if(copyboard[i][j] != 0) {
						continue;
					}
					if(i == 8 && j==4) {
						System.out.println("我是8三" + copyboard[i][j]);
					}
					ArrayList<Integer> list1 = new ArrayList<>();
					int c = flag(board,direction,i,j,id);
					if(id == 1) {
						if(c == 1) {
							list1.add(i);
							list1.add(j);
							cp1.add(list1);
						}else if(c == 2) {
							list1.add(i);
							list1.add(j);
							cp2.add(list1);
						}else if(c == 3) {
							list1.add(i);
							list1.add(j);
							cp3.add(list1);
						}else if(c == 4) {
							list1.add(i);
							list1.add(j);
							g1.add(list1);
						}
					}else if(id == 2) {
						if(c == 1) {
							list1.add(i);
							list1.add(j);
							us1.add(list1);
						}else if(c == 2) {
							list1.add(i);
							list1.add(j);
							us2.add(list1);
						}else if(c == 3) {
							list1.add(i);
							list1.add(j);
							us3.add(list1);
						}else if(c == 4) {
							list1.add(i);
							list1.add(j);
							g1.add(list1);
						}
					}
				}
			}
		}
		return null;
	}
	/**
	 * 返回他这次的最高级。
	 * 相应容器装着
	 * 
	 */
	public void fill() {
		cc ++;
		initpoth += 24;
		//initpoth += Rectsize;
		repaint();
	}
	class TLL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			fill();
		}
	}
	public void paint(Graphics g) {
		super.paint(g);
		if(startGui) {
			g.setColor(new Color(31,27,16));
			g.fillRect(0, 0, 640, 700);
			g.drawImage(Loading1, 155, 300, 500, 92, null);
			g.setColor(new Color(255,255,255));
				if(cc== 20) {
					time.stop();
					g.fillRect(176, 348, initpoth - 25, 24);
					startGui = false;
					mypenal2.start = false;
					new Thread() {
						public void run() {
							mypenal.repaint();
						}
					}.start();
					new Thread() {
						public void run() {
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}
							repaint();
						}
					}.start();
				}else {
					g.fillRect(176, 348, initpoth, 24);
				}
		}else if(!startGui){
			g.drawImage(bg, -80, -130, 850, 850, null);
			if(isdown) {
				for(int i=0;i<board.length;i++) {
					for(int j=0;j<board[i].length;j++) {
						if(board[i][j] != 0) {
							int multiplyX = 4+ j * 36;
							if(j > 7) {
								multiplyX = multiplyX - j + 7;
							}
							if(board[i][j] == 1) {
								g.drawImage(white, multiplyX, 8+i*36, 80, 80, null);
							}else if(board[i][j] == 2) {
								g.drawImage(black, multiplyX, 8+i*36, 80, 80, null);
							}
						}
					}
				}
				isdown = false;
			}
		}
		if(qd) {
			time = new Timer(100, new TLL());
			time.start();
			qd = false;
		}
		//x = 36,y = 
		//48 - 616
	}
	/**
	 * 通过8个方向来判定是否符合条件
	 * [0,1][1,0][1,1][-1,-1][0,-1][-1,0][-1,1][1,-1]
	 *      不能超出边界 17*17 0-17 这个范围
	 */
	public boolean win(int[][] Board,int x, int y,int whoID) {
		int[][] direction = new int [][]{{0,1},{1,0},{1,1},
			{-1,1},{1,-1},{0,-1},{-1,0},{-1,-1}};
		for (int[] is : direction) {
			int count = 0;
			int X = x;
			int Y = y;
			X += -is[1];
			Y += -is[0];
			while(isOnBoard(X, Y) && Board[X][Y] == whoID) {
				X += -is[1];
				Y += -is[0];
				count++;
			}
			X = x;
			Y = y;
			while(isOnBoard(X, Y) && Board[X][Y] == whoID) {
				X += is[1];
				Y += is[0];
				count++;
			}
			if(count >= 5) {
				System.out.println(count);
				return true;
			}
		}
		return false;
	}

	/**
	 * 只用来判定当前棋局上的棋子是否能获胜， 到时候，电脑会拷贝一份board模拟下棋的每一个位置，同过judge
	 * 来反馈怎么下才最好。
	 * 计算出来的位置，返回给电脑，再落子。
	 */
	/**
	 * 判定是不是有4个棋子，两边为空，先判定电脑的
	 * 再判定是不是有两个方向的棋子皆为3个，两边皆为空
	 * 优先级为2以下的需要随机选择，如果有优先级为1的，直接返回。
	 * 写规则。
	 */
	public boolean isOnBoard(int x, int y) {
		return (x < 17 && x >= 0) && (y<17 && y>=0);
	}
	/**
	 *放一个已经下了的拷贝模板过来，再判定下的这个位置， 
	 *相反方向需要判定是自己的棋子，count++如果遇到空格则返回count；
	 *
	 *
	 *
	 *   1 
	 *1 1
	 *11
	 *
	 */
	public int flag(int[][] Board,int[][] direction,int x, int y,int whoID) {
		int Uid = whoID == 1?2:1;
		int count = 0;
		int count2 = 0;
		int count3 = 0;
		int you1 = 0;
		int you2 = 0;
		int you3 = 0;
		for (int[] is : direction) {
			boolean bl = false;
			boolean db = false;
			int X = x;
			int Y = y;
			int a = 0;
			int b = 0;
			int spackcount = 0;
			X += -is[0];
			Y += -is[1];
			if(isOnBoard(X, Y) && board[X][Y] == 0) {
				spackcount++;
				X += -is[0];
				Y += -is[1];
			}
			while(isOnBoard(X, Y) && Board[X][Y] == whoID  && spackcount<=1){
				a++;
				X += -is[0];
				Y += -is[1];
				/**
				 * 不管等于多少都要把这一行算完分隔符为0和2
				 */
				if(a >= 4 && spackcount == 0) {
					System.out.println("win");
					return 1;
				}
				if(isOnBoard(X, Y) && Board[X][Y] == 0) {
					X = x;
					Y = y;
					X += is[0];
					Y += is[1];
					if(isOnBoard(X, Y) && a==2 && Board[X][Y] == 0) {
						you2++;
					}
					while(isOnBoard(X, Y) && Board[X][Y] == whoID) {
						a++;
						X += is[0];
						Y += is[1];
						if(isOnBoard(X, Y) && Board[X][Y] == 0) {
							if(a == 2) {
								you2++;
								System.out.println(70);
							}else if(a == 3 && spackcount == 0) {
								you1++;
							}else if(a == 3 && spackcount != 0) {
								you2++;
							}else if(a >= 4) {
								System.out.println("赢了81");
								return 1;
							}
						}else if(isOnBoard(X, Y) && Board[X][Y] == Uid) {
							if(a == 3) {
								count3++;
							}else if(a > 3) {
								System.out.println("win89");
								return 1;
							}
						}
					}
				}else if(isOnBoard(X, Y) && a >= 2 && Board[X][Y] == Uid) {
					db = true;
					b = a;
				}else if(isOnBoard(X, Y) && a == 1 && Board[X][Y] == Uid) {
					X = x;
					Y = y;
					X += is[0];
					Y += is[1];
					while(isOnBoard(X, Y) && Board[X][Y] == whoID){
						a++;
						X += is[0];
						Y += is[1];
						if(isOnBoard(X, Y) && a >= 4 && Board[X][Y] == 0) {
							return 1;
						}
					}
				}else if(isOnBoard(X, Y) && a==1 && Board[X][Y] == 0) {
					you3++;
				}else if(!isOnBoard(X, Y)) {
					X = x;
					Y = y;
					X += is[0];
					Y += is[1];
					while(isOnBoard(X, Y) && Board[X][Y] == 1) {
						a++;
						X += is[0];
						Y += is[1];
						if(a >= 4) {
							System.out.println("winwin");
							return 1;
						}
					}
				}
			}
			a = 0;
			if(db) {
				X = x;
				Y = y;
				X += is[0];
				Y += is[1];
				while(isOnBoard(X, Y) && Board[X][Y] == whoID){
					a++;
					X += is[0];
					Y += is[1];
					if(a + b >= 4) {
						System.out.println("win163");
						return 1;
					}
					if(isOnBoard(X, Y) && a >= 2 && Board[X][Y] == 0) {
						System.out.println("win122");
						return 1;
					}else if(isOnBoard(X, Y) && a == 1 && Board[X][Y] == 0) {
						count3++;
						System.out.println("123 count3");
					}
					else if(isOnBoard(X, Y) && a >= 2 && Board[X][Y] == 2) {
						System.out.println("130win5卡1");
						return 1;
					}
				}
			}
			a = 0;
			X = x;
			Y = y;
			X += -is[0];
			Y += -is[1];
			if(isOnBoard(X, Y) && Board[X][Y] == 0) {
				X = x;
				Y = y;
				X += is[0];
				Y += is[1];
				while(isOnBoard(X, Y) && Board[X][Y] == whoID) {
					a++;
					X += is[0];
					Y += is[1];
					if(isOnBoard(X, Y) && a >=4) {
						System.out.println("我赢了宝贝");
						return 1;
					}
					if(isOnBoard(X, Y) && a >=3 && Board[X][Y] == Uid) {
						count3++;
					}else if(isOnBoard(X, Y) && a >= 3 && Board[X][Y] == 0) {
						you1++;
					}else if(isOnBoard(X, Y) && a >= 2 && Board[X][Y] == 0) {
						you2++;
					}else if(isOnBoard(X, Y) && a==1 && board[X][Y] == 0) {
						
						you3++;
					}
				}
			}
		}
		if(you1 >= 1) {
			return 2;
		}
		if(count3 >= 2 || count3 == 1 && you2 >= 1) {
			count3 = 0;
			return 2;
		}
		if(you2 >= 2) {
			you2 = 0;
			return 2;
		}
		if(you2 == 1) {
			return 3;
		}
		if(you3 >= 1) {
			return 4;
		}
		return -1;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_SPACE:
			rup();
			break;
		case KeyEvent.VK_ENTER:
			if(ID == 1)
				RightComputerMove();
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自动生成的方法存根
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		//System.out.println(e.getX() + " " + e.getY());
		int x = e.getX();
		int y = e.getY();
		System.out.println(x + " " + y);
		if(ID == 2)
			down(x,y);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mouseDragged(MouseEvent e) {
	}
	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
