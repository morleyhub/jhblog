package io.github.jhipster.application.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;

import io.github.jhipster.application.domain.ClockIn;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ActivityParticipation entity.
 */
public class ActivityParticipationDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String wechatUserId;

    private Instant participationTime;

    private Long activityId;
    
    private Set<ClockInDTO> clockIns = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public Instant getParticipationTime() {
        return participationTime;
    }

    public void setParticipationTime(Instant participationTime) {
        this.participationTime = participationTime;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Set<ClockInDTO> getClockIns() {
		return clockIns;
	}

	public void setClockIns(Set<ClockInDTO> clockIns) {
		this.clockIns = clockIns;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActivityParticipationDTO activityParticipationDTO = (ActivityParticipationDTO) o;
        if(activityParticipationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activityParticipationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActivityParticipationDTO{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", participationTime='" + getParticipationTime() + "'" +
            "}";
    }
}
