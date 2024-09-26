package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.dto.RegisterDTO;
import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "accountRole",  constant = "USER")
    Account registerDtoToAccount(RegisterDTO registerDTO);

    List<AccountDto> accountsToAccountDtos(List<Account> accounts);
    List<Account> accountsDtosToAccounts(List<AccountDto> accountDtos);
    AccountDto accountToAccountDto(Account account);
    Account accountDtoToAccount(AccountDto accountDto);
}
