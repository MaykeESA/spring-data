package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {

	private final FuncionarioRepository fr;
	private final CargoRepository cr;
	private final UnidadeTrabalhoRepository utr;
	
	private final CrudCargoService crs;
	
	public CrudFuncionarioService(FuncionarioRepository fr, CargoRepository cr, UnidadeTrabalhoRepository utr) {
		this.fr = fr;
		this.cr = cr;
		this.utr = utr;
		this.crs = new CrudCargoService(cr);
	}
	
	public void inicial(Scanner scanner) {
		
		loop:while(true) {
			System.out.println("\n----| Cadastro de Funcionario |----"
				     + "\n1 - Cadastrar funcionario"
				     + "\n2 - Atualizar cadastro"
				     + "\n3 - Listar funcionarios"
				     + "\n4 - Sair\n");

			System.out.println("Input: ");
			int input = scanner.nextInt();
			
			switch(input) {
			
			case 1:
				this.salvar(scanner);
			case 2:
				this.atualizar(scanner);
			case 3:
				this.listar();
			case 4:
				break loop;
			default:
				System.out.println("| Valor Invalido |");
			}
		}
	}

	public void salvar(Scanner scanner) {
		
		System.out.println("\nNome do funcionario: ");
		String nome = scanner.next();
		
		System.out.println("\nCPF do funcionario: ");
		String cpf = scanner.next();
		
		System.out.println("\nSalario do funcionario: ");
		BigDecimal salario = scanner.nextBigDecimal();
		
		Cargo cargo = this.cargoFuncionario(scanner);
		List<UnidadeTrabalho> unidades = this.unidadeTrabalho(scanner);
		
		Funcionario funcionario = new Funcionario(nome, cpf, salario, cargo, unidades);
		
		this.fr.save(funcionario);
	}
	
	public void atualizar(Scanner scanner) {
		List<Funcionario> listaFunc = (List<Funcionario>) this.fr.findAll();

		this.listar();

		System.out.println("Digite o Id: ");
		int inputIndex = scanner.nextInt();
		Funcionario func = listaFunc.get(inputIndex - 1);

		loop: while (true) {
			System.out.println("\n---| Dados Funcionario |---"
							 + "\n1 - Nome"
							 + "\n2 - CPF"
							 + "\n3 - Salario"
							 + "\n4 - Cargo"
							 + "\n5 - Unidades"
							 + "\n6 - Sair");
			System.out.println("\nO que deseja ser alterado? ");
			int inputCargo = scanner.nextInt();

			switch (inputCargo) {
			case 1:
				System.out.println("Digite o nome: ");
				String nome = scanner.next();
				func.setNome(nome);
				break;
			case 2:
				System.out.println("Digite o CPF: ");
				String cpf = scanner.next();
				func.setCpf(cpf);
				break;
			case 3:
				System.out.println("Digite o salario: ");
				BigDecimal salario = scanner.nextBigDecimal();
				func.setSalario(salario);
				break;
			case 4:
				Cargo cargo = this.cargoFuncionario(scanner);
				func.setCargo(cargo);
				break;
			case 5:
				List<UnidadeTrabalho> listaUnidades = this.unidadeTrabalho(scanner);
				func.setUnidadeTrabalhos(listaUnidades);
				break;
			case 6:
				break loop;
			default:
				System.out.println("| Valor Invalido |");
			}

		}
		this.fr.save(func);
	}
	
	public void listar() {
		List<Funcionario> listaFunc = (List<Funcionario>) this.fr.findAll();
		
		System.out.println("\n---| Lista de Funcionarios |----");
		for (Funcionario func : listaFunc) {
			System.out.println(func.toString());
		}
	}
	
	private Cargo cargoFuncionario(Scanner scanner) {
		
		this.crs.listar();
		System.out.println("\nEscolha o cargo do funcionario: ");
		
		int inputIndex = scanner.nextInt();
		Optional<Cargo> cargo = this.cr.findById(inputIndex);
		return cargo.get();
	}
	
	private List<UnidadeTrabalho> unidadeTrabalho(Scanner scanner) {
		List<UnidadeTrabalho> unidades = new ArrayList<>();

		loop: while (true) {
			System.out.println("Escolha a unidade: (Para sair digite 0)");
			Integer unidadeId = scanner.nextInt();

			if (unidadeId != 0) {
				Optional<UnidadeTrabalho> unidade = utr.findById(unidadeId);
				unidades.add(unidade.get());
			} else {
				break loop;
			}
		}

		return unidades;
	}
	
}
