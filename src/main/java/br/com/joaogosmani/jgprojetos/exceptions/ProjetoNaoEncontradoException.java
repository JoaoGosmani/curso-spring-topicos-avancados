package br.com.joaogosmani.jgprojetos.exceptions;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProjetoNaoEncontradoException extends EntityNotFoundException {

    public ProjetoNaoEncontradoException(Long id) {
        super(String.format("Projeto com o ID %s não encontrado", id));
    }

}
