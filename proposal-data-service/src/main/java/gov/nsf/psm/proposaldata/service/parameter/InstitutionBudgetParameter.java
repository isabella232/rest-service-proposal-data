package gov.nsf.psm.proposaldata.service.parameter;

import java.util.List;

import gov.nsf.psm.proposaldata.entity.BudgetInstitutionEntity;
import gov.nsf.psm.proposaldata.entity.EquipmentBudgetCostEntity;
import gov.nsf.psm.proposaldata.entity.FringeBenefitBudgetEntity;
import gov.nsf.psm.proposaldata.entity.IndirectBudgetCostEntity;
import gov.nsf.psm.proposaldata.entity.OtherDirectCostEntity;
import gov.nsf.psm.proposaldata.entity.OtherPersonsBudgetCostEntity;
import gov.nsf.psm.proposaldata.entity.ParticipantsSupportCostEntity;
import gov.nsf.psm.proposaldata.entity.ProposalMessageEntity;
import gov.nsf.psm.proposaldata.entity.ProposalPersonsEntity;
import gov.nsf.psm.proposaldata.entity.SeniorPersonsBudgetCostEntity;
import gov.nsf.psm.proposaldata.entity.TravelBudgetCostEntity;

public class InstitutionBudgetParameter {

    private BudgetInstitutionEntity bie;
    private List<OtherPersonsBudgetCostEntity> opbceList;
    private List<FringeBenefitBudgetEntity> fbbeList;
    private List<EquipmentBudgetCostEntity> ebceList;
    private List<TravelBudgetCostEntity> tbceList;
    private List<ParticipantsSupportCostEntity> psceList;
    private List<OtherDirectCostEntity> odceList;
    private List<IndirectBudgetCostEntity> ibceList;
    private List<ProposalPersonsEntity> propPersList;
    private List<SeniorPersonsBudgetCostEntity> spbceList;
    private List<ProposalMessageEntity> propMsgList;

    public List<SeniorPersonsBudgetCostEntity> getSpbceList() {
        return spbceList;
    }

    public void setSpbceList(List<SeniorPersonsBudgetCostEntity> spbceList) {
        this.spbceList = spbceList;
    }

    public BudgetInstitutionEntity getBie() {
        return bie;
    }

    public void setBie(BudgetInstitutionEntity bie) {
        this.bie = bie;
    }

    public List<OtherPersonsBudgetCostEntity> getOpbceList() {
        return opbceList;
    }

    public void setOpbceList(List<OtherPersonsBudgetCostEntity> opbceList) {
        this.opbceList = opbceList;
    }

    public List<FringeBenefitBudgetEntity> getFbbeList() {
        return fbbeList;
    }

    public void setFbbeList(List<FringeBenefitBudgetEntity> fbbeList) {
        this.fbbeList = fbbeList;
    }

    public List<EquipmentBudgetCostEntity> getEbceList() {
        return ebceList;
    }

    public void setEbceList(List<EquipmentBudgetCostEntity> ebceList) {
        this.ebceList = ebceList;
    }

    public List<TravelBudgetCostEntity> getTbceList() {
        return tbceList;
    }

    public void setTbceList(List<TravelBudgetCostEntity> tbceList) {
        this.tbceList = tbceList;
    }

    public List<ParticipantsSupportCostEntity> getPsceList() {
        return psceList;
    }

    public void setPsceList(List<ParticipantsSupportCostEntity> psceList) {
        this.psceList = psceList;
    }

    public List<OtherDirectCostEntity> getOdceList() {
        return odceList;
    }

    public void setOdceList(List<OtherDirectCostEntity> odceList) {
        this.odceList = odceList;
    }

    public List<IndirectBudgetCostEntity> getIbceList() {
        return ibceList;
    }

    public void setIbceList(List<IndirectBudgetCostEntity> ibceList) {
        this.ibceList = ibceList;
    }

    public List<ProposalPersonsEntity> getPropPersList() {
        return propPersList;
    }

    public void setPropPersList(List<ProposalPersonsEntity> propPersList) {
        this.propPersList = propPersList;
    }

    /**
     * @return the propMsgList
     */
    public List<ProposalMessageEntity> getPropMsgList() {
        return propMsgList;
    }

    /**
     * @param propMsgList
     *            the propMsgList to set
     */
    public void setPropMsgList(List<ProposalMessageEntity> propMsgList) {
        this.propMsgList = propMsgList;
    }

}
