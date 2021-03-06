package hu.indicium.dev.ledenadministratie.infrastructure.mail;

import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.list.MailChimpService;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.transactional.TransactionalMailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@AllArgsConstructor
@Service
public class MailServiceImpl implements MailService {

    private final TransactionalMailService transactionalMailService;

    private final MailChimpService mailChimpService;

    @Override
    public void sendMail(MailAddress mailAddress, Name name, MailType mailType, Map<String, Object> params) {
        transactionalMailService.sendMail(mailAddress, name, mailType, params);
    }

    @Override
    public void addMailAddressToMailingList(MailAddress mailAddress, Name name, MailListType mailListType) {
        mailChimpService.addMailAddressToMailingList(mailAddress, name, mailListType);
    }

    @Override
    public void removeMailAddressToMailingList(MailAddress mailAddress, Name name, MailListType mailListType) {
        mailChimpService.removeMailAddressToMailingList(mailAddress, name, mailListType);
    }
}
