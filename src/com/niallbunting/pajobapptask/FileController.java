package com.niallbunting.pajobapptask;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.iptc.IptcDirectory;

public class FileController {

	private LinkedList<File> fileList;
	private String folder;
	
	/**
	 * Creates the file controller. Walks over the folder adding each
	 * file into the linked list.
	 *
	 * @param Takes the string to the directory of the folder.
	 */
	public FileController(String folder){
		this.fileList = new LinkedList<File>();
		this.folder = folder;

		//Add slash to end of the folder
		if(this.folder.charAt(this.folder.length()-1) != '/'){
			this.folder = this.folder + "/";
		}

		try(Stream<Path> paths = Files.walk(Paths.get(this.folder))) {
		    paths.forEach(filePath -> {
		        if (Files.isRegularFile(filePath)) {
		            this.fileList.add(new File(filePath));
		        }
		    });
		} catch (IOException e) {
			System.out.println("Error reading path.");
			System.exit(1);
		}

	}

	/**
	 * This gets the next file from the directory given.
	 * @return File of image.
	 */
	public File getNextFile(){
		if(!this.fileList.isEmpty()){
			return this.fileList.removeFirst();
		}else{
			return null;
		}
	}

	/**
	 * Takes a file and a target folder name and moves the file into it.
	 * @param file The file to move.
	 * @param target Name of the target folder.
	 */
	public void writeFile(File file, String target){
		try {
			Files.createDirectories(Paths.get(this.folder + target));
		} catch (IOException e1) {
			System.out.println("Could not create directories.");
			System.exit(1);
		}

		try {
			Files.copy(file.getPath(), Paths.get(this.folder + target + "/" + file.getPath().getFileName()));
		} catch (IOException e) {
			System.out.println("IOException: Failed to copy file...");
		}
	}

}
