package com.alten;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.alten.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.alten.model.Product;

@RestController
@SpringBootApplication
public class BackApplication {

	@Autowired 
    private ProductRepository productRepo;

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

	@GetMapping("/products")
    public List<Product> getProducts(){
        return productRepo.findAll(); 
    }

	@PostMapping("/products")
    public Product createProduct(Product product){
        return productRepo.save(product); 
    }

	@GetMapping("/product/{id}")
    public Product getProduct(@PathVariable Long id){
		Optional<Product> product = productRepo.findById(id);
        if(product.isPresent()){
			return product.get();
		} else{
			return null;
		}
    }

	@DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Long id){
    	productRepo.deleteById(id); 
    }

	@PatchMapping(path = "/product/{id}", consumes = "application/json-patch+json")
	public Product updateCustomer(@PathVariable Long id, @RequestBody JsonPatch patch) {

		Optional<Product> optProduct = productRepo.findById(id);
		Product result = null;

		if(optProduct.isPresent()){

			try {

				// Générer une version json du produit patché
				ObjectMapper mapper = new ObjectMapper();
				JsonNode patched = patch.apply(mapper.convertValue(optProduct.get(), JsonNode.class));

				// Convertir le json en objet Produit et sauvegarder
				Product product = mapper.treeToValue(patched, Product.class);
				productRepo.save(product);
				
				result = productRepo.findById(id).get();

			} catch (JsonProcessingException | IllegalArgumentException e) {
				e.printStackTrace();
			} catch (JsonPatchException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
