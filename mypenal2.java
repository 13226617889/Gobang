package 五子棋.test1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class mypenal2 extends JPanel{
	private static BufferedImage bg;
	static boolean start = true;
	static {
		try {
			bg =  ImageIO.read(new File("image\\bg2.jpg"));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public void paint(Graphics g) {
		super.paint(g);
		if(start) {
			g.setColor(new Color(31,27,16));
			g.fillRect(0, 0, 160, 700);
		}else {
			g.setFont(new Font("楷体", Font.PLAIN, 18));
			g.drawImage(bg,0, 0, 300, 850, null);
			g.setColor(Color.black);
			g.drawString("玩家棋子：黑棋", 20, 100);
			g.drawString("电脑棋子：白棋", 20, 140);
			g.drawString("Enter: 重新开始:", 20, 180);
			g.drawString("玩法介绍：", 20, 380);
			g.drawString("鼠标左单击落棋！" , 20, 400);
			g.drawString("软开1933 何锦辉", 20, 480);
		}
	}
}
