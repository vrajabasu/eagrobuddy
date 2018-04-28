package com.eagro.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * UserLayoutMapping entity.
 */
@Entity
@Table(name = "user_layout_mapping")
public class UserLayoutMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "active_flag")
    private boolean activeFlag;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToMany(mappedBy = "userLayoutMappings")
    @JsonIgnore
    private Set<Layout> layouts = new HashSet<>();

    @ManyToMany(mappedBy = "userLayoutMappings")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

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

	public LocalDate getCreatedDate() {
        return createdDate;
    }

    public UserLayoutMapping createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
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

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public UserLayoutMapping updatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
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

    public Set<Layout> getLayouts() {
        return layouts;
    }

    public UserLayoutMapping layouts(Set<Layout> layouts) {
        this.layouts = layouts;
        return this;
    }

    public UserLayoutMapping addLayout(Layout layout) {
        this.layouts.add(layout);
        layout.getUserLayoutMappings().add(this);
        return this;
    }

    public UserLayoutMapping removeLayout(Layout layout) {
        this.layouts.remove(layout);
        layout.getUserLayoutMappings().remove(this);
        return this;
    }

    public void setLayouts(Set<Layout> layouts) {
        this.layouts = layouts;
    }

    public Set<User> getEagroUsers() {
        return users;
    }

    public UserLayoutMapping eagroUsers(Set<User> eagroUsers) {
        this.users = eagroUsers;
        return this;
    }

    public UserLayoutMapping addEagroUser(User eagroUser) {
        this.users.add(eagroUser);
        eagroUser.getUserLayoutMappings().add(this);
        return this;
    }

    public UserLayoutMapping removeEagroUser(User eagroUser) {
        this.users.remove(eagroUser);
        eagroUser.getUserLayoutMappings().remove(this);
        return this;
    }

    public void setEagroUsers(Set<User> eagroUsers) {
        this.users = eagroUsers;
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
        return "UserLayoutMapping{" +
            "id=" + getId() +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
