package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	private final CrudUnidadeTrabalhoService cuts;
	
	public CrudFuncionarioService(FuncionarioRepository fr, CargoRepository cr, UnidadeTrabalhoRepository utr) {
		this.fr = fr;
		this.cr = cr;
		this.utr = utr;
		this.crs = new CrudCargoService(cr);
		this.cuts = new CrudUnidadeTrabalhoService(utr);
		
	}
	
	public void inicial(Scanner scanner) {
		
		loop:while(true) {
			System.out.println("\n----| Cadastro de Funcionario |----"
				     + "\n1 - Cadastrar funcionario"
				     + "\n2 - Atualizar cadastro"
				     + "\n3 - Listar funcionarios"
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
				this.listar(scanner);
				break;
			case 4:
				break loop;
			default:
				System.out.println("| Valor Invalido |");
			}
		}
	}

	private void salvar(Scanner scanner) {
		
		System.out.println("\nNome do funcionario: ");
		String nome = scanner.next();
		
		System.out.println("\nCPF do funcionario: ");
		String cpf = scanner.next();
		
		Cargo cargo = this.cargoFuncionario(scanner);
		List<UnidadeTrabalho> unidades = this.unidadeTrabalho(scanner);
		
		System.out.println("\nSalario do funcionario: ");
		BigDecimal salario = scanner.nextBigDecimal();
		
		Funcionario funcionario = new Funcionario(nome, cpf, salario, cargo, unidades);
		this.fr.save(funcionario);
	}
	
	private void atualizar(Scanner scanner) {

		this.listar(scanner);

		System.out.println("Digite o Id: ");
		int inputIndex = scanner.nextInt();
		Funcionario func = this.fr.findById(inputIndex).get();

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
	
	private void listar(Scanner scanner) {
		System.out.println("\nEscolha a pagina: ");
		Integer page = scanner.nextInt() - 1;
		
		Pageable paginas = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "salario"));
		Page<Funcionario> listaFunc = this.fr.findAll(paginas);
		
		System.out.println("\n---| Lista de Funcionarios |----");
		listaFunc.forEach(funcionario -> System.out.println(funcionario));
		System.out.println("Pagina: " + (listaFunc.getNumber() + 1) + "/" + (listaFunc.getSize() - 1));
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
		
		this.cuts.listar();
		
		loop: while (true) {
			System.out.println("\nEscolha a unidade: (Para sair digite 0)");
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
