import java.util.ArrayList;
import java.util.Stack;


public class Choices {
	ArrayList <Integer> moves;
	Pair startPos, endPos;
	Field field;
	//Stack <Integer> prv;
	
	public Choices (ArrayList <Integer> moves, Pair startPos, Field field) {
		this.moves = moves;
		this.startPos = new Pair (startPos);
		this.field = field;
		computeEndPos ();
	//	prv = new Stack <Integer> ();
	}
	
	public Choices (int min) {
		endPos = new Pair (12, 4);
	}
	
	protected void computeEndPos () {
		int x, y;
		x = startPos.x;
		y= startPos.y;
		if (moves.size() == 0) {
			System.out.println("ERROR IN CHOICES: moves.size() == 0 !!");
		}
		for (int i = 0; i < moves.size(); i++) {
			x = x + field.d[moves.get (i)].x;
			y = y + field.d[moves.get (i)].y;
		}
		endPos = new Pair (x, y);
	}
	
}
