package br.com.alura.spring.data.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudUnidadeTrabalhoService {

	private final UnidadeTrabalhoRepository utr;
	
	public CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository utr) {
		this.utr = utr;
	}
	
	public void inicial(Scanner scanner) {
		loop: while(true) {
			System.out.println("\n---| Cadastro Unidade |---"
							 + "\n1 - Cadastrar unidades"
							 + "\n2 - Atualizar unidades"
							 + "\n3 - Listar unidades"
							 + "\n4 - Sair");
			
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
				System.out.println("| Valor invalido |");
			}
		}
	}
	
	public void salvar(Scanner scanner) {
		
		System.out.println("\nDigite o nome da unidade: ");
		String nome = scanner.next();
		
		System.out.println("\nDigite o endereco: ");
		String endereco = scanner.next();
		
		UnidadeTrabalho ut = new UnidadeTrabalho(nome, endereco);
		utr.save(ut);
	}
	
	public void atualizar(Scanner scanner) {
		this.listar();
		
		System.out.println("Digite o Id: ");
		int inputIndex = scanner.nextInt();
		UnidadeTrabalho ut = this.utr.findById(inputIndex).get();
		
		loop: while(true) {
			System.out.println("\n---| Dados da unidade |---"
							 + "\n1 - Nome da unidade"
							 + "\n2 - Endereco da unidade"
							 + "\n3 - Sair");

			int input = scanner.nextInt();
			switch (input) {
			case 1:
				System.out.println("Digite o nome: ");
				String nome = scanner.next();
				ut.setDescricao(nome);
				break;
			case 2:
				System.out.println("Digite o endereco: ");
				String endereco = scanner.next();
				ut.setEnedereco(endereco);
				break;
			case 3:
				break loop;
			default:
				System.out.println("| Valor Invalido |");
			}
		}
		this.utr.save(ut);
	}
	
	public void listar() {
		List<UnidadeTrabalho> listaUt = (List<UnidadeTrabalho>) this.utr.findAll();
		
		System.out.println("\n---| Lista de Unidades |----");
		for (UnidadeTrabalho ut : listaUt) {
			System.out.println(ut.toString());
		}
	}
	
}
