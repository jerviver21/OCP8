package edu.paidea.ocp8.ch8;

import java.io.BufferedInputStream;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * 
 * checklist:
 * 
 * 1- creation param for low leves and high levels
 * 2- compatibilidad de objetos con archivos.
 *
 */
public class Ch8IO {

	public static void main(String[] args)throws Exception {
		Ch8IO obj = new Ch8IO();
		//obj.testCreation();
		obj.testConsole();
	}
	
	public void testCreation()throws IOException{
		InputStream is = new BufferedInputStream(new FileInputStream("/home/jerviver21/ocp8/dir0/file0.txt"));
		InputStream w1 = new BufferedInputStream(is);
		//InputStream w2 = new ObjectInputStream(is);
		
		OutputStream o1 = new PrintStream(new FileOutputStream("/home/jerviver21/ocp8/dir0/file0.txt"));

		is.close();
		o1.close();
		
		System.out.println("BufferedInputStream Alto nivel (InputStream)");
		System.out.println("ObjectInputStream Alto nivel (InputStream)");
		System.out.println("FileInputStream bajo nivel (File|String)");
		System.out.println("Misma lógica para Readers con Readers, Writers con Writers y OutputStreams con OutputStreams");
		
		System.out.println("Abstractas: InputStream, OutputStream, Reader, Writer");
		System.out.println("Low Level: FileInputStream, FileOutputStream, FileReader, FileWriter");
		
		System.out.println("Hight Level: BufferedIS, BufferedOS, BufferedReader, BufferedWriter, \n"
				+ "ObjectInputStream, ObjectOutputStream, PrintStream, PrintWriter");
		System.out.println("Los Objects solo leen objetos o lanzarán exception");
		
	}
	
	public void testFile(){
		System.out.println("Con File no se declaran las exception, pero se podría lanzar");
		File file = new File("/home/jerviver21/ocp8/dir0/file0.txt");
		
		if(!file.isFile()){
			for(File f : file.listFiles()){
				
			}
		}else{
			file.delete();
		}
	}
	
	
	public void testConsole(){
		Console console = System.console();
		if(console != null){
			String usr = console.readLine("Digite su usuario:");
			
			char[] pass = console.readPassword("Digite su password:");
			
			console.printf(usr+" - "+pass);
			console.format(usr+" - "+pass);
			
			console.writer().println(usr+" - "+pass);
			console.writer().append("Hola: ");
			console.writer().flush();
			
			
		}else{
			System.out.println("Console is null");
			System.out.println("char[] del password es por seguridad, para evitar el string reusable del pool y ademas se puede remover más fácil de memoria");
			System.out.println("Al construirlo desde IDE da null, su construcción es através de un Singleton");
		}
		
	}

}
