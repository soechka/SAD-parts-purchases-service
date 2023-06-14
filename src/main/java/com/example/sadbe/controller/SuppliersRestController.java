package com.example.sadbe.controller;

import com.example.sadbe.controller.dto.ResponseList;
import com.example.sadbe.dto.SupplierDto;
import com.example.sadbe.exception.NotFoundException;
import com.example.sadbe.service.SuppliersService;
import com.example.sadbe.sort.SuppliersSortEnum;
import com.example.sadbe.tables.pojos.Suppliers;
import lombok.AllArgsConstructor;
import org.jooq.SortOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/suppliers")
public class SuppliersRestController {

    private final SuppliersService suppliersService;

    @GetMapping(value = "/{entityId}")
    public ResponseEntity<Suppliers> get(@PathVariable UUID entityId) {
        try {
            return ResponseEntity.ok(suppliersService.getOne(entityId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseList<Suppliers> list(
            @RequestParam(value = "sortBy", defaultValue = "NAME")SuppliersSortEnum suppliersSortEnum,
            @RequestParam(value = "sortOrder", defaultValue = "ASC")SortOrder sortOrder,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
            @RequestParam(value = "name", required = false)String name,
            @RequestParam(value = "address", required = false)String address,
            @RequestParam(value = "phone", required = false)String phone,
            @RequestParam(value = "search", required = false)String search
    ) {
        return suppliersService.list(page, pageSize, name, address, phone, suppliersSortEnum, sortOrder, search);
    }

    @PostMapping
    public void create(@RequestBody SupplierDto supplierDto) {
        suppliersService.createSupplier(supplierDto);
    }

    @PutMapping
    public Suppliers update(@RequestBody Suppliers supplier) {
        return suppliersService.updateSupplier(supplier);
    }

    @DeleteMapping("/{entityId}")
    public void delete(@PathVariable UUID entityId) {
        suppliersService.delete(entityId);
    }
}
