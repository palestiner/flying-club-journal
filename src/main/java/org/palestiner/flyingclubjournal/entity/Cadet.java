package org.palestiner.flyingclubjournal.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "CADET", indexes = {
        @Index(name = "IDX_CADET_RATE_ID", columnList = "RATE_ID")
})
@Entity
public class Cadet {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull(message = "{msg://org.palestiner.flyingclubjournal.entity/Cadet.name.validation.NotNull}")
    @InstanceName
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull(message = "{msg://org.palestiner.flyingclubjournal.entity/Cadet.surname.validation.NotNull}")
    @Column(name = "SURNAME", nullable = false)
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull(message = "{msg://org.palestiner.flyingclubjournal.entity/Cadet.rate.validation.NotNull}")
    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "RATE_ID", nullable = false)
    private Rate rate;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

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

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
