package br.com.joaogosmani.jgprojetos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joaogosmani.jgprojetos.models.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    
}
