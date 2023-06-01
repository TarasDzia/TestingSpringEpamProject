package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.model.Account;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @IterableMapping(qualifiedByName = "toAccountDto")
    List<AccountDto> toAccountDtos(List<Account> accounts);

    default Page<AccountDto> toAccountDtos(Page<Account> page) {
        return page.map(this::toAccountDto);
    }

    List<Account> toAccounts(List<AccountDto> accountDtos);

    @Named("toAccountDto")
    @Mapping(target = "password", ignore = true)
    AccountDto toAccountDto(Account account);

    @Mapping(target = "accountRole", defaultValue = "USER")
    @Mapping(target = "banned", defaultValue = "false")
    Account toAccount(AccountDto accountDto);
}
