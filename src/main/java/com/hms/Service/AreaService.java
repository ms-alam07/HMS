package com.hms.Service;

import com.hms.Entity.Area;
import com.hms.Payload.AreaDto;
import com.hms.Repository.AreaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    // Convert AreaDto to Area Entity
    private Area mapToEntity(AreaDto areaDto) {
        Area area = new Area();
        area.setName(areaDto.getName());
        return area;
    }

    // Convert Area Entity to AreaDto
    private AreaDto mapToDto(Area area) {
        AreaDto areaDto = new AreaDto();
        areaDto.setName(area.getName());
        return areaDto;
    }

    // Create a new Area
    public AreaDto createArea(AreaDto areaDto) {
        Area area = mapToEntity(areaDto);
        Area savedArea = areaRepository.save(area);
        return mapToDto(savedArea);
    }

    // Get Area by ID
    public AreaDto getAreaById(Long id) {
        Optional<Area> area = areaRepository.findById(id);
        if (area.isPresent()) {
            return mapToDto(area.get());
        } else {
            throw new IllegalArgumentException("Area not found");
        }
    }

    // Get all Areas
    public List<AreaDto> getAllAreas() {
        List<Area> areas = areaRepository.findAll();
        return areas.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // Update an Area
    public AreaDto updateArea(Long id, AreaDto areaDto) {
        Optional<Area> areaOpt = areaRepository.findById(id);
        if (areaOpt.isPresent()) {
            Area area = areaOpt.get();
            area.setName(areaDto.getName());
            Area updatedArea = areaRepository.save(area);
            return mapToDto(updatedArea);
        } else {
            throw new IllegalArgumentException("Area not found");
        }
    }

    // Delete an Area
    public void deleteArea(Long id) {
        Optional<Area> area = areaRepository.findById(id);
        if (area.isPresent()) {
            areaRepository.delete(area.get());
        } else {
            throw new IllegalArgumentException("Area not found");
        }
    }
}
