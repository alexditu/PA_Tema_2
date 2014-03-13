class Pair {
	public int x, y;
	
	public Pair (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Pair (Pair p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public int getX () {
		return x;
	}
	
	public int getY () {
		return y;
	}
	
	public void set (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString () {
		String s = "(" + x + ", " + y + ")";
		return s;
	}
	
}



  







