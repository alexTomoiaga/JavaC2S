package launchcode.org.ballersGuide.models.data;

import launchcode.org.ballersGuide.models.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TeamDao extends CrudRepository<Team, Integer> {
}
