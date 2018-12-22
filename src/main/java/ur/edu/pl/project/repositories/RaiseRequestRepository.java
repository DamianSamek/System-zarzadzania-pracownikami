package ur.edu.pl.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ur.edu.pl.project.model.RaiseRequest;
import ur.edu.pl.project.model.Role;

@RepositoryRestResource(collectionResourceRel = "raiserequest", path = "raiserequest")
public interface RaiseRequestRepository extends JpaRepository<RaiseRequest, Integer> {

    RaiseRequest findByAgreementId(@Param ("id") int id);

}