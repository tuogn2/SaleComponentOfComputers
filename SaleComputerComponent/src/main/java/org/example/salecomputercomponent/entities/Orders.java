package org.example.salecomputercomponent.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Orders {

	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "orderid")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "userid")
	private Users user;
	private float totalPrice;
	private String status;
	private Date createdAt;
	

}
