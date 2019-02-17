package classes;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
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
	
	public JTextField[] MatchResults(JFrame MW, int left, int height, String res1, String res2, String res3, String res4, int AorR){
		int box_height = 25;
		int txt_len = 140;
		int res_len = 30;
		int title_height = box_height - 5;
		JTextField[] ARres = new JTextField[4];
		if(AorR == 1){
			JLabel lblA = new GUIcompon().LabelContainer("A");
			lblA.setBounds(left, height-title_height, 2*res_len, title_height);
			MW.add(lblA);
			JLabel lblR = new GUIcompon().LabelContainer("R");
			lblR.setBounds(left+2*res_len+2*txt_len, height-title_height, 2*res_len, title_height);
			MW.add(lblR);
		}
        JTextField Ares1 = ResContainer();
        Ares1.setBounds(left, height, res_len, box_height);
        Ares1.setText(res1);
        MW.add(Ares1);
        JTextField Ares2 = ResContainer();
        Ares2.setBounds(left+res_len, height, res_len, box_height);
        Ares2.setText(res2);
        MW.add(Ares2);
        JTextField Rres1 = ResContainer();
        Rres1.setBounds(left+2*res_len+2*txt_len, height, res_len, box_height);
        Rres1.setText(res3);
        MW.add(Rres1);
        JTextField Rres2 = ResContainer();
        Rres2.setBounds(left+3*res_len+2*txt_len, height, res_len, box_height);
        Rres2.setText(res4);
        MW.add(Rres2);
        ARres[0] = Ares1; ARres[1] = Ares2;
        ARres[2] = Rres1; ARres[3] = Rres2;
        return ARres;
	}
	
	public JTextPane[] MatchPlayers(JFrame MW, int left, int height, String player1, String player2, String title){
		int box_height = 25;
		int txt_len = 140;
		int res_len = 30;
		int title_height = box_height - 5;
		JTextPane[] texts = new JTextPane[2];
		if(!title.isEmpty()){
			JLabel lbl = new GUIcompon().LabelContainer(title);
			lbl.setBounds(left+2*res_len, height-title_height, 2*txt_len, title_height);
			MW.add(lbl);
		}
        JTextPane txt1 = TableContainer();
        txt1.setBounds(left+2*res_len, height, txt_len, box_height);
        new ResultsOps().SetPlayer(txt1, player1);
        MW.add(txt1);
        JTextPane txt2 = TableContainer();
        txt2.setBounds(left+2*res_len+txt_len, height, txt_len, box_height);
        new ResultsOps().SetPlayer(txt2, player2);
        MW.add(txt2);
        texts[0] = txt1; texts[1] = txt2;
        return texts;
	}
	
}
