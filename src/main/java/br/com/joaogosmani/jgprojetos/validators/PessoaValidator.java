package br.com.joaogosmani.jgprojetos.validators;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.joaogosmani.jgprojetos.models.Pessoa;

public class PessoaValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Pessoa.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pessoa pessoa = (Pessoa) target;

        if (pessoa.getDataNascimento() != null) {
            int idade = Period.between(pessoa.getDataNascimento(), LocalDate.now()).getYears();

            if (idade < 18 || idade >= 100) {
                errors.rejectValue("dataNascimento", "validacao.pessoa.idade.menor18.maiorIgual100");
            }
        }

    }
    
}
