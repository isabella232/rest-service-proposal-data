package gov.nsf.psm.proposaldata.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.util.StringUtils;

import gov.nsf.psm.foundation.model.PSMRole;
import gov.nsf.psm.foundation.model.Personnel;
import gov.nsf.psm.foundation.model.lookup.ProposalStatusTypeLookUp;
import gov.nsf.psm.foundation.model.proposal.Proposal;
import gov.nsf.psm.foundation.model.proposal.ProposalRevision;
import gov.nsf.psm.foundation.model.proposal.ProposalStatus;
import gov.nsf.psm.proposaldata.entity.ProposalPersonsEntity;
import gov.nsf.psm.proposaldata.entity.ProposalPrepRevision;

public class ProposalQueryUtility {
    
    private  ProposalQueryUtility() {
        // Private constructor
    }
    
    public static Optional<ProposalStatusTypeLookUp> getStatusLookup(List<ProposalStatusTypeLookUp> lookups, String code) {
        return lookups.stream().filter(l -> l.getCode().trim().equals(code.trim())).findAny();
    }
    
    public static Proposal addPersonnel(Proposal proposal, ProposalPrepRevision rev) {
        List<Personnel> persons = new ArrayList<>();
        for (ProposalPersonsEntity entity : rev.getPersons()) {
            persons.add(ConverterUtility.convertSeniorPersonnelEntityToDto(entity));
            if (entity.getPropPersonRoleCode().equals(PSMRole.ROLE_PI)) {
                String fullName = ProposalDataUtility.buildFullName(entity.getOthrSeniorPersonFirstName(),
                        entity.getOthrSeniorPersonMiddleInitial(), entity.getOthrSeniorPersonLasttName());
                proposal.setPiName(fullName);
                if(!StringUtils.isEmpty(entity.getPropPersonNsfId())) {
                    proposal.setPiNsfId(entity.getPropPersonNsfId());
                }
                proposal.setPiLastName(entity.getOthrSeniorPersonLasttName());
            }
        }
        Collections.sort(persons, (Personnel p1, Personnel p2) -> p1.getPsmRole().getCode().compareTo(p2.getPsmRole().getCode()));
        proposal.setPersonnel(persons);
        return proposal;
    }
    
    public static ProposalRevision addRevisionPersonnel(ProposalRevision propRev, ProposalPrepRevision rev) {
        List<Personnel> persons = new ArrayList<>();
        for (ProposalPersonsEntity entity : rev.getPersons()) {
            persons.add(ConverterUtility.convertSeniorPersonnelEntityToDto(entity));
            if (entity.getPropPersonRoleCode().equals(PSMRole.ROLE_PI)) {
                String fullName = ProposalDataUtility.buildFullName(entity.getOthrSeniorPersonFirstName(),
                        entity.getOthrSeniorPersonMiddleInitial(), entity.getOthrSeniorPersonLasttName());
                propRev.setPiName(fullName);
                if(!StringUtils.isEmpty(entity.getPropPersonNsfId())) {
                    propRev.setPiNsfId(entity.getPropPersonNsfId());
                }
                propRev.setPiLastName(entity.getOthrSeniorPersonLasttName());
            }
        }
        Collections.sort(persons, (Personnel p1, Personnel p2) -> p1.getPsmRole().getCode().compareTo(p2.getPsmRole().getCode()));
        propRev.setPersonnel(persons);
        return propRev;
    }

}
