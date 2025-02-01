package com.hms.Controller;

import com.hms.Payload.AreaDto;
import com.hms.Service.AreaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hms/areas")
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    // Create a new Area
    @PostMapping
    public ResponseEntity<AreaDto> createArea(@RequestBody AreaDto areaDto) {
        AreaDto createdArea = areaService.createArea(areaDto);
        return new ResponseEntity<>(createdArea, HttpStatus.CREATED);
    }

    // Get a single Area by ID
    @GetMapping("/{id}")
    public ResponseEntity<AreaDto> getAreaById(@PathVariable Long id) {
        AreaDto areaDto = areaService.getAreaById(id);
        return ResponseEntity.ok(areaDto);
    }

    // Get all Areas
    @GetMapping
    public ResponseEntity<List<AreaDto>> getAllAreas() {
        List<AreaDto> areas = areaService.getAllAreas();
        return ResponseEntity.ok(areas);
    }

    // Update an existing Area
    @PutMapping("/{id}")
    public ResponseEntity<AreaDto> updateArea(@PathVariable Long id, @RequestBody AreaDto areaDto) {
        AreaDto updatedArea = areaService.updateArea(id, areaDto);
        return ResponseEntity.ok(updatedArea);
    }

    // Delete an Area
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArea(@PathVariable Long id) {
        areaService.deleteArea(id);
        return ResponseEntity.ok("Area deleted successfully.");
    }
}
