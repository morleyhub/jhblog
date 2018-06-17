package io.github.jhipster.application.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;

import io.github.jhipster.application.domain.Pics;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ClockIn entity.
 */
public class ClockInDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String wechatUserId;

    @Size(max = 1024)
    private String signNote;

    private Instant punchDateTime;

    private Long activityParticipationId;
    
    private Set<PicsDTO> pics = new HashSet<>();

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

    public String getSignNote() {
        return signNote;
    }

    public void setSignNote(String signNote) {
        this.signNote = signNote;
    }

    public Instant getPunchDateTime() {
        return punchDateTime;
    }

    public void setPunchDateTime(Instant punchDateTime) {
        this.punchDateTime = punchDateTime;
    }

    public Long getActivityParticipationId() {
        return activityParticipationId;
    }

    public void setActivityParticipationId(Long activityParticipationId) {
        this.activityParticipationId = activityParticipationId;
    }
    

    public Set<PicsDTO> getPics() {
		return pics;
	}

	public void setPics(Set<PicsDTO> pics) {
		this.pics = pics;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClockInDTO clockInDTO = (ClockInDTO) o;
        if(clockInDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clockInDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClockInDTO{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", signNote='" + getSignNote() + "'" +
            ", punchDateTime='" + getPunchDateTime() + "'" +
            "}";
    }
}
