package application.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.model.Confirmation;

@Repository("confirmationDao")
public interface ConfirmationDao extends JpaRepository<Confirmation, String> {

	List<Confirmation> findByConfirmationToken(String confirmationToken);
   
}