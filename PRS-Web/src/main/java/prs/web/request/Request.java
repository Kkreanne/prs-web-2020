package prs.web.request;

import java.util.Date;
import javax.persistence.*;

import prs.web.user.User;

@Entity
@Table
public class Request {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=100, nullable=false)
	private String description;
	@Column(length=255, nullable=false)
	private String justification;
	@Temporal(TemporalType.DATE)
	@Column(columnDefinition="date default current_timestamp NOT NULL", insertable=true, updatable=true)
	private Date dateNeeded;
	@Column(length=25, nullable=false)
	private String deliveryMode;
	@Column(columnDefinition="varchar(20) NOT NULL DEFAULT 'NEW'")
	private String status;
	@Column(columnDefinition="deciaml(10, 2) NOT NULL DEFAULT 0.0")
	private double total;
	@Column(columnDefinition="date default current_timestamp NOT NULL")
	private Date submittedDate;
	@Column(length=100, nullable=true)
	private String reasonForRejection;
	@ManyToOne(optional=false)
	@JoinColumn(name="userId")
	private User user;
	
	public Request() {
	}

	public Request(int id, String description, String justification, Date dateNeeded, String deliveryMode,
			String status, double total, Date submittedDate, String reasonForRejection, User user) {
		super();
		this.id = id;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = status;
		this.total = total;
		this.submittedDate = submittedDate;
		this.reasonForRejection = reasonForRejection;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public Date getDateNeeded() {
		return dateNeeded;
	}

	public void setDateNeeded(Date dateNeeded) {
		this.dateNeeded = dateNeeded;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getReasonForRejection() {
		return reasonForRejection;
	}

	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", description=" + description + ", justification=" + justification
				+ ", dateNeeded=" + dateNeeded + ", deliveryMode=" + deliveryMode + ", status=" + status + ", total="
				+ total + ", submittedDate=" + submittedDate + ", reasonForRejection=" + reasonForRejection + ", user="
				+ user + "]";
	}
}
