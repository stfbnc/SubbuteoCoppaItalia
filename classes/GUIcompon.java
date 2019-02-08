package classes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class GUIcompon{
	
	public ImageIcon ResizeImg(String imgPath, String imgName, int width, int height){
		//Resize an image
        ImageIcon imageIcon = new ImageIcon(imgPath + imgName); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(width, height, Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg); // transform it back
        return imageIcon;
	}
	
	public JLabel PlayerName(String name, Color color){
		JLabel txt = new JLabel(name);
        txt.setFont(new Font("Serif", Font.BOLD, 28));
        txt.setForeground(color);
        return txt;
	}
	
	public JTextPane TableContainer(){
		JTextPane pane = new JTextPane();
		//pane.setFont(new Font("monospace",Font.PLAIN,16));
        pane.setEditable(false);
        pane.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.5f)));
        return pane;
	}
	
	public JTextField ResContainer(){
		JTextField txtf = new JTextField();
		txtf.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1.5f)));
		txtf.setHorizontalAlignment(JTextField.CENTER);
        return txtf;
	}
	
	public JLabel LabelContainer(String field){
		JLabel label = new JLabel(field, SwingConstants.CENTER);
        Font font = label.getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
        label.setFont(boldFont);
        return label;
	}
	
	public JTextField[] Results(int start, int end){
		int xShift = 30;
		int yShift = 25;
		JTextField[] res = new JTextField[6];
		JTextField res1 = new GUIcompon().ResContainer();
        res1.setBounds(start,end,30,25);
        JTextField res2 = new GUIcompon().ResContainer();
        res2.setBounds(start,end+yShift,30,25);
        JTextField res3 = new GUIcompon().ResContainer();
        res3.setBounds(start,end+yShift*2,30,25);
        JTextField res4 = new GUIcompon().ResContainer();
        res4.setBounds(start+xShift,end,30,25);
        JTextField res5 = new GUIcompon().ResContainer();
        res5.setBounds(start+xShift,end+yShift,30,25);
        JTextField res6 = new GUIcompon().ResContainer();
        res6.setBounds(start+xShift,end+yShift*2,30,25);
        res[0] = res1; res[1] = res2; res[2] = res3;
        res[3] = res4; res[4] = res5; res[5] = res6;
        return res;
	}
	
	public JTextPane[] Matches(int start, int end, String[] players){
		int xShift = 160;
		int yShift = 25;
		JTextPane[] mtch = new JTextPane[6];
		JTextPane mtch1 = new GUIcompon().TableContainer();
		mtch1.setBounds(start,end,100,25);
		JTextPane mtch2 = new GUIcompon().TableContainer();
		mtch2.setBounds(start,end+yShift,100,25);
		JTextPane mtch3 = new GUIcompon().TableContainer();
		mtch3.setBounds(start,end+yShift*2,100,25);
		JTextPane mtch4 = new GUIcompon().TableContainer();
		mtch4.setBounds(start+xShift,end,100,25);
		JTextPane mtch5 = new GUIcompon().TableContainer();
		mtch5.setBounds(start+xShift,end+yShift,100,25);
		JTextPane mtch6 = new GUIcompon().TableContainer();
		mtch6.setBounds(start+xShift,end+yShift*2,100,25);
		mtch[0] = mtch1; mtch[1] = mtch2; mtch[2] = mtch3;
		mtch[3] = mtch4; mtch[4] = mtch5; mtch[5] = mtch6;
		new ResultsOps().SetMatches(players, mtch);
		return mtch;
	}
	
}
