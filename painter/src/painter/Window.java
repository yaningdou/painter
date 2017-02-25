package painter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
@SuppressWarnings("serial")
public class Window extends JFrame implements ActionListener,MouseMotionListener,MouseListener{
	JLabel label = new JLabel();
	JMenuBar menu1 = new JMenuBar();
	JMenuBar  menu2= new JMenuBar();// 创建菜单栏组件
	JMenu wenjian = new JMenu("文件(F)");// 创建菜单组件
	JPopupMenu pmu = new JPopupMenu();// 创建弹出式菜单选项
	JPanel jpanel1 = new JPanel();
	JPanel jpanel2 = new JPanel();
    JPanel jpanel3 = new JPanel();
    JPanel jpanel4 = new JPanel();
	JButton btn1 = new JButton("铅         笔");
    JButton btn2 = new JButton("橡         皮");
 	JButton	btn3 = new JButton("颜        料");
 	JButton btn4 = new JButton("直         线");
 	JButton btn5 = new JButton("矩         形");
 	JButton btn6= new JButton("圆         形");
 	JButton btn7 = new JButton("椭         圆");
 	JButton btn8 = new JButton("圆角矩形");
 	JButton  btn9 = new JButton("多  边  形");
	JSeparator seperate1=new JSeparator();
	JSeparator seperate2=new JSeparator();//加入下划线
	JMenuItem  openfile, save, saveas, exit;
	Box box = Box.createVerticalBox();//我们的画笔选择栏的容器
	Vector<Point> point= null;//点向量组
	Color color = new Color(0,0,0);
	Point mark = new Point(-1, -1, 15, color);//点截断标志
	int flag=1;//默认为铅笔
	int x=-1,y=-1;//当前点的坐标
	int n=1;
	public Window() {
		super();
		//对于文件操作的选项
		openfile = new JMenuItem("打开(O)",KeyEvent.VK_O);
		save = new JMenuItem("保存(S)",KeyEvent.VK_S);
		saveas = new JMenuItem("另存为(D)",KeyEvent.VK_D);
		exit = new JMenuItem("退出(X)",KeyEvent.VK_F4);
		// 将选项加入到菜单栏里
		menu1.add(wenjian);
		wenjian.add(openfile);
	    wenjian.add(save);
		wenjian.add(saveas);
		wenjian.add(seperate1);// 加入分割线
		wenjian.add(exit);
		jpanel1.add(box);
		box.add(btn1);
		box.add(new JSeparator());
		box.add(btn2);
		box.add(new JSeparator());
		box.add(btn3);
		box.add(new JSeparator());
		box.add(btn4);
		box.add(new JSeparator());
		box.add(btn5);
		box.add(new JSeparator());
		box.add(btn6);
		box.add(new JSeparator());
		box.add(btn7);
		box.add(new JSeparator());
		box.add(btn8);
		box.add(new JSeparator());
		box.add(btn9);
		
		//设置快捷键
		wenjian.setMnemonic(KeyEvent.VK_F);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.ALT_MASK));
		openfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.CTRL_MASK));
		// 增加监视器
		openfile.addActionListener(this);
		save.addActionListener(this);
		saveas.addActionListener(this);
		exit.addActionListener(this);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btn5.addActionListener(this);
		btn6.addActionListener(this);
		btn7.addActionListener(this);
		btn8.addActionListener(this);
		btn9.addActionListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		// 设置窗口位置和大小
		setTitle("画图");
		setBounds(300, 200, 700, 450);
		setJMenuBar(menu1);// 将菜单栏加入到窗口
		setBackground(jpanel4.getBackground());// 设置窗口背景颜色
		setBackground(jpanel2.getBackground());
		menu2.setBackground(Color.lightGray);
		add(menu2,BorderLayout.SOUTH);
		menu2.add(label);
		point = new Vector<Point>();
		add(jpanel1,BorderLayout.WEST);
		add(jpanel2,BorderLayout.NORTH);
		add(jpanel3,BorderLayout.EAST);
		add(jpanel4,BorderLayout.CENTER);
		jpanel1.setBackground(Color.cyan);
		jpanel2.setBackground(Color.gray);
		jpanel3.setBackground(Color.gray);
		jpanel4.setBackground(Color.white);
		setVisible(true);// 显示窗口		
		add(pmu);// 将弹出式菜单加入到窗口	
		pmu.show(this, 350, 200);// 在指定坐标显示弹出式菜单
		pmu.setBackground(Color.gray);
		validate();
	}

	//响应事件
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {// 获取事件源
		         //显示保存对话框
				 int result = JOptionPane.showConfirmDialog(this, "將更改保存到 未命名？", "画图",
					JOptionPane.YES_NO_OPTION);
			switch (result) {
			case JOptionPane.YES_OPTION:
				FileDialog FD2 = new FileDialog(this, "保存", FileDialog.SAVE);
				FD2.setVisible(true);
				try {
					File fileout = new File(FD2.getDirectory(),FD2.getFile()+".jpg");
					FileOutputStream fout = new FileOutputStream(fileout);
					ObjectOutputStream oout = new ObjectOutputStream(fout);
					oout.writeObject(point);
					oout.close();
				} catch (IOException IOe) {
					int res = JOptionPane.showConfirmDialog(this, "此文件无法保存！", "警告",
							JOptionPane.CANCEL_OPTION);
					if(res==JOptionPane.CANCEL_OPTION) {
						return;
					}
				}
				break;
			case JOptionPane.NO_OPTION:
				System.exit(0);
				break;
			}
		}else if (e.getSource() == openfile) {
			FileDialog FD1 = new FileDialog(this, "打开", FileDialog.LOAD);
			FD1.setVisible(true);
			if (FD1.getFile() != null) {
			    int tempflag=0;
			    tempflag = flag;
			    flag=0;
			    repaint();
				try {
					point.removeAllElements();
					File filein = new File(FD1.getDirectory(),FD1.getFile());
					FileInputStream fin = new FileInputStream(filein);
					ObjectInputStream oin = new ObjectInputStream(fin);
					point = (Vector<Point>) oin.readObject();
					oin.close();
					repaint();
					flag= tempflag;
				}
				catch (IOException e1) {
					repaint();
					flag = tempflag;
					int result = JOptionPane.showConfirmDialog(this, "此文件无法读取！", "警告",
							JOptionPane.CANCEL_OPTION);
					if(result==JOptionPane.CANCEL_OPTION) {
						return;
					}
				} catch (ClassNotFoundException e2) {
					// TODO Auto-generated catch block
					repaint();
					flag = tempflag;
					int result = JOptionPane.showConfirmDialog(this, "此文件无法读取！", "警告",
							JOptionPane.CANCEL_OPTION);
					if(result==JOptionPane.CANCEL_OPTION) {
						return;
					}
				}
			}
		} else if (e.getSource() == save) {
			FileDialog FD2 = new FileDialog(this, "保存", FileDialog.SAVE);
			FD2.setVisible(true);
			try {
				File fileout = new File(FD2.getDirectory(),FD2.getFile()+".jpg");
				FileOutputStream fout = new FileOutputStream(fileout);
				ObjectOutputStream oout = new ObjectOutputStream(fout);
				oout.writeObject(point);
				oout.close();
			} catch (IOException IOe) {
				int result = JOptionPane.showConfirmDialog(this, "此文件无法保存！", "警告",
						JOptionPane.CANCEL_OPTION);
				if(result==JOptionPane.CANCEL_OPTION) {
					return;
				}
			}
		} else if (e.getSource() == saveas) {
			FileDialog FD3 = new FileDialog(this, "另存为", FileDialog.SAVE);
			FD3.setVisible(true);
			try {
				File fileout = new File(FD3.getDirectory(),FD3.getFile()+".jpg");
				FileOutputStream fout = new FileOutputStream(fileout);
				ObjectOutputStream oout = new ObjectOutputStream(fout);
				oout.writeObject(point);
				oout.close();
			} catch (IOException IOe) {
				int result = JOptionPane.showConfirmDialog(this, "此文件无法保存！", "警告",
						JOptionPane.CANCEL_OPTION);
				if(result==JOptionPane.CANCEL_OPTION) {
					return;
				}
			}
		}else if (e.getSource() == btn3) {
			Color newColor = JColorChooser.showDialog(this, "调色板",color);
		    color=newColor;
		    flag=1;
			}else if(e.getSource()==btn1){
				flag=1;//铅笔
			}else if(e.getSource()==btn2){
				flag=2;//橡皮
			}else if(e.getSource()==btn3){
				flag=3;//颜料
			}else if(e.getSource()==btn4){
				flag=4;//直线
			}else if(e.getSource()==btn5){
				flag=5;//矩形
			}else if(e.getSource()==btn6){
				flag=6;//圆形
			}else if(e.getSource()==btn7){
				flag=7;//椭圆
			}else if(e.getSource()==btn8){
				flag=8;//圆角矩阵
			}else if(e.getSource()==btn9){
				flag=9;//多边形
			}
		}

	public void paint(Graphics g) {
		Graphics2D grap = (Graphics2D) g;
		Point p1, p2;
		n = point.size();
		if (flag == 0){
			g.clearRect(0, 0, getSize().width, getSize().height);// 清除
		    repaint();
		}
		//下面是具体的各种图形的实现
		for (int i = 0; i < n - 1; i++) {
			p1 = (Point) point.elementAt(i);
			p2 = (Point) point.elementAt(i + 1);
			grap.setColor(p1.color);
			if (p1.tool == p2.tool) {
				switch (p1.tool) {
				case 1:// 画笔
					Line2D line1 = new Line2D.Double(p1.x, p1.y, p2.x, p2.y);
					grap.draw(line1);
					break;
				case 2:// 橡皮
					setBackground(jpanel4.getBackground());
					g.clearRect(p1.x, p1.y, 20, 20);
					break;
				case 4:// 画直线
					Line2D line2 = new Line2D.Double(p1.x, p1.y, p2.x, p2.y);
					grap.draw(line2);
					break;
				case 5:// 画矩形
					Rectangle2D rect = new Rectangle2D.Double(p1.x, p1.y,
							Math.abs(p2.x - p1.x), Math.abs(p2.y - p1.y));
					grap.draw(rect);
					break;
				case 6:// 画圆
					Arc2D circle = new Arc2D.Double(p1.x, p1.y,
							Math.abs(p2.x - p1.x), Math.abs(p2.x - p1.x),0,360,Arc2D.OPEN);
					grap.draw(circle);
					break;
				case 7:// 画椭圆
					Ellipse2D ellipse = new Ellipse2D.Double(p1.x, p1.y,
							Math.abs(p2.x - p1.x), Math.abs(p2.y - p1.y));
					grap.draw(ellipse);
					break;
				case 8:// 画圆角矩形
					RoundRectangle2D rect_round = new RoundRectangle2D.Double(
							p1.x, p1.y, Math.abs(p2.x - p1.x), Math.abs(p2.y
									- p1.y), 20, 10);
					grap.draw(rect_round);
					break;
				case 9:// 画多边形
					int px[]={p1.x,p2.x+100,p1.y+70,p2.y-100};
					int py[]={p2.y-100,p1.y+30,p2.y+100,p2.y-30};
				     g.drawPolygon(px,py,4);
					
					break;
				case 15:// 截断，跳过
					i = i + 1;
					break;
				default:
				}
			}
		}
	}
	
	 public void update(Graphics g)
	 {
	  paint(g);
	 }
      //下面是相应的鼠标事件
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		  Point p3 ;
		  switch(flag){
		   case 1://铅笔
		   case 2://橡皮
		     x = (int)e.getX();
		     y = (int)e.getY();
		     p3 = new Point(x, y, flag,color);
		     point.addElement(p3);
		     repaint();
		     break;
		   default :
		  }
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
			label.setText("                    欢迎使用！");
		}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Point p1;
		switch(flag){
		   case 4://直线
		   case 5: //矩形
		   case 6: //圆
		   case 7: //椭圆
		   case 8: //圆角矩形
		   case 9: //多边形
			     x = (int)e.getX();
			     y = (int)e.getY();
			     p1 = new Point(x, y, flag, color);
			     point.addElement(p1);
			     break;
		   default :
		  } 
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		Point p2;
		switch (flag) {
		case 1: // 铅笔
		case 2: // 橡皮
			point.addElement(mark);
			break;
		case 4: // 直线
		case 5: // 矩形
		case 6: // 圆
		case 7: // 椭圆
		case 8: // 圆角矩形
		case 9: // 多边形
			x = (int) e.getX();
			y = (int) e.getY();
			p2 = new Point(x, y, flag, color);
			point.addElement(p2);
			point.addElement(mark);
			repaint();
			break;
		default:
		}
	}

public static void main(String[] args) {
	// TODO Auto-generated method stub
	@SuppressWarnings("unused")
	Window win=new Window();	
}
}

