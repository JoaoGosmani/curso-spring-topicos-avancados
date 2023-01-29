package br.com.joaogosmani.jgprojetos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaogosmani.jgprojetos.exceptions.FuncionarioEhLiderDeProjeto;
import br.com.joaogosmani.jgprojetos.exceptions.FuncionarioNaoEncontradoException;
import br.com.joaogosmani.jgprojetos.models.Funcionario;
import br.com.joaogosmani.jgprojetos.models.Projeto;
import br.com.joaogosmani.jgprojetos.repositories.FuncionarioRepository;
import br.com.joaogosmani.jgprojetos.repositories.ProjetoRepository;
import br.com.joaogosmani.jgprojetos.utils.SenhaUtils;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    public List<Funcionario> buscarTodos() {
        return funcionarioRepository.findAll();
    }

    public Funcionario buscarPorId(Long id) {
        return funcionarioRepository.findById(id)
            .orElseThrow(() -> new FuncionarioNaoEncontradoException(id));
    }

    public Funcionario cadastrar(Funcionario funcionario) {
        String senhaEncriptada = SenhaUtils.encode(funcionario.getSenha());

        funcionario.setSenha(senhaEncriptada);

        return funcionarioRepository.save(funcionario);
    }

    public Funcionario atualizar(Funcionario funcionario, Long id) {
        Funcionario funcionarioEncontrado = buscarPorId(id);

        funcionario.setSenha(funcionarioEncontrado.getSenha());

        return funcionarioRepository.save(funcionario);
    }

    public void excluirPorId(Long id) {
        Funcionario funcionarioEncontrado = buscarPorId(id);

        if (projetoRepository.findByLider(funcionarioEncontrado).isEmpty()) {
            if (!funcionarioEncontrado.getProjetos().isEmpty()) {
                for (Projeto projeto : funcionarioEncontrado.getProjetos()) {
                    projeto.getEquipe().remove(funcionarioEncontrado);
                    projetoRepository.save(projeto);
                }
            }

            funcionarioRepository.delete(funcionarioEncontrado);
        } else {
            throw new FuncionarioEhLiderDeProjeto(id);
        }
    }

}
