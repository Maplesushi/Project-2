package application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import application.model.Confirmation;
import application.model.User;
import application.service.ConfirmationService;
import application.service.EmailService;
import application.service.UserService;

@RestController
public class RegisterController {
	
	private UserService userService;
	private EmailService emailService;
	private ConfirmationService confirmationService;
	
	
	@Autowired
	public RegisterController(UserService userService, EmailService emailService, ConfirmationService confirmationService ) {
		this.userService = userService;
		this.emailService = emailService;
		this.confirmationService = confirmationService;
	}
	
//	@GetMapping(value="/register")
//	public ModelAndView displayRegistration(HttpSession session, User user)
//	{
//		session.setAttribute("user", user);
//		session.setViewName("register");
//		return session;
//	}
	
	@PostMapping(value="/register")
	public void sendEmailLink(@RequestBody User sentUser) {
		Confirmation confirmationToken = new Confirmation(sentUser);
		confirmationService.createConfirmationToken(confirmationToken);
		
		String toEmail = sentUser.getEmail();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
        +"http://localhost:9022/confirm-account/?token="+confirmationToken.getConfirmationToken());

        emailService.sendEmail(mailMessage);
	}	
	
	@RequestMapping(value="/confirm-account/*", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken){

		Confirmation token = confirmationService.findByConfirmationToken(confirmationToken);

        if(token != null){
        	User user = userService.getUserByEmail(token.getUser().getEmail());
        	user.setConfirmed(true);
        	userService.createUser(user);
            
        	token.setConfirmationToken(null);
            
        }
        else{
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }
	
}