package jana60.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jana60.model.Ingredienti;
import jana60.repository.IngredientiRepository;

@Controller
@RequestMapping("/ingredienti")
public class IngredientiController {

	
	@Autowired
	private IngredientiRepository repo;
	
	@GetMapping
	public String listaIngredienti(Model model) {
		model.addAttribute("ingredienti", repo.findAllByOrderByNome());
		model.addAttribute("nuovoIngrediente", new Ingredienti());
		return"/ingredienti/ingredienti";
	}
	
	@PostMapping("/add")
	  public String saveIngrediente(@Valid @ModelAttribute("nuovoIngrediente") Ingredienti formIngredienti,
	      BindingResult br, Model model) {
	    if (br.hasErrors()) {
	      model.addAttribute("ingredienti", repo.findAllByOrderByNome());
	      return "/ingredienti/ingredienti";

	    } else {
	      repo.save(formIngredienti);
	      return "redirect:/ingredienti";
	    }

	  }
	
}
