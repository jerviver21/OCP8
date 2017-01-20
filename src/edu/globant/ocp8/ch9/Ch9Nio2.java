package edu.globant.ocp8.ch9;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;
import java.util.stream.Collectors;

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
		//object.testingAttributes();
		//object.testingToAbsolutePath();
		//object.testingSymbolicLink();
		//object.testingWalk();
		//object.testingResolve();
		//object.testingLines();
		//object.testingMove();
		//object.testingCopy();
		//object.testingFind();
		//object.testingFlatMap();
		//object.testingNio();
		object.testingNormalize();
		
	}
	
	public void testingNormalize()throws IOException{
		Path path0 = Paths.get(".").normalize();

		System.out.println("-"+path0+"- is the return of normalize - COUNT= "+path0.getNameCount()+" name: -"+path0.getName(0)+"- "+path0.toAbsolutePath());
		
		int count = 0;
		for(int i=0; i<path0.getNameCount(); ++i) {
			count++;
		}
		System.out.println(count);


	}
	
	public void testingNio()throws IOException{
		Path path0 = Paths.get(".").toRealPath();
		Path path1 = path0.getParent();
		System.out.println(path0+" is the return of toRealPath");
		System.out.println(path1+" is the return of getParent");
		Files.walk(path1.resolve("OCP8"))
			.map(p -> p.toAbsolutePath().toString())
			.filter(p -> p.matches(".*.java"))
			.forEach(System.out::println);
	}
	
	public void testingFlatMap()throws IOException{
		Path path0 = FileSystems.getDefault().getPath("/home/jerviver21/ocp8/dir0/file0.txt");
		System.out.println("flatMap, siempre lleva como retorno un Stream, no importa como lo cree un Stream.of(List1, List2) es algo tipico");
		/*Files.lines(path0)
			.flatMap(p -> Stream.of(p.split(",")))
			.map(s -> s.toUpperCase())
			.forEach(System.out::println);*/

	}
	
	public void testingFind()throws IOException{
		Path path0 = FileSystems.getDefault().getPath("/home/jerviver21/ocp8/dir0");
		System.out.println("Probando Files.find");
		Files.find(path0,0, (p,a)->Files.isDirectory(path0))
			.map(p -> p.toString())
			.collect(Collectors.toList())
			.forEach(System.out::println);
		
		System.out.println("find recibe Path, int profundidad de busqueda, y un Predicate (condición de búsqueda que debe tener el Path)");
		System.out.println("map(Function<T,R>), y collect un Collector");
		
		Files.list(path0).forEach(System.out::println);
		System.out.println("Mientras que el primer Path que retorna find es si mismo, list solo retorna lo que este dentro");
	}
	
	public void testingCopy()throws IOException{
		Path path0 = Paths.get("/home/jerviver21/ocp8/dir0/file0.txt");
		Path path1 = Paths.get("/home/jerviver21/ocp8/dir1/file0.txt");
		
		Files.copy(path0
				,path1
				,StandardCopyOption.COPY_ATTRIBUTES
				,LinkOption.NOFOLLOW_LINKS);
		
		System.out.println("isSameFiles is : "+Files.isSameFile(path0, path1)+" when is the same file in diferent locations");
		
		
	}
	
	
	
	public void testingMove()throws IOException{
		System.out.println("Files.move mueve el archivo con todo y metadata");
		Files.move(Paths.get("/home/jerviver21/ocp8/dir1/dir10")
				,Paths.get("/home/jerviver21/ocp8/dir0/file0.txt")
				,StandardCopyOption.ATOMIC_MOVE
				,LinkOption.NOFOLLOW_LINKS);
	}
	
	public void testingLines()throws IOException{
		Path path0 = FileSystems.getDefault().getPath("/home/jerviver21/ocp8/dir0");
		System.out.println("Trabaja con archivos grandes, y con Streams es una gran ventaja");
		Files.lines(
				Files.list(path0)
					.peek(System.out::println)
					.findFirst().get())
		.forEach(System.out::println);;
	}
	
	public void testingResolve()throws IOException{
		Path path1 = Paths.get("/pets/../cat.txt/./dog.txt");
		Path path2 = Paths.get("./dog.txt");
		
		System.out.println("Resolve une una relativa a otra relativa o absuluta: "+path1.resolve(path2));
		System.out.println("Si la que entra como parámetro es absoluta entonces ignora la otra "+path2.resolve(path1));
		
	}
	
	public void testingWalk()throws IOException{
		Path path0 = Paths.get("/home/jerviver21/ocp8/dir1");
		Optional<Path> path1 = Files.walk(path0)
				.filter((a)-> Files.isDirectory(a))
				.findFirst();
		
		System.out.println(" Files.walk retorna un Stream<Path>, filter recibe un Predicate<Path>, "
				+ "findFirst() encuentra \n el primero y retorna un Optional, si hay archivos es ese directorio? "+path1.get());
		
		Files.walk(path0)
		.filter((a)-> Files.exists(a))
		.forEach(p -> System.out.println(p));
		
		System.out.println("Recuerde que walk es depth hasta Integer.MAX_VALUE");
		
	}
	
	public void testingSymbolicLink()throws IOException{
		Path path0 = Paths.get("/home/jerviver21/ocp8/dir1");
		Path path1 = Paths.get("/home/jerviver21/ocp8/dir3");
		Path path2 = path1.resolve("joey");
		
		//Files.deleteIfExists(path0);
		
		if(Files.isDirectory(path1) && Files.isSymbolicLink(path1) && !Files.exists(path2)){
			Files.createDirectory(path1.resolve("joey"));
		}
		
		System.out.println("Resolve joins two paths: "+Files.exists(path2)+" - "+path2+ " Is directory: "
		+Files.isDirectory(path1)+" - is simbolic: "+Files.isSymbolicLink(path1));
		System.out.println("Symbolic link could point to a no existing directory!   ?? does not work for me :( ");
	}
	
	public void testingToAbsolutePath()throws IOException{
		Path path = Paths.get("/zoo/animals/bear/koala/food.txt");
		Path subpath = path.subpath(1, 3);
		System.out.println("For this path: "+path);
		System.out.println("subpath(1,3): Subpath is last minus first including the first (0-index: without consider /) : "+subpath);

		Path dir1 = subpath.getName(1);
		System.out.println("getName(1): "+dir1);
		
		System.out.println("toAbsoutePath works considering the current directory (if the path is relative it adds the absolute path of the current): "+dir1.toAbsolutePath());
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
