package br.com.alura.spring.data.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CrudCargoService {

	private final CargoRepository cr;
	
	public CrudCargoService(CargoRepository cr) {
		this.cr = cr;
	}
	
	public void inicial(Scanner scanner) {
		
		loop: while(true) {
			System.out.println("\n----| Cadastro de Cargo |----"
				     + "\n1 - Cadastrar cargo"
				     + "\n2 - Atualizar cadastro"
				     + "\n3 - Listar cargos"
				     + "\n4 - Sair\n");

			System.out.println("\nInput: ");
			int input = scanner.nextInt();
			
			switch(input) {
			
			case 1:
				this.salvar(scanner);
				break;
			case 2:
				this.atualizar(scanner);
				break;
			case 3:
				this.listar();
				break;
			case 4:
				break loop;
			default:
				System.out.println("| Valor Invalido |");
			}
		}
	}

	public void salvar(Scanner scanner) {
		System.out.println("\nDescricao do cargo: ");
		String desc = scanner.next();

		Cargo cargo = new Cargo(desc);
		this.cr.save(cargo);
	}

	public void atualizar(Scanner scanner) {
		this.listar();
		
		System.out.println("\nDigite o Id: ");
		int inputIndex = scanner.nextInt();
		Cargo cargo = this.cr.findById(inputIndex).get();

		System.out.println("\nDigite o novo cargo: ");
		String inputCargo = scanner.next();
		cargo.setDescricao(inputCargo);
		this.cr.save(cargo);

	}
	
	public void listar() {
		List<Cargo> listaCargos = (List<Cargo>) this.cr.findAll();
		
		System.out.println("\n---| Lista de Cargos |----");
		for (Cargo cargo : listaCargos) {
			System.out.println(cargo.toString());
		}
	}
}
