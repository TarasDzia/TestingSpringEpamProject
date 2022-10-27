package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.model.Account;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

        List<AccountDto> accountsToAccountDtos(List<Account> accounts);
    List<Account> accountsDtosToAccounts(List<AccountDto> accountDtos);
    @Mapping(target = "password", ignore = true)
    AccountDto accountToAccountDto(Account account);
    @Mapping(target = "accountRole",  defaultValue = "USER")
    @Mapping(target = "id", ignore = true)
    Account accountDtoToAccount(AccountDto accountDto);
}
