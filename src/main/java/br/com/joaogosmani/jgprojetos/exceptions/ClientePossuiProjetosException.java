package br.com.joaogosmani.jgprojetos.exceptions;

public class ClientePossuiProjetosException extends RuntimeException {

    public ClientePossuiProjetosException(Long id) {
        super(String.format("Cliente com o ID %s possui projeto(s) relacionado(s)", id));
    }
    
}
