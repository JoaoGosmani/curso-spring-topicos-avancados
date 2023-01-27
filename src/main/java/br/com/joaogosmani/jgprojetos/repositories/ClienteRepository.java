package br.com.joaogosmani.jgprojetos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joaogosmani.jgprojetos.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @EntityGraph(attributePaths = "endereco")
    List<Cliente> findAll();

}
