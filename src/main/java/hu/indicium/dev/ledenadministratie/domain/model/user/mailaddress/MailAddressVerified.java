package hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress;

import hu.indicium.dev.ledenadministratie.domain.DomainEvent;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;

import java.util.Date;

public class MailAddressVerified implements DomainEvent {

    private final String address;

    private final Date verifiedAt;

    private final int eventVersion = 1;

    private final Date occurredOn;

    public MailAddressVerified(String address, Date verifiedAt) {
        this.address = address;
        this.verifiedAt = verifiedAt;
        this.occurredOn = new Date();
    }

    @Override
    public int eventVersion() {
        return eventVersion;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }
}
