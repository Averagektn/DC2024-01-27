package org.education.service;

import org.education.bean.Marker;
import org.education.exception.NoSuchMarker;
import org.education.repository.MarkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkerService {

    private final MarkerRepository markerRepository;

    @Autowired
    public MarkerService(MarkerRepository markerRepository) {
        this.markerRepository = markerRepository;
    }

    public List<Marker> getAll(){
        return markerRepository.findAll();
    }

    public Marker getById(int id){
        return markerRepository.getReferenceById(id);
    }

    public Marker create(Marker marker){
        markerRepository.save(marker);
        return marker;
    }

    public Marker update(Marker marker){
        if(!markerRepository.existsById(marker.getId())) throw new NoSuchMarker("There is no such marker with this id");
        markerRepository.save(marker);
        return marker;
    }

    public void delete(int id){
        if(!markerRepository.existsById(id)) throw new NoSuchMarker("There is no such marker with this id");
        markerRepository.deleteById(id);
    }
}
