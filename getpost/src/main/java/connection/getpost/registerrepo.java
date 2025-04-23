package connection.getpost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface registerrepo extends JpaRepository<Courseregister, Long>{

}
