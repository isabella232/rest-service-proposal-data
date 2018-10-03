package gov.nsf.psm.proposaldata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.nsf.psm.proposaldata.entity.FacilityEquipmentEntity;

public interface FacilityEquipmentRepository extends JpaRepository<FacilityEquipmentEntity, Long> {
    List<FacilityEquipmentEntity> findByPropPrepRevnId(Long propPrepRevnId);
}
