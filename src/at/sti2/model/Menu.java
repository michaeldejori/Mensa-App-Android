package at.sti2.model;

import java.util.Date;

public class Menu {

	private String name;
	private String description;
	private Date availabilityStarts;
	private Date availabilityEnds;

	public Menu(String name, String description, Date availabilityStarts, Date availabilityEnds) {
		this.name = name;
		this.description = description;
		this.availabilityStarts = availabilityStarts;
		this.availabilityEnds = availabilityEnds;
	}

	public Menu() {
		// TODO Auto-generated constructor stub
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getAvailabilityStarts() {
		return availabilityStarts;
	}

	public void setAvailabilityStarts(Date availabilityStarts) {
		this.availabilityStarts = availabilityStarts;
	}

	public Date getAvailabilityEnds() {
		return availabilityEnds;
	}

	public void setAvailabilityEnds(Date availabilityEnds) {
		this.availabilityEnds = availabilityEnds;
	}

}
