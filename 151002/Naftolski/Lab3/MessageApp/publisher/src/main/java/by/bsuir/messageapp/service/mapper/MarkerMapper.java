package by.bsuir.messageapp.service.mapper;

import by.bsuir.messageapp.model.entity.Marker;
import by.bsuir.messageapp.model.request.MarkerRequestTo;
import by.bsuir.messageapp.model.response.MarkerResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MarkerMapper {
    MarkerResponseTo getResponse(Marker marker);
    List<MarkerResponseTo> getListResponse(Iterable<Marker> markers);
    @Mapping(target = "stories", ignore = true)
    Marker getMarker(MarkerRequestTo markerRequestTo);
}
