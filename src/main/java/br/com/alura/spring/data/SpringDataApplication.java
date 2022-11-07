package br.com.alura.spring.data;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.spring.data.service.CrudCargoService;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner{
	
	private final CrudCargoService cargoService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	public SpringDataApplication(CrudCargoService cargoService) {
		this.cargoService = cargoService;
	}
	
	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.println("\n----| Servicos |----"
						     + "\n1 - Cargo"
						     + "\n2 - Sair\n");
			
			System.out.println("Input: ");
			int input = scanner.nextInt();
			
			if(input == 1) {
				this.cargoService.inicial(scanner);
			}
			else if(input == 2) {
				break;
			}
			else {
				System.out.println("| Valor Invalido |");
			}
		}
		
	}

}
