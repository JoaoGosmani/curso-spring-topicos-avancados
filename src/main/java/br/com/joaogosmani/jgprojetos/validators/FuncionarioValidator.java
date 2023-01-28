package br.com.joaogosmani.jgprojetos.validators;

import java.util.Optional;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.joaogosmani.jgprojetos.models.Funcionario;
import br.com.joaogosmani.jgprojetos.repositories.FuncionarioRepository;

public class FuncionarioValidator implements Validator {

    private FuncionarioRepository funcionarioRepository;

    public FuncionarioValidator(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Funcionario.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Funcionario funcionario = (Funcionario) target;

        if (funcionario.getDataAdmissao() != null & funcionario.getDataDemissao() != null) {
            if (funcionario.getDataDemissao().isBefore(funcionario.getDataAdmissao())) {
                errors.rejectValue("dataDemissao", "validacao.funcionario.dataAdmissao.posterior.dataDemissao");
            }
        }

        Optional<Funcionario> funcionarioEncontrado = funcionarioRepository.findByEmail(funcionario.getEmail());
        if (funcionarioEncontrado.isPresent() && !funcionarioEncontrado.get().equals(funcionario)) {
            errors.rejectValue("email", "validacao.funcionario.email.existente");
        }

        funcionarioEncontrado = funcionarioRepository.findByCpf(funcionario.getCpf());
        if (funcionarioEncontrado.isPresent() && !funcionarioEncontrado.get().equals(funcionario)) {
            errors.rejectValue("cpf", "validacao.funcionario.cpf.existente");
        }

    }
    
}
