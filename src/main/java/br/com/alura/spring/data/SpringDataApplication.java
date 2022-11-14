package br.com.alura.spring.data;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.spring.data.service.CrudCargoService;
import br.com.alura.spring.data.service.CrudFuncionarioService;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner{
	
	private final CrudCargoService cargoService;
	private final CrudFuncionarioService funcService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	public SpringDataApplication(CrudCargoService cargoService, CrudFuncionarioService funcService) {
		this.cargoService = cargoService;
		this.funcService = funcService;
	}
	
	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		
		loop: while(true) {
			System.out.println("\n----| Servicos |----"
						     + "\n1 - Cargo"
						     + "\n2 - Funcionario"
						     + "\n3 - Sair\n");
			
			System.out.println("Input: ");
			int input = scanner.nextInt();
			
			switch(input) {
			
			case 1:
				cargoService.inicial(scanner);
				break;
			
			case 2:
				funcService.inicial(scanner);
				break;
				
			case 3:
				break loop;
				
			default:
				System.out.println("| Valor Invalido |");
			}
		}
		
	}

}
