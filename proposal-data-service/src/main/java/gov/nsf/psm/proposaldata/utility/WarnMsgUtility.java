package gov.nsf.psm.proposaldata.utility;

import java.util.List;

import org.springframework.util.StringUtils;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;

import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.PSMMessage;
import gov.nsf.psm.foundation.model.PSMMessageType;

public class WarnMsgUtility {

    private WarnMsgUtility() {
    }

    public static List<PSMMessage> getMessagesBySectionCode(List<PSMMessage> msgs, String sectionCode) throws CommonUtilException {
        if (!msgs.isEmpty() && !StringUtils.isEmpty(sectionCode)) {
            return msgs.stream().filter(msg -> msg.getSectionCode() != null && msg
                    .getSectionCode().trim().equals(sectionCode.trim())).map(PSMMessage.class::cast)
                    .collect(toList());
        } else {
            return new ArrayList<PSMMessage>();
        }
    }
    
    public static List<PSMMessage> getErrorMessagesBySectionCodeAndPersonId(List<PSMMessage> msgs, String sectionCode, String persId) throws CommonUtilException {
        if (!msgs.isEmpty() && !StringUtils.isEmpty(sectionCode)) {
            return msgs.stream().filter(msg -> msg.getSectionCode() != null && msg
                    .getSectionCode().trim().equals(sectionCode.trim()) && PSMMessageType.ERROR.getCode().equals(msg.getType().getCode()) && msg.getPersonId() == Integer.parseInt(persId)).map(PSMMessage.class::cast)
                    .collect(toList());
        } else {
            return new ArrayList<PSMMessage>();
        }
    }
    
    public static List<PSMMessage> getWarnMessagesBySectionCodeAndPersonId(List<PSMMessage> msgs, String sectionCode, String persId) throws CommonUtilException {
        if (!msgs.isEmpty() && !StringUtils.isEmpty(sectionCode)) {
            return msgs.stream().filter(msg -> msg.getSectionCode() != null && msg
                    .getSectionCode().trim().equals(sectionCode.trim()) && PSMMessageType.WARNING.getCode().equals(msg.getType().getCode()) && msg.getPersonId() == Integer.parseInt(persId)).map(PSMMessage.class::cast)
                    .collect(toList());
        } else {
            return new ArrayList<PSMMessage>();
        }
    }
    
}
