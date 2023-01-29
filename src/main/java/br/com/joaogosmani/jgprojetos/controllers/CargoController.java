package br.com.joaogosmani.jgprojetos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.joaogosmani.jgprojetos.dto.AlertDTO;
import br.com.joaogosmani.jgprojetos.exceptions.CargoPossuiFuncionariosException;
import br.com.joaogosmani.jgprojetos.models.Cargo;
import br.com.joaogosmani.jgprojetos.services.CargoService;

@Controller
@RequestMapping("/cargos")
public class CargoController {
    
    @Autowired
    private CargoService cargoService;

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("cargo/home");

        modelAndView.addObject("cargos", cargoService.buscarTodos());

        return modelAndView;
    }
    
    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("cargo/formulario");

        modelAndView.addObject("cargo", new Cargo());
        
        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cargo/formulario");

        modelAndView.addObject("cargo", cargoService.buscarPorId(id));

        return modelAndView;
    }

    @PostMapping({"/cadastrar", "/{id}/editar"})
    public String salvar(@Valid Cargo cargo, BindingResult resultado, RedirectAttributes attrs, @PathVariable(required = false) Long id) {
        if (resultado.hasErrors()) {
            return "cargo/formulario";
        }

        if (cargo.getId() == null) {
            cargoService.cadastrar(cargo);
            attrs.addFlashAttribute("alert", new AlertDTO("Cargo cadastrado com sucesso!", "alert-success"));
        } else {
            cargoService.atualizar(cargo, id);
            attrs.addFlashAttribute("alert", new AlertDTO("Cargo editado com sucesso!", "alert-success"));
        }

        return "redirect:/cargos";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attrs) {
        try {         
            cargoService.excluirPorId(id);
            attrs.addFlashAttribute("alert", new AlertDTO("Cargo excluído com sucesso!", "alert-success"));
        } catch (CargoPossuiFuncionariosException e) {
            attrs.addFlashAttribute("alert", new AlertDTO("Cargo não pode ser excluído, pois possui funcionario(s) relacionado(s)!", "alert-danger"));        
        }

        return "redirect:/cargos";
    }

}
