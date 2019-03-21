package launchcode.org.ballersGuide.models.data;

import launchcode.org.ballersGuide.models.Position;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PositionDao extends CrudRepository<Position, Integer> {
}
