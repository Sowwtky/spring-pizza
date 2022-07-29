package jana60.model;

import org.springframework.web.multipart.MultipartFile;

public class ImmagineForm {

	private Integer id;
	private Pizza pizza;
	private MultipartFile contenutoMultiparte;
	
	//getter e setter
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Pizza getPizza() {
		return pizza;
	}
	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}
	public MultipartFile getContenutoMultiparte() {
		return contenutoMultiparte;
	}
	public void setContenutoMultiparte(MultipartFile contenutoMultiparte) {
		this.contenutoMultiparte = contenutoMultiparte;
	}
	
	
}
