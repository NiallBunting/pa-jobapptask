package com.niallbunting.pajobapptask;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Iterator;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class File {

	private InputStream inputStream;
	private Path path;

	public File(Path path){
		this.path = path;
		this.inputStream = this.pathToInputStream(path);
	}

	public InputStream getInputStream(){
		return this.inputStream;
	}

	public Path getPath(){
		return this.path;
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
	 * Looks for tag in image
	 * 
	 * @param tagName The string of the tag name.
	 * @return String Returns the tag value as string or null.
	 */
	public String getTagValue(String tagName){

		try{
			//Read the metadata using metadata-extractor
			Metadata metadata = ImageMetadataReader.readMetadata(this.inputStream);

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
