package jana60.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import jana60.model.Immagine;
import jana60.model.ImmagineForm;
import jana60.service.ImmaginiService;

@Controller
@RequestMapping("/image")
public class ImmagineController {

	@Autowired
	ImmaginiService service;
	
	@GetMapping("/{pizzaId}")
	public String pizzaImmagini(@PathVariable("pizzaId") Integer pizzaId, Model model) {
		List<Immagine> immagine = service.getImmagineByPizzaId(pizzaId);
		ImmagineForm immagineForm = service.createImmagineForm(pizzaId);
		model.addAttribute("listaImmagini", immagine);
		model.addAttribute("immagineForm", immagineForm);
		return "/image/image";
	}
	
	@PostMapping("/save")
	public String salvaImmagine(@ModelAttribute("immagineForm") ImmagineForm immagineForm) {
		try {
		      Immagine immagineSalvata = service.createImmagine(immagineForm);
		      return "redirect:/image/" + immagineSalvata.getPizza().getId();
		    } catch (IOException e) {
		      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Impossibile salvare l'immagine");
		    }
	}
}
