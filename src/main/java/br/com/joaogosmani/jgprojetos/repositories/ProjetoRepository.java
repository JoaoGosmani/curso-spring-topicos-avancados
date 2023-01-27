package br.com.joaogosmani.jgprojetos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joaogosmani.jgprojetos.models.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    
    @EntityGraph(attributePaths = {"cliente", "lider"})
    List<Projeto> findAll();

}
