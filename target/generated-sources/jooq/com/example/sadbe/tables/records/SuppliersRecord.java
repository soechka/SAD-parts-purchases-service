/*
 * This file is generated by jOOQ.
 */
package com.example.sadbe.tables.records;


import com.example.sadbe.tables.Suppliers;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SuppliersRecord extends UpdatableRecordImpl<SuppliersRecord> implements Record7<UUID, UUID, String, String, String, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>sad.suppliers.id</code>.
     */
    public SuppliersRecord setId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>sad.suppliers.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>sad.suppliers.entity_id</code>.
     */
    public SuppliersRecord setEntityId(UUID value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>sad.suppliers.entity_id</code>.
     */
    public UUID getEntityId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>sad.suppliers.name</code>.
     */
    public SuppliersRecord setName(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>sad.suppliers.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>sad.suppliers.address</code>.
     */
    public SuppliersRecord setAddress(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>sad.suppliers.address</code>.
     */
    public String getAddress() {
        return (String) get(3);
    }

    /**
     * Setter for <code>sad.suppliers.phone</code>.
     */
    public SuppliersRecord setPhone(String value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>sad.suppliers.phone</code>.
     */
    public String getPhone() {
        return (String) get(4);
    }

    /**
     * Setter for <code>sad.suppliers.datetime_of_creation</code>.
     */
    public SuppliersRecord setDatetimeOfCreation(LocalDateTime value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>sad.suppliers.datetime_of_creation</code>.
     */
    public LocalDateTime getDatetimeOfCreation() {
        return (LocalDateTime) get(5);
    }

    /**
     * Setter for <code>sad.suppliers.datetime_of_delete</code>.
     */
    public SuppliersRecord setDatetimeOfDelete(LocalDateTime value) {
        set(6, value);
        return this;
    }

    /**
     * Getter for <code>sad.suppliers.datetime_of_delete</code>.
     */
    public LocalDateTime getDatetimeOfDelete() {
        return (LocalDateTime) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<UUID, UUID, String, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<UUID, UUID, String, String, String, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Suppliers.SUPPLIERS.ID;
    }

    @Override
    public Field<UUID> field2() {
        return Suppliers.SUPPLIERS.ENTITY_ID;
    }

    @Override
    public Field<String> field3() {
        return Suppliers.SUPPLIERS.NAME;
    }

    @Override
    public Field<String> field4() {
        return Suppliers.SUPPLIERS.ADDRESS;
    }

    @Override
    public Field<String> field5() {
        return Suppliers.SUPPLIERS.PHONE;
    }

    @Override
    public Field<LocalDateTime> field6() {
        return Suppliers.SUPPLIERS.DATETIME_OF_CREATION;
    }

    @Override
    public Field<LocalDateTime> field7() {
        return Suppliers.SUPPLIERS.DATETIME_OF_DELETE;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public UUID component2() {
        return getEntityId();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public String component4() {
        return getAddress();
    }

    @Override
    public String component5() {
        return getPhone();
    }

    @Override
    public LocalDateTime component6() {
        return getDatetimeOfCreation();
    }

    @Override
    public LocalDateTime component7() {
        return getDatetimeOfDelete();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public UUID value2() {
        return getEntityId();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public String value4() {
        return getAddress();
    }

    @Override
    public String value5() {
        return getPhone();
    }

    @Override
    public LocalDateTime value6() {
        return getDatetimeOfCreation();
    }

    @Override
    public LocalDateTime value7() {
        return getDatetimeOfDelete();
    }

    @Override
    public SuppliersRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public SuppliersRecord value2(UUID value) {
        setEntityId(value);
        return this;
    }

    @Override
    public SuppliersRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public SuppliersRecord value4(String value) {
        setAddress(value);
        return this;
    }

    @Override
    public SuppliersRecord value5(String value) {
        setPhone(value);
        return this;
    }

    @Override
    public SuppliersRecord value6(LocalDateTime value) {
        setDatetimeOfCreation(value);
        return this;
    }

    @Override
    public SuppliersRecord value7(LocalDateTime value) {
        setDatetimeOfDelete(value);
        return this;
    }

    @Override
    public SuppliersRecord values(UUID value1, UUID value2, String value3, String value4, String value5, LocalDateTime value6, LocalDateTime value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SuppliersRecord
     */
    public SuppliersRecord() {
        super(Suppliers.SUPPLIERS);
    }

    /**
     * Create a detached, initialised SuppliersRecord
     */
    public SuppliersRecord(UUID id, UUID entityId, String name, String address, String phone, LocalDateTime datetimeOfCreation, LocalDateTime datetimeOfDelete) {
        super(Suppliers.SUPPLIERS);

        setId(id);
        setEntityId(entityId);
        setName(name);
        setAddress(address);
        setPhone(phone);
        setDatetimeOfCreation(datetimeOfCreation);
        setDatetimeOfDelete(datetimeOfDelete);
    }
}
