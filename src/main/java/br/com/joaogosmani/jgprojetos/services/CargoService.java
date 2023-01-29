package br.com.joaogosmani.jgprojetos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaogosmani.jgprojetos.exceptions.CargoNaoEncontradoException;
import br.com.joaogosmani.jgprojetos.exceptions.CargoPossuiFuncionariosException;
import br.com.joaogosmani.jgprojetos.models.Cargo;
import br.com.joaogosmani.jgprojetos.repositories.CargoRepository;
import br.com.joaogosmani.jgprojetos.repositories.FuncionarioRepository;

@Service
public class CargoService {
    
    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Cargo> buscarTodos() {
        return cargoRepository.findAll();
    }

    public Cargo buscarPorId(Long id) {
        Cargo cargoEncontrado = cargoRepository.findById(id)
            .orElseThrow(() -> new CargoNaoEncontradoException(id));

        return cargoEncontrado;
    }

    public Cargo cadastrar(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public Cargo atualizar(Cargo cargo, Long id) {
        buscarPorId(id);

        return cargoRepository.save(cargo);
    }

    public void excluirPorId(Long id) {
        Cargo cargoEncontrado = buscarPorId(id);

        if (funcionarioRepository.findByCargo(cargoEncontrado).isEmpty()) {
            cargoRepository.delete(cargoEncontrado);
        } else {
            throw new CargoPossuiFuncionariosException(id);
        }

    }

}
