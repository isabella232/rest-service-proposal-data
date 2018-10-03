package gov.nsf.psm.proposaldata.repository.userpermissions;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.nsf.psm.proposaldata.entity.LoginUserRolePermissionEntity;

public interface LoginUserRolePermissionRepository extends JpaRepository<LoginUserRolePermissionEntity, String> {

    @Transactional
    @Query("select p " + "from LoginUserRolePermissionEntity p, LoginUserRolePermissionXrefEntity x "
            + "where p.propPermCode = x.loginPermissionCode " + "and x.propStatusCode = :propStatusCode "
            + "and x.loginUserRoleCode in :userRoleCodes")
    List<LoginUserRolePermissionEntity> findByCode(@Param("propStatusCode") String propStatusCode,
            @Param("userRoleCodes") List<String> userRoleCodes);
}
