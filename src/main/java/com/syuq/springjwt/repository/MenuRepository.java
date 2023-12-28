package com.syuq.springjwt.repository;

import com.syuq.springjwt.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
  	List<Menu> findByVoucher(boolean voucher);
	List<Menu> findByNameContaining(String name);
}
