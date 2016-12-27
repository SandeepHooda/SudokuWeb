package com.dancingLink.algo.data;

import java.util.ArrayList;
import java.util.List;

public class PuzzleStatusCheck {
	
	public Boolean legal = false;
	public Boolean complete = false;
	public Integer solSize = 0;
	public Integer returedDueToSolSizeOverFlow = 0;
	public List<List<DataObject>> solutionFoundSoFar = new ArrayList<List<DataObject>>();

}
