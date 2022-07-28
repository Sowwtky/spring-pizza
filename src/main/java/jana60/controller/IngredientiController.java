package jana60.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jana60.model.Ingredienti;
import jana60.model.Pizza;
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
	
	 @GetMapping("/delete/{id}")
	  public String delete(@PathVariable("id") Integer ingredienteId, RedirectAttributes ra) {
	    Optional<Ingredienti> result = repo.findById(ingredienteId);
	    if (result.isPresent()) {
	      repo.delete(result.get());
	      ra.addFlashAttribute("successMessage", "Ingrediente " + result.get().getNome() + " eliminato!");
	      return "redirect:/ingredienti";
	    } else {
	      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
	          "Ingrediente con id " + ingredienteId + " non presente");
	    }
	  }
}
