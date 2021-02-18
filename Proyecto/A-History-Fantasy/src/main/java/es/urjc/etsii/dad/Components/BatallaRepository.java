package es.urjc.etsii.dad.Components;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BatallaRepository extends JpaRepository<Batalla, Long> {

}
