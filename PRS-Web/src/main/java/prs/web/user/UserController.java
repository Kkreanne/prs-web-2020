package prs.web.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import prs.web.util.JsonResponse;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/login")
	public JsonResponse login(@RequestBody User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		try {
			User u = userRepo.findByUsernameAndPassword(username, password);
			if(u==null) {
				return JsonResponse.getInstance("Username or password incorrect. Please try again.");
			}
			return JsonResponse.getInstance(u);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e.getMessage());
		}
	}
	
	@GetMapping()
	public JsonResponse getAll() {
		try {
			return JsonResponse.getInstance(userRepo.findAll());			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable Integer id) {
		try {
			Optional<User> u = userRepo.findById(id);
			if(u==null)
				return JsonResponse.getInstance("Id cannot be null.");
			if(!u.isPresent()) {
				return JsonResponse.getInstance("User not found.");
			}
			return JsonResponse.getInstance(u.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e.getMessage());
		}
	}
		
	private JsonResponse save(User user) {
		try {
			return JsonResponse.getInstance(userRepo.save(user));
		} catch (IllegalArgumentException iae) {
			return JsonResponse.getInstance("User cannot be null.");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e.getMessage());
		}
	}
	
	@PostMapping("/")
	public JsonResponse insert(@RequestBody User user) {
		try {
			return save(user);			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PutMapping("/")
	public JsonResponse update(@RequestBody User user) {
		try {
			return save(user);			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse delete(@PathVariable Integer id) {
		try {
			Optional<User> u = userRepo.findById(id);
			if(id==null)
				return JsonResponse.getInstance("Id cannot be null.");
			if(!u.isPresent()) {
				return JsonResponse.getInstance("User not found.");
			}
			userRepo.deleteById(id);
			return JsonResponse.getInstance(u.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
}
