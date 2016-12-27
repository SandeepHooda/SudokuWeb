package com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RandomModel
 */
@WebServlet("/RandomModel")
public class RandomModel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RandomModel() {
        super();
        // TODO Auto-generated constructor stub
    }
     
    public class solution{
    	public int [][] model;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
   static int[][] board = {{0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 0, 0, 0, 0, 0, 0, 0, 0},
    		{0, 0, 0, 0, 0, 0, 0, 0, 0}};
   public static int [][] model = new int[9][9];
   static {
	// Create the initial situation
	      model[0][0] = 9 ;
	      model[0][4] = 2 ;
	      model[0][6] = 7 ;
	      model[0][7] = 5 ;

	      model[1][0] = 6 ;
	      model[1][4] = 5 ;
	      model[1][7] = 4 ;

	      model[2][1] = 2 ;
	      model[2][3] = 4 ;
	      model[2][7] = 1 ;

	      model[3][0] = 2 ;
	      model[3][2] = 8 ;

	      model[4][1] = 7 ;
	      model[4][3] = 5 ;
	      model[4][5] = 9 ;
	      model[4][7] = 6 ;

	      model[5][6] = 4 ;
	      model[5][8] = 1 ;

	      model[6][1] = 1 ;
	      model[6][5] = 5 ;
	      model[6][7] = 8 ;

	      model[7][1] = 9 ;
	      model[7][4] = 7 ;
	      model[7][8] = 4 ;

	      model[8][1] = 8 ;
	      model[8][2] = 2 ;
	      model[8][4] = 4 ;
	      model[8][8] = 6 ;
   }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		for (int row = 0;row <9;row++){
			int colToBeFilled =0;
			while( true){
				if (colToBeFilled > 2 && colToBeFilled < 6)  break;
				colToBeFilled =(int) (Math.random()*10);
			}
			
			
			String colFilled = "";
			while(true){
				
				int randomCol = (int) (Math.random()*10);
				int randomNum = (int) (Math.random()*10);
				
				
				
				if (randomCol < 9 && randomNum >0 &&randomNum <= 9 && (colFilled.indexOf(""+randomCol) == -1) ){
					if( checkRow(row,randomNum,board) && checkCol(randomCol,randomNum,board) && checkBox(row,randomCol,randomNum,board) ){
						 board[row][randomCol] = randomNum;
						
						 colFilled += randomCol;
					 }
				}
				 
				if (colFilled.length() ==colToBeFilled){
					break;
				}
			}
			
		}
		StringBuilder builder = new StringBuilder("<table  cellspacing=\"0\" cellpadding =\"0\">");
		for (int r =0;r<9;r++){
			builder.append("<tr>");
			for(int c=0;c<9;c++){
				String tdclass ="";
				String ipclassBlack ="blackWriting";
				String ipclassBlue ="blueWriting";
				
				switch(r){
				case 0:{
					 
					 if ((c%3) ==0 ){
						 tdclass ="boxRow1"; 
					 }else {
						 tdclass ="boxRow0";
					 }
					break;
				}
				case 1: case 2: case 4: case 5: case 7:{
					if ((c%3) ==0){
						 tdclass ="middleRow1"; 
					 }else {
						 tdclass ="middleRow0";
					 }
					break;
				}
				
				case 3: case 6:{
					if ((c%3) ==0){
						 tdclass ="boxRow1"; 
					 }else {
						 tdclass ="boxRow0";
					 }
					break;
				}
				
				
				
				case 8:{
					if ((c%3) ==0){
						 tdclass ="lastRow1"; 
					 }else {
						 tdclass ="lastRow0";
					 }
					break;
				}
				}
				if (c == 8){
					tdclass +=" col9"; 
				}
				builder.append("<td class=\""+tdclass+"\" >");
				if(board[r][c] ==0){
					builder.append("<input class=\""+ipclassBlue+"\" style=\"border solid 0; #fff\" size=\"2\" autocomplete=\"off\"  maxlength=\"1\" id=\""+r+c+"\"  />");
				}else{
					builder.append("<input class=\""+ipclassBlack+"\" border=\"solid 0 #fff\"  size=\"2\" autocomplete=\"off\" readonly=\"\" value=\""+board[r][c]+"\" id=\""+r+c+"\" />");
				}
				builder.append("</td>");
			}
			builder.append("</tr>");
		}
		
		builder.append("</table>");
		
		solution sol = new solution();
		sol.model = model;
		try {
			solve( 0, 0, sol ) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		 
		printBoard(model);
		response.getWriter().write(builder.toString());
	}
	
	  /** Recursive function to find a valid number for one single cell */
	   public void solve( int row, int col, solution sol  ) throws Exception
	   {
		   model = sol.model;
	      // Throw an exception to stop the process if the puzzle is solved
	      if( row > 8 )
	         throw new Exception( "Solution found" ) ;

	      // If the cell is not empty, continue with the next cell
	      if( model[row][col] != 0 )
	         next( row, col, sol ) ;
	      else
	      {
	         // Find a valid number for the empty cell
	         for( int num = 1; num < 10; num++ )
	         {
	            if( checkRow(row,num,model) && checkCol(col,num,model) && checkBox(row,col,num,model) )
	            {
	               model[row][col] = num ;
	               

	               // Let the observer see it
	               //Thread.sleep( 1000 ) ;

	               // Delegate work on the next cell to a recursive call
	               next( row, col, sol ) ;
	            }
	         }

	         // No valid number was found, clean up and return to caller
	         model[row][col] = 0 ;
	         
	      }
	   }

	   /** Calls solve for the next cell */
	   public void next( int row, int col, solution sol ) throws Exception
	   {
	      if( col < 8 )
	         solve( row, col + 1, sol ) ;
	      else
	         solve( row + 1, 0 , sol) ;
	   }
	
	/** Checks if num is an acceptable value for the given row */
	   protected static boolean checkRow( int row, int num , int[][] model)
	   {
	      for( int col = 0; col < 9; col++ )
	         if( model[row][col] == num )
	            return false ;

	      return true ;
	   }

	   /** Checks if num is an acceptable value for the given column */
	   protected static boolean checkCol( int col, int num, int[][] model )
	   {
	      for( int row = 0; row < 9; row++ )
	         if( model[row][col] == num )
	            return false ;

	      return true ;
	   }
	   
	   /** Checks if num is an acceptable value for the box around row and col */
	   protected static boolean checkBox( int row, int col, int num , int[][] model)
	   {
	      row = (row / 3) * 3 ;
	      col = (col / 3) * 3 ;

	      for( int r = 0; r < 3; r++ )
	         for( int c = 0; c < 3; c++ )
	         if( model[row+r][col+c] == num )
	            return false ;

	      return true ;
	   }
	public static void printBoard(int[][] board){
		
		for(int r=0;r<9;r++){
			for (int c=0;c<9;c++){
				System.out.print(board[r][c] +" ");
			}
			System.out.println();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
