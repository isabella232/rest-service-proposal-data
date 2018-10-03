package gov.nsf.psm.proposaldata.repository.userpermissions;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.nsf.psm.proposaldata.entity.LoginUserRoleTypeEntity;

public interface LoginUserRoleTypeRepository extends JpaRepository<LoginUserRoleTypeEntity, String> {

    @Override
    List<LoginUserRoleTypeEntity> findAll();

    @Transactional
    @Query("select l from LoginUserRoleTypeEntity l where l.propLognUserRoleTypeCode IN :codes")
    List<LoginUserRoleTypeEntity> findByCode(@Param("codes") List<String> codes);

}
