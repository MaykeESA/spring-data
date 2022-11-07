package br.com.alura.spring.data.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CrudCargoService {

	private final CargoRepository cp;
	
	public CrudCargoService(CargoRepository rep) {
		this.cp = rep;
	}
	
	public void inicial(Scanner scanner) {
		
		while(true) {
			System.out.println("\n----| Cadastro de Cargo |----"
				     + "\n1 - Cadastrar cargo"
				     + "\n2 - Atualizar cadastro"
				     + "\n3 - Sair\n");

			System.out.println("Input: ");
			int input = scanner.nextInt();

			if (input == 1) {
				this.salvar(scanner);
				
			} else if (input == 2) {
				this.atualizar(scanner);
				
			} else if (input == 3) {
				break;
				
			} else {
				System.out.println("| Valor Invalido |");
			}
		}
	}

	public void salvar(Scanner scanner) {
		System.out.println("\nDescricao do cargo: ");
		String desc = scanner.next();

		Cargo cargo = new Cargo(desc);
		this.cp.save(cargo);
	}

	public void atualizar(Scanner scanner) {
		List<Cargo> listaCargos = (List<Cargo>) this.cp.findAll();

		System.out.println("\n---| Lista de Cargos |----");
		for (Cargo cargo : listaCargos) {
			System.out.println(cargo.getId() + " - " + cargo.getDescricao());
		}

		int inputIndex = scanner.nextInt();
		Cargo c = listaCargos.get(inputIndex - 1);

		System.out.println("\nDigite o novo cargo: ");
		String inputCargo = scanner.next();
		c.setDescricao(inputCargo);
		this.cp.save(c);

	}
}
