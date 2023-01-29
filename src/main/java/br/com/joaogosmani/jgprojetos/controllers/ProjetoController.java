package br.com.joaogosmani.jgprojetos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.joaogosmani.jgprojetos.dto.AlertDTO;
import br.com.joaogosmani.jgprojetos.models.Projeto;
import br.com.joaogosmani.jgprojetos.services.ClienteService;
import br.com.joaogosmani.jgprojetos.services.FuncionarioService;
import br.com.joaogosmani.jgprojetos.services.ProjetoService;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("projeto/home");

        modelAndView.addObject("projetos", projetoService.buscarTodos());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projeto/detalhes");

        modelAndView.addObject("projeto", projetoService.buscarPorId(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("projeto/formulario");

        modelAndView.addObject("projeto", new Projeto());
        popularFormulario(modelAndView);

        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projeto/formulario");

        modelAndView.addObject("projeto", projetoService.buscarPorId(id));
        popularFormulario(modelAndView);

        return modelAndView;        
    }

    @PostMapping({"/cadastrar", "/{id}/editar"})
    public String salvar(@Valid Projeto projeto, BindingResult resultado, ModelMap model, RedirectAttributes attrs, @PathVariable(required = false) Long id) {
        if (resultado.hasErrors()) {
            popularFormulario(model);    

            return "projeto/formulario";
        }
        
        if (projeto.getId() == null) {
            projetoService.cadastrar(projeto);
            attrs.addFlashAttribute("alert", new AlertDTO("Projeto cadastrado com sucesso!", "alert-success"));
        } else {
            projetoService.atualizar(projeto, id);
            attrs.addFlashAttribute("alert", new AlertDTO("Projeto editado com sucesso!", "alert-success"));
        }

        return "redirect:/projetos";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attrs) {
        projetoService.excluirPorId(id);
        attrs.addFlashAttribute("alert", new AlertDTO("Projeto excluido com sucesso!", "alert-success"));

        return "redirect:/projetos";
    }

    private void popularFormulario(ModelAndView modelAndView) {
        modelAndView.addObject("clientes", clienteService.buscarTodos());
        modelAndView.addObject("lideres", funcionarioService.buscarLideres());
        modelAndView.addObject("funcionarios", funcionarioService.buscarEquipe());
    }

    private void popularFormulario(ModelMap model) {
        model.addAttribute("clientes", clienteService.buscarTodos());
        model.addAttribute("lideres", funcionarioService.buscarLideres());
        model.addAttribute("funcionarios", funcionarioService.buscarEquipe());    
    }

}
