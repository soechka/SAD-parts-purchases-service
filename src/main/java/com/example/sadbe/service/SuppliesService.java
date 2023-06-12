package com.example.sadbe.service;

import com.example.sadbe.controller.dto.ResponseList;
import com.example.sadbe.dto.SupplyDto;
import com.example.sadbe.exception.NotFoundException;
import com.example.sadbe.exception.ValidationException;
import com.example.sadbe.repository.SuppliedPartsRepository;
import com.example.sadbe.repository.SuppliesRepository;
import com.example.sadbe.sort.SuppliesSortEnum;
import com.example.sadbe.tables.daos.SuppliesDao;
import com.example.sadbe.tables.pojos.Supplies;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.sadbe.Tables.*;

@Service
@AllArgsConstructor
public class SuppliesService {

    private final SuppliesDao suppliesDao;
    private final SuppliesRepository suppliesRepository;
    private final SuppliedPartsRepository suppliedPartsRepository;

    public void createSupply(SupplyDto supplyDto) {
        validate(supplyDto);
        Supplies supply = getSupplyPojoFromDto(supplyDto);
        suppliesDao.insert(supply);
    }

    public Supplies getOne(UUID entityId) throws NotFoundException {
        return suppliesRepository.getActualByEntityId(entityId).orElseThrow(NotFoundException::new);
    }

    private void validate(SupplyDto supplyDto) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!suppliedPartsRepository.exists(SUPPLIED_PARTS.ENTITY_ID.eq(supplyDto.getSupplied_parts()))) {
            stringBuilder.append("Supplying part does not exist\n");
        }
        if (!stringBuilder.isEmpty()) {
            throw new ValidationException(stringBuilder.toString());
        }
    }

    protected Supplies getSupplyPojoFromDto(SupplyDto supplyDto) {
        Supplies supply = new Supplies();
        supply.setAmount(supplyDto.getAmount());
        supply.setDate(supplyDto.getDate());
        supply.setSuppliedParts(supplyDto.getSupplied_parts());
        return supply;
    }

    public ResponseList<Supplies> list(
            Integer page,
            Integer pageSize,
            Long amount,
            LocalDateTime date,
            UUID suppliedPart,
            SuppliesSortEnum suppliesSortEnum,
            SortOrder sortOrder,
            String search
    ) {
        SortField<?> sortField = switch (suppliesSortEnum) {
            case ENTITY_ID -> SUPPLIES.ENTITY_ID.sort(sortOrder);
            case AMOUNT -> SUPPLIES.AMOUNT.sort(sortOrder);
            case DATE -> SUPPLIES.DATE.sort(sortOrder);
            case SUPPLIED_PART -> SUPPLIES.SUPPLIED_PARTS.sort(sortOrder);
        };

        Condition condition = SUPPLIES.DATETIME_OF_DELETE.isNull();
        if (Objects.nonNull(amount)) {
            condition =
                    condition.and(SUPPLIES.AMOUNT.containsIgnoreCase(amount));
        }
        if (Objects.nonNull(date)) {
            condition =
                    condition.and(SUPPLIES.DATE.containsIgnoreCase(date));
        }
        if (Objects.nonNull(suppliedPart)) {
            condition =
                    condition.and(SUPPLIES.SUPPLIED_PARTS.containsIgnoreCase(suppliedPart));
        }
        if (Objects.nonNull(search)) {
            condition =
                    condition.and(SUPPLIES.AMOUNT.containsIgnoreCase(Long.valueOf(search)))
                            .or(SUPPLIES.DATE.containsIgnoreCase(LocalDateTime.parse(search)))
                            .or(SUPPLIES.SUPPLIED_PARTS.containsIgnoreCase(UUID.fromString(search)));
        }

        List<Supplies> items = suppliesRepository.list(condition, page, pageSize, sortField);
        Long total = suppliesRepository.getCount(condition);

        ResponseList<Supplies> result = new ResponseList<>();
        result.setItems(items);
        result.setTotal(total);

        return result;
    }

    @Transactional
    public Supplies updateSupply(Supplies supply) {
        supply.setId(null);

        Supplies actual = suppliesRepository.getActualByEntityId(supply.getEntityId()).orElseThrow(NotFoundException::new);

        actual.setDatetimeOfDelete(LocalDateTime.now());
        suppliesDao.update(actual);

        supply.setDatetimeOfCreation(actual.getDatetimeOfDelete());
        if (Objects.isNull(supply.getAmount())) {
            supply.setAmount(actual.getAmount());
        }
        if (Objects.isNull(supply.getDate())) {
            supply.setDate(actual.getDate());
        }
        if (Objects.isNull(supply.getSuppliedParts())) {
            supply.setSuppliedParts(actual.getSuppliedParts());
        }
        suppliesDao.insert(supply);
        return supply;
    }

    public void delete(UUID entityId) {
        suppliesRepository.updateDatetimeOfDeleteByEntityIds(List.of(entityId), LocalDateTime.now());
    }
}
