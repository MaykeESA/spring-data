package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.specification.SpecificationFuncionario;

@Service
public class RelatorioFuncionarioDinamico {

	private final FuncionarioRepository fr;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public RelatorioFuncionarioDinamico(FuncionarioRepository fr) {
		this.fr = fr;
	}
	
	public void inicial(Scanner scanner) {
		loop:while(true) {
			System.out.println("\n----| Relatorio Dinamico de Funcionario |----"
				     + "\n1 - Busca por nome"
				     + "\n2 - Busca por cpf"
				     + "\n3 - Busca por salario"
				     + "\n4 - Busca por data"
				     + "\n5 - Sair\n");

			System.out.println("\nInput: ");
			int input = scanner.nextInt();
			
			switch(input) {
			
			case 1:
				this.buscarDinamicaNome(scanner);
				break;
			case 2:
				this.buscarDinamicaCpf(scanner);
				break;
			case 3:
				this.buscarDinamicaSalario(scanner);
				break;
			case 4:
				this.buscarDinamicaData(scanner);
				break;
			case 5:
				break loop;
			default:
				System.out.println("| Valor Invalido |");
			}
		}
	}
	
	public void buscarDinamicaNome(Scanner scanner) {
		System.out.println("\nDigite o nome: ");
		String nome = scanner.next();
		
		List<Funcionario> listFunc = fr.findAll(Specification.where(SpecificationFuncionario.nome(nome)));
		
		this.listar(listFunc);
	}
	
	public void buscarDinamicaCpf(Scanner scanner) {
		System.out.println("\nDigite o cpf: ");
		String cpf = scanner.next();
		
		List<Funcionario> listFunc = fr.findAll(Specification.where(SpecificationFuncionario.cpf(cpf)));
		
		this.listar(listFunc);
	}

	public void buscarDinamicaSalario(Scanner scanner) {
		System.out.println("\nDigite o salario: ");
		BigDecimal salario = scanner.nextBigDecimal();
		
		List<Funcionario> listFunc = fr.findAll(Specification.where(SpecificationFuncionario.salario(salario)));
		
		this.listar(listFunc);
	}
	
	public void buscarDinamicaData(Scanner scanner) {
		System.out.println("\nDigite a data: ");
		String data = scanner.next();
		
		LocalDate dataContratacao = LocalDate.parse(data, this.formatter);
		
		List<Funcionario> listFunc = fr.findAll(Specification.where(SpecificationFuncionario.dataContratacao(dataContratacao)));
		
		this.listar(listFunc);
	}
	
	public void listar(List<Funcionario> listFunc) {
		for(Funcionario func : listFunc) {
			System.out.println(func.toString());
		}
	}
}
