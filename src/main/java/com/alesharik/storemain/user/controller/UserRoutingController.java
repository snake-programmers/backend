package com.alesharik.storemain.user.controller;

import com.alesharik.storemain.dto.DeliveryRoutingDto;
import com.alesharik.storemain.dto.RouteParamsDto;
import com.alesharik.storemain.entity.DeliveryMean;
import com.alesharik.storemain.entity.Store;
import com.alesharik.storemain.repository.DeliveryPointRepository;
import com.alesharik.storemain.repository.DeliveryServiceRepository;
import com.alesharik.storemain.repository.StoreRepository;
import com.alesharik.storemain.repository.projections.DeliveryPointProjection;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateXY;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserRoutingController {
    private final GeometryFactory geometryFactory;
    private final StoreRepository storeRepository;
    private final DeliveryPointRepository deliveryPointRepository;
    private final DeliveryServiceRepository deliveryServiceRepository;

    @PostMapping("/user/route")
    public List<DeliveryRoutingDto> route(@RequestBody RouteParamsDto dto) {
        List<Long> lst = new ArrayList<>(dto.getStores().length);
        for (int i = 0; i < dto.getStores().length; i++) {
            lst.add(dto.getStores()[i]);
        }
        var stores = storeRepository.findAllById(lst);
        var storesList = new ArrayList<Store>();
        for (Store store : stores) {
            storesList.add(store);
        }
        var coords = new Coordinate[storesList.size() + 1];
        var i = 0;
        for (Store store : storesList) {
            coords[i] = store.getLocation().getCoordinate();
            i++;
        }
        coords[i] = new CoordinateXY(dto.getLat(), dto.getLon());
        var str = geometryFactory.createLineString(coords);
        str.setSRID(4326);
        var nearest = deliveryPointRepository.findAllByNearest(str);
        var services = deliveryServiceRepository.findAllById(nearest.stream().map(DeliveryPointProjection::getServiceid).collect(Collectors.toList())).iterator();
        List<DeliveryRoutingDto> ret = new ArrayList<>();
        for (DeliveryPointProjection deliveryPointProjection : nearest) {
            var serv = services.next();
            for (DeliveryMean mean : serv.getMeans()) {
                if (deliveryPointProjection.getDistance() < mean.getMinMeters() || deliveryPointProjection.getDistance() > mean.getMaxMeters()) continue;
                ret.add(new DeliveryRoutingDto(
                        deliveryPointProjection.getDistance(),
                        mean.getName(),
                        mean.getId(),
                        serv.getName(),
                        serv.getId(),
                        mean.getPricePerKm(),
                        deliveryPointProjection.getId()
                ));
            }
        }
        return ret;
    }
}
