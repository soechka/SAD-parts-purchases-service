package com.example.sadbe.repository;

import com.example.sadbe.tables.pojos.Suppliers;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.sadbe.Tables.SUPPLIED_PARTS;
import static com.example.sadbe.Tables.SUPPLIERS;

@Repository
@AllArgsConstructor
public class SuppliersRepository {

    private final DSLContext dslContext;

    public List<Suppliers> list(Condition condition) {
        return dslContext
                .selectFrom(SUPPLIERS)
                .where(condition)
                .fetchInto(Suppliers.class);
    }

    public List<Suppliers> list(Condition condition, Integer page, Integer pageSize, SortField<?> sortField) {
        return dslContext
                .selectFrom(SUPPLIERS)
                .where(condition)
                .orderBy(sortField)
                .limit(pageSize)
                .offset(pageSize * (page - 1))
                .fetchInto(Suppliers.class);
    }

    public Optional<Suppliers> getActualByEntityId(UUID entityId) {
        return Optional.ofNullable(
                dslContext
                        .selectFrom(SUPPLIERS)
                        .where(SUPPLIERS.ENTITY_ID.eq(entityId),
                                SUPPLIERS.DATETIME_OF_DELETE.isNull())
                        .fetchOneInto(Suppliers.class)
        );
    }

    public void updateDatetimeOfDeleteByEntityIds(List<UUID> entityIds, LocalDateTime localDateTime) {
        dslContext
                .update(SUPPLIERS)
                .set(SUPPLIERS.DATETIME_OF_DELETE, localDateTime)
                .where(SUPPLIERS.ENTITY_ID.in(entityIds))
                .execute();
    }

    public boolean exists(Condition condition) {
        return dslContext
                .fetchExists(dslContext.selectFrom(SUPPLIERS).where(condition));
    }

    public Long getCount(Condition condition) {
        return dslContext
                .selectCount()
                .from(SUPPLIERS)
                .where(condition)
                .fetchOneInto(Long.class);
    }
}
