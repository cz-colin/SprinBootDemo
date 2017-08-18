package com.cloudzon.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(length = 36)
	private String uuid;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "last_modified_by")
	private Long lastModifiedBy;

	@CreatedDate
	@Column(name = "created_date", nullable = false)
	@JsonIgnore
	private Date createdDate = new Date();

	@LastModifiedDate
	@Column(name = "last_modified_date")
	@JsonIgnore
	private Date lastModifiedDate = new Date();

	public BaseEntity() {
		this(UUID.randomUUID());
	}

	public BaseEntity(UUID guid) {
		Assert.notNull(guid, "UUID is required");
		setUuid(guid.toString());
	}

	public UUID getUuid() {
		return UUID.fromString(uuid);
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int hashCode() {
		return getUuid().hashCode();
	}

	public Object getIdentifier() {
		return getUuid().toString();
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
