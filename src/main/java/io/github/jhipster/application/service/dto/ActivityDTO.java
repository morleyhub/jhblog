package io.github.jhipster.application.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;

import io.github.jhipster.application.domain.ActivityParticipation;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Activity entity.
 */
public class ActivityDTO implements Serializable {

    private Long id;

    @Size(max = 64)
    private String title;

    @Size(max = 128)
    private String descrption;

    @Size(max = 128)
    private String wechatUserId;

    private Instant signStartTime;

    private Instant signEndTime;

    private Instant activityStartTime;

    private Instant activityEndTime;
    
    private Set<ActivityParticipationDTO> activityParticipations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public Instant getSignStartTime() {
        return signStartTime;
    }

    public void setSignStartTime(Instant signStartTime) {
        this.signStartTime = signStartTime;
    }

    public Instant getSignEndTime() {
        return signEndTime;
    }

    public void setSignEndTime(Instant signEndTime) {
        this.signEndTime = signEndTime;
    }

    public Instant getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(Instant activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public Instant getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(Instant activityEndTime) {
        this.activityEndTime = activityEndTime;
    }
    

	public Set<ActivityParticipationDTO> getActivityParticipations() {
		return activityParticipations;
	}

	public void setActivityParticipations(Set<ActivityParticipationDTO> activityParticipations) {
		this.activityParticipations = activityParticipations;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActivityDTO activityDTO = (ActivityDTO) o;
        if(activityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActivityDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", descrption='" + getDescrption() + "'" +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", signStartTime='" + getSignStartTime() + "'" +
            ", signEndTime='" + getSignEndTime() + "'" +
            ", activityStartTime='" + getActivityStartTime() + "'" +
            ", activityEndTime='" + getActivityEndTime() + "'" +
            "}";
    }
}
