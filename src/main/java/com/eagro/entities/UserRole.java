package com.eagro.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * UserRoles entity.
 */
@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_desc")
    private String roleDesc;

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

    @OneToOne
    @JsonIgnore
    private User user;

    public Long getRoleId() {
        return roleId;
    }

    public UserRole roleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public UserRole roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public UserRole roleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
        return this;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
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

    public UserRole createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public UserRole createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public UserRole updatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public UserRole updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public User getUser() {
        return user;
    }

    public UserRole user(User user) {
        this.user = user;
        return this;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserRole userRole = (UserRole) o;
        if (userRole.getRoleId() == null || getRoleId() == null) {
            return false;
        }
        return Objects.equals(getRoleId(), userRole.getRoleId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getRoleId());
    }

    @Override
    public String toString() {
        return "UserRole{" +
            ", roleId=" + getRoleId() +
            ", roleName='" + getRoleName() + "'" +
            ", roleDesc='" + getRoleDesc() + "'" +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
