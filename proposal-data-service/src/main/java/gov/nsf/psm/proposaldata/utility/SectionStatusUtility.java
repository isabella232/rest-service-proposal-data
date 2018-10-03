package gov.nsf.psm.proposaldata.utility;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import gov.nsf.psm.foundation.model.Personnel;
import gov.nsf.psm.foundation.model.SectionStatus;
import gov.nsf.psm.proposaldata.entity.ProposalPersonsEntity;

public class SectionStatusUtility {

	private SectionStatusUtility() {
	}

	public static SectionStatus setDefaultMissingDocumentCount(SectionStatus status) {
		if (status.getFilePath() == null || StringUtils.isEmpty(status.getFilePath())) {
			status.setEmptyDocumentCount(Constants.DEFAULT_SECTION_MISSING_DOCUMENT_COUNT);
		} else {
			status.setEmptyDocumentCount(Constants.DEFAULT_SECTION_NO_MISSING_DOCUMENT_COUNT);
		}
		return status;
	}

	public static int getPreviousPersonCount(final Set<ProposalPersonsEntity> personnel, final String firstName,
			final String middleInitial, final String lastName) {
		return personnel
				.stream().filter(
						p -> (p.getOthrSeniorPersonFirstName().trim().equalsIgnoreCase(firstName)
								&& (!StringUtils.isEmpty(p.getOthrSeniorPersonMiddleInitial())
										&& !StringUtils.isEmpty(middleInitial)
										&& p.getOthrSeniorPersonMiddleInitial().trim().toLowerCase()
												.charAt(0) == middleInitial.toLowerCase().charAt(0))
								|| StringUtils.isEmpty(middleInitial))
								&& p.getOthrSeniorPersonLasttName().trim().equalsIgnoreCase(lastName))
				.collect(Collectors.toSet()).size();
	}

	public static int getCurrentPersonCount(final List<Personnel> personnel, final String firstName,
			final String middleInitial, final String lastName) {
		return personnel.stream().filter(p -> (p.getFirstName().trim().equalsIgnoreCase(firstName)
				&& (!StringUtils.isEmpty(p.getMiddleName()) && !StringUtils.isEmpty(middleInitial)
						&& p.getMiddleName().trim().toLowerCase().charAt(0) == middleInitial.toLowerCase().charAt(0))
				|| StringUtils.isEmpty(middleInitial)) && p.getLastName().trim().equalsIgnoreCase(lastName))
				.collect(Collectors.toSet()).size();
	}

}
