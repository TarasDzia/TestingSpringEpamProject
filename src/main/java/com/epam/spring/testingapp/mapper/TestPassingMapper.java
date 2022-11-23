//package com.epam.spring.testingapp.mapper;
//
//import com.epam.spring.testingapp.dto.TestPassingDto;
//import com.epam.spring.testingapp.dto.UserAnswerDto;
//import com.epam.spring.testingapp.model.Answer;
//import com.epam.spring.testingapp.model.RunningTest;
//import com.epam.spring.testingapp.model.UserAnswer;
//import org.mapstruct.IterableMapping;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Named;
//import org.mapstruct.factory.Mappers;
//
//import java.util.List;
//import java.util.Set;
//
//@Mapper(uses = TestMapper.class)
//public interface TestPassingMapper {
//    TestPassingMapper INSTANCE = Mappers.getMapper(TestPassingMapper.class);
//
//    @Mapping(target = "accountId", source = "account.id")
//    @Mapping(target = "userAnswers", source = "userAnswers", qualifiedByName = "mapUserAnswers")
//    TestPassingDto toDto (RunningTest runningTest);
//
//    @Named("mapUserAnswers")
//    @IterableMapping(qualifiedByName = "mapUserAnswer")
//    List<UserAnswerDto> mapUserAnswers(List<UserAnswer> userAnswers);
//
//    @Named("mapUserAnswer")
//    @Mapping(target = "questionId", source = "question.id")
//    @Mapping(target = "answerIds", source = "answers", qualifiedByName = "mapAnswerSet")
//    @Mapping(target = "testPassingId", source = "getRunningTest.id")
//    UserAnswerDto mapUserAnswer(UserAnswer userAnswer);
//
//    @Named("mapAnswerSet")
//    @IterableMapping(qualifiedByName = "mapAnswer")
//    Set<Integer> mapAnswerSet(Set<Answer> userAnswers);
//
//    @Named("mapAnswer")
//    default Integer mapAnswer(Answer answer){
//        return  answer.getId();
//    }
//}
