import java.util.ArrayList;

class InputMsg {
	int type;//0 1 2 - S M F
	String msg;
	int N;
	ArrayList <Integer> moves;
	
	public InputMsg (int type) {
		this.type = type;
	}
	
	public InputMsg (int type, int N, ArrayList <Integer> moves) {
		this.type = type;
		this.N = N;
		this.moves = moves;
	}
	
	/*public InputMsg (int type, String msg) {
		this.type = type;
		this.msg = msg;
	} */
}