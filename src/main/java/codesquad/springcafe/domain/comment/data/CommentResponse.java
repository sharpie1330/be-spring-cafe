package codesquad.springcafe.domain.comment.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentResponse {
    private final Long commentId;
    private final Long questionId;
    private final String userLoginId;
    private final String userName;
    private final String content;
    private final String modifiedAt;
    private final boolean isModified;
    private final boolean isMy;
    private final boolean isUserWithdrawn;
}
