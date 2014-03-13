import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BotAI {
	
	

	public int pScore[][];
	
	public BotAI () {
		initPScore();
	}
	
	public int min (int a, int b) {
		if (a < b)
			return a;
		else
			return b;
	}
	
	public void initPScore () {
		int i, j;
		int linesNo = 13;
		int colsNo = 9;
		pScore = new int[linesNo][colsNo];
		for (i = 1; i < linesNo; i++) {
			for (j = 0; j < colsNo; j++) {
				pScore [i][j] = (6 - i) + min ((4-j), (j-4));
			}
		}
		pScore[1][3] = 99;
		pScore[1][4] = 10;
		pScore[1][5] = 99;
		pScore[0][4] = 100;
		
		pScore[12][3] = -99;
		pScore[12][4] = -100;
		pScore[12][4] = -99;
		
		//pt colturi: invers
		pScore[1][0] = -100;
		pScore[1][8] = -100;
		
		
		
		
	}
	
	
	
	public Choices getBestMove (ArrayList <Choices> moves) {
		Choices best = new Choices (-200);
		int max = -2000;
		
		for (int i = 0; i < moves.size(); i++) {
			Choices crt = moves.get(i);
			int aux = pScore [crt.endPos.x][crt.endPos.y];
			if (aux > max) {
				max = aux;
				best = crt;
			}	
//			if (crt.endPos.x == gol.x && crt.endPos.y == gol.y) {
//				System.out.println("Dar max = " + max);
//				System.out.println("GOL= " + pScore[gol.x][gol.y]);
//			}
		}
//		if (best.endPos.x == gol.x && best.endPos.y == gol.y) 
//			System.out.println("S_A ALES GOL");
		return best;
	}
	
	
	
	public static int rec = 0;
	public static Pair gol = new Pair (0, 4);
	
	public ArrayList <Choices> getPossibleMoves (Pair crtPos, Field field) {
		int newX, newY;
		ArrayList <Choices> possibleMoves = new ArrayList <> ();

		for (int i = 0; i < 8; i++) {
			newX = crtPos.x + field.d[i].x;
			newY = crtPos.y + field.d[i].y;
			Pair nextPos = new Pair (newX, newY);
			
			if (field.isInField (nextPos) ) {
				
				if (field.f[crtPos.x][crtPos.y][i] == 0) {			
				
					if (field.f[newX][newY][8] == 0) {//daca nu a fost vizitat acest punct, mut acolo
						
						ArrayList <Integer> moves = new ArrayList <Integer> ();
						moves.add (i);		
						Field fldCopy = new Field (field);
						fldCopy.setMoveRev(i, crtPos); //marchez mutarea pe teren
						
						possibleMoves.add(new Choices (moves, crtPos, fldCopy));
						
					//	System.out.println("pt dir= " + i + " crt= " + crtPos + " possibleMove este:" + i);
						
					}else{ //varful e vizitat, tb sa calculez pana ajung intr-o pozitie nevizitata
						
						if (nextPos.x == gol.x && nextPos.y == gol.y) {
				//			System.out.println("S-A intrat Aici!!");
							ArrayList <Integer> moves = new ArrayList <> ();
							moves.add(i);
							Field fldCopy = new Field (field);
							fldCopy.setMoveRev(i, crtPos);
							possibleMoves.add (new Choices (moves, crtPos, fldCopy));
						}else{
						
							ArrayList <Choices> moreMoves;
							rec++;
				//			System.out.println("REC = " + rec);
				//			System.out.println("i=" + i);
							
							Field fieldCopy2 = new Field (field);
							nextPos = fieldCopy2.setMoveRev(i, crtPos); //NU AM SETAT CRT POS DIN FIELDCOPPY!!!! = nextPos
			
							moreMoves = getPossibleMoves (nextPos, fieldCopy2);
							//adug si mutarea anterioara si setez startPos;
	//						for (Choices j : moreMoves) {
		//						j.moves.add(i);
		//						System.out.println("Added i= " + i + "size= " + j.moves.size());
		//						j.startPos = new Pair (crtPos);
		//					}				
							if (moreMoves.size() != 0) {
								Choices bst = getBestMove (moreMoves);
								bst.moves.add(i);
								bst.startPos = new Pair (crtPos);
								//System.out.println(bst.moves + " " + crtPos + " end: " + bst.endPos);
		
								possibleMoves.add (bst);
							} //daca nu sunt mutari posibile, nu fac nimic
						}
					}
				}
			}
			//else: daca nu e in teren, mutarea nu o iau in calcul!
			
		
		}
		//Acum in ALTA FUNCTIE trebuie sa aleg mutarea cu scorul cel mai mare 
		//dintre toate PossibleMoves mutari :)
		return possibleMoves;
	}
	
	

	/*
	public static void main(String[] args) throws IOException {
		
		Field field = new Field ();
		field.makeBorder();
		
		field.showField ();
		
		CommunicationInOut data = new CommunicationInOut ();
		InputMsg inData;
		
		Pair []dir = field.d;
		Pair crt = new Pair (6, 4);
		int newX, newY;
		String outMsg;
		ArrayList <Integer> compMoves;
		
		//inData = data.read();
		inData = new InputMsg (0);
		
		ArrayList <Choices> posM = getPossibleMoves (crt, field);
		/*
		while (inData.type != 2) {
		
			inData = data.read ();
			if (inData.type == 0) { //incep eu primul
				field.setMoveRev(0, crt); //marchez mutarea pe teren
				//actualizez cursorul/pozitia curenta
				newX = crt.x + dir[0].x;
				newY = crt.y + dir[0].y;
				crt.set (newX, newY);
				
				System.out.println("M 0 0");
				field.showField();
			} else {
				if (inData.type == 1) {
					
					//marchez mutarile adversarului
					for (int next : inData.moves) {
						System.out.println("Bot move: d= " + next + " " + crt);
						field.setMoveRev (next, crt);						
						newX = crt.x + dir[next].x;
						newY = crt.y + dir[next].y;
						crt.set (newX, newY);
						System.out.println("Now crt= " + crt);
					}
					System.out.println("Rezultat Mutarile Adversarului:");
					field.showField();
					
					//calculez mutarile mele
					//System.out.println(" crt = " + crt);
				
					//sterg intrarile din myMoves - desi ar trebui sa fie gol!
					for (int i = 0; i < myMoves.size(); i++) {
						myMoves.remove(i);
					}
					
					System.out.println("PosNow= " + crt);
					computeNextMove (crt, dir, field);
					crt = field.crtPos;
					System.out.println("Pos after myMove= " + crt);
					int mv;
					outMsg = "M " + myMoves.size();
					for (int i = 0; i < myMoves.size(); i++) {
						mv = myMoves.remove(i);
						outMsg += " " + mv;
						System.out.println("Mymove= " + mv);
					}
					System.out.println("Mutarile mele:");
					field.showField();
					//System.out.println(outMsg);
					//System.out.println("Now crt= " + crt + " Waiting for bot:");
					
				
				} else {
					break;
				}
			}
		}
		

		

	}
			*/

}


