package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 活动标题
     */
    @Size(max = 64)
    @ApiModelProperty(value = "活动标题")
    @Column(name = "title", length = 64)
    private String title;

    /**
     * 活动描述
     */
    @Size(max = 128)
    @ApiModelProperty(value = "活动描述")
    @Column(name = "descrption", length = 128)
    private String descrption;

    /**
     * 用户id 创建人
     */
    @Size(max = 128)
    @ApiModelProperty(value = "用户id 创建人")
    @Column(name = "wechat_user_id", length = 128)
    private String wechatUserId;

    /**
     * 报名开始时间
     */
    @ApiModelProperty(value = "报名开始时间")
    @Column(name = "sign_start_time")
    private Instant signStartTime;

    /**
     * 报名截至时间
     */
    @ApiModelProperty(value = "报名截至时间")
    @Column(name = "sign_end_time")
    private Instant signEndTime;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @Column(name = "activity_start_time")
    private Instant activityStartTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @Column(name = "activity_end_time")
    private Instant activityEndTime;

    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<ActivityParticipation> activityParticipations = new HashSet<>();

    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<Pics> pics = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Activity title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrption() {
        return descrption;
    }

    public Activity descrption(String descrption) {
        this.descrption = descrption;
        return this;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public Activity wechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public Instant getSignStartTime() {
        return signStartTime;
    }

    public Activity signStartTime(Instant signStartTime) {
        this.signStartTime = signStartTime;
        return this;
    }

    public void setSignStartTime(Instant signStartTime) {
        this.signStartTime = signStartTime;
    }

    public Instant getSignEndTime() {
        return signEndTime;
    }

    public Activity signEndTime(Instant signEndTime) {
        this.signEndTime = signEndTime;
        return this;
    }

    public void setSignEndTime(Instant signEndTime) {
        this.signEndTime = signEndTime;
    }

    public Instant getActivityStartTime() {
        return activityStartTime;
    }

    public Activity activityStartTime(Instant activityStartTime) {
        this.activityStartTime = activityStartTime;
        return this;
    }

    public void setActivityStartTime(Instant activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public Instant getActivityEndTime() {
        return activityEndTime;
    }

    public Activity activityEndTime(Instant activityEndTime) {
        this.activityEndTime = activityEndTime;
        return this;
    }

    public void setActivityEndTime(Instant activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public Set<ActivityParticipation> getActivityParticipations() {
        return activityParticipations;
    }

    public Activity activityParticipations(Set<ActivityParticipation> activityParticipations) {
        this.activityParticipations = activityParticipations;
        return this;
    }

    public Activity addActivityParticipation(ActivityParticipation activityParticipation) {
        this.activityParticipations.add(activityParticipation);
        activityParticipation.setActivity(this);
        return this;
    }

    public Activity removeActivityParticipation(ActivityParticipation activityParticipation) {
        this.activityParticipations.remove(activityParticipation);
        activityParticipation.setActivity(null);
        return this;
    }

    public void setActivityParticipations(Set<ActivityParticipation> activityParticipations) {
        this.activityParticipations = activityParticipations;
    }

    public Set<Pics> getPics() {
        return pics;
    }

    public Activity pics(Set<Pics> pics) {
        this.pics = pics;
        return this;
    }

    public Activity addPics(Pics pics) {
        this.pics.add(pics);
        pics.setActivity(this);
        return this;
    }

    public Activity removePics(Pics pics) {
        this.pics.remove(pics);
        pics.setActivity(null);
        return this;
    }

    public void setPics(Set<Pics> pics) {
        this.pics = pics;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        if (activity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Activity{" +
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
