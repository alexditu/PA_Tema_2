import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class CommunicationInOut {
	InputMsg message;
	BufferedReader br;
//	BufferedWriter bw;
	
	public CommunicationInOut () throws IOException {
		br = new BufferedReader (new InputStreamReader (System.in));
	}
	
	public InputMsg read () throws IOException {
		StringTokenizer stk;
		String line = br.readLine();
		if (line == null) {
			//System.out.println("ERROR READING DATA!");
			
		}
		stk = new StringTokenizer (line);
		String type = stk.nextToken();
		if (type.equals("S")) {
			message = new InputMsg (0);
		} else {
			if (type.equals("M")) {
				int N = -1;
				if (stk.hasMoreElements()) //////////////////////////////////////////Atentie: nu verific aici daca am luat N corect!
					N = Integer.parseInt (stk.nextToken());
				ArrayList <Integer> moves = new ArrayList <> ();
				while (stk.hasMoreElements()) {
					moves.add(Integer.parseInt (stk.nextToken()));
				}
				message = new InputMsg (1, N, moves);			
			} else {
				if (type.equals("F")) {
					message = new InputMsg (2);
				} else {
					message = new InputMsg (3); //ERROR
				}
			}
		}
		return message;
	}
	
	public void write (String moves) {
		System.out.println (moves);
	}
	
	
	
}