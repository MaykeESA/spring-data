package br.com.alura.spring.data.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.orm.Funcionario;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {

	//Derived Query
	public List<Funcionario> findByNome(String nome);
	
	//JPQL
	@Query("SELECT f FROM "
			+ "Funcionario f WHERE f.nome = :nome "
			+ "AND  f.salario >= :salario "
			+ "AND f.dataContratacao = :data")
	public List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, BigDecimal salario, LocalDate data);
	
	//Native Query
	@Query(nativeQuery = true, value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data")
	public List<Funcionario> findDataContratacaoMaior(LocalDate data);
}
