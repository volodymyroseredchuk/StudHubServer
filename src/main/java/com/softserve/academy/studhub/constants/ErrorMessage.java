package com.softserve.academy.studhub.constants;

public class ErrorMessage {

    public static final String USER_NOT_FOUND_BY_ID = "The user does not exist by this id: ";
    public static final String USER_NOT_FOUND_BY_USERNAME = "The user does not exist by this username: ";
    public static final String USER_NOT_FOUND_BY_EMAIL = "The user does not exist by this email: ";
    public static final String USER_ALREADY_EXISTS_BY_USERNAME = "The user already exists with this username";
    public static final String USER_ALREADY_EXISTS_BY_EMAIL = "The user already exists with this email";

    public static final String QUESTION_NOTFOUND = "Requested question does not exist by this id: ";
    public static final String ANSWER_NOTFOUND = "Requested answer does not exist by this id: ";
    public static final String COMMENT_NOTFOUND = "Requested comment does not exist by this id: ";
    public static final String TEACHER_NOTFOUND = "Requested teacher does not exist by this id: ";
    public static final String UNIVERSITY_NOTFOUND = "Requested university does not exist by this id: ";
    public static final String TAG_NOT_FOUND_BY_ID = "Requested tag does not exist by this id: ";
    public static final String TAG_NOT_FOUND_BY_NAME = "Requested tag does not exist by this name: ";
    public static final String FEEDBACK_NOTFOUND = "Requested feedback does not exist by this id: ";
    public static final String VOTE_NOTFOUND = "Requested vote does not exist by this id: ";

    public static final String NOT_AUTHORISED = "You don't have rights to access this resource ";
    public static final String WRONG_GOOGLE_DATA = "Invalid Google information.";

    public static final String BAD_ARGUMENT = "One or more passed parameters is wrong.";

    public static final String SERVER_ERROR = "Ooops... Server doesn't feel good. Try to connect later.";

    public static final String WRONG_METHOD = " method is not allowed for this operation";

    public static final String ASK_TO_CONFIRM_ACC = "Please, activate your account";

    public static final String USER_IS_ALREADY = "This user is already ";
    public static final String USER_IS_NOT = "This user isn't ";

    public static final String CONFIRMATION_LINK_IS_EXPIRED_OR_INVALID =  "This link is expired or is invalid. Please issue a new e-mail confirmation request.";
    public static final String PASSWORD_RESET_LINK_IS_EXPIRED_OR_INVALID =  "This link is expired or is invalid. Please issue a new password-reset request.";

    public static final String VOTE_POST_DTO_NULL_FIELDS = "Way too much null fields in VotePostDTO. BMake sure you are sending valid VotePostDTO object";

    public static final String QUESTION_NOT_DELETED = "This question already has answers and can not be deleted";

    public static final String ROLE_NOT_FOUND_BY_NAME = "The role is not found by name: ";

}
