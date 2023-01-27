package br.com.joaogosmani.jgprojetos.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.joaogosmani.jgprojetos.dao.AlterarSenhaDAO;
import br.com.joaogosmani.jgprojetos.models.Funcionario;
import br.com.joaogosmani.jgprojetos.repositories.FuncionarioRepository;
import br.com.joaogosmani.jgprojetos.utils.SenhaUtils;

@Controller
public class UsuarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/login")
    public String login() {
        return "usuario/login";
    }

    @GetMapping("/perfil")
    public ModelAndView perfil(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("usuario/perfil");

        Funcionario usuario = funcionarioRepository.findByEmail(principal.getName()).get();
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("alterarSenhaForm", new AlterarSenhaDAO());

        return modelAndView;
    }

    @PostMapping("/alterar-senha")
    public String alterarSenha(AlterarSenhaDAO form, Principal principal) {
        Funcionario usuario = funcionarioRepository.findByEmail(principal.getName()).get();

        if (SenhaUtils.matches(form.getSenhaAtual(), usuario.getSenha())) {
            usuario.setSenha(SenhaUtils.encode(form.getNovaSenha()));
            funcionarioRepository.save(usuario);
        }

        return "redirect:/perfil";
    }

}
