package hu.indicium.dev.ledenadministratie.domain.model.user.member.membership;

import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;
import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentStatus;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Membership {

    @EmbeddedId
    private MembershipId membershipId;

    private Date startDate;

    private Date endDate;

    @ManyToOne(optional = false)
    private Member member;

    private PaymentId paymentId;

    private PaymentStatus paymentStatus;

    public Membership(MembershipId membershipId, Date startDate, Date endDate, Member member) {
        this.membershipId = membershipId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.member = member;
    }

    public void assignPayment(PaymentId paymentId) {
        this.paymentId = paymentId;
        this.paymentStatus = PaymentStatus.OPEN;
    }

    public void updatePaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public boolean isActive() {
        return this.endDate.before(new Date());
    }
}
