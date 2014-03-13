class Field {
	public int linesNo, colsNo;
	public int f[][][];
	public Pair d[];
	public Pair crtPos;
	
	public void showField () {
		for (int i = 0; i < linesNo; i++) {
			for (int j = 0; j < colsNo; j++) {
				System.out.print (f[i][j][8] + " ");
			}
			System.out.println();
		}
	}
	
	public Field () {
		f = new int [13][9][9];
		d = new Pair [8];
		setDirectionsVector ();
		linesNo = 13;
		colsNo = 9;
		crtPos = new Pair (1, 1);
		makeBorder();
		
	}
	

	
	public void setCrtPos (Pair pos) {
		crtPos = new Pair (pos);
	}
	
	public Field (Field fld) {
		linesNo = fld.linesNo;
		colsNo = fld.colsNo;
		f = new int [linesNo][colsNo][9];
		for (int i = 0; i < linesNo; i++) {
			for (int j = 0; j < colsNo; j++) {
				for (int k = 0; k < 9; k++) {
					f[i][j][k] = fld.f[i][j][k];
				}
			}
		}
		d = fld.d;
		setDirectionsVector ();
		crtPos = new Pair (-1, -1);
		makeBorder();
	}
	

	
	public void setDirectionsVector () {
		d[0] = new Pair (-1, 0); // N
		d[1] = new Pair (-1, 1); //NE
		d[2] = new Pair (0, 1); // E
		d[3] = new Pair (1, 1); //SE
		d[4] = new Pair (1, 0); //S
		d[5] = new Pair (1, -1); //SV
		d[6] = new Pair (0, -1); //V
		d[7] = new Pair (-1, -1); //NV
		//d[8] = new Pair (0, 0); //initial
	}
	
	/* marchez marginea terenului astfel:
	 * pentru fiecare punct de pe margine, setez valoarea t[i][j][x] = 1, adica
	 * muchia (i,j) pe directia x, a fost folosita, deci nu se poate merge pe
	 * ea;
	 * 
	 */
	public void makeBorder () {
		int S = 4, E = 2, NE = 1;
		/* bordez matricea pe coloana
		 * bordez si coloanele corespunzatoare portilor
		 */
		
		for (int j = 0; j < 2; j++) {
			for (int i = 1; i < linesNo - 2; i++) {//i = 1 : 10
				if (j == 0) {
					setMoveRev (S, new Pair (i, j));
				} else {
					if (j == 1) { //daca j = 1, ma mut pe coloana 8
						j = 8;
					}
					setMoveRev (S, new Pair (i, j));
					
				}
			}
		}
		/* bordez poarta pe coloana: */
		setMoveRev (S, new Pair (0, 3));
		setMoveRev (S, new Pair (0, 5));
		setMoveRev (S, new Pair (11, 3));
		setMoveRev (S, new Pair (11, 5));
		
		
		
		/* bordez matricea pe linie
		 * bordez si liniile corespunzatoare portilor
		 */
		
		for (int i = 1; i < 3; i++) {
			for (int j = 0; j < colsNo - 1; j++) {//j = 0 : 7
				if (i == 1) {
					if (j != 3 && j != 4) {
						setMoveRev (E, new Pair (i, j));
					}
				} else {
					//mut i-ul pe pozitia 11 = linia orizontala de jos
					if (i == 2) {
						i = 11;
					}
					if (j != 3 && j != 4) { //nu marchez inca o data linia portii de jos
						setMoveRev (E, new Pair (i, j)); 
					}
				}
			}
		} 
		
		/* bordez poarta pe orizontala: */
		setMoveRev (E, new Pair (0, 3));
		setMoveRev (E, new Pair (0, 4));
		setMoveRev (E, new Pair (12, 3));
		setMoveRev (E, new Pair (12, 4));
		
		/* setez si poztiile urm, sa ca isInBounds sa nu dea gres */
		setMoveRev (NE, new Pair (1,2));
		setMoveRev (7, new Pair (1, 6));
		
		setMoveRev (3, new Pair (11, 2));
		setMoveRev (5, new Pair (11, 6));
		
		
		
	}
	/* marchez pe teren/ in matrice, mutarea de pe pozitia pos, pe directia
	 * direction
	 */
	public void setMove (int dir, Pair pos) {
		f[pos.x][pos.y][dir] = 1;
		f[pos.x][pos.y][8] = 1; // varful din pozitia (x,y) e folosit
		
		//acum setez si drumul invers:
		//int op_dir = (dir + 4) / 8; //directia opusa, directiei dir
		//t[pos.x + d[dir].x][pos.y + d[dir].y][op_dir] = 1;
	}
	
	
	/* verific daca punctul p este in teren */
	public boolean isInField (Pair p) {
		int x = p.getX();
		int y = p.getY();
		
		return ((x > 0 && x <linesNo && y >= 0 && y < colsNo) || //fie e in teren
				((x == 0 || x == 12) && y >= 3 && y <= 5)); //fie e pe linia portii
	}
	
	
	/* marchez pe teren/ in matrice, mutarea de pe pozitia pos, pe directia
	 * direction
	 */
	public Pair setMoveRev (int dir, Pair pos) {
		f[pos.x][pos.y][dir] = 1;
		f[pos.x][pos.y][8] = 1; // varful din pozitia (x,y) e folosit
		
		//acum setez si drumul invers:
		int opX, opY, opDir; 
		opDir = (dir + 4) % 8;//directia opusa, directiei dir
		
		//pozitia capatului opus muchiei marcate
		opX = pos.x + d[dir].x; 
		opY = pos.y + d[dir].y;
		crtPos = new Pair (opX, opY);

		f[opX][opY][opDir] = 1;
		f[opX][opY][8] = 1; //varful din pozitia opusa;
		
		return new Pair (opX, opY);
	}
	
	/* functie care verifica daca pot face o mutare, in directia primita ca
	 * parametru;
	 * intoarce urmatoarele posibile valori:
	 * 0 - mutare invalida
	 * 1 - mutare valida, iar punctul in care ma mut, nu a mai fost atins
	 * 2 - mutare valida, iar punctul in care ma mut a mai fost atins
	 * 		deci trebuie sa mai fac o mutare
	 */
	public int isValidMove (Pair pos, int to) {
		Pair nextMove = new Pair (pos.x + d[to].x, pos.y + d[to].y);
		
		if (isInField (nextMove)){ //daca nu iese din teren
			if (f[pos.x][pos.y][to] == 1) {//daca s-a mai mers pe aici intorc 0
				return 0;//mutare invalida
			}else{
				//este o mutare valida
				//verific daca s-a mai trecut prin acest punct
				if (f[pos.x][pos.y][8] == 1) {
					return 2;
				} else {
					return 1;
				}
			}
		} else {
			return 0;
		}
	}
}