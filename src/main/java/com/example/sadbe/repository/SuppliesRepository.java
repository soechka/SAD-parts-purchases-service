package com.example.sadbe.repository;

import com.example.sadbe.tables.pojos.Suppliers;
import com.example.sadbe.tables.pojos.Supplies;
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
import static com.example.sadbe.Tables.SUPPLIERS;

@Repository
@AllArgsConstructor
public class SuppliesRepository {

    private final DSLContext dslContext;

    public List<Supplies> list(Condition condition) {
        return dslContext
                .selectFrom(SUPPLIES)
                .where(condition)
                .fetchInto(Supplies.class);
    }

    public List<Supplies> list(Condition condition, Integer page, Integer pageSize, SortField<?> sortField) {
        return dslContext
                .selectFrom(SUPPLIES)
                .where(condition)
                .orderBy(sortField)
                .limit(pageSize)
                .offset(pageSize * (page - 1))
                .fetchInto(Supplies.class);
    }

    public Optional<Supplies> getActualByEntityId(UUID entityId) {
        return Optional.ofNullable(
                dslContext
                        .selectFrom(SUPPLIES)
                        .where(SUPPLIES.ENTITY_ID.eq(entityId),
                                SUPPLIES.DATETIME_OF_DELETE.isNull())
                        .fetchOneInto(Supplies.class)
        );
    }

    public void updateDatetimeOfDeleteByEntityIds(List<UUID> entityIds, LocalDateTime localDateTime) {
        dslContext
                .update(SUPPLIES)
                .set(SUPPLIES.DATETIME_OF_DELETE, localDateTime)
                .where(SUPPLIES.ENTITY_ID.in(entityIds))
                .execute();
    }

    public boolean exists(Condition condition) {
        return dslContext
                .fetchExists(dslContext.selectFrom(SUPPLIES).where(condition));
    }

    public Long getCount(Condition condition) {
        return dslContext
                .selectCount()
                .from(SUPPLIES)
                .where(condition)
                .fetchOneInto(Long.class);
    }

    public void insert(Supplies supply) {
        dslContext
                .insertInto(SUPPLIERS)
                .columns(
                        SUPPLIES.AMOUNT,
                        SUPPLIES.DATE,
                        SUPPLIES.SUPPLIED_PARTS,
                        SUPPLIES.DATETIME_OF_CREATION,
                        SUPPLIES.DATETIME_OF_DELETE)
                .values(
                        supply.getAmount(),
                        supply.getDate(),
                        supply.getSuppliedParts(),
                        supply.getDatetimeOfCreation(),
                        supply.getDatetimeOfDelete()
                ).execute();
    }
}
