import java.io.IOException;
import java.util.ArrayList;


public class Bot {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		BotAI bot = new BotAI ();
		Field field = new Field ();

		
	//	field.showField ();
		
		CommunicationInOut data = new CommunicationInOut ();
		InputMsg inData;
		
		Pair crt = new Pair (6, 4);
		int newX, newY;
		String outMsg;
		ArrayList <Integer> compMoves;
		
		/* Afisare matrice scoruri
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print (bot.pScore[i][j] + "  ");
			}
			System.out.println();
		}
		*/
		
		
		//inData = data.read();
		inData = new InputMsg (0);

	
/*		crt = field.setMoveRev(0, crt);
		crt = field.setMoveRev(2, crt);
		crt = field.setMoveRev(2, new Pair(4,4));
		crt = field.setMoveRev(0, new Pair (5,4));
		crt = field.setMoveRev(0, new Pair (5,5));

		field.showField();
	//	System.out.println("CRT= " + crt);

		//System.out.println(crt.equals(new Pair(6,4)));
		
		
		ArrayList <Choices> posM = bot.getPossibleMoves (new Pair (2, 4), field);
		Choices bm = bot.getBestMove(posM);
		int k = bm.moves.size() - 1;
		System.out.println("k= " + k);
		while (k >= 0) {
			System.out.print (bm.moves.get(k) + " ");
			k --;
		}
		System.out.println();
		
		int j = 0;
		for (Choices i : posM) {

			System.out.println("bm["+j+"]:" + "strt: " + i.startPos + i.moves.get(0) + 
					" end: " + i.endPos);
			j++;
		}
		System.out.println("BM= " + bot.getBestMove(posM).endPos + " start= "
				+ bot.getBestMove(posM).startPos);
		

		
		*/
	
		
		
		
		
		
		
		while (inData.type != 2) {
		
			inData = data.read ();
			if (inData.type == 0) { //incep eu primul
				crt = field.setMoveRev(0, crt); //marchez mutarea pe teren	 	
				
				System.out.println("M 1 0");
//				field.showField();
			} else {
				if (inData.type == 1) {
					
					//marchez mutarile adversarului
					for (int next : inData.moves) {
//						System.out.println("Bot move: d= " + next + " " + crt);
						crt = field.setMoveRev (next, crt);	
//						System.out.println("Now crt= " + crt);
					}
//					System.out.println("Rezultat Mutarile Adversarului:");
//					field.showField();
					
					//calculez mutarile mele
//					System.out.println(" crt= " + crt);
					ArrayList <Choices> possibleMoves = bot.getPossibleMoves(crt, field);
					Choices bestMove = bot.getBestMove(possibleMoves);
					int N = bestMove.moves.size();
					outMsg = "M " + N;
					int i = N - 1;
					while (i >= 0) {
						outMsg += " " + bestMove.moves.remove(i);
						i --;
					}
					
					System.out.println (outMsg);
//					System.out.println("#moves= " + bestMove.moves.size());
					field = bestMove.field;
//					System.out.println("oldcrt= " + crt + " new= " + bestMove.startPos +
//							" endPos= " + bestMove.endPos);
					
					crt = bestMove.endPos;
//					System.out.println("Now crt= " + crt);
					//crt = bestMove.startPos;
//					System.out.println("dupa mutarile mele: ");
					//field.showField();
					
				
				} else {
					break;
				}
			}
		}
		
		
		

	}

}
