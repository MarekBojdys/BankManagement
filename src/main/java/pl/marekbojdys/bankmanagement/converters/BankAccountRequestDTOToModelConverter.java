package pl.marekbojdys.bankmanagement.converters;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.marekbojdys.bankmanagement.dtos.BankAccountAddRequestDTO;
import pl.marekbojdys.bankmanagement.models.BankAccount;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class BankAccountRequestDTOToModelConverter implements Converter<BankAccountAddRequestDTO, BankAccount> {

    private final ModelMapper modelMapper;

    public BankAccountRequestDTOToModelConverter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        customizeMapper();
    }

    @Override
    public BankAccount convert(final BankAccountAddRequestDTO source) {
        final BankAccount bankAccount = modelMapper.map(source, BankAccount.class);
        bankAccount.setBalance(new AtomicReference<BigDecimal>(source.getBalance()));
        return bankAccount;
    }

    private void customizeMapper() {
        modelMapper.addMappings(new PropertyMap<BankAccountAddRequestDTO, BankAccount>() {
            @Override
            protected void configure() {
                skip(destination.getBalance());
            }
        });
    }
}
