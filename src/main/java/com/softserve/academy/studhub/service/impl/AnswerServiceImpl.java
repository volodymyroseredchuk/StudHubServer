package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.dto.AnswerApproveDTO;
import com.softserve.academy.studhub.dto.AnswerCreateDTO;
import com.softserve.academy.studhub.dto.AnswerDTO;
import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.entity.Comment;
import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.SocketMessage;
import com.softserve.academy.studhub.repository.AnswerRepository;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.repository.UserRepository;
import com.softserve.academy.studhub.service.AnswerService;
import com.softserve.academy.studhub.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private AnswerRepository answerRepository;

    private ModelMapper modelMapper;

    private QuestionRepository questionRepository;

    private UserRepository userRepository;

    private SubscriptionService subscriptionService;

    @Override
    public Answer findById(Integer answerId){
        Optional<Answer> result = answerRepository.findById(answerId);
        if (!result.isPresent()) {
            throw new IllegalArgumentException("Requested answer does not exist");
        }
        return result.get();
    }

    @Override
    public List<Answer> findAllByQuestionId(Integer questionId) {
        List<Answer> answers = answerRepository.findByQuestionIdOrderByCreationDateAsc(questionId);


        return answers;
    }

    @Override
    public Answer save(AnswerCreateDTO answerCreateDTO, Integer questionId, String username) {
        Answer answer = new Answer();
        answer.setBody(answerCreateDTO.getBody());
        answer.setCreationDate(LocalDateTime.now());
        answer.setComment(new ArrayList<Comment>());
        answer.setApproved(false);
        answer.setRate(0);
        answer.setQuestion(questionRepository.findById(questionId).get());
        answer.setUser(userRepository.findByUsername(username).get());
        Answer returnAnswer = answerRepository.saveAndFlush(answer);
        subscriptionService.handleMessage(new SocketMessage(returnAnswer.getId().toString()));
        return returnAnswer;

    }

    @Override
    public void deleteById(Integer answerId) {
        answerRepository.deleteById(answerId);
    }

    @Override
    public AnswerApproveDTO approve(AnswerApproveDTO answerApproveDTO) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerApproveDTO.getAnswerId());
        if(optionalAnswer.isPresent()){
            Answer answer = optionalAnswer.get();
            answer.setApproved(answerApproveDTO.getApproved());
            answerRepository.saveAndFlush(answer);
            return answerApproveDTO;
        } else {
            throw new IllegalArgumentException("Requested answer does not exist");
        }
    }


}
