package edu.paidea.ocp8.ch7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Checklist:
 * 1- diferenciar métodos de ExecutorService y SchedulleExecutorService
 * 2- condición de RecursiveTask en compute() para el return debe cumplirse
 * 3- Verificación de los InterruptedException (timeouts, Future.get, join())
 * 4- Verificar el shutdown
 *
 */
public class Ch7Concurrency {
	AtomicInteger sheepCount1 = new AtomicInteger(0); // w1
	int sheepCount2 = 0;

	public static void main(String[] args) throws Exception{
		Ch7Concurrency ch7 = new Ch7Concurrency();
		//ch7.testCreation();
		ch7.testCallableRunable();
		//ch7.testSchedulle();
		//ch7.testAtomicsVariables();
		//ch7.testConcurrentCollections();
		//ch7.testSynchronized();
		//ch7.testReduce();
		//ch7.testFlatMap();
		//ch7.testBlockingDeque();
		//ch7.testSubmit();
		//ch7.testExecute();
		//ch7.testCyclicBarrier();
		//ch7.testSingleThread();
	}
	
	public void testSingleThread(){
		ExecutorService service = Executors.newSingleThreadExecutor();
		final List<Future<?>> results = new ArrayList<>();
		IntStream.range(0, 10)
			.forEach(i -> results.add(service.submit(() -> performCount(i)))); // o2
		
		results.stream().forEach(f -> printResults(f));
		service.shutdown();
	}
	
	
	public  Integer performCount(int exhibitNumber) {
		throw new RuntimeException();
		//return null;
	}
	
	public  void printResults(Future<?> f) {
		try {
			System.out.println(f.get()); // o1
		} catch (Exception e) {
			System.out.println("Exception!");
		}
	}
	
	private void testExecute()throws InterruptedException{
		System.out.println("El Thread.sleep no da garantia de que la salida sea 100 - 100, pero si se \n "
				+ "asume en la pregunta que es suficiente tiempo, entonces si seria 100 - 100");
		final ExecutorService service = Executors.newSingleThreadExecutor();
		try{
			for(int i=0; i<100; i++)
				service.execute(() -> {
					sheepCount1.getAndIncrement(); 
					sheepCount2++;
					}); // w3
				Thread.sleep(1);
				System.out.println(sheepCount1+" "+sheepCount2);
		}finally{
			if (service!= null)
				service.shutdown();
		}
	}
	
	public void testCyclicBarrier(){
		CyclicBarrier cb = new CyclicBarrier(4, () -> System.out.println("Stock Room Full!"));
		IntStream.iterate(1, i -> 1).limit(4).parallel().forEach(c -> await(cb));
		
		System.out.println("cada hilo independiente se ejecuta, y al llamar a await(cb) se queda esperando hasta completar 10 hilos, como el stream da nueve, se va a quedar colgado");
		System.out.println("En este pc se cuelga con 10, porque solo tiene 8 procesadores, y el paralell solo crea 8 hilos, por eso se queda en un deadlock");
	}
	
	public void await(CyclicBarrier cb){
		try{
			cb.await();
		}catch(InterruptedException | BrokenBarrierException e){
			
		}
	}
	
	private void testSubmit(){
		System.out.println("Si se usa una variable de un método local en una clase \ninterna o lambda, la variable debe ser efectivamente final. ");
		System.out.println("forEach(DoubleConsumer), execute(Runnable), submit(Runnable|Callable)");
		System.out.println("Esta expresión es valida como si no retornara nada c -> service.submit(() -> System.out.println(10*c))");
		System.out.println("Aunque para mi retorna un Future!!");
		System.out.println("El enigma se resuelve que podría ser usado en ambos contextos, porque por si solo no seria un error");
		System.out.println("Ejemplo si pones 10;  por si solo daria error, por eso solo se puede usar como retorno");
		
		final ExecutorService service = Executors.newScheduledThreadPool(10);
		try{
			DoubleStream.of(3.14159, 2.71828)
				.forEach(c -> service.submit(() -> System.out.println(10*c)));
			
			service.execute(() -> System.out.println("Printed"));
			
			DoubleStream.of(3.14159, 2.71828)
			.forEach(c -> System.out.println(c));
			
			Future<?> task = service.submit(() -> System.out.println(10));
			
		}finally{
			if (service!= null)
				service.shutdown();
		}
	}
	
	private void testBlockingDeque() throws InterruptedException{
		BlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
		deque.offer(1);
		deque.offerFirst(20, 1, TimeUnit.SECONDS);
		deque.offerLast(5, 7, TimeUnit.HOURS);
		System.out.println(deque.pollFirst(200, TimeUnit.NANOSECONDS));
		System.out.println(deque.pollLast(1, TimeUnit.MINUTES));
	}

	public void testCreation(){
		Collection c = new ArrayList<>();
		Stream s = Stream.of(1,2,3);
		System.out.println("Collection use c.parallelStream(): "+c.parallelStream());
		System.out.println("Stream use s.parallel(): "+s.parallel());
	}
	
	
	public void testCallableRunable(){	
		ExecutorService executor = null;
		try{
			executor = Executors.newFixedThreadPool(10);
			System.out.println("ExecutorService métodos importantes execute(Runnable), submit(Runnable), submit(Callable) ");
			//executor = Executors.newSingleThreadExecutor();
			executor.submit(()-> System.out.println("Runnable: () return void;"));
			Future<Integer> result = executor.submit(()-> {
															System.out.println("Callable: ()throws Exception return T;"); 
															return 5+10;
													});
			System.out.println("Main Thread!");
		}finally{
			if(executor!=null)
				executor.shutdown();
		}
	}
	
	public void testSchedulle()throws InterruptedException, ExecutionException{
		//ExecutorService executor = null;
		ScheduledExecutorService executor = null;
		System.out.println("SchedulleExecutorService es un ExecutorService, con este se pueden mandar tareas programadas");
		try{
			System.out.println("SchedulleExecutorService métodos importantes: \n"
					+ "schedulle(Callable, long delay, TimeUnit); \n"
					+ "schedulle(Runnable, long delay, TimeUnit); \n"
					+ "schedulleAtFixedRate(Runnable, initialDelay, period, TimeUnit); \n"
					+ "schedulleWithFixedDelay(Runnable, initialDelay, delay, unit)");
			executor = Executors.newSingleThreadScheduledExecutor();
			executor.scheduleWithFixedDelay(()->{
													System.out.println("Ejecución de tarea programada");
												}, 5, 2, TimeUnit.SECONDS);
			/*executor.schedule(()->{
										System.out.println("Ejecución de tarea programada");
									}, 5, TimeUnit.SECONDS);*/
			
			Future<?> future = executor.submit(()-> 10);
			System.out.println(" ---> "+future.get());
			
		}finally{
			System.out.println("Si se ejecuta shutdown, los schedulles se abortan.");
			if(executor!=null)
				executor.shutdown();
		}
	}
	
	public void testAtomicsVariables(){
		AtomicLong value1 = new AtomicLong(0);
		final long[] value2 = {0};
		IntStream.iterate(1, i -> 1).limit(100).parallel().forEach(i -> value1.incrementAndGet());
		IntStream.iterate(1, i -> 1).limit(100).parallel().forEach(i -> ++value2[0]);
		System.out.println("Indeterminado un stream enviado a varios hilos para incrementar una variable, por cuestiones de race condition");
		System.out.println(value1+" - "+value2[0]);
	}
	
	public void testConcurrentCollections(){
		List<Integer> l1 = Arrays.asList(1,2,3);
		System.out.println("CopyOnWriteArrayList: hace una copia para realizar la modificación concurrente y luego  une");
		List<Integer> l2 = new CopyOnWriteArrayList<>(l1);
		System.out.println("ConcurrentSkipListSet: se refieren a Sets Sorted, siempre que tengan la palabra skip es sorted");
		Set<Integer> s3 = new ConcurrentSkipListSet<>();
		s3.addAll(l1);
		
		for(Integer item:l2)
			l2.add(4);
		
		for(Integer item:s3)
			s3.add(5);
		
		System.out.println(l1.size()+" - "+l2.size()+" - "+s3.size());
	}
	
	public void testSynchronized(){
		System.out.println("Recuerde findAny  (siempre toma el primero) y findFirst retornan Optional<T>");
		Integer i1 = Arrays.asList(1,2,3,4,5).stream().findAny().get();
		synchronized (i1) {
			Integer i2 = Arrays.asList(6,7,8,9,10)
					.parallelStream()
					.sorted()
					.findAny().get();
			
			System.out.println(i1+" - "+i2);
		}
	}
	
	public void testReduce(){
		System.out.println(Arrays.asList("duck", "chicken", "flamingo", "pelican")
				.parallelStream().parallel()
				.reduce(0, 
						(c1, c2) -> c1+c2.length() ,
						(s1, s2) -> s1 + s2));
		
		System.out.println("Stream<U>.reduce(T, BiFunction<T,U,T>, BiOperator<T>)");
	}
	
	public void testFlatMap(){
		System.out.println("flatMap(Function<T,Stream>)");
		Stream<String> cats = Stream.of("leopard","lynx","ocelot","puma").parallel();
		Stream<String> bears = Stream.of("panda","grizzly","polar").parallel();
		ConcurrentMap<Boolean, List<String>> data = Stream.of(cats, bears)
				.flatMap(s -> s)
				.collect(Collectors
						.groupingByConcurrent(s -> !s.startsWith("p")));
		
		System.out.println("flatMap le entran dos paralelos pero sale uno secuencial");
		System.out.println("groupingByConcurrent(Function<String, Boolean>)");
		System.out.println("groupingBy(Function<T,R>): todos los T que cumplan la condicion que T.operacion de R se agruparian en un solo grupo");
		
		System.out.println(data.get(false).size()+" - "+data.get(true).size());
	}
	
	

}
