package com.marcel.inventory.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "item")
@ApiModel(description = "All details about the item")

public class Item {
    
	@ApiModelProperty(notes = "The database generated item ID")
    private long id;
	
	@ApiModelProperty(notes = "Item name")
	private  String name;
	
	@ApiModelProperty(notes = "Quantity that available in stock")
	private int quantity;
	
	@ApiModelProperty(notes = "Inventory code")
	private String code;
	
	public Item() {}
	
	public Item(String name, int quantity,String code)
	{
		this.name = name;
		this.quantity = quantity;
		this.code = code;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    @Column(name = "item_name", nullable = false)
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

    @Column(name = "code", nullable = false)
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
