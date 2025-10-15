package progweb.locagest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progweb.locagest.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
