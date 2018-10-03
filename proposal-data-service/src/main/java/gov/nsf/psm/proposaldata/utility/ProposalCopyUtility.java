package gov.nsf.psm.proposaldata.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import gov.nsf.psm.foundation.model.Directorate;
import gov.nsf.psm.foundation.model.Division;
import gov.nsf.psm.foundation.model.FundingOpportunity;
import gov.nsf.psm.foundation.model.Institution;
import gov.nsf.psm.foundation.model.ProgramElement;
import gov.nsf.psm.foundation.model.budget.BudgetRecord;
import gov.nsf.psm.foundation.model.budget.EquipmentCost;
import gov.nsf.psm.foundation.model.budget.FringeBenefitCost;
import gov.nsf.psm.foundation.model.budget.IndirectCost;
import gov.nsf.psm.foundation.model.budget.InstitutionBudget;
import gov.nsf.psm.foundation.model.budget.OtherDirectCost;
import gov.nsf.psm.foundation.model.budget.OtherPersonnelCost;
import gov.nsf.psm.foundation.model.budget.ParticipantSupportCost;
import gov.nsf.psm.foundation.model.budget.SeniorPersonnelCost;
import gov.nsf.psm.foundation.model.budget.TravelCost;
import gov.nsf.psm.foundation.model.coversheet.PrimaryPlaceOfPerformance;
import gov.nsf.psm.foundation.model.lookup.Deadline;
import gov.nsf.psm.foundation.model.proposal.ProposalPackage;
import gov.nsf.psm.foundation.model.proposal.ProposalRevisionPersonnel;
import gov.nsf.psm.proposaldata.entity.ProposalPrep;
import gov.nsf.psm.proposaldata.entity.ProposalPrepRevision;
import gov.nsf.psm.proposaldata.entity.UnitOfConsideration;

public class ProposalCopyUtility {

    private ProposalCopyUtility() {
        // Private constructor
    }

    public static ProposalPackage extractPropPackage(ProposalPrep prop, ProposalPrepRevision rev) {
        ProposalPackage pkg = new ProposalPackage();
        if (rev.getPropDueDate() != null) {
            Deadline deadline = new Deadline();
            deadline.setDeadlineDate(rev.getPropDueDate());
            deadline.setDeadlineTypeCode(rev.getPropDueDateTypeCode().trim());
            pkg.setDeadline(deadline);
        }
        pkg.setCollabType(prop.getPropColbTypeCode());
        FundingOpportunity foDto = new FundingOpportunity();
        foDto.setFundingOpportunityId(prop.getFundOppId());
        pkg.setFundingOp(foDto);
        pkg.setProposalStatus(rev.getPropPrepSttsCode());
        pkg.setProposalTitle(rev.getPropTitl());
        pkg.setProposalType(prop.getPropTypeCode());
        pkg.setPropPrepId(String.valueOf(prop.getPropPrepId()));
        pkg.setOrigPropRevId(String.valueOf(rev.getPropPrepRevnId()));
        pkg.setSubmissionType(prop.getPropSubmTypeCode());
        if (prop.getUocs() != null && !prop.getUocs().isEmpty()) {
            List<gov.nsf.psm.foundation.model.UnitOfConsideration> uocDtos = new ArrayList<>();
            for (UnitOfConsideration uocEntity : prop.getUocs()) {
                Directorate dirDto = new Directorate();
                dirDto.setDirectorateCode(
                        ConverterUtility.extractDirectorateCodeFromDivisionCode(uocEntity.getOrgCode().trim()));

                Division divDto = new Division();
                divDto.setDivisionCode(uocEntity.getOrgCode().trim());

                ProgramElement pgmEleDto = new ProgramElement();
                pgmEleDto.setProgramElementCode(uocEntity.getPgmEleCode().trim());

                gov.nsf.psm.foundation.model.UnitOfConsideration uocDto = new gov.nsf.psm.foundation.model.UnitOfConsideration();
                uocDto.setDirectorate(dirDto);
                uocDto.setDivision(divDto);
                uocDto.setProgramElement(pgmEleDto);
                uocDto.setUocOrdrNum(uocEntity.getUocOrdrNum());

                uocDtos.add(uocDto);
            }
            pkg.setUocs(uocDtos.toArray(new gov.nsf.psm.foundation.model.UnitOfConsideration[uocDtos.size()]));
        }
        pkg.setLastUpdatedPgm(prop.getLastUpdtPgm());
        pkg.setLastUpdatedTmsp(prop.getLastUpdtTmsp());
        pkg.setLastUpdatedUser(prop.getLastUpdtUser());
        pkg.setRevNum(rev.getRevnNum());
        return pkg;
    }

    public static InstitutionBudget cloneInstitutionBudget(InstitutionBudget budget,
            List<ProposalRevisionPersonnel> persons, String lastUpdtPgm, String lastUpdtUser, Date lastUpdtTmsp) {
        InstitutionBudget instBudget = new InstitutionBudget();
        instBudget.setBudgetRecordList(new ArrayList<BudgetRecord>());
        for (BudgetRecord rec : budget.getBudgetRecordList()) {
            BudgetRecord newRec = new BudgetRecord();
            newRec.setBudgetYear(rec.getBudgetYear());
            if (rec.getEquipmentList() != null && !rec.getEquipmentList().isEmpty()) {
                List<EquipmentCost> equipmentList = new ArrayList<>();
                for (EquipmentCost cost : rec.getEquipmentList()) {
                    EquipmentCost newCost = new EquipmentCost();
                    newCost.setEquipmentCostBudgetJsutificationText(cost.getEquipmentCostBudgetJsutificationText());
                    newCost.setEquipmentDollarAmount(cost.getEquipmentDollarAmount());
                    newCost.setEquipmentName(cost.getEquipmentName());
                    newCost.setLastUpdatedPgm(lastUpdtPgm);
                    newCost.setLastUpdatedUser(lastUpdtUser);
                    newCost.setLastUpdatedTmsp(lastUpdtTmsp);
                    equipmentList.add(newCost);
                }
                newRec.setEquipmentList(equipmentList);
            }
            if (rec.getFringeBenefitCost() != null) {
                FringeBenefitCost newCost = new FringeBenefitCost();
                newCost.setFringeBenefitBudgJustification(
                        rec.getFringeBenefitCost().getFringeBenefitBudgJustification());
                newCost.setFringeBenefitDollarAmount(rec.getFringeBenefitCost().getFringeBenefitDollarAmount());
                newCost.setLastUpdatedPgm(lastUpdtPgm);
                newCost.setLastUpdatedUser(lastUpdtUser);
                newCost.setLastUpdatedTmsp(lastUpdtTmsp);
                newRec.setFringeBenefitCost(newCost);
            }
            if (rec.getIndirectCostsList() != null && !rec.getIndirectCostsList().isEmpty()) {
                List<IndirectCost> indirectCostList = new ArrayList<>();
                for (IndirectCost cost : rec.getIndirectCostsList()) {
                    IndirectCost newCost = new IndirectCost();
                    newCost.setIndirectCostBaseDollarAmount(cost.getIndirectCostBaseDollarAmount());
                    newCost.setIndirectCostBudgetJustificationText(cost.getIndirectCostBudgetJustificationText());
                    newCost.setIndirectCostItemName(cost.getIndirectCostItemName());
                    newCost.setIndirectCostRate(cost.getIndirectCostRate());
                    newCost.setLastUpdatedPgm(lastUpdtPgm);
                    newCost.setLastUpdatedUser(lastUpdtUser);
                    newCost.setLastUpdatedTmsp(lastUpdtTmsp);
                    indirectCostList.add(newCost);
                }
                newRec.setIndirectCostsList(indirectCostList);
            }
            if (rec.getParticipantsSupportCost() != null) {
                ParticipantSupportCost newCost = new ParticipantSupportCost();
                newCost.setOtherDollarAmount(rec.getParticipantsSupportCost().getOtherDollarAmount());
                newCost.setParticipantSupportJustificationText(
                        rec.getParticipantsSupportCost().getParticipantSupportJustificationText());
                newCost.setPartNumberCount(rec.getParticipantsSupportCost().getPartNumberCount());
                newCost.setStipendDollarAmount(rec.getParticipantsSupportCost().getStipendDollarAmount());
                newCost.setSubsistenceDollarAmount(rec.getParticipantsSupportCost().getSubsistenceDollarAmount());
                newCost.setTravelDollarAmount(rec.getParticipantsSupportCost().getTravelDollarAmount());
                newCost.setLastUpdatedPgm(lastUpdtPgm);
                newCost.setLastUpdatedUser(lastUpdtUser);
                newCost.setLastUpdatedTmsp(lastUpdtTmsp);
                newRec.setParticipantsSupportCost(newCost);
            }
            if (rec.getOtherDirectCost() != null) {
                OtherDirectCost newCost = new OtherDirectCost();
                newCost.setComputerServicesDollarAmount(rec.getOtherDirectCost().getComputerServicesDollarAmount());
                newCost.setConsultantServicesDollarAmount(rec.getOtherDirectCost().getConsultantServicesDollarAmount());
                newCost.setMaterialsDollarAmount(rec.getOtherDirectCost().getMaterialsDollarAmount());
                newCost.setOtherDirectCostBudgetJustificationText(
                        rec.getOtherDirectCost().getOtherDirectCostBudgetJustificationText());
                newCost.setOtherDirectCostDollarAmount(rec.getOtherDirectCost().getOtherDirectCostDollarAmount());
                newCost.setPublicationDollarAmount(rec.getOtherDirectCost().getPublicationDollarAmount());
                newCost.setSubContractDollarAmount(rec.getOtherDirectCost().getSubContractDollarAmount());
                newCost.setLastUpdatedPgm(lastUpdtPgm);
                newCost.setLastUpdatedUser(lastUpdtUser);
                newCost.setLastUpdatedTmsp(lastUpdtTmsp);
                newRec.setOtherDirectCost(newCost);
            }
            if (rec.getOtherPersonnelList() != null && !rec.getOtherPersonnelList().isEmpty()) {
                List<OtherPersonnelCost> opcList = new ArrayList<>();
                for (OtherPersonnelCost cost : rec.getOtherPersonnelList()) {
                    OtherPersonnelCost newCost = new OtherPersonnelCost();
                    newCost.setOtherPersonCount(cost.getOtherPersonCount());
                    newCost.setOtherPersonDollarAmount(cost.getOtherPersonDollarAmount());
                    newCost.setOtherPersonMonthCount(cost.getOtherPersonMonthCount());
                    newCost.setOtherPersonTypeCode(cost.getOtherPersonTypeCode());
                    newCost.setLastUpdatedPgm(lastUpdtPgm);
                    newCost.setLastUpdatedUser(lastUpdtUser);
                    newCost.setLastUpdatedTmsp(lastUpdtTmsp);
                    opcList.add(newCost);
                }
                newRec.setOtherPersonnelList(opcList);
            }
            if (rec.getTravelCost() != null) {
                TravelCost newCost = new TravelCost();
                newCost.setDomesticTravelDollarAmount(rec.getTravelCost().getDomesticTravelDollarAmount());
                newCost.setForeignTravelDollarAmount(rec.getTravelCost().getForeignTravelDollarAmount());
                newCost.setTravelCostBudgetJustificationText(
                        rec.getTravelCost().getTravelCostBudgetJustificationText());
                newCost.setLastUpdatedPgm(lastUpdtPgm);
                newCost.setLastUpdatedUser(lastUpdtUser);
                newCost.setLastUpdatedTmsp(lastUpdtTmsp);
                newRec.setTravelCost(newCost);
            }
            if (rec.getSrPersonnelList() != null && !rec.getSrPersonnelList().isEmpty()) {
                List<SeniorPersonnelCost> srPersonnelList = new ArrayList<>();
                for (SeniorPersonnelCost cost : rec.getSrPersonnelList()) {
                    SeniorPersonnelCost newCost = new SeniorPersonnelCost();
                    newCost.setHidden(cost.isHidden());
                    newCost.setSeniorPersonDollarAmount(cost.getSeniorPersonDollarAmount());
                    newCost.setSeniorPersonFirstName(cost.getSeniorPersonFirstName());
                    newCost.setSeniorPersonLastName(cost.getSeniorPersonLastName());
                    newCost.setSeniorPersonMiddleInitial(cost.getSeniorPersonMiddleInitial());
                    newCost.setSeniorPersonInstId(cost.getSeniorPersonInstId());
                    newCost.setSeniorPersonJustificationText(cost.getSeniorPersonJustificationText());
                    newCost.setSeniorPersonMiddleInitial(cost.getSeniorPersonMiddleInitial());
                    newCost.setSeniorPersonMonthCount(cost.getSeniorPersonMonthCount());
                    if (cost.getSeniorPersonNsfId() != null) {
                        newCost.setSeniorPersonNsfId(cost.getSeniorPersonNsfId());
                    }
                    newCost.setSeniorPersonRoleCode(cost.getSeniorPersonRoleCode());
                    for (ProposalRevisionPersonnel propRevPers : persons) {
                        if (propRevPers.getPrevPersonnel().getPropPersId().trim().equals(cost.getPropPersId())) {
                            newCost.setPropPersId(propRevPers.getRevPersonnel().getPropPersId());
                        }
                    }
                    srPersonnelList.add(newCost);
                }
                newRec.setSrPersonnelList(srPersonnelList);
            }
            instBudget.getBudgetRecordList().add(newRec);
        }
        return instBudget;
    }

    public static PrimaryPlaceOfPerformance populatePpop(Institution awdOrganization) {
        PrimaryPlaceOfPerformance ppop = new PrimaryPlaceOfPerformance();
        ppop.setStreetAddress(awdOrganization.getAddress().getStreetAddress());
        ppop.setStreetAddress2(awdOrganization.getAddress().getStreetAddress2());
        ppop.setCityName(awdOrganization.getAddress().getCityName());
        ppop.setCountryCode(awdOrganization.getAddress().getCountryCode());
        ppop.setOrganizationName(awdOrganization.getOrganizationName());
        ppop.setPostalCode(formatPostalCode(awdOrganization.getAddress().getPostalCode()));
        ppop.setStateCode(awdOrganization.getAddress().getStateCode());
        return ppop;
    }

    public static String formatPostalCode(String postalCode) {
        return String.valueOf(postalCode).replaceFirst("(\\d{5})(\\d+)", "$1-$2");
    }

    public static String correctDBString(String value) {
        return (value != null && !StringUtils.isEmpty(value)) ? value.trim() : null;
    }

}
