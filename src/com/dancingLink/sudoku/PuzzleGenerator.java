package com.dancingLink.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dancingLink.algo.DLXSudokuSolver;
import com.dancingLink.algo.data.DataObject;
import com.dancingLink.algo.data.PuzzleObject;
import com.dancingLink.algo.data.PuzzleStatusCheck;

public class PuzzleGenerator {

	final static byte puzzle[] = {
			0, 0, 6, 9, 0, 0, 0, 7, 0,
			0, 0, 0, 0, 1, 0, 0, 0, 2,
			8, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 2, 0, 0, 0, 0, 0, 0, 4,
			0, 0, 0, 0, 0, 0, 0, 0, 1,
			0, 0, 5, 0, 0, 6, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 6, 0,
			0, 0, 0, 0, 0, 2, 0, 5, 0,
			0, 1, 0, 0, 4, 3, 0, 0, 0,
	};
	
	final static  byte puzzleBlank[] = {
			0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0,
	};
	
	private static byte[] generateSolvedBytes(List<DataObject> solution){
		byte solvedPuzzle[] = puzzleBlank.clone();
		for (DataObject sol: solution){
			solvedPuzzle[(sol.rowColValue[0] - 1)*9 + (sol.rowColValue[1] - 1)] = (byte)sol.rowColValue[2];
		}
		return solvedPuzzle;
	}
	public static PuzzleObject getSolutionOfAPuzzle(byte[] problem, List<DataObject> solution) {
		DLXSudokuSolver solver = new DLXSudokuSolver();
		PuzzleObject puzzle = new PuzzleObject();
		
		PuzzleStatusCheck puzzleStatus = solver.createPuzzle(problem, solution, 2);
		if (!puzzleStatus.legal || puzzleStatus.solutionFoundSoFar.size() > 1) {
			return null;
		}
		 byte[] solvedPuzzle = generateSolvedBytes(puzzleStatus.solutionFoundSoFar.get(0));
		 puzzle.solution = solvedPuzzle.clone();
		 return puzzle; 
	}
	public static PuzzleObject generateAPuzzle() {
		Random random = new Random();
		DLXSudokuSolver solver = new DLXSudokuSolver();
		List<DataObject> solution = new ArrayList<DataObject>();
		PuzzleStatusCheck puzzleStatus = null;
		byte[] solvedPuzzle = puzzleBlank.clone();
		
		//this will generate a random puzzle set of 1000 then chose a random puzzle, then then clear rows
		//8 to 1, 7 to 1, 6 to 1 so on. After each time we clear rows (make them 0) we generate a new puzzle new puzzle and chose random puzzle
		for (int rowNotToclear =8;rowNotToclear>=1;rowNotToclear--){
			 puzzleStatus = solver.createPuzzle(solvedPuzzle, solution, 1000);
		
			 if (puzzleStatus.solSize > 5){
				
				 solvedPuzzle = generateSolvedBytes(puzzleStatus.solutionFoundSoFar.get(random.nextInt(puzzleStatus.solSize)));
			 }else {
				 solvedPuzzle = generateSolvedBytes(puzzleStatus.solutionFoundSoFar.get(0));
			 }
			 for (int cellVal = rowNotToclear*9;cellVal>=1;cellVal--){
				 solvedPuzzle[cellVal-1] = 0;
			 }
			 
		}
		
		puzzleStatus = solver.createPuzzle(solvedPuzzle, solution, 1000);
		solvedPuzzle = generateSolvedBytes(puzzleStatus.solutionFoundSoFar.get(0));
		
		
		if (!puzzleStatus.legal){
			System.out.println(" puzzle is not legal");
			for (int cellVal = 0;cellVal<81;cellVal++){
				 System.out.print(", "+solvedPuzzle[cellVal] );
			 }
		}
		
		PuzzleObject puzzle = new PuzzleObject();
		puzzle.solution = solvedPuzzle.clone();
		int cellPos = -1;
		byte cellValue = 0;
		while(true){
			cellPos = random.nextInt(81);
			cellValue = solvedPuzzle[cellPos];
			solvedPuzzle[cellPos] = 0;
			puzzleStatus = solver.createPuzzle(solvedPuzzle, solution, 2);
			if (puzzleStatus.solSize > 1){
				break;//We can't further omit data from puzzle and still have only one solution to puzzle
			}
		}
		
		solvedPuzzle[cellPos] = cellValue;//put that removed value back to that puzzle has only one solution
		puzzle.puzzle = solvedPuzzle.clone(); 
		
		
		StringBuilder sb = new StringBuilder();
		for (int cellVal = 0;cellVal<81;cellVal++){
			sb.append(puzzle.solution[cellVal]);
			 //System.out.print(", "+puzzle.solution[cellVal] );
		 }
		puzzle.solStr = sb.toString();
		return puzzle;
	}

}
