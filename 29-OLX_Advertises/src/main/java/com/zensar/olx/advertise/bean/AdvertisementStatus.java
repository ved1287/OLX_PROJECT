package com.zensar.olx.advertise.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Embeddable
public class AdvertisementStatus {
	@Column(name="adv_status_id")
	private int id;
	@Transient
	private String status;

	public AdvertisementStatus(int id, String status) {
		super();
		this.id = id;
		this.status = status;
	}

	public AdvertisementStatus(String status) {
		super();
		this.status = status;
	}

	public AdvertisementStatus(int id) {
		super();
		this.id = id;
	}

	public AdvertisementStatus() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AdvertisementStatus [id=" + id + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdvertisementStatus other = (AdvertisementStatus) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
}
