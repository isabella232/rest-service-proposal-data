package gov.nsf.psm.proposaldata.utility;

import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.budget.InstitutionBudget;
import gov.nsf.psm.foundation.model.budget.OtherPersonnelCost;

public class BudgetDataUtility {

    private BudgetDataUtility() {
    }

    public static boolean hasPostdocFunding(InstitutionBudget propBudget) throws CommonUtilException {
        // check if any of the budget periods contain postdoctorate funding
        if (!propBudget.getBudgetRecordList().isEmpty()) {
            return propBudget.getBudgetRecordList().stream()
                    .anyMatch(budgetRecord -> budgetRecord.getOtherPersonnelList() != null && budgetRecord
                            .getOtherPersonnelList().stream()
                            .filter(othPersons -> othPersons.getOtherPersonTypeCode() != null && othPersons
                                    .getOtherPersonTypeCode().equals(OtherPersonnelCost.CODE_STUDENTS_POST_DOCTORAL))
                            .anyMatch(othPerson -> othPerson.getOtherPersonDollarAmount() != null
                                    && othPerson.getOtherPersonDollarAmount().doubleValue() > 0.0));
        }
        return false;
    }
}
