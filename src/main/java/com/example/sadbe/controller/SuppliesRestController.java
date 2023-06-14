package com.example.sadbe.controller;

import com.example.sadbe.controller.dto.ResponseList;
import com.example.sadbe.dto.SupplyDto;
import com.example.sadbe.exception.NotFoundException;
import com.example.sadbe.service.SuppliesService;
import com.example.sadbe.sort.SuppliesSortEnum;
import com.example.sadbe.tables.pojos.Supplies;
import lombok.AllArgsConstructor;
import org.jooq.SortOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/supplies")
public class SuppliesRestController {

    private final SuppliesService suppliesService;

    @GetMapping(value = "/{entityId}")
    public ResponseEntity<Supplies> get(@PathVariable UUID entityId) {
        try {
            return ResponseEntity.ok(suppliesService.getOne(entityId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseList<Supplies> list(
            @RequestParam(value = "sortBy", defaultValue = "DATE")SuppliesSortEnum suppliesSortEnum,
            @RequestParam(value = "sortOrder", defaultValue = "ASC")SortOrder sortOrder,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
            @RequestParam(value = "amount", required = false)Long amount,
            @RequestParam(value = "date", required = false)LocalDateTime date,
            @RequestParam(value = "suppliedPart", required = false)UUID suppliedPart,
            @RequestParam(value = "search", required = false)String search
    ) {
        return suppliesService.list(page, pageSize, amount, date, suppliedPart, suppliesSortEnum, sortOrder, search);
    }

    @PostMapping
    public void create(@RequestBody SupplyDto supplyDto) {
        suppliesService.createSupply(supplyDto);
    }

    @PutMapping
    public Supplies update(@RequestBody Supplies supply) {
        return suppliesService.updateSupply(supply);
    }

    @DeleteMapping("/{entityId}")
    public void delete(@PathVariable UUID entityId) {
        suppliesService.delete(entityId);
    }
}
