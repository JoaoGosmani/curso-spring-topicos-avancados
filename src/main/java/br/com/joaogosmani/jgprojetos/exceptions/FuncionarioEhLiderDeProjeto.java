package br.com.joaogosmani.jgprojetos.exceptions;

public class FuncionarioEhLiderDeProjeto extends RuntimeException{

    public FuncionarioEhLiderDeProjeto(Long id) {
        super(String.format("Funcionário com o ID %s é líder de projeto(s)", id));
    }
    
}
