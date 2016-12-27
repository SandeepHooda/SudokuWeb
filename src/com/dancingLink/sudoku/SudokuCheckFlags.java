package com.dancingLink.sudoku;

import com.dancingLink.sudoku.SudokuUtils;


/**
 * Flags which are used in conjunction with the {@link SudokuUtils}
 * <code>checkX()</code> family of functions to test whether a row is solved,
 * unsolved or broken.
 */
public enum SudokuCheckFlags {
	ERROR, UNSOLVED, SOLVED
}
