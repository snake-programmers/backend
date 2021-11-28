package com.alesharik.storemain.repository;

import com.alesharik.storemain.entity.Product;
import com.alesharik.storemain.repository.projections.NearestProductProjection;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    @Query(
            value = """
        select ST_Distance(
           ST_Transform(s.location, 3857),
           ST_Transform(:point, 3857)
        ) as distance, product.price as price, s.name as store, s.address as address, product.id as id, s.id as storeid
            from product left join store s on s.id = product.store_id
            where producttype_id = :type
            order by distance
            limit 10
    """,
            nativeQuery = true
    )
    List<NearestProductProjection> findAllNearestByProductType(@Param("type") Long type, @Param("point") Point point);
}
