package jana60.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jana60.model.Immagine;
import jana60.model.ImmagineForm;
import jana60.model.Pizza;
import jana60.repository.ImmaginiRepository;
import jana60.repository.PizzaRepository;

@Service
public class ImmaginiService {

	@Autowired
	private ImmaginiRepository repo;
	
	@Autowired
	private PizzaRepository pizzaRepo;
	
	
	public List<Immagine> getImmagineByPizzaId(Integer pizzaId){
		Pizza pizza = pizzaRepo.findById(pizzaId).get();
		return repo.findByPizza(pizza);
	}
	
	public ImmagineForm createImmagineForm(Integer pizzaId) {
		Pizza pizza = pizzaRepo.findById(pizzaId).get();
		ImmagineForm img = new ImmagineForm();
		img.setPizza(pizza);
		return img;
	}
	
	
	public Immagine createImmagine(ImmagineForm immagineForm) throws IOException{
		Immagine imgDaSalvare = new Immagine();
		imgDaSalvare.setPizza(immagineForm.getPizza());
		if(immagineForm.getContenutoMultiparte() != null) {
			byte[] contentSerialized = immagineForm.getContenutoMultiparte().getBytes();
			imgDaSalvare.setContenuto(contentSerialized);
		}
		
		return repo.save(imgDaSalvare);
	}
	
	public byte[] getContenutoImmagine(Integer id) {
		Immagine img = repo.findById(id).get();
		return img.getContenuto();
	}
	
}
