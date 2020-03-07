package prs.web.line;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import prs.web.request.Request;
import prs.web.request.RequestController;
import prs.web.request.RequestRepository;
import prs.web.util.JsonResponse;

@CrossOrigin
@RestController
@RequestMapping("/line/items")
public class LineItemController {

	@Autowired
	private LineItemRepository lineItemRepo;
	@Autowired
	private RequestRepository requestRepo;
	
	private void calculation(int requestId) throws Exception {
		Optional<Request> r = requestRepo.findById(requestId);
		if(!r.isPresent()) {
			throw new Exception("Cannot find request with id " + requestId);
		}
		Request request = r.get();
		Iterable<LineItem> lines = lineItemRepo.getLineItemByRequestId(request.getId());
		double total = 0;
		for(LineItem line : lines) {
			total += line.getQuantity() * line.getProduct().getPrice();
		}
		request.setTotal(total);
		request.setStatus(RequestController.REQUEST_STATUS_EDIT);
		requestRepo.save(request);
	}
	
	@GetMapping()
	public JsonResponse listAll() {
		try {
			return JsonResponse.getInstance(lineItemRepo.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable Integer id) {
		try {
			Optional<LineItem> l = lineItemRepo.findById(id);
			if(id==null) 
				return JsonResponse.getInstance("The id cannot be null.");
			if(!l.isPresent()) {
				return JsonResponse.getInstance("Line Item not found.");
			}
			return JsonResponse.getInstance(l.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e.getMessage());
		}
	}
	
	private JsonResponse save(LineItem lineItem) {
		try {
			return JsonResponse.getInstance(lineItemRepo.saveAndFlush(lineItem));
		} catch (IllegalArgumentException iae) {
			return JsonResponse.getInstance(iae);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e.getMessage());
		}
	}
	
	@PostMapping("/")
	public JsonResponse insert(@RequestBody LineItem lineItem) {
		try {
			JsonResponse jr = save(lineItem);
			calculation(lineItem.getRequest().getId());
			return jr;
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PutMapping("/")
	public JsonResponse update(@RequestBody LineItem lineItem) {
		try {
			JsonResponse jr = save(lineItem);
			calculation(lineItem.getRequest().getId());
			return jr;
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse delete(@PathVariable Integer id) {
		try {
			Optional<LineItem> l = lineItemRepo.findById(id);
			if(id==null) 
				return JsonResponse.getInstance("Cannot be null.");
			if(!l.isPresent()) return JsonResponse.getInstance("Line Item not found.");
			LineItem line = lineItemRepo.getOne(id);
			lineItemRepo.deleteById(id);
			lineItemRepo.flush();
			calculation(line.getRequest().getId());
			return JsonResponse.getInstance(l.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@GetMapping("/request/{id}")
	public JsonResponse getByRequest(@PathVariable Integer id) {
		try {
			Iterable<LineItem> lines = lineItemRepo.getLineItemByRequestId(id);
			return JsonResponse.getInstance(lines);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
}