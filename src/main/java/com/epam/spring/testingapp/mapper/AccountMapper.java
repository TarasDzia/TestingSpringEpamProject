package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.model.Account;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    List<AccountDto> toAccountDtos(List<Account> accounts);
    List<Account> toAccounts(List<AccountDto> accountDtos);
    @Mapping(target = "password", ignore = true)
    AccountDto toAccountDto(Account account);
    @Mapping(target = "accountRole",  defaultValue = "USER")
    Account toAccount(AccountDto accountDto);
}
