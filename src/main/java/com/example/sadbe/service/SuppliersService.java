package com.example.sadbe.service;

import com.example.sadbe.controller.dto.ResponseList;
import com.example.sadbe.dto.SupplierDto;
import com.example.sadbe.exception.NotFoundException;
import com.example.sadbe.repository.SuppliersRepository;
import com.example.sadbe.sort.SuppliersSortEnum;
import com.example.sadbe.tables.daos.SuppliersDao;
import com.example.sadbe.tables.pojos.Suppliers;
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

import static com.example.sadbe.Tables.SUPPLIERS;

@Service
@AllArgsConstructor
public class SuppliersService {

    private final SuppliersDao suppliersDao;
    private final SuppliersRepository suppliersRepository;

    public void createSupplier(SupplierDto supplierDto) {
        Suppliers supplier = getSupplierPojoFromDto(supplierDto);
        suppliersRepository.insert(supplier);
    }

    public Suppliers getOne(UUID entityId) throws NotFoundException {
        return suppliersRepository.getActualByEntityId(entityId).orElseThrow(NotFoundException::new);
    }

    public ResponseList<Suppliers> list(
            Integer page,
            Integer pageSize,
            String name,
            String address,
            String phone,
            SuppliersSortEnum suppliersSortEnum,
            SortOrder sortOrder,
            String search
    ) {
        SortField<?> sortField = switch (suppliersSortEnum) {
            case NAME -> SUPPLIERS.NAME.sort(sortOrder);
            case ADDRESS -> SUPPLIERS.ADDRESS.sort(sortOrder);
            case PHONE -> SUPPLIERS.PHONE.sort(sortOrder);
        };

        Condition condition = SUPPLIERS.DATETIME_OF_DELETE.isNull();
        if (Objects.nonNull(name)) {
            condition =
                    condition.and(SUPPLIERS.NAME.containsIgnoreCase(name));
        }
        if (Objects.nonNull(address)) {
            condition =
                    condition.and(SUPPLIERS.ADDRESS.containsIgnoreCase(address));
        }
        if (Objects.nonNull(phone)) {
            condition =
                    condition.and(SUPPLIERS.PHONE.containsIgnoreCase(phone));
        }
        if (Objects.nonNull(search)) {
            condition =
                    condition.and(SUPPLIERS.NAME.containsIgnoreCase(search)
                            .or(SUPPLIERS.ADDRESS.containsIgnoreCase(search))
                            .or(SUPPLIERS.PHONE.containsIgnoreCase(search)));
        }

        List<Suppliers> items = suppliersRepository.list(condition, page, pageSize, sortField);
        Long total = suppliersRepository.getCount(condition);

        ResponseList<Suppliers> result = new ResponseList<>();
        result.setItems(items);
        result.setTotal(total);

        return result;
    }

    protected Suppliers getSupplierPojoFromDto(SupplierDto supplierDto) {
        Suppliers supplierPojo = new Suppliers();
        supplierPojo.setId(null);
        supplierPojo.setEntityId(null);
        supplierPojo.setName(supplierDto.getName());
        supplierPojo.setAddress(supplierDto.getAddress());
        supplierPojo.setPhone(supplierDto.getPhone());
        supplierPojo.setDatetimeOfCreation(LocalDateTime.now());
        return supplierPojo;
    }

    @Transactional
    public Suppliers updateSupplier(Suppliers supplier) {
        supplier.setId(null);

        Suppliers actual = suppliersRepository.getActualByEntityId(supplier.getEntityId()).orElseThrow(NotFoundException::new);

        actual.setDatetimeOfDelete(LocalDateTime.now());
        suppliersDao.update(actual);

        supplier.setEntityId(actual.getEntityId());
        supplier.setDatetimeOfCreation(actual.getDatetimeOfCreation());
        if (Objects.isNull(supplier.getName())) {
            supplier.setName(actual.getName());
        }
        if (Objects.isNull(supplier.getAddress())) {
            supplier.setAddress(actual.getAddress());
        }
        if (Objects.isNull(supplier.getPhone())) {
            supplier.setPhone(actual.getPhone());
        }
        suppliersRepository.insert(supplier);
        return supplier;
    }

    public void delete(UUID id) {
        suppliersRepository.updateDatetimeOfDeleteByEntityIds(List.of(id), LocalDateTime.now());
    }
}
