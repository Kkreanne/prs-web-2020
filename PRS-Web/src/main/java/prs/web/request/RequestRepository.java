package prs.web.request;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Integer> {
	Iterable<Request> getRequestByStatusAndUserIdNot(String status, Integer id);
}
