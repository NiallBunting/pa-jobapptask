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
	
	private LinkedList<InputStream> inputStreamList;
	
	/**
	 * Creates the file controller. Walks over the folder adding each
	 * file into the linked list.
	 * 
	 * @param Takes the string to the directory of the folder.
	 */
	public FileController(String folder){
		this.inputStreamList = new LinkedList<InputStream>();
		
		try(Stream<Path> paths = Files.walk(Paths.get(folder))) {
		    paths.forEach(filePath -> {
		        if (Files.isRegularFile(filePath)) {
		            this.inputStreamList.add(this.pathToInputStream(filePath));
		        }
		    });
		} catch (IOException e) {
			System.out.println("Error reading path.");
			System.exit(1);
		} 
		
	}
	
	/**
	 * This gets the next input stream from the directory given.
	 * @return InputStream for directory.
	 */
	public InputStream getNextInputStream(){
		if(!this.inputStreamList.isEmpty()){
			return this.inputStreamList.removeFirst();
		}else{
			return null;
		}
	}
	
	/**
	 * This takes a path and returns an InputStream.
	 * 
	 * @param imagePath Path to the image.
	 * @return InputStream Returns the InputStream of the path.
	 */
	private InputStream pathToInputStream(Path imagePath){
		InputStream inputStream = null;
		
		try {
			inputStream = new FileInputStream(imagePath.toString());
		} catch (FileNotFoundException e1) {
			System.out.println("File not found.");
			System.exit(1);
		}
		
		return inputStream;
	}
	
	/**
	 * This takes an inputStream and looks for a tag.
	 * 
	 * @param inputStream The input stream to search.
	 * @param tagName The string of the tag name.
	 * @return String Returns the tag value as string or null.
	 */
	public String GetTagValue(InputStream inputStream, String tagName){
		
		try{
			//Read the metadata using metadata-extractor
			Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
			
			//Get the Directorys
			Iterable<Directory> directoryCollection = metadata.getDirectories();
			
			for(Directory dir : directoryCollection){
				
				//Iterator of all tags in directory.
				Iterator<Tag> tags = dir.getTags().iterator();
				
				while(tags.hasNext()){
					Tag tag = tags.next();
					//Tag name is the string.
					if(tag.getTagName() == tagName){
						//Get the value from the hashtable using the type.
						return dir.getString(tag.getTagType());	
					}
					
				}
				//Use int as key for hashtable.
			}
			
				
				
		} catch (ImageProcessingException e) {
			System.out.println("Image processing exception.");
			System.exit(1);
		} catch (IOException e) {;
			System.out.println("IOException.");
			System.exit(1);
		}
		
		return null;
		
	}
}
