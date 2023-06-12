package com.example.sadbe.controller;

import com.example.sadbe.controller.dto.ResponseList;
import com.example.sadbe.dto.SuppliedPartDto;
import com.example.sadbe.exception.NotFoundException;
import com.example.sadbe.service.SuppliedPartsService;
import com.example.sadbe.sort.SuppliedPartsSortEnum;
import com.example.sadbe.tables.pojos.SuppliedParts;
import lombok.AllArgsConstructor;
import org.jooq.SortOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/parts/supplied")
public class SuppliedPartsRestController {

    private final SuppliedPartsService suppliedPartsService;

    @GetMapping(value = "/{entityId}")
    public ResponseEntity<SuppliedParts> get(@PathVariable UUID entityId) {
        try {
            return ResponseEntity.ok(suppliedPartsService.getOne(entityId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseList<SuppliedParts> list(
            @RequestParam(value = "sortBy", defaultValue = "ENTITY_ID")SuppliedPartsSortEnum suppliedPartsSortEnum,
            @RequestParam(value = "sortOrder",defaultValue = "ASC")SortOrder sortOrder,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
            @RequestParam(value = "date", required = false)LocalDateTime date,
            @RequestParam(value = "search", required = false)String search
    ) {
        return suppliedPartsService.list(page, pageSize, date, suppliedPartsSortEnum, sortOrder, search);
    }

    @PostMapping
    public void create(@RequestBody SuppliedPartDto suppliedPartDto) {
        suppliedPartsService.createSuppliedPart(suppliedPartDto);
    }

    @PutMapping
    public SuppliedParts update(@RequestBody SuppliedParts suppliedParts) {
        return suppliedPartsService.updateSuppliedParts(suppliedParts);
    }

    @DeleteMapping("/{entityId}")
    public void delete(@PathVariable UUID entityId) {
        suppliedPartsService.delete(entityId);
    }
}
