package edu.paidea.ocp8.ch4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * checklist
 * 
 * 0- Verificar terminal operation
 * 1- Semilla de un reduce debe ser compatible con el tipo
 * 2- Stream gastados no se pueden volver a usar, ojo si los ponen en sysout
 * 3- infinitos con all, none y any (retornan boolean, entra predicate) sobre el papel
 * 4- compatibilidad mapToTipo con Tipo
 * 5- revisión de genericos de las interfaces funcionales
 * 
 *
 */
public class Ch4Streams {

	public static void main(String[] args) {
		Ch4Streams ch4 = new Ch4Streams();
		//ch4.testIterate();
		//ch4.testGeneric();
		//ch4.testCompare();
		//ch4.testIntStream();
		//ch4.testJoining();
		//ch4.testAsList();
		//ch4.testPartition();
		ch4.otherTest();
	}
	
	public void otherTest(){
		DoubleStream s = DoubleStream.of(1.2, 2.4);
		s.peek(System.out::println).filter(x -> x > 2).count();
		
		List<Integer> l = IntStream.range(1, 6)
				.mapToObj(i -> i).collect(Collectors.toList());
				l.forEach(System.out::println);
				
		System.out.println("Ojo que range no incluye el ultimo. y Solo funciona para IntStream o LongStream");
		LongStream.range(1, 6).forEach(System.out::println);
	}
	
	public void testPartition(){
		Map<Boolean, Set<String>> pb = Stream.of("Jerson", "Yaneth", "Ludivia", "Michael")
				.collect(Collectors.partitioningBy(s -> s.length() == 6, Collectors.toSet()));
		
		System.out.println(pb);
		
		
		System.out.println("PartitioningBy siempre saca al menos [false={}, true={}], así el stream esté vacio");
		Stream<String> s = Stream.empty();
		Stream<String> s2 = Stream.empty();
		Map<Boolean, List<String>> p = s.collect( Collectors.partitioningBy(b -> b.startsWith("c")));
		Map<Boolean, List<String>> g = s2.collect(Collectors.groupingBy(b -> b.startsWith("c")));
		System.out.println(p + " " + g);
	}
	
	public void testAsList(){
		
		System.out.println("Stream.flatMap(Function<T,Stream)");
		List<Integer> l1 = Arrays.asList(1,2,3);
		List<Integer> l2 = Arrays.asList(4,5,6);
		List<Integer> l3 = Arrays.asList();
		
		Stream.of(l1, l2, l3).flatMap(x -> x.stream()).forEach(System.out::println);
		
		
		Stream<Integer> s = Stream.of(1);
		IntStream is = s.mapToInt(x -> x);
		DoubleStream ds = is.mapToDouble(x -> x);
		System.out.println("mapToObj no mapea a un generico si el tipo del retorno  no es el del generico");
		Stream<Integer> s2 = ds.mapToObj(x -> new Integer(x+""));
		s2.forEach(System.out::print);

	}
	
	public void testJoining(){
		
		System.out.println(" return x++, retorna x antes de incrementar");
		System.out.println("map (Function<T,R>");
		String c = Stream.iterate(1, x ->  ++x)
			.limit(5)
			.map(x -> x+"")
			.collect(Collectors.joining(","));
		System.out.println(c);
		
		System.out.println("Collectors.joinin() retorna un String, y debe crearse a partir de un Stream de Strings");


	}
	
	public void testIntStream(){
		IntStream is = IntStream.empty();
		System.out.println("Se puede crear un Stream empty con empty()");
		
		System.out.println("average():OptionalDocuble: "+is.average());
		System.out.println("sum():int "+IntStream.empty().sum());
		System.out.println("findAny:OptionalInt "+IntStream.empty().findAny());
		
		LongStream ls = LongStream.of(1, 2, 3);
		OptionalLong ol = ls.map(s -> s*10).filter(n -> n< 50).findFirst();
		

		if(ol.isPresent())
			System.out.println("OptionalLong.getAsLong: "+ol.getAsLong());
		
		ol.ifPresent(System.out::println);
		
		
		Stream.generate(() ->  "1")
			.limit(10)
			.peek(System.out::println)
			.filter(x -> x.length()>1)
			.forEach(System.out::println);
	}
	
	public void testCompare(){
		List<String> copy = new ArrayList<>();
		copy.add("A");
		copy.add("C");
		copy.add("B");
		
		System.out.println("Lista sin ordenar "+copy);
		
		Collections.sort(copy, (a,b) -> b.compareTo(a));
		
		System.out.println("Lista ordenada con b.compareTo(a): "+copy);
		
		copy = copy.stream().sorted((a,b) -> a.compareTo(b)).collect(Collectors.toList());
		
		System.out.println("Lista ordenada con a.compareTo(b): "+copy);
		
		System.out.println("Stream.sorted(Comparator) ");
		
		
	}

	public void testIterate(){
		System.out.println("Stream.iterate(T, Function<T,R>) genera un stream infinito, lo que se retorna en la función es la entrada en la siguiente iteración, ojo con eso");
		Stream<String> stream = Stream.iterate("", s -> s+"1");
		System.out.println(stream.limit(2).map(s -> s+"2"));
		System.out.println("Ojo que stream se gastó y ya no se puede referenciar");
		System.out.println(Stream.iterate("", s -> s+"1").limit(2).map(s->s+"2").collect(Collectors.toList()));
		System.out.println(Stream.iterate("", s -> s+"1").limit(5).map(s->s+"2").collect(Collectors.toList()));
	}
	
	public void testGeneric(){
		System.out.println("<? super String> implica a String o cualquier Super de String");
		Predicate<? super String> p1 = s -> s.startsWith("g");
		System.out.println("Stream.generate(Supplier<T>)!");
		Stream<String> stream1 = Stream.generate(() -> "growl! ");
		Stream<String> stream2 = Stream.generate(() -> "growl! ");
		System.out.println("Stream.anyMatch(Predicate<T>):boolean, Stream.allMatch(Predicate<T>):boolean");
		boolean b1 = stream1.anyMatch(p1);
		boolean b2 = stream2.limit(4).allMatch(p1);
		System.out.println("allMatch sin limit se colgaria. "+b1+" - "+b2);
		
		Predicate<? super String> p2 = s -> s.length() > 3;
		Stream<String> stream = Stream.iterate("-", (s) -> s + s);
		b1 = stream.noneMatch(p2);
		System.out.println("Primera norma del checklist no se puede reusar streams");
		//b2 = stream.anyMatch(p2);
		b2 = Stream.iterate("-", (s) -> s + s).anyMatch(p2);
		System.out.println(b1 + " " + b2);
	}
	

}
