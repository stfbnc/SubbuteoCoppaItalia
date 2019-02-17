package classes;

import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ResultsOps{
	
	public void SetPlayer(JTextPane pane, String player){
		pane.setContentType("text/html");
		pane.setText("<html><font face=\"Serif\" ><table style=\"width:100%\"; align=\"center\">" +
				     "<tr><td align=\"center\">" + player + "</td></table></font></html>");
	}
	
	public String NoHtmlText(String HTMLtxt, String tag){
		String toSearch = "<"+tag+".*>\\s*(.*)\\s*</"+tag+">";
		System.out.println(toSearch);
        Matcher matcher = Pattern.compile(toSearch).matcher(HTMLtxt);
        if(matcher.find())
        	return matcher.group(1);
        throw new IllegalArgumentException("Match not found");
	}
	
	public void EmptyPlayer(JTextPane pane){
		pane.setContentType("text/plain");
		pane.setText("");
	}
	
	public void SetMatches(String[] group, JTextPane[] pane){
		new ResultsOps().SetPlayer(pane[0], group[0]);
		new ResultsOps().SetPlayer(pane[1], group[1]);
		new ResultsOps().SetPlayer(pane[2], group[2]);
		new ResultsOps().SetPlayer(pane[3], group[1]);
		new ResultsOps().SetPlayer(pane[4], group[2]);
		new ResultsOps().SetPlayer(pane[5], group[0]);
	}
	
	public boolean ResChecks(JTextField[] Ares, JTextField[] Bres, JTextField semi11, JTextField semi12,
                 			 JTextField semi21, JTextField semi22, JTextPane p11, JTextPane p12,
                 			 JTextPane p21, JTextPane p22, JTextField fin1, JTextField fin2,
                 			 JTextPane f1, JTextPane f2){
		boolean chk = true;
		//check for a single empty value in a match
		for(int i = 0; i < (Ares.length / 2); i++){
			if((Ares[i].getText().isEmpty()) ^ (Ares[i + (Ares.length / 2)].getText().isEmpty()))
				chk = false;
		}
		for(int i = 0; i < (Bres.length / 2); i++){
			if((Bres[i].getText().isEmpty()) ^ (Bres[i + (Bres.length / 2)].getText().isEmpty()))
				chk = false;
		}
		if((semi11.getText().isEmpty() || semi11.getText() == "") ^ (semi12.getText().isEmpty() || semi12.getText() == ""))
			chk = false;
		if(((!semi11.getText().isEmpty() && semi11.getText() != "") && (!semi12.getText().isEmpty() && semi12.getText() != ""))
			&& ((p11.getText().isEmpty() || p11.getText() == "") || (p12.getText().isEmpty() || p12.getText() == "")))
			chk = false;
		if((semi21.getText().isEmpty() || semi21.getText() == "") ^ (semi22.getText().isEmpty() || semi22.getText() == ""))
			chk = false;
		if(((!semi21.getText().isEmpty() && semi21.getText() != "") && (!semi22.getText().isEmpty() && semi22.getText() != ""))
			&& ((p21.getText().isEmpty() || p21.getText() == "") || (p22.getText().isEmpty() || p22.getText() == "")))
			chk = false;
		if((fin1.getText().isEmpty() || fin1.getText() == "") ^ (fin2.getText().isEmpty() || fin2.getText() == ""))
			chk = false;
		if(((!fin1.getText().isEmpty() && fin1.getText() != "") && (!fin2.getText().isEmpty() && fin2.getText() != ""))
			&& ((f1.getText().isEmpty() || f1.getText() == "") || (f2.getText().isEmpty() || f2.getText() == "")))
			chk = false;
		//check for non numeric strings
		if(chk){
			for(int i = 0; i < Ares.length; i++){
				if(!Ares[i].getText().matches("^[0-9]+$") && !Ares[i].getText().isEmpty())
					chk = false;
			}
			for(int i = 0; i < Bres.length; i++){
				if(!Bres[i].getText().matches("^[0-9]+$") && !Bres[i].getText().isEmpty())
					chk = false;
			}
			if(!semi11.getText().matches("^[0-9]+$") && !semi11.getText().isEmpty())
				chk = false;
			if(!semi12.getText().matches("^[0-9]+$") && !semi12.getText().isEmpty())
				chk = false;
			if(!semi21.getText().matches("^[0-9]+$") && !semi21.getText().isEmpty())
				chk = false;
			if(!semi22.getText().matches("^[0-9]+$") && !semi22.getText().isEmpty())
				chk = false;
			if(!fin1.getText().matches("^[0-9]+$") && !fin1.getText().isEmpty())
				chk = false;
			if(!fin2.getText().matches("^[0-9]+$") && !fin2.getText().isEmpty())
				chk = false;
		}
		return chk;
	}
	
	public String CheckG(String[][] LeagueTbl){
		String chkG = "1";
		for(int i = 0; i < LeagueTbl.length; i++){
			if(Integer.valueOf(LeagueTbl[i][2]) != (LeagueTbl.length - 1)){
				chkG = "0";
				break;
			}
		}
		return chkG;
	}
	
	public String[] LeagueTable(JTextPane pane, String[][] LeagueTbl){
		//scontri diretti
		Comparator<String[]> comparator = Comparator.comparing(x -> x[1]);
		Arrays.sort(LeagueTbl, comparator.thenComparing(x -> (Integer.valueOf(x[6])-Integer.valueOf(x[7])))
					.thenComparing(x -> x[6]).reversed());
		String[] FirstTwoAndCheck = new String[3];
		FirstTwoAndCheck[0] = LeagueTbl[0][0];
		FirstTwoAndCheck[1] = LeagueTbl[1][0];
		FirstTwoAndCheck[2] = new ResultsOps().CheckG(LeagueTbl);
		String html = "<html><font face=\"Serif\" ><table style=\"width:100%\"; align=\"left\">" +
		   "<tr><th> </th><th align=\"left\">Squadra</th><th align=\"left\">P</th><th align=\"left\">G</th>" +
		   "<th align=\"left\">V</th><th align=\"left\">N</th><th align=\"left\">P</th><th align=\"left\">GF</th>" +
		   "<th align=\"left\">GS</th></tr>";
		for(int i = 0; i < LeagueTbl.length; i++){
			html += "<tr><td>" + (i + 1) + ".</td>";
			for(int j = 0; j < LeagueTbl[0].length; j++){
				html += "<td>" + LeagueTbl[i][j] + "</td>";
			}
			html += "</tr>";
		}
		html += "</table></font></html>";
		pane.setContentType("text/html");
        pane.setText(html);
        return FirstTwoAndCheck;
	}

}
