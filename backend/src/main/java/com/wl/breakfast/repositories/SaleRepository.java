package com.wl.breakfast.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wl.breakfast.dto.SaleSuccessDTO;
import com.wl.breakfast.dto.SaleSumDTO;
import com.wl.breakfast.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long>{
	
	@Query("SELECT NEW com.wl.breakfast.dto.SaleSumDTO (obj.seller, SUM(obj.amount )) "
			+ "FROM Sale AS obj GROUP BY obj.seller")
	List<SaleSumDTO> amountGroupedBySeller();
	
	@Query("SELECT NEW com.wl.breakfast.dto.SaleSuccessDTO (obj.seller, SUM(obj.visited), SUM(obj.deals)) "
			+ "FROM Sale AS obj GROUP BY obj.seller")
	List<SaleSuccessDTO> successGroupedBySeller();

}
