package com.fileprocessing.mail.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fileprocessing.mail.model.AllStores;

@Repository
public interface AllStoresRepository extends JpaRepository<AllStores, Integer>{
	
	@Query("SELECT s FROM AllStores s where s.storeId IN :store")
	List<AllStores> findByStoreIdIn(@Param("store") List<Integer> storeList);

}
