package 五子棋.test1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;
public class GUI extends JFrame{
	static boolean startGui = true;
	
	public GUI() {
		final int WIDTH = 800;
		final int HEIGHT = 700;
		this.setTitle("五子棋对战");
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		JPanel contentPane = new JPanel();
		this.setContentPane(contentPane);
		this.setLayout(new BorderLayout());
		mypenal2 m2 = new mypenal2();
		Test1 m1 = new Test1(m2);
		m1.setPreferredSize(new Dimension(640,700));
		contentPane.add(m1,"West");
		contentPane.add(m2,"East");
		m2.setPreferredSize(new Dimension(158,700));
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int x = (screenSize.width - WIDTH) / 2;
		int y = (screenSize.height - HEIGHT) / 2;
		this.setLocation(x,y);
		this.setResizable(false);
		this.addMouseListener(m1);
		this.addMouseMotionListener(m1);
		this.addKeyListener(m1);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new GUI();
	}
	
}
