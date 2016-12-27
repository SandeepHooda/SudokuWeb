package com.dancingLink.algo.data;

public class DataObject {
	public DataObject L, R, U, D, C;
	public int[] rowColValue = null;
	public void printRowColValue(){
		if (null != rowColValue && rowColValue.length == 3){
			System.out.println(rowColValue[0]+" "+rowColValue[1]+" "+rowColValue[2]);
		}
	}
}