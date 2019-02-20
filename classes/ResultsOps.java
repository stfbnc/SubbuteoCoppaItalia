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
        else
        	return "";
	}
	
	public boolean ValidateFields(JTextField[] res, JTextPane[] players){
		boolean chk = true;
		//check for a single empty value in a result couple
		for(int i = 0; i < res.length; i += 2){
			String res1 = res[i].getText();
			String res2 = res[i+1].getText();
			if((res1.isEmpty() || res1 == "") ^ (res2.isEmpty() || res2 == ""))
				chk = false;
		}
		//check for a single empty value in a player couple
		String pl1 = NoHtmlText(players[0].getText(), "td");
		String pl2 = NoHtmlText(players[1].getText(), "td");
		if((pl1.isEmpty() || pl1 == "") ^ (pl2.isEmpty() || pl2 == ""))
			chk = false;
		//check for non numeric strings
		if(chk){
			for(int i = 0; i < res.length; i++){
				if(!res[i].getText().matches("^[0-9]+$") && !res[i].getText().isEmpty())
					chk = false;
			}
		}
		return chk;
	}
	
	public boolean CheckG(String[][] LeagueTbl){
		boolean chkG = true;
		for(int i = 0; i < LeagueTbl.length; i++){
			if(Integer.valueOf(LeagueTbl[i][2]) != (LeagueTbl.length - 1)){
				chkG = false;
				break;
			}
		}
		return chkG;
	}
	
	public void LeagueTable(JTextPane pane, String[][] LeagueTbl){
		//scontri diretti
		Comparator<String[]> comparator = Comparator.comparing(x -> x[1]);
		Arrays.sort(LeagueTbl, comparator.thenComparing(x -> (Integer.valueOf(x[6])-Integer.valueOf(x[7])))
					.thenComparing(x -> x[6]).reversed());
		//String[] FirstTwoAndCheck = new String[3];
		//FirstTwoAndCheck[0] = LeagueTbl[0][0];
		//FirstTwoAndCheck[1] = LeagueTbl[1][0];
		//FirstTwoAndCheck[2] = new ResultsOps().CheckG(LeagueTbl);
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
        //return FirstTwoAndCheck;
	}

}
