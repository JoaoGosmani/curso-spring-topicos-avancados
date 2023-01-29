package br.com.joaogosmani.jgprojetos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaogosmani.jgprojetos.exceptions.ClienteNaoEncontradoException;
import br.com.joaogosmani.jgprojetos.exceptions.ClientePossuiProjetosException;
import br.com.joaogosmani.jgprojetos.models.Cliente;
import br.com.joaogosmani.jgprojetos.repositories.ClienteRepository;
import br.com.joaogosmani.jgprojetos.repositories.ProjetoRepository;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }

    public Cliente cadastrar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Cliente cliente, Long id) {
        buscarPorId(id);

        return clienteRepository.save(cliente);
    }

    public void excluirPorId(Long id) {
        Cliente clienteEncontrado = buscarPorId(id);

        if (projetoRepository.findByCliente(clienteEncontrado).isEmpty()) {
            clienteRepository.delete(clienteEncontrado);
        } else {
            throw new ClientePossuiProjetosException(id);
        }
    }

}
