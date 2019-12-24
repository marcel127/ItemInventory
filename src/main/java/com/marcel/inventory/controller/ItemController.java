package com.marcel.inventory.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcel.inventory.model.Item;
import com.marcel.inventory.repository.ItemRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
@Api(description= "Methods to Http Request,that can get/post/put/delete items from H2 DB")
public class ItemController {
	
	@Autowired
    private ItemRepository itemRepository;

	    @GetMapping("/items")
		@ApiOperation("List all items that exist in DB")
	    public List<Item> GetAllItems() {
	        return itemRepository.findAll();
	    }
	 
	    @GetMapping("/items/{id}")
		@ApiOperation("Get item by ID")
	    public ResponseEntity<Optional<Item>> GetItemById(
	    @ApiParam(value = "Item id that want to get", required = true)
	    @PathVariable(value = "id") Long id){
	        Optional<Item> item = itemRepository.findById(id);
	        return ResponseEntity.ok().body(item);
	    }
	    
	    @PostMapping("/items")
		@ApiOperation("Add Item to DB,id generted automatically")
	    public Item AddItem(@ApiParam(value = "Add item with this format: "
	    		+ "{\"name\" : \"joe\" ,\"quantity\" = 5 , "
	    		+ "\"code\" : \"abc123\" }" , required = true)
	    @Valid @RequestBody Item item) {
	        return itemRepository.save(item);
	    }
	    
	    @PostMapping("/items/multiadd")
		@ApiOperation("Add more one item,to DB,id generted automatically")
	    public List<Item> AddMultiItems(@ApiParam(value = "Add item with this format: "
	    		+ "[{\"name\" : \"joe\" ,\"quantity\" = 5 , "
	    		+ "\"code\" : \"abc123\" }, ....]" , required = true)
	    @Valid @RequestBody Item item[]) {
	    	List <Item> items = new ArrayList<Item>();
	        
	    	for(int i = 0; i < item.length ; ++i)
	        {
	        	items.add(AddItem(item[i]));
	        }
	    	
	        return items;
	    }
	    
	    @DeleteMapping("/items/{id}")
		@ApiOperation("Delete Item by id from DB")
	    public String DeleteEmployee(@ApiParam(value = "Enter id that you want to delete./n"
	    		+ "if id not exist, notihing changes", required = true)
	    @PathVariable(value = "id") Long id){
			
			if(!itemRepository.existsById(id))
			{
				return new String("the item with id: " + id + " not exist");
			}
			
			itemRepository.deleteById(id);
			
			return new String("the item with id: " + id + " deleted");
		}
	    
	    @PutMapping("/items/{id}/{quantity}")
		@ApiOperation("Withdrawl / Deposit multiple items from inventory")
	    public String ModifyQuantityEmployee(
	    		@ApiParam(value = "Enter the ID,if not exist nothing changes."
	    	    ,required = true)
	    		@PathVariable(value = "id") Long id,
	    		@ApiParam(value = "Enter quantity,"
	    		+ "that you want to add or subtract,\n" + 
	    		" if you want to add write positive number, to substract, "
	    		+ "write negative number\n "
	    		+ "if you try to reduce more than the exist quantity in the stock "
	    		+ "nothing will change", required = true)
	    		@PathVariable(value = "quantity") int quantity) {
	    	
	    	if(!itemRepository.existsById(id))
			{
				return new String("the item with id: " + id + " not exist");
			}
	    	
	    	Optional<Item> item = itemRepository.findById(id);
	    	
	    	int newQuantity = item.get().getQuantity() + quantity;
	    	
	    	if(newQuantity < 0)
	    	{
	    		return new String("You tried to drop more than the existing quantity ,nothing change");
	    	}
	    	
	    	item.get().setQuantity(newQuantity);
	    	
	    	itemRepository.save(item.get());
	    	
	    	return new String("the item with id: " + id + " updated and now the"
	    			+ " quantitiy is " + newQuantity);
	    }
	  
}
