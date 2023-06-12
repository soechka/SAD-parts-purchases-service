package com.example.sadbe.repository;

import com.example.sadbe.tables.pojos.Parts;
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

@Repository
@AllArgsConstructor
public class PartsRepository {

    private final DSLContext dslContext;

    public List<Parts> list(Condition condition) {
        return dslContext
                .selectFrom(PARTS)
                .where(condition)
                .fetchInto(Parts.class);
    }

    public List<Parts> list(Condition condition, Integer page, Integer pageSize, SortField<?> sortField) {
        return dslContext
                .selectFrom(PARTS)
                .where(condition)
                .orderBy(sortField)
                .limit(pageSize)
                .offset(pageSize * (page - 1))
                .fetchInto(Parts.class);
    }

    public Optional<Parts> getActualByEntityId(UUID entityId) {
        return Optional.ofNullable(
                dslContext
                        .selectFrom(PARTS)
                        .where(PARTS.ENTITY_ID.eq(entityId),
                                PARTS.DATETIME_OF_DELETE.isNull())
                        .fetchOneInto(Parts.class)
        );
    }

    public void updateDatetimeOfDeleteByEntityIds(List<UUID> entityIds, LocalDateTime localDateTime) {
        dslContext
                .update(PARTS)
                .set(PARTS.DATETIME_OF_DELETE, localDateTime)
                .where(PARTS.ENTITY_ID.in(entityIds))
                .execute();
    }

    public boolean exists(Condition condition) {
        return dslContext
                .fetchExists(dslContext.selectFrom(PARTS).where(condition));
    }

    public Long getCount(Condition condition) {
        return dslContext
                .selectCount()
                .from(PARTS)
                .where(condition)
                .fetchOneInto(Long.class);
    }
}
