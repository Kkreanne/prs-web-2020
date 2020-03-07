package prs.web.line;

import javax.persistence.*;

import prs.web.product.Product;
import prs.web.request.Request;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(name="UIDX_request_product", columnNames= {"requestId", "productId"}))
public class LineItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=100, nullable=false)
	private int quantity;
	@ManyToOne(optional=false)
	@JoinColumn(name="requestId")
	private Request request;
	@ManyToOne(optional=false)
	@JoinColumn(name="productId")
	private Product product;
	
	public LineItem() {
	}

	public LineItem(int id, int quantity, Request request, Product product) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.request = request;
		this.product = product;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "LineItem [id=" + id + ", quantity=" + quantity + ", product=" + product + "]";
	}
}
