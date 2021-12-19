package org.palestiner.flyingclubjournal.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "FIGHT_ACCOUNTING", indexes = {
        @Index(name = "IDX_FIGHTACCOUNTING_CADET_ID", columnList = "CADET_ID"),
        @Index(name = "IDX_FIGHTACCOUNTING", columnList = "INSTRUCTOR_ID"),
        @Index(name = "IDX_FIGHTACCOUNTING", columnList = "FLIGHT_TYPE_ID")
})
@Entity
public class FightAccounting {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull(message = "{msg://org.palestiner.flyingclubjournal.entity/FightAccounting.cadet.validation.NotNull}")
    @OnDeleteInverse(DeletePolicy.DENY)
    @OnDelete(DeletePolicy.UNLINK)
    @JoinColumn(name = "CADET_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cadet cadet;

    @NotNull(message = "{msg://org.palestiner.flyingclubjournal.entity/FightAccounting.instructor.validation.NotNull}")
    @OnDeleteInverse(DeletePolicy.DENY)
    @OnDelete(DeletePolicy.UNLINK)
    @JoinColumn(name = "INSTRUCTOR_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Instructor instructor;

    @NotNull(message = "{msg://org.palestiner.flyingclubjournal.entity/FightAccounting.flightDate.validation.NotNull}")
    @Column(name = "FLIGHT_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date flightDate = new Date();

    @NotNull(message = "{msg://org.palestiner.flyingclubjournal.entity/FightAccounting.flightTime.validation.NotNull}")
    @Column(name = "FLIGHT_TIME", nullable = false)
    private Double flightTime;

    @OnDeleteInverse(DeletePolicy.DENY)
    @OnDelete(DeletePolicy.UNLINK)
    @JoinColumn(name = "FLIGHT_TYPE_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private FlightType flightType;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    public FlightType getFlightType() {
        return flightType;
    }

    public void setFlightType(FlightType flightType) {
        this.flightType = flightType;
    }

    public Double getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Double flightTime) {
        this.flightTime = flightTime;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Cadet getCadet() {
        return cadet;
    }

    public void setCadet(Cadet cadet) {
        this.cadet = cadet;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @InstanceName
    @DependsOnProperties({"flightDate", "flightTime", "cadet"})
    public String getInstanceName() {
        return String.format("%s %f min [%s]", flightDate, flightTime, cadet);
    }
}
