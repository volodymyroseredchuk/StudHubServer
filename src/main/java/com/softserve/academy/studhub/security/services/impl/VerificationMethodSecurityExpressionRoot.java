package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.repository.QuestionRepository;
import com.softserve.academy.studhub.service.IQuestionService;
import com.softserve.academy.studhub.service.impl.QuestionServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;


public class VerificationMethodSecurityExpressionRoot
        extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;

    public VerificationMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean isQuestionCreator(Integer questionId, IQuestionService questionService) {

        if(this.getPrincipal() == null){
            throw new NullPointerException("princpal is null!");
        }
        if(questionService == null){
            throw new NullPointerException("questionService is null!");
        }
        return questionService.findById(questionId)
                .getUser()
                .getUsername()
                .equals(
                        ((UserPrinciple) this.getPrincipal())
                                .getUsername());
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public void setFilterObject(Object obj) {
        this.filterObject = obj;
    }

    @Override
    public void setReturnObject(Object obj) {
        this.returnObject = obj;
    }
}
