package br.com.joaogosmani.jgprojetos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.joaogosmani.jgprojetos.repositories.ClienteRepository;
import br.com.joaogosmani.jgprojetos.repositories.FuncionarioRepository;
import br.com.joaogosmani.jgprojetos.validators.ClienteValidator;
import br.com.joaogosmani.jgprojetos.validators.FuncionarioValidator;
import br.com.joaogosmani.jgprojetos.validators.PessoaValidator;

@Configuration
public class ValidatorConfig {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Bean
    public ClienteValidator clienteValidator() {
        return new ClienteValidator(clienteRepository);
    }

    @Bean
    public FuncionarioValidator funcionarioValidator() {
        return new FuncionarioValidator(funcionarioRepository);
    }

    @Bean
    public PessoaValidator pessoaValidator() {
        return new PessoaValidator();
    }
    
}
