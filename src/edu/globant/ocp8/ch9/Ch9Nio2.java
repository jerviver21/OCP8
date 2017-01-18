package edu.globant.ocp8.ch9;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Checklist:
 * 1. Creation of Paths
 * 2. Declaration of handling of IOException
 *
 */
public class Ch9Nio2 {

	
	
	public static void main(String[] args)throws IOException {
		Ch9Nio2 object = new Ch9Nio2();
		//object.pathsCreationNormalizeAndRelativize();
		//object.testingDelete();
		object.testingAttributes();
		
	}
	
	public void testingAttributes()throws IOException{
		Path file1 = Paths.get("/home/developer/ocp8/dir0/file.txt");
		BasicFileAttributes attributes = Files.readAttributes(file1, BasicFileAttributes.class);
		System.out.println("For reading use - readAttributes(Path, class) -");
		BasicFileAttributeView view = Files.getFileAttributeView(file1, BasicFileAttributeView.class);
		System.out.println("For writing use - getFileAttributeView(Path, class) -");
		if(attributes.size() > 0 && attributes.creationTime().toMillis() > 0){
			view.setTimes(null, null, null);
			System.out.println("setTimes(null, null, null) do not modify anything!");
		}
		
	}
	
	public void pathsCreationNormalizeAndRelativize(){
		try{
			Path f0 = new File("/home/developer/ocp8").toPath();
			Path file1 = Paths.get("/tmp/.././home/developer","ocp8/dir1");
			Path file2 = FileSystems.getDefault().getPath("/home/developer");
			System.out.println("To create a Path use Paths.get() or FileSystema.getDefault().getPath() or new File().toPath");
			
			Files.createDirectories(file1);
			System.out.println("To create a directory use Files.createDirectory(Path)");
			
			Path normalizedPath =  file1.normalize();
			System.out.printf("The normilized version of this - /tmp/.././home/developer/ocp8/dir1 - is %s", normalizedPath.toString());
			System.out.println("");
			
			
			Path fileRelativeToHome = normalizedPath.relativize(file2);
			System.out.printf(" (%s).relativize(/home/developer) is %s  (Que tengo que hacerle a Path1 para ser Path2  Path1.relativize(Path2))", normalizedPath.toString(), fileRelativeToHome.toString());

		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void testingDelete()throws IOException{
		try{
			Path p1 = Paths.get("/home/developer/ocp8/dir0");//Have files
			boolean delete = Files.deleteIfExists(p1);
			System.out.printf("%s is posible to delete a no empty File ", delete);
			System.out.println();
		}catch(IOException e){
			System.out.printf("is not posible to delete a no empty File ");
			System.out.println();
		}
		
		Path emptyDir = Paths.get("/home/developer/ocp8/dir1");

		System.out.printf("%b is posible to delete an empty file ", Files.deleteIfExists(emptyDir));
		Files.createDirectory(emptyDir);
		System.out.println();
		
		System.out.printf("%b is posible to delete a link that points to an empty file ", Files.deleteIfExists(Paths.get("/home/developer/ocp8/dir2")));
		
	}

}
