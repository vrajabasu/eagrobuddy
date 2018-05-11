package com.eagro.service.dto;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A DTO for the Layout entity.
 */
public class LayoutDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long layoutId;

    private String layoutName;

    private String layoutDesc;

    private Double widthX;

    private Double heightY;

    private boolean activeFlag;

    private LocalDateTime createdDate;

    private String createdBy;

    private LocalDateTime updatedDate;

    private String updatedBy;

    public Long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }

    public String getLayoutDesc() {
        return layoutDesc;
    }

    public void setLayoutDesc(String layoutDesc) {
        this.layoutDesc = layoutDesc;
    }

    public Double getWidthX() {
        return widthX;
    }

    public void setWidthX(Double widthX) {
        this.widthX = widthX;
    }

    public Double getHeightY() {
        return heightY;
    }

    public void setHeightY(Double heightY) {
        this.heightY = heightY;
    }



    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LayoutDTO layoutDTO = (LayoutDTO) o;
        if(layoutDTO.getLayoutId() == null || getLayoutId() == null) {
            return false;
        }
        return Objects.equals(getLayoutId(), layoutDTO.getLayoutId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getLayoutId());
    }

    @Override
    public String toString() {
        return "LayoutDTO{" +
            ", layoutId=" + getLayoutId() +
            ", layoutName='" + getLayoutName() + "'" +
            ", layoutDesc='" + getLayoutDesc() + "'" +
            ", widthX=" + getWidthX() +
            ", heightY=" + getHeightY() +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }

	public boolean isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
}
