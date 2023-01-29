package br.com.joaogosmani.jgprojetos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.joaogosmani.jgprojetos.enums.UF;
import br.com.joaogosmani.jgprojetos.validators.PessoaValidator;

@ControllerAdvice(assignableTypes = {FuncionarioController.class, ClienteController.class})
public class PessoaController { 

    @Autowired
    private PessoaValidator pessoaValidator;
    
    @InitBinder(value = {"funcionario", "cliente"})
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(pessoaValidator);
    }

    @ModelAttribute("ufs")
    public UF[] getUfs() {
        return UF.values();
    }

}
