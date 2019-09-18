package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.dto.AnswerApproveDTO;
import com.softserve.academy.studhub.dto.AnswerCreateDTO;
import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.entity.Comment;
import com.softserve.academy.studhub.entity.SocketMessage;
import com.softserve.academy.studhub.entity.enums.SocketMessageType;
import com.softserve.academy.studhub.repository.AnswerRepository;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.repository.UserRepository;
import com.softserve.academy.studhub.service.AnswerService;
import com.softserve.academy.studhub.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private AnswerRepository answerRepository;

    private QuestionRepository questionRepository;

    private UserRepository userRepository;

    private SubscriptionService subscriptionService;

    @Override
    public Answer findById(Integer answerId) {
        Optional<Answer> result = answerRepository.findById(answerId);
        if (!result.isPresent()) {
            throw new IllegalArgumentException(ErrorMessage.ANSWER_NOTFOUND);
        }
        return result.get();
    }

    @Override
    public List<Answer> findAllByQuestionId(Integer questionId) {
        return answerRepository.findByQuestionIdOrderByCreationDateDesc(questionId);
    }

    @Override
    public Answer save(AnswerCreateDTO answerCreateDTO, Integer questionId, String username) {
        Answer answer = new Answer();
        answer.setBody(answerCreateDTO.getBody());
        answer.setCreationDate(LocalDateTime.now());
        answer.setComment(new ArrayList<Comment>());
        answer.setApproved(false);
        answer.setRate(0);
        answer.setQuestion(questionRepository.findById(questionId).orElseThrow(
                () -> new IllegalArgumentException(ErrorMessage.QUESTION_NOTFOUND)
        ));
        answer.setUser(userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException(ErrorMessage.USER_NOT_FOUND_BY_USERNAME)
        ));
        Answer returnAnswer = answerRepository.saveAndFlush(answer);
        subscriptionService.handleMessage(
                new SocketMessage(returnAnswer.getQuestion().getId().toString(), SocketMessageType.NOTIFICATION));
        return returnAnswer;

    }

    @Override
    public Boolean deleteById(Integer answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (optionalAnswer.isPresent()) {
            Answer answer = optionalAnswer.get();
            if (answer.getApproved()) {
                return false;
            } else {
                answerRepository.deleteById(answerId);
                return true;
            }
        } else {
            return false;
        }

    }

    @Override
    public AnswerApproveDTO approve(AnswerApproveDTO answerApproveDTO) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerApproveDTO.getAnswerId());
        if (optionalAnswer.isPresent()) {
            Answer answer = optionalAnswer.get();
            answer.setApproved(answerApproveDTO.getApproved());
            answerRepository.saveAndFlush(answer);
            return answerApproveDTO;
        } else {
            throw new IllegalArgumentException(ErrorMessage.ANSWER_NOTFOUND);
        }
    }

    @Override
    public Integer countByUserUsername(String username) {

        return answerRepository.countByUserUsername(username);
    }

    @Override
    public Integer countByApprovedTrueAndUserUsername(String username) {

        return answerRepository.countByApprovedTrueAndUserUsername(username);
    }

    @Override
    public Integer sumOfRatingByUserUsername(String username) {

        return answerRepository.sumOfRatingByUserUsername(username);
    }
}
