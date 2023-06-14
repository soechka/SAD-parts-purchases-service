package com.example.sadbe.service;

import com.example.sadbe.controller.dto.ResponseList;
import com.example.sadbe.dto.PartDto;
import com.example.sadbe.exception.NotFoundException;
import com.example.sadbe.repository.PartsRepository;
import com.example.sadbe.sort.PartsSortEnum;
import com.example.sadbe.tables.daos.PartsDao;
import com.example.sadbe.tables.pojos.Parts;
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

import static com.example.sadbe.Tables.PARTS;

@Service
@AllArgsConstructor
public class PartsService {

    private final PartsDao partsDao;
    private final PartsRepository partsRepository;

    public void createPart(PartDto partDto) {
        Parts part = getPartPojoFromDto(partDto);
        partsRepository.insert(part);
    }

    public Parts getOne(UUID entityId) throws NotFoundException {
        return partsRepository.getActualByEntityId(entityId).orElseThrow(NotFoundException::new);
    }

    public ResponseList<Parts> list(
            Integer page,
            Integer pageSize,
            String name,
            String partNumber,
            String note,
            PartsSortEnum partsSortEnum,
            SortOrder sortOrder,
            String search
    ) {
        SortField<?> sortField = switch (partsSortEnum) {
            case NAME -> PARTS.NAME.sort(sortOrder);
            case PART_NUMBER -> PARTS.PART_NUMBER.sort(sortOrder);
            case NOTE -> PARTS.NOTE.sort(sortOrder);
        };

        Condition condition = PARTS.DATETIME_OF_DELETE.isNull();
        if (Objects.nonNull(name)) {
            condition =
                    condition.and(PARTS.NAME.containsIgnoreCase(name));
        }
        if (Objects.nonNull(partNumber)) {
            condition =
                    condition.and(PARTS.PART_NUMBER.containsIgnoreCase(partNumber));
        }
        if (Objects.nonNull(note)) {
            condition =
                    condition.and(PARTS.NOTE.containsIgnoreCase(note));
        }
        if (Objects.nonNull(search)) {
            condition =
                    condition.and(PARTS.NAME.containsIgnoreCase(search)
                            .or(PARTS.PART_NUMBER.containsIgnoreCase(search))
                            .or(PARTS.NOTE.containsIgnoreCase(search)));
        }

        List<Parts> items = partsRepository.list(condition, page, pageSize, sortField);
        Long total = partsRepository.getCount(condition);

        ResponseList<Parts> result = new ResponseList<>();
        result.setItems(items);
        result.setTotal(total);

        return result;
    }

    protected Parts getPartPojoFromDto(PartDto partDto) {
        Parts partPojo = new Parts();
        partPojo.setName(partDto.getName());
        partPojo.setPartNumber(partDto.getPartNumber());
        partPojo.setNote(partDto.getNote());
        partPojo.setDatetimeOfCreation(LocalDateTime.now());
        return partPojo;
    }

    @Transactional
    public Parts updatePart(Parts part) {
        part.setId(null);

        Parts actual = partsRepository.getActualByEntityId(part.getEntityId()).orElseThrow(NotFoundException::new);

        actual.setDatetimeOfDelete(LocalDateTime.now());
        partsDao.update(actual);

        part.setDatetimeOfCreation(actual.getDatetimeOfCreation());
        if (Objects.isNull(part.getName())) {
            part.setName(actual.getName());
        }
        if (Objects.isNull(part.getPartNumber())) {
            part.setPartNumber(actual.getPartNumber());
        }
        if (Objects.isNull(part.getNote())) {
            part.setNote(actual.getNote());
        }
        partsRepository.insert(part);
        return part;
    }

    public void delete(UUID entityId) {
        partsRepository.updateDatetimeOfDeleteByEntityIds(List.of(entityId), LocalDateTime.now());
    }
}
