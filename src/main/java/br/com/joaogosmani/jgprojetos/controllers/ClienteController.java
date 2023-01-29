package br.com.joaogosmani.jgprojetos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.joaogosmani.jgprojetos.dto.AlertDTO;
import br.com.joaogosmani.jgprojetos.exceptions.ClientePossuiProjetosException;
import br.com.joaogosmani.jgprojetos.models.Cliente;
import br.com.joaogosmani.jgprojetos.repositories.ClienteRepository;
import br.com.joaogosmani.jgprojetos.services.ClienteService;
import br.com.joaogosmani.jgprojetos.validators.ClienteValidator;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @InitBinder("cliente")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new ClienteValidator(clienteRepository));
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("cliente/home");

        modelAndView.addObject("clientes", clienteService.buscarTodos());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cliente/detalhes");

        modelAndView.addObject("cliente", clienteService.buscarPorId(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("cliente/formulario");

        modelAndView.addObject("cliente", new Cliente());

        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cliente/formulario");

        modelAndView.addObject("cliente", clienteService.buscarPorId(id));

        return modelAndView;
    }

    @PostMapping({"/cadastrar", "/{id}/editar"})
    public String salvar(@Valid Cliente cliente, BindingResult resultado, RedirectAttributes attrs, @PathVariable(required = false) Long id) {
        if (resultado.hasErrors()) {
            return "cliente/formulario";
        }

        if (cliente.getId() == null) {
            clienteService.cadastrar(cliente);
            attrs.addFlashAttribute("alert", new AlertDTO("Cliente cadastrado com sucesso!", "alert-success"));
        } else {
            clienteService.atualizar(cliente, id);
            attrs.addFlashAttribute("alert", new AlertDTO("Cliente editado com sucesso!", "alert-success"));
        }
        
        return "redirect:/clientes";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attrs) {
        try { 
            clienteService.excluirPorId(id);
            attrs.addFlashAttribute("alert", new AlertDTO("Cliente excluído com sucesso!", "alert-success"));
        } catch (ClientePossuiProjetosException e) {
            attrs.addFlashAttribute("alert", new AlertDTO("Cliente não pode ser excluído, pois possui projeto(s) relacionado(s)!", "alert-danger"));
        }

        return "redirect:/clientes";
    }

}
