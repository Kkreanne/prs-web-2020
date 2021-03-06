package prs.web.line;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemRepository extends JpaRepository<LineItem, Integer> {
	Iterable<LineItem> getLineItemByRequestId(int requestId);
}
