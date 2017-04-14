package com.niallbunting.pajobapptask;

import java.util.Scanner;

public class PaJobAppTask {
	
	public void main(){
		
		String dirPath = this.ReadFolder();
		FileController fileController = new FileController(dirPath);
		
		
	}
	
	private String ReadFolder(){
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Enter Folder Path: ");
		
		return scanner.next();
	}
	

}
