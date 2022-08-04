package jana60.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Ingredienti {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Integer id;
	  
	  @NotEmpty(message = "L'ingrediente deve avere un nome")
	  @Column (nullable=false)
	  private String nome;

	  @ManyToMany (mappedBy = "ingredienti")
	  @JsonBackReference
	  private List<Pizza> pizza;
	  
	//getter e setter
	 
	public Integer getId() {
		return id;
	}

	public List<Pizza> getPizza() {
		return pizza;
	}

	public void setPizza(List<Pizza> pizza) {
		this.pizza = pizza;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	  
	
	 public int getNumberOfIngredienti() {
		    return pizza.size();
	 }
}
