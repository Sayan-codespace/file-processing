package com.fileprocessing.mail.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fileprocessing.mail.model.CurrentStores;

@Repository
public interface CurrentStoresRepository extends JpaRepository<CurrentStores, Integer> {
	
	@Query("SELECT s from CurrentStores s where s.storeId IN :stores ")
	List<CurrentStores> findByStoreId(@Param("stores") List<Integer> stores);

}
