package br.com.joaogosmani.jgprojetos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import br.com.joaogosmani.jgprojetos.exceptions.FuncionarioEhLiderDeProjeto;
import br.com.joaogosmani.jgprojetos.models.Funcionario;
import br.com.joaogosmani.jgprojetos.services.CargoService;
import br.com.joaogosmani.jgprojetos.services.FuncionarioService;
import br.com.joaogosmani.jgprojetos.validators.FuncionarioValidator;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private CargoService cargoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private FuncionarioValidator funcionarioValidator;

    @InitBinder("funcionario")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(funcionarioValidator);
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("funcionario/home");

        modelAndView.addObject("funcionarios", funcionarioService.buscarTodos());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("funcionario/detalhes");

        modelAndView.addObject("funcionario", funcionarioService.buscarPorId(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("funcionario/formulario");

        modelAndView.addObject("funcionario", new Funcionario());
        modelAndView.addObject("cargos", cargoService.buscarTodos());

        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("funcionario/formulario");

        modelAndView.addObject("funcionario", funcionarioService.buscarPorId(id));
        modelAndView.addObject("cargos", cargoService.buscarTodos());

        return modelAndView;
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid Funcionario funcionario, BindingResult resultado, ModelMap model, RedirectAttributes attrs) {
        if (resultado.hasErrors()) {
            model.addAttribute("cargos", cargoService.buscarTodos());

            return "funcionario/formulario";
        }

        funcionarioService.cadastrar(funcionario);
        attrs.addFlashAttribute("alert", new AlertDTO("Funcionário cadastrado com sucesso!", "alert-success"));

        return "redirect:/funcionarios";
    }

    @PostMapping("/{id}/editar")
    public String editar(Funcionario funcionario, BindingResult resultado, @PathVariable Long id, ModelMap model, RedirectAttributes attrs) {
        if (resultado.hasErrors()) {
            model.addAttribute("cargos", cargoService.buscarTodos());

            return "funcionario/formulario";
        }
        
        funcionarioService.atualizar(funcionario, id);
        attrs.addFlashAttribute("alert", new AlertDTO("Funcionário editado com sucesso!", "alert-success"));

        return "redirect:/funcionarios";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attrs) {
        try {
                funcionarioService.excluirPorId(id);
                attrs.addFlashAttribute("alert", new AlertDTO("Funcionário excluído com sucesso!", "alert-success"));   
            } catch (FuncionarioEhLiderDeProjeto e) {
                attrs.addFlashAttribute("alert", new AlertDTO("Funcionário não pode ser excluído, pois é líder de algum projeto!", "alert-danger"));
            }

        return "redirect:/funcionarios";
    }

}
