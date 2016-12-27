package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.RandomModel.solution;
import com.dancingLink.algo.data.DataObject;
import com.dancingLink.algo.data.PuzzleObject;
import com.dancingLink.sudoku.PuzzleGenerator;

/**
 * Servlet implementation class GetPuzzle
 */
@WebServlet("/GetPuzzle")
public class GetPuzzle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPuzzle() {
        super();
        // TODO Auto-generated constructor stub
    }

    private static StringBuilder printPuzzleOnBoard(byte[] board , boolean generateReadOnly){
    	String readOnly = "readonly=\"\"";
    	if (!generateReadOnly){
    		readOnly = "";
    	}
    	StringBuilder builder = new StringBuilder("<table  cellspacing=\"0\" cellpadding =\"0\" id=\"puzzleboard\" name=\"puzzleboard\">");
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
				if(board[r*9 + c] ==0){
					builder.append("<input class=\""+ipclassBlue+"\" style=\"border solid 0; #fff\" size=\"2\" autocomplete=\"off\"  maxlength=\"1\" id=\""+r+c+"\"  />");
				}else{
					builder.append("<input class=\""+ipclassBlack+"\" border=\"solid 0 #fff\"  size=\"2\" autocomplete=\"off\" "+readOnly+" value=\""+board[r*9 + c]+"\" id=\""+r+c+"\" />");
				}
				builder.append("</td>");
			}
			builder.append("</tr>");
		}
		
		builder.append("</table>");
		return builder;
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	byte puzzle[] = new byte[81];
    	String problemStr = request.getParameter("problem");
    	String cheater = request.getParameter("cheater");
    	if ("blankBoard".equals(problemStr)){
    		for(int i= 0;i<81;i++){
        		puzzle[i] = 0;
        	}
    		StringBuilder builder = printPuzzleOnBoard(puzzle, false);
    		response.getWriter().write("<span id =\"suduko\">"+builder.toString()+"</span><input type=\"hidden\" id=\"cheater\" value=\""+cheater+"\"></input>");

    	}else {
    		for(int i= 0;i<81;i++){
        		puzzle[i] = Byte.parseByte(""+problemStr.charAt(i));
        	}
        	List<DataObject> solution = new ArrayList<DataObject>();
        	PuzzleObject puzzleObj = PuzzleGenerator.getSolutionOfAPuzzle(puzzle ,solution);
        	if(null != puzzleObj){
        		StringBuilder builder = printPuzzleOnBoard(puzzleObj.solution, false);
        		response.getWriter().write("<span id =\"suduko\">"+builder.toString()+"</span><input type=\"hidden\" id=\"cheater\" value=\""+cheater+"\"></input>");

        	}else{
        		StringBuilder builder = printPuzzleOnBoard(puzzle, false);
        		response.getWriter().write("<span id =\"suduko\">"+builder.toString()+"</span><input type=\"hidden\" id=\"cheater\" value=\""+cheater+"\"></input><br/> This is not a valid puzzle.</span>");

        	}
        	   
    	}
    	
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PuzzleObject puzzle = PuzzleGenerator.generateAPuzzle();
		   byte[] board = puzzle.puzzle;
		   StringBuilder builder = printPuzzleOnBoard(board, true);
			
			response.getWriter().write("<span id =\"suduko\">"+builder.toString()+"</span><input type=\"hidden\" id=\"cheater\" value=\""+puzzle.solStr+"\"></input>");
		
	}

}
