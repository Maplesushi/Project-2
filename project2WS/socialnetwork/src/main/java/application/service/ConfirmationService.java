package application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.dao.ConfirmationDao;
import application.model.Confirmation;


@Service("ConfirmationService")
public class ConfirmationService {
	private ConfirmationDao dao;
	
	@Autowired
	public ConfirmationService(ConfirmationDao dao) {
		this.dao = dao;
	}
	

	public Confirmation findByConfirmationToken(String confirmationToken) {
		return dao.findByConfirmationToken(confirmationToken).get(0);
	}
	
	public void createConfirmationToken(Confirmation confirmationToken) {
		dao.save(confirmationToken);
	}
	
}
