package ur.edu.pl.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ur.edu.pl.project.model.Agreement;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.User;

@RepositoryRestResource(collectionResourceRel = "agreement", path = "agreement")
public interface AgreementRepository extends JpaRepository<Agreement, Integer> {

    Agreement findByNumber(@Param("number") String number);
    Agreement findByEmployeeId(@Param("id") int id);
    List<Agreement> findAllByNumber(@Param("number") String number);
}
