package com.syuq.springjwt.models;


import jakarta.persistence.*;

@Entity
@Table(name = "menus")
public class Menu {
    @Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private String price;

	@Column(name = "voucher")
	private boolean voucher;

	public Menu() {

	}

	public Menu(String name, String price, boolean voucher) {
		this.name = name;
		this.price = price;
		this.voucher = voucher;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public boolean isVoucher() {
		return voucher;
	}

	public void setVoucher(boolean isVoucher) {
		this.voucher = isVoucher;
	}

	@Override
	public String toString() {
		return "Menus [id=" + id + ", name=" + name + ", price=" + price + ", voucher=" + voucher + "]";
	}
}
