package com.example.sadbe.repository;

import com.example.sadbe.Tables;
import com.example.sadbe.tables.pojos.Users;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.selectFrom;

@Repository
@AllArgsConstructor
public class UsersRepository {

    private final DSLContext dslContext;

    public Boolean fetchExists(String login) {
        return dslContext
                .fetchExists(
                        selectFrom(Tables.USERS)
                                .where(Tables.USERS.LOGIN.eq(login))
                );
    }

    public List<Users> list(Condition condition, Integer page, Integer pageSize){
        return dslContext
                .selectFrom(Tables.USERS)
                .where(condition)
                .limit(pageSize)
                .offset(pageSize * (page - 1))
                .fetchInto(Users.class);
    }

    public Users getActual(String login){
        return dslContext
                .selectFrom(Tables.USERS)
                .where(Tables.USERS.LOGIN.eq(login))
                .fetchOneInto(Users.class);
    }

    public Long getCount(Condition condition) {
        return dslContext
                .selectCount()
                .from(Tables.USERS)
                .where(condition)
                .fetchOneInto(Long.class);
    }

    public void changeRole(UUID id) {
        Boolean prevRole = dslContext.select(Tables.USERS.IS_ADMIN).from(Tables.USERS).where(Tables.USERS.ID.eq(id)).fetchOneInto(Boolean.class);
        dslContext
                .update(Tables.USERS)
                .set(Tables.USERS.IS_ADMIN, Boolean.FALSE.equals(prevRole))
                .where(Tables.USERS.ID.eq(id));
    }
}
