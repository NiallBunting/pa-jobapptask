package com.niallbunting.pajobapptask;

import java.io.InputStream;
import java.util.Scanner;

public class PaJobAppTask {
	
	public void main(){
		
		String dirPath = this.ReadFolder();
		FileController fileController = new FileController(dirPath);
		
		while(true){
			File file = fileController.getNextFile();

			//All files are complete in this case
			if(file == null){break;}

			String tag =file.getTagValue("Copyright Notice");

			if(tag.contains("Editorial")){
				fileController.writeFile(file, "newspaper");
			}else if(tag.contains("Sport")){
				fileController.writeFile(file, "sportmagazine");
			}else if(tag.contains("Singer") || tag.contains("Showbiz") || tag.contains("Dance")){
				fileController.writeFile(file, "showbizwebsite");
			}
		}
	}

	/**
	 * This reads the folder path from the user.
	 * @return String user inserted path.
	 */
	private String ReadFolder(){
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Enter Folder Path: ");
		
		return scanner.next();
	}
	

}
