package com.hms.Service;

import com.hms.Entity.Area;
import com.hms.Payload.AreaDto;
import org.springframework.stereotype.Service;

@Service
public class AreaService {

    Area mapToEntity(AreaDto dto){
        Area area = new Area();
        area.setName(dto.getName());
        return area;
    }

    AreaDto mapToDto(Area area){
        AreaDto areaDto = new AreaDto();
        areaDto.setName(area.getName());
        return areaDto;
    }
}
