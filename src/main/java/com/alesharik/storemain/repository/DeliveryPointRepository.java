package com.alesharik.storemain.repository;

import com.alesharik.storemain.entity.DeliveryPoint;
import com.alesharik.storemain.repository.projections.DeliveryPointProjection;
import org.locationtech.jts.geom.LineString;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryPointRepository extends PagingAndSortingRepository<DeliveryPoint, Long> {
    @Query(value = """
select st_distance(
    st_transform(delivery_point.location, 3857),
    ST_Transform(:st, 3857)
) as distance, delivery_point.id as id, delivery_point.address as address, delivery_point.name as name, delivery_point.service_id as serviceid
from delivery_point
order by distance
limit 10
""", nativeQuery = true)
    List<DeliveryPointProjection> findAllByNearest(@Param("st") LineString string);
}
