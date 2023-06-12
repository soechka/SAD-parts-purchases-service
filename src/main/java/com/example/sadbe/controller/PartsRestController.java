package com.example.sadbe.controller;

import com.example.sadbe.controller.dto.ResponseList;
import com.example.sadbe.dto.PartDto;
import com.example.sadbe.exception.NotFoundException;
import com.example.sadbe.service.PartsService;
import com.example.sadbe.sort.PartsSortEnum;
import com.example.sadbe.tables.pojos.Parts;
import lombok.AllArgsConstructor;
import org.jooq.SortOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/parts")
public class PartsRestController {

    private final PartsService partsService;

    @GetMapping(value = "/{entityId}")
    public ResponseEntity<Parts> get(@PathVariable UUID entityId) throws NotFoundException {
        try {
            return ResponseEntity.ok(partsService.getOne(entityId));
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseList<Parts> list(
            @RequestParam(value = "sortBy", defaultValue = "ENTITY_ID")PartsSortEnum partsSortEnum,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") SortOrder sortOrder,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
            @RequestParam(value = "name", required = false)String name,
            @RequestParam(value = "partNumber", required = false)String partNumber,
            @RequestParam(value = "note", required = false)String note,
            @RequestParam(value = "search", required = false)String search
            ) {
        return partsService.list(page, pageSize, name, partNumber, note, partsSortEnum, sortOrder, search);
    }

    @PostMapping
    public void create(@RequestBody PartDto partDto) {
        partsService.createPart(partDto);
    }

    @PutMapping
    public Parts update(@RequestBody Parts part) {
        return partsService.updatePart(part);
    }

    @DeleteMapping("/{entityId}")
    public void delete(@PathVariable UUID entityId) {
        partsService.delete(entityId);
    }
}
