package by.bsuir.publisher.dao.repository;

import by.bsuir.publisher.model.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, Long> {

}
