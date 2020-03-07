package prs.web.request;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import prs.web.product.Product;
import prs.web.util.JsonResponse;

@CrossOrigin
@RestController
@RequestMapping("/requests")
public class RequestController {
	
	public static final String REQUEST_STATUS_NEW = "New";
	public static final String REQUEST_STATUS_EDIT = "Edit";
	public static final String REQUEST_STATUS_REVIEW = "Review";
	public static final String REQUEST_STATUS_APPROVED = "Approved";
	public static final String REQUEST_STATUS_REJECTED = "Rejected";
	
	@Autowired
	private RequestRepository requestRepo;
	
	@GetMapping()
	public JsonResponse getAll() {
		try {
			return JsonResponse.getInstance(requestRepo.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable Integer id) {
		try {
			Optional<Request> r = requestRepo.findById(id);
			if(r==null) 
				return JsonResponse.getInstance("Id cannot be null.");
			if(!r.isPresent()) {
				return JsonResponse.getInstance("Request not found.");
			}
			return JsonResponse.getInstance(r.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	private JsonResponse save(Request request) {
		try {
			return JsonResponse.getInstance(requestRepo.save(request));
		} catch (IllegalArgumentException iae) {
			return JsonResponse.getInstance("Request cannot be null.");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e.getMessage());
		}
	}
	
	@PostMapping("/")
	public JsonResponse add(@RequestBody Request request) {
		try {
			request.setStatus(REQUEST_STATUS_NEW);
			request.setTotal(0);
			request.setSubmittedDate(new Date(System.currentTimeMillis()));
			return save(request);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}

	@GetMapping("/list-review/{userId}")
	public JsonResponse getRequestWithStatusOfReview(@PathVariable Integer userId) {
		try {
			if(userId==null) return JsonResponse.getInstance("The id for this user cannot be null.");
			Iterable<Request> requests = requestRepo.getRequestByStatusAndUserIdNot(REQUEST_STATUS_REVIEW, userId);
			return JsonResponse.getInstance(requests);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	private JsonResponse setRequestStatus(Request request, String status) {
		try {
			request.setStatus(status);
			return save(request);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PutMapping("/submit-review")
	public JsonResponse review(@RequestBody Request request) {
		try {
			request.setSubmittedDate(new Date(System.currentTimeMillis()));
			if(request.getTotal() <= 50) {
				return setRequestStatus(request, REQUEST_STATUS_APPROVED);
			}
			return setRequestStatus(request, REQUEST_STATUS_REVIEW);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PutMapping("/approve")
	public JsonResponse approve(@RequestBody Request request) {
		try {
			return setRequestStatus(request, REQUEST_STATUS_APPROVED);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PutMapping("/reject")
	public JsonResponse reject(@RequestBody Request request) {
		try {
			return setRequestStatus(request, REQUEST_STATUS_REJECTED);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PutMapping("/")
	public JsonResponse update(@RequestBody Request request) {
		try {
			return save(request);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse delete(@PathVariable Integer id) {
		try {
			Optional<Request> r = requestRepo.findById(id);
			if(id==null)
				return JsonResponse.getInstance("Id cannot be null");
			if(!r.isPresent()) 
				return JsonResponse.getInstance("Request not found");
			requestRepo.deleteById(r.get().getId());
			return JsonResponse.getInstance(r.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
}
