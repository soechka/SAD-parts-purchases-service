package com.example.sadbe.service;

import com.example.sadbe.controller.dto.ResponseList;
import com.example.sadbe.dto.SuppliedPartDto;
import com.example.sadbe.exception.NotFoundException;
import com.example.sadbe.exception.ValidationException;
import com.example.sadbe.repository.PartsRepository;
import com.example.sadbe.repository.SuppliedPartsRepository;
import com.example.sadbe.repository.SuppliersRepository;
import com.example.sadbe.sort.SuppliedPartsSortEnum;
import com.example.sadbe.tables.daos.SuppliedPartsDao;
import com.example.sadbe.tables.pojos.SuppliedParts;
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
public class SuppliedPartsService {

    private final SuppliedPartsDao suppliedPartsDao;
    private final SuppliersRepository suppliersRepository;
    private final PartsRepository partsRepository;
    private final SuppliedPartsRepository suppliedPartsRepository;

    public void createSuppliedPart(SuppliedPartDto suppliedPartDto) {
        validate(suppliedPartDto);
        SuppliedParts supPart = getSupPartPojoFromDto(suppliedPartDto);
        suppliedPartsRepository.insert(supPart);
    }

    public SuppliedParts getOne(UUID entityId) throws NotFoundException {
        return suppliedPartsRepository.getActualByEntityId(entityId).orElseThrow(NotFoundException::new);
    }

    public ResponseList<SuppliedParts> list(
            Integer page,
            Integer pageSize,
            LocalDateTime date,
            SuppliedPartsSortEnum suppliedPartsSortEnum,
            SortOrder sortOrder,
            String search
    ) {
        SortField<?> sortField = switch (suppliedPartsSortEnum) {
            case DATE -> SUPPLIED_PARTS.DATE.sort(sortOrder);
            case COST -> SUPPLIED_PARTS.COST.sort(sortOrder);
        };

        Condition condition = SUPPLIED_PARTS.DATETIME_OF_DELETE.isNull();
        if (Objects.nonNull(date)) {
            condition =
                    condition.and(SUPPLIED_PARTS.DATE.containsIgnoreCase(date));
        }
        if (Objects.nonNull(search)) {
            condition =
                    condition.and(SUPPLIED_PARTS.ENTITY_ID.containsIgnoreCase(UUID.fromString(search))
                            .or(SUPPLIED_PARTS.PART.containsIgnoreCase(UUID.fromString(search)))
                            .or(SUPPLIED_PARTS.DATE.containsIgnoreCase(LocalDateTime.parse(search)))
                            .or(SUPPLIED_PARTS.COST.containsIgnoreCase(Long.valueOf(search)))
                            .or(SUPPLIED_PARTS.SUPPLIER.containsIgnoreCase(UUID.fromString(search))));
        }

        List<SuppliedParts> items = suppliedPartsRepository.list(condition, page, pageSize, sortField);
        Long total = suppliedPartsRepository.getCount(condition);

        ResponseList<SuppliedParts> result = new ResponseList<>();
        result.setItems(items);
        result.setTotal(total);

        return result;
    }

    protected SuppliedParts getSupPartPojoFromDto(SuppliedPartDto suppliedPartDto) {
        SuppliedParts supPartPojo = new SuppliedParts();
        supPartPojo.setPart(suppliedPartDto.getPart());
        supPartPojo.setCost(suppliedPartDto.getCost());
        supPartPojo.setDate(suppliedPartDto.getDate());
        supPartPojo.setSupplier(suppliedPartDto.getSupplier());
        return supPartPojo;
    }

    private void validate(SuppliedPartDto suppliedPartDto) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!partsRepository.exists(PARTS.ENTITY_ID.eq(suppliedPartDto.getPart()))) {
            stringBuilder.append("Part of supply does not exist\n");
        }
        if (!suppliersRepository.exists(SUPPLIERS.ENTITY_ID.eq(suppliedPartDto.getSupplier()))) {
            stringBuilder.append("Supplier of supply does not exist\n");
        }
        if (!stringBuilder.isEmpty()) {
            throw new ValidationException(stringBuilder.toString());
        }
    }

    @Transactional
    public SuppliedParts updateSuppliedParts(SuppliedParts suppliedPart) {
        suppliedPart.setId(null);

        SuppliedParts actual = suppliedPartsRepository.getActualByEntityId(suppliedPart.getEntityId()).orElseThrow(NotFoundException::new);

        actual.setDatetimeOfDelete(LocalDateTime.now());
        suppliedPartsDao.update(actual);

        suppliedPart.setDatetimeOfCreation(actual.getDatetimeOfCreation());
        if (Objects.isNull(suppliedPart.getPart())) {
            suppliedPart.setPart(actual.getPart());
        }
        if (Objects.isNull(suppliedPart.getCost())) {
            suppliedPart.setCost(actual.getCost());
        }
        if (Objects.isNull(suppliedPart.getDate())) {
            suppliedPart.setDate(actual.getDate());
        }
        if (Objects.isNull(suppliedPart.getSupplier())) {
            suppliedPart.setSupplier(actual.getSupplier());
        }
        suppliedPartsRepository.insert(suppliedPart);
        return suppliedPart;
    }

    public void delete(UUID id) {
        suppliedPartsRepository.updateDatetimeOfDeleteByEntityIds(List.of(id), LocalDateTime.now());
    }
}
