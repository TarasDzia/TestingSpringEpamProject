package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.TestResultDto;
import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.Test;
import com.epam.spring.testingapp.model.TestResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TestResultMapper {
    TestResultMapper INSTANCE = Mappers.getMapper(TestResultMapper.class);

    List<TestResultDto> testResultsToTestResultsDtos(List<TestResult> testResults);
    List<TestResult> testResultsDtosToTestResults(List<TestResultDto> testResultDtos);

    @Mapping(target = "accountId", source = "testResult.account.id")
    TestResultDto testResultToTestResultDto(TestResult testResult);

    @Mapping(target = "account", source = "accountId")
    TestResult testResultDtoToTestResult(TestResultDto testResultDto);

    default Account mapAccount(int accountId) {
        return Account.builder().id(accountId).build();
    }
}
