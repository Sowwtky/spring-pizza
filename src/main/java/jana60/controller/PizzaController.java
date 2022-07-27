package jana60.controller;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jana60.model.Pizza;
import jana60.repository.IngredientiRepository;
import jana60.repository.PizzaRepository;

@Controller
@RequestMapping("/")
public class PizzaController {

	@Autowired
	private PizzaRepository repo;
	
	@Autowired
	private IngredientiRepository ingredientiRepo;
	
	@GetMapping
	public String pizzaList(Model model) {
		model.addAttribute("pizza", repo.findAll());
		return "/pizza/menu";
	}
	
	@GetMapping("/add")
	public String pizzaForm(Model model) {
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("listaIngredienti", ingredientiRepo.findAllByOrderByNome());
		return "/pizza/edit";
	}
	
	 @PostMapping("/add")
	  public String save(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult br, Model model) {
		 boolean hasErrors = br.hasErrors();
		 boolean validateNome = true;
		 if(formPizza.getId() != null) {
			 Pizza pizzaBeforeUpdate = repo.findById(formPizza.getId()).get();
			 if(pizzaBeforeUpdate.getNome().equals(formPizza.getNome())) {
				 validateNome = false;
			 }
		}
		 
		 if (validateNome && repo.countByNome(formPizza.getNome()) > 0) {
		      br.addError(new FieldError("pizza", "name", "Non ci possono essere due pizze con lo stesso nome"));
		      hasErrors = true;
		    }
		 
	    if (hasErrors) {
	    	model.addAttribute("listaIngredienti", ingredientiRepo.findAllByOrderByNome());
	      return "/pizza/edit";
	    } else {
	    	
	      try {
			repo.save(formPizza);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Unable to save the pizza");
			model.addAttribute("listaIngredienti", ingredientiRepo.findAllByOrderByNome());
			return "/pizza/edit";
		}
	      
	      return "redirect:/";
	    }
	  }
	
	 @GetMapping("/delete/{id}")
	  public String delete(@PathVariable("id") Integer pizzaId, RedirectAttributes ra) {
	    Optional<Pizza> result = repo.findById(pizzaId);
	    if (result.isPresent()) {
	      repo.delete(result.get());
	      ra.addFlashAttribute("successMessage", "Pizza " + result.get().getNome() + " eliminata!");
	      return "redirect:/";
	    } else {
	      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
	          "Book con id " + pizzaId + " non presente");
	    }
	  }
	 
	 @GetMapping("/edit/{id}")
	  public String edit(@PathVariable("id") Integer pizzaId, Model model) {
	    Optional<Pizza> result = repo.findById(pizzaId);
	    if (result.isPresent()) {
	      model.addAttribute("pizza", result.get());
	      model.addAttribute("listaIngredienti", ingredientiRepo.findAllByOrderByNome());
	      return "/pizza/edit";
	    } else {
	      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
	          "Pizza con id " + pizzaId + " non presente");
	    }
	  }
}