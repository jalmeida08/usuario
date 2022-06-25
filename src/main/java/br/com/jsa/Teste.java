package br.com.jsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;import java.util.function.Consumer;
import java.util.function.Function;

public class Teste {
	
	public static void main(String[] args) {
		
		var lst = List.of("Ana Clara", "Mirele", "Jefferson", "Manoel", "Rita");
		
//		lst.forEach((String t) -> System.out.println(t));
		Function<String, Boolean> funcTest = a -> a.length() > 2;
		Function<String, Boolean> funcTest2 = a -> a.length() > 2;
		System.out.println(funcTest.apply("Ana"));
		
	}

}
