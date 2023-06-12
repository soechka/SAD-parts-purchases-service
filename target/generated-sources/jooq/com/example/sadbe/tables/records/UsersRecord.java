/*
 * This file is generated by jOOQ.
 */
package com.example.sadbe.tables.records;


import com.example.sadbe.tables.Users;

import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UsersRecord extends UpdatableRecordImpl<UsersRecord> implements Record5<UUID, UUID, String, String, Boolean> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>sad.users.id</code>.
     */
    public UsersRecord setId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>sad.users.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>sad.users.entity_id</code>.
     */
    public UsersRecord setEntityId(UUID value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>sad.users.entity_id</code>.
     */
    public UUID getEntityId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>sad.users.login</code>.
     */
    public UsersRecord setLogin(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>sad.users.login</code>.
     */
    public String getLogin() {
        return (String) get(2);
    }

    /**
     * Setter for <code>sad.users.password</code>.
     */
    public UsersRecord setPassword(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>sad.users.password</code>.
     */
    public String getPassword() {
        return (String) get(3);
    }

    /**
     * Setter for <code>sad.users.is_admin</code>.
     */
    public UsersRecord setIsAdmin(Boolean value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>sad.users.is_admin</code>.
     */
    public Boolean getIsAdmin() {
        return (Boolean) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<UUID, UUID, String, String, Boolean> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<UUID, UUID, String, String, Boolean> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Users.USERS.ID;
    }

    @Override
    public Field<UUID> field2() {
        return Users.USERS.ENTITY_ID;
    }

    @Override
    public Field<String> field3() {
        return Users.USERS.LOGIN;
    }

    @Override
    public Field<String> field4() {
        return Users.USERS.PASSWORD;
    }

    @Override
    public Field<Boolean> field5() {
        return Users.USERS.IS_ADMIN;
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
        return getLogin();
    }

    @Override
    public String component4() {
        return getPassword();
    }

    @Override
    public Boolean component5() {
        return getIsAdmin();
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
        return getLogin();
    }

    @Override
    public String value4() {
        return getPassword();
    }

    @Override
    public Boolean value5() {
        return getIsAdmin();
    }

    @Override
    public UsersRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public UsersRecord value2(UUID value) {
        setEntityId(value);
        return this;
    }

    @Override
    public UsersRecord value3(String value) {
        setLogin(value);
        return this;
    }

    @Override
    public UsersRecord value4(String value) {
        setPassword(value);
        return this;
    }

    @Override
    public UsersRecord value5(Boolean value) {
        setIsAdmin(value);
        return this;
    }

    @Override
    public UsersRecord values(UUID value1, UUID value2, String value3, String value4, Boolean value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UsersRecord
     */
    public UsersRecord() {
        super(Users.USERS);
    }

    /**
     * Create a detached, initialised UsersRecord
     */
    public UsersRecord(UUID id, UUID entityId, String login, String password, Boolean isAdmin) {
        super(Users.USERS);

        setId(id);
        setEntityId(entityId);
        setLogin(login);
        setPassword(password);
        setIsAdmin(isAdmin);
    }
}
