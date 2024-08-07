package org.example.salecomputercomponent.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class OrderDetail {
	
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderdetailid;

	@ManyToOne
	@JoinColumn(name = "orderid")
	private Orders order;
	
	
	@ManyToOne
	@JoinColumn(name = "productid")
	private Product product;

	private float quantity;
	private float price;	
}
