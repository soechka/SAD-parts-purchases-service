/*
 * This file is generated by jOOQ.
 */
package com.example.sadbe.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Supplies implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID          id;
    private UUID          entityId;
    private Long          amount;
    private LocalDateTime date;
    private UUID          suppliedParts;
    private LocalDateTime datetimeOfCreation;
    private LocalDateTime datetimeOfDelete;

    public Supplies() {}

    public Supplies(Supplies value) {
        this.id = value.id;
        this.entityId = value.entityId;
        this.amount = value.amount;
        this.date = value.date;
        this.suppliedParts = value.suppliedParts;
        this.datetimeOfCreation = value.datetimeOfCreation;
        this.datetimeOfDelete = value.datetimeOfDelete;
    }

    public Supplies(
        UUID          id,
        UUID          entityId,
        Long          amount,
        LocalDateTime date,
        UUID          suppliedParts,
        LocalDateTime datetimeOfCreation,
        LocalDateTime datetimeOfDelete
    ) {
        this.id = id;
        this.entityId = entityId;
        this.amount = amount;
        this.date = date;
        this.suppliedParts = suppliedParts;
        this.datetimeOfCreation = datetimeOfCreation;
        this.datetimeOfDelete = datetimeOfDelete;
    }

    /**
     * Getter for <code>sad.supplies.id</code>.
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Setter for <code>sad.supplies.id</code>.
     */
    public Supplies setId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Getter for <code>sad.supplies.entity_id</code>.
     */
    public UUID getEntityId() {
        return this.entityId;
    }

    /**
     * Setter for <code>sad.supplies.entity_id</code>.
     */
    public Supplies setEntityId(UUID entityId) {
        this.entityId = entityId;
        return this;
    }

    /**
     * Getter for <code>sad.supplies.amount</code>.
     */
    public Long getAmount() {
        return this.amount;
    }

    /**
     * Setter for <code>sad.supplies.amount</code>.
     */
    public Supplies setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Getter for <code>sad.supplies.date</code>.
     */
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Setter for <code>sad.supplies.date</code>.
     */
    public Supplies setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    /**
     * Getter for <code>sad.supplies.supplied_parts</code>.
     */
    public UUID getSuppliedParts() {
        return this.suppliedParts;
    }

    /**
     * Setter for <code>sad.supplies.supplied_parts</code>.
     */
    public Supplies setSuppliedParts(UUID suppliedParts) {
        this.suppliedParts = suppliedParts;
        return this;
    }

    /**
     * Getter for <code>sad.supplies.datetime_of_creation</code>.
     */
    public LocalDateTime getDatetimeOfCreation() {
        return this.datetimeOfCreation;
    }

    /**
     * Setter for <code>sad.supplies.datetime_of_creation</code>.
     */
    public Supplies setDatetimeOfCreation(LocalDateTime datetimeOfCreation) {
        this.datetimeOfCreation = datetimeOfCreation;
        return this;
    }

    /**
     * Getter for <code>sad.supplies.datetime_of_delete</code>.
     */
    public LocalDateTime getDatetimeOfDelete() {
        return this.datetimeOfDelete;
    }

    /**
     * Setter for <code>sad.supplies.datetime_of_delete</code>.
     */
    public Supplies setDatetimeOfDelete(LocalDateTime datetimeOfDelete) {
        this.datetimeOfDelete = datetimeOfDelete;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Supplies other = (Supplies) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (entityId == null) {
            if (other.entityId != null)
                return false;
        }
        else if (!entityId.equals(other.entityId))
            return false;
        if (amount == null) {
            if (other.amount != null)
                return false;
        }
        else if (!amount.equals(other.amount))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        }
        else if (!date.equals(other.date))
            return false;
        if (suppliedParts == null) {
            if (other.suppliedParts != null)
                return false;
        }
        else if (!suppliedParts.equals(other.suppliedParts))
            return false;
        if (datetimeOfCreation == null) {
            if (other.datetimeOfCreation != null)
                return false;
        }
        else if (!datetimeOfCreation.equals(other.datetimeOfCreation))
            return false;
        if (datetimeOfDelete == null) {
            if (other.datetimeOfDelete != null)
                return false;
        }
        else if (!datetimeOfDelete.equals(other.datetimeOfDelete))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.entityId == null) ? 0 : this.entityId.hashCode());
        result = prime * result + ((this.amount == null) ? 0 : this.amount.hashCode());
        result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
        result = prime * result + ((this.suppliedParts == null) ? 0 : this.suppliedParts.hashCode());
        result = prime * result + ((this.datetimeOfCreation == null) ? 0 : this.datetimeOfCreation.hashCode());
        result = prime * result + ((this.datetimeOfDelete == null) ? 0 : this.datetimeOfDelete.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Supplies (");

        sb.append(id);
        sb.append(", ").append(entityId);
        sb.append(", ").append(amount);
        sb.append(", ").append(date);
        sb.append(", ").append(suppliedParts);
        sb.append(", ").append(datetimeOfCreation);
        sb.append(", ").append(datetimeOfDelete);

        sb.append(")");
        return sb.toString();
    }
}
