/*
 * This file is generated by jOOQ.
 */
package com.example.sadbe.tables.daos;


import com.example.sadbe.tables.Parts;
import com.example.sadbe.tables.records.PartsRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class PartsDao extends DAOImpl<PartsRecord, com.example.sadbe.tables.pojos.Parts, UUID> {

    /**
     * Create a new PartsDao without any configuration
     */
    public PartsDao() {
        super(Parts.PARTS, com.example.sadbe.tables.pojos.Parts.class);
    }

    /**
     * Create a new PartsDao with an attached configuration
     */
    @Autowired
    public PartsDao(Configuration configuration) {
        super(Parts.PARTS, com.example.sadbe.tables.pojos.Parts.class, configuration);
    }

    @Override
    public UUID getId(com.example.sadbe.tables.pojos.Parts object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.sadbe.tables.pojos.Parts> fetchRangeOfId(UUID lowerInclusive, UUID upperInclusive) {
        return fetchRange(Parts.PARTS.ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.example.sadbe.tables.pojos.Parts> fetchById(UUID... values) {
        return fetch(Parts.PARTS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.example.sadbe.tables.pojos.Parts fetchOneById(UUID value) {
        return fetchOne(Parts.PARTS.ID, value);
    }

    /**
     * Fetch records that have <code>entity_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.sadbe.tables.pojos.Parts> fetchRangeOfEntityId(UUID lowerInclusive, UUID upperInclusive) {
        return fetchRange(Parts.PARTS.ENTITY_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>entity_id IN (values)</code>
     */
    public List<com.example.sadbe.tables.pojos.Parts> fetchByEntityId(UUID... values) {
        return fetch(Parts.PARTS.ENTITY_ID, values);
    }

    /**
     * Fetch records that have <code>name BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.sadbe.tables.pojos.Parts> fetchRangeOfName(String lowerInclusive, String upperInclusive) {
        return fetchRange(Parts.PARTS.NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.example.sadbe.tables.pojos.Parts> fetchByName(String... values) {
        return fetch(Parts.PARTS.NAME, values);
    }

    /**
     * Fetch records that have <code>part_number BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.sadbe.tables.pojos.Parts> fetchRangeOfPartNumber(String lowerInclusive, String upperInclusive) {
        return fetchRange(Parts.PARTS.PART_NUMBER, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>part_number IN (values)</code>
     */
    public List<com.example.sadbe.tables.pojos.Parts> fetchByPartNumber(String... values) {
        return fetch(Parts.PARTS.PART_NUMBER, values);
    }

    /**
     * Fetch records that have <code>note BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.sadbe.tables.pojos.Parts> fetchRangeOfNote(String lowerInclusive, String upperInclusive) {
        return fetchRange(Parts.PARTS.NOTE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>note IN (values)</code>
     */
    public List<com.example.sadbe.tables.pojos.Parts> fetchByNote(String... values) {
        return fetch(Parts.PARTS.NOTE, values);
    }

    /**
     * Fetch records that have <code>datetime_of_creation BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.sadbe.tables.pojos.Parts> fetchRangeOfDatetimeOfCreation(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(Parts.PARTS.DATETIME_OF_CREATION, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>datetime_of_creation IN (values)</code>
     */
    public List<com.example.sadbe.tables.pojos.Parts> fetchByDatetimeOfCreation(LocalDateTime... values) {
        return fetch(Parts.PARTS.DATETIME_OF_CREATION, values);
    }

    /**
     * Fetch records that have <code>datetime_of_delete BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.example.sadbe.tables.pojos.Parts> fetchRangeOfDatetimeOfDelete(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(Parts.PARTS.DATETIME_OF_DELETE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>datetime_of_delete IN (values)</code>
     */
    public List<com.example.sadbe.tables.pojos.Parts> fetchByDatetimeOfDelete(LocalDateTime... values) {
        return fetch(Parts.PARTS.DATETIME_OF_DELETE, values);
    }
}