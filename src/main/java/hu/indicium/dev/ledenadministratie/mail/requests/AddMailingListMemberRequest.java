package hu.indicium.dev.ledenadministratie.mail.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMailingListMemberRequest {

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("merge_fields")
    private Map<String, String> mergeFields;

    public AddMailingListMemberRequest(MailEntryDTO mailEntryDTO) {
        this.emailAddress = mailEntryDTO.getEmail();
        this.status = "subscribed";
        this.mergeFields = new HashMap<>();
        this.mergeFields.put("FNAME", mailEntryDTO.getFirstName());
        this.mergeFields.put("LNAME", mailEntryDTO.getLastName());
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getTags() {
        return tags;
    }

    public Map<String, String> getMergeFields() {
        return mergeFields;
    }
}
