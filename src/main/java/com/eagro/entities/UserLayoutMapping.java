package com.eagro.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * UserLayoutMapping entity.
 */
@Entity
@Table(name = "user_layout_mapping")
public class UserLayoutMapping implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_user_layout_mapping")
	@SequenceGenerator(name = "sequence_user_layout_mapping", sequenceName = "sequence_user_layout_mapping", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "active_flag")
	private boolean activeFlag;

	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name = "created_date", columnDefinition = "TIMESTAMP(3)", nullable = false)
	private LocalDateTime createdDate;

	@Column(name = "created_by")
	private String createdBy;

	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@OneToOne
	@JoinColumn(name = "layout_id", nullable = false)
	private Layout layout;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	

	public String getCreatedBy() {
		return createdBy;
	}

	public UserLayoutMapping createdBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public UserLayoutMapping updatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		return this;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserLayoutMapping userLayoutMapping = (UserLayoutMapping) o;
		if (userLayoutMapping.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), userLayoutMapping.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "UserLayoutMapping{" + "id=" + getId() + ", activeFlag='" + isActiveFlag() + "'" + ", createdDate='"
				+ getCreatedDate() + "'" + ", createdBy='" + getCreatedBy() + "'" + ", updatedDate='" + getUpdatedDate()
				+ "'" + ", updatedBy='" + getUpdatedBy() + "'" + "}";
	}
}
