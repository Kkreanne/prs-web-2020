package prs.web.vendor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import prs.web.util.JsonResponse;

@CrossOrigin
@RestController
@RequestMapping("/vendors")
public class VendorController {

	@Autowired
	private VendorRepository vendorRepo;
	
	@GetMapping()
	public JsonResponse getAll() {
		try {
			return JsonResponse.getInstance(vendorRepo.findAll());			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable Integer id) {
		try {
			Optional<Vendor> v = vendorRepo.findById(id);
			if(v==null)
				return JsonResponse.getInstance("Id cannot be null.");
			if(!v.isPresent()) {
				return JsonResponse.getInstance("Vendor not found.");
			}
			return JsonResponse.getInstance(v.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e.getMessage());
		}
	}
	
	private JsonResponse save(Vendor vendor) {
		try {
			return JsonResponse.getInstance(vendorRepo.save(vendor));
		} catch (IllegalArgumentException iae) {
			return JsonResponse.getInstance("Vendor cannot be null.");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e.getMessage());
		}
	}
	
	@PostMapping("/")
	public JsonResponse insert(@RequestBody Vendor vendor) {
		try {
			return save(vendor);			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@PutMapping("/")
	public JsonResponse update(@RequestBody Vendor vendor) {
		try {
			return save(vendor);			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse delete(@PathVariable Integer id) {
		try {
			Optional<Vendor> v = vendorRepo.findById(id);
			if(id==null)
				return JsonResponse.getInstance("Id cannot be null.");
			if(!v.isPresent()) {
				return JsonResponse.getInstance("Vendor not found.");
			}
			vendorRepo.deleteById(v.get().getId());
			return JsonResponse.getInstance(v.get());
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResponse.getInstance(e);
		}
	}
}
