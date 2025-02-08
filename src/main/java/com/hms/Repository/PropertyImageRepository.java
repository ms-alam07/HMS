package com.hms.Repository;

import com.hms.Entity.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    @Query("SELECT pi FROM PropertyImage pi WHERE pi.property.id = :propertyId")
    List<PropertyImage> findAllByPropertyId(@Param("propertyId") Long propertyId);
}
