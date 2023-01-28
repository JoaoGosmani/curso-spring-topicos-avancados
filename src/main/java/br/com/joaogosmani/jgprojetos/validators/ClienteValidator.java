package br.com.joaogosmani.jgprojetos.validators;

import java.util.Optional;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.joaogosmani.jgprojetos.models.Cliente;
import br.com.joaogosmani.jgprojetos.repositories.ClienteRepository;

public class ClienteValidator implements Validator {

    private ClienteRepository clienteRepository;

    public ClienteValidator(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Cliente.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Cliente cliente = (Cliente) target;
        
        Optional<Cliente> clienteEncontrado = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteEncontrado.isPresent() && !clienteEncontrado.get().equals(cliente)) {
            errors.rejectValue("email", "validacao.cliente.email.existente");
        }

        clienteEncontrado = clienteRepository.findByCpf(cliente.getCpf());
        if (clienteEncontrado.isPresent() && !clienteEncontrado.get().equals(cliente)) {
            errors.rejectValue("cpf", "validacao.cliente.cpf.existente");
        }
        
    }
    
}
