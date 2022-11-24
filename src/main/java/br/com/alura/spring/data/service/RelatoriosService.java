package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	FuncionarioRepository fr;

	public RelatoriosService(FuncionarioRepository fr) {
		this.fr = fr;
	}

	public void inicial(Scanner scanner) {
		loop: while (true) {
			System.out.println("\n----| Relatorios |----"
				     + "\n1 - Buscar funcionario (Nome)"
				     + "\n2 - Buscar funcionario (Nome, Salario, Data Contracao)"
				     + "\n3 - Buscar funcionario (Data Contracao)"
				     + "\n4 - Buscar funcionario (Salario)"
				     + "\n5 - Sair");

			System.out.println("\nInput: ");
			int input = scanner.nextInt();

			switch (input) {
			case 1:
				this.buscarFuncNome(scanner);
				break;
			case 2:
				this.buscarFuncNomeSalarioMaiorDataContratacao(scanner);
				break;
			case 3:
				this.buscarDataContratacaoMaior(scanner);
				break;
			case 4:
				this.buscarFuncSalario();
				break;
			case 5:
				break loop;
			default:
				System.out.println("| Valor Invalido |");
			}
		}
	}

	private void buscarFuncNome(Scanner scanner) {
		System.out.println("\nDigite o nome do funcionario: ");
		String nome = scanner.next();
		
		List<Funcionario> listFunc = this.fr.findByNome(nome);
		for(Funcionario func : listFunc) {
			System.out.println(func.toString());
		}
	}
	
	private void buscarFuncNomeSalarioMaiorDataContratacao(Scanner scanner) {
		System.out.println("\nDigite o nome: ");
		String nome = scanner.next();
		
		System.out.println("\nDigite a data de contracao: ");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, this.formatter);
		
		System.out.println("\nDigite o salario: ");
		BigDecimal salario = scanner.nextBigDecimal();
		
		List<Funcionario> listFunc = fr.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);
		
		for(Funcionario func : listFunc) {
			System.out.println(func.toString());
		}
		
	}

	private void buscarDataContratacaoMaior(Scanner scanner) {
		System.out.println("\nDigite a data: ");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, this.formatter);
		
		List<Funcionario> listFunc = this.fr.findDataContratacaoMaior(localDate);
		
		for(Funcionario func : listFunc) {
			System.out.println(func.toString());
		}
	}
	
	private void buscarFuncSalario() {
		List<FuncionarioProjecao> listFunc = fr.findFuncionarioSalario();
		
		for(FuncionarioProjecao func : listFunc) {
			System.out.println("\nId: " + func.getId() + 
							   "\nNome: " + func.getNome() +
							   "\nSalario: " + func.getSalario());
		}
	}
}
