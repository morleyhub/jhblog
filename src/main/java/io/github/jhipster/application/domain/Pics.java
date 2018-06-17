package io.github.jhipster.application.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Pics.
 */
@Entity
@Table(name = "pics")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 图片路径
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "图片路径")
    @Column(name = "oss_path", length = 1024)
    private String ossPath;

    @ManyToOne
    private Activity activity;

    @ManyToOne
    private ClockIn clockIn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOssPath() {
        return ossPath;
    }

    public Pics ossPath(String ossPath) {
        this.ossPath = ossPath;
        return this;
    }

    public void setOssPath(String ossPath) {
        this.ossPath = ossPath;
    }

    public Activity getActivity() {
        return activity;
    }

    public Pics activity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ClockIn getClockIn() {
        return clockIn;
    }

    public Pics clockIn(ClockIn clockIn) {
        this.clockIn = clockIn;
        return this;
    }

    public void setClockIn(ClockIn clockIn) {
        this.clockIn = clockIn;
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
        Pics pics = (Pics) o;
        if (pics.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pics.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pics{" +
            "id=" + getId() +
            ", ossPath='" + getOssPath() + "'" +
            "}";
    }
}
