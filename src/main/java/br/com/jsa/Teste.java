package br.com.jsa;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;

public class Teste {
	
	public static void main(String[] args) {
		
		try {
			var path = Path.of("src/main/resources/META-INF/teste.html");
//			System.out.println(path.toAbsolutePath());
			var file = Files.readString(path.toAbsolutePath(), UTF_8);
//			System.out.println(file);
//			System.out.println(file.contains("${LINK}"));
//			System.out.println(file.replace("${LINK}", "https://www.aquivaiseulink.com.br/foice/vaice"));
			System.out.println(new BCryptPasswordEncoder().encode("123123"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		var lst = List.of("Ana Clara", "Mirele", "Jefferson", "Manoel", "Rita");
//		
////		lst.forEach((String t) -> System.out.println(t));
//		Function<String, Boolean> funcTest = a -> a.length() > 2;
//		Function<String, Boolean> funcTest2 = a -> a.length() > 2;
////		System.out.println(funcTest.apply("Ana"));
//		System.out.println(
//				new BCryptPasswordEncoder()
//						.matches("1231234",
//								"$2a$10$oCMspUjF55rX9IyHJdu/r.ZLf1Gn3z3jrBosh0PKmjvX7MAt.YWkm"));
//		
	}

}
