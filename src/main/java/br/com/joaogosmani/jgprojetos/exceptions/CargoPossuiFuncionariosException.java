package br.com.joaogosmani.jgprojetos.exceptions;

public class CargoPossuiFuncionariosException extends RuntimeException {

    public CargoPossuiFuncionariosException(Long id) {
        super(String.format("Cargo com o ID %s possui funcionário(s) relacionado(s)", id));
    }
    
}
