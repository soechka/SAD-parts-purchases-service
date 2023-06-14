package com.example.sadbe.repository;

import com.example.sadbe.tables.pojos.Parts;
import com.example.sadbe.tables.pojos.SuppliedParts;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.sadbe.Tables.*;
import static com.example.sadbe.Tables.PARTS;

@Repository
@AllArgsConstructor
public class SuppliedPartsRepository {

    private final DSLContext dslContext;

    public List<SuppliedParts> list(Condition condition) {
        return dslContext
                .selectFrom(SUPPLIED_PARTS)
                .where(condition)
                .fetchInto(SuppliedParts.class);
    }

    public List<SuppliedParts> list(Condition condition, Integer page, Integer pageSize, SortField<?> sortField) {
        return dslContext
                .selectFrom(SUPPLIED_PARTS)
                .where(condition)
                .orderBy(sortField)
                .limit(pageSize)
                .offset(pageSize * (page - 1))
                .fetchInto(SuppliedParts.class);
    }

    public Optional<SuppliedParts> getActualByEntityId(UUID entityId) {
        return Optional.ofNullable(
                dslContext
                        .selectFrom(SUPPLIED_PARTS)
                        .where(SUPPLIED_PARTS.ENTITY_ID.eq(entityId),
                                SUPPLIED_PARTS.DATETIME_OF_DELETE.isNull())
                        .fetchOneInto(SuppliedParts.class)
        );
    }

    public void updateDatetimeOfDeleteByEntityIds(List<UUID> entityIds, LocalDateTime localDateTime) {
        dslContext
                .update(SUPPLIED_PARTS)
                .set(SUPPLIED_PARTS.DATETIME_OF_DELETE, localDateTime)
                .where(SUPPLIED_PARTS.ENTITY_ID.in(entityIds))
                .execute();
    }

    public boolean exists(Condition condition) {
        return dslContext
                .fetchExists(dslContext.selectFrom(SUPPLIED_PARTS).where(condition));
    }

    public Long getCount(Condition condition) {
        return dslContext
                .selectCount()
                .from(SUPPLIED_PARTS)
                .where(condition)
                .fetchOneInto(Long.class);
    }

    public void insert(SuppliedParts supPart) {
        dslContext
                .insertInto(SUPPLIED_PARTS)
                .columns(
                        SUPPLIED_PARTS.PART,
                        SUPPLIED_PARTS.DATE,
                        SUPPLIED_PARTS.COST,
                        SUPPLIED_PARTS.SUPPLIER,
                        SUPPLIED_PARTS.DATETIME_OF_CREATION,
                        SUPPLIED_PARTS.DATETIME_OF_DELETE)
                .values(
                        supPart.getPart(),
                        supPart.getDate(),
                        supPart.getCost(),
                        supPart.getSupplier(),
                        supPart.getDatetimeOfCreation(),
                        supPart.getDatetimeOfDelete()
                ).execute();
    }
}
