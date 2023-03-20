package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.SubjectDto;
import com.epam.spring.testingapp.dto.TestResultDto;
import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.model.TestResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(uses = TestMapper.class)
public interface TestResultMapper {
    TestResultMapper INSTANCE = Mappers.getMapper(TestResultMapper.class);

    default Page<TestResultDto> toTestResultsDtos(Page<TestResult> page) {
        return page.map(this::toTestResultDto);
    }

    List<TestResult> toTestResults(List<TestResultDto> testResultDtos);

    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "test", source = "runningTest.test", qualifiedByName = "toDtoWithoutQuestions")
    TestResultDto toTestResultDto(TestResult testResult);

    @Mapping(target = "account", source = "accountId")
    TestResult toTestResult(TestResultDto testResultDto);

    default Account mapAccount(int accountId) {
        return Account.builder().id(accountId).build();
    }
}
