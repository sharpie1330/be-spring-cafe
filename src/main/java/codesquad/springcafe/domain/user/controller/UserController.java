package codesquad.springcafe.domain.user.controller;

import codesquad.springcafe.domain.user.data.UserData;
import codesquad.springcafe.domain.user.data.UserJoinData;
import codesquad.springcafe.domain.user.data.UserListData;
import codesquad.springcafe.domain.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 가입
    @PostMapping
    public String join(@Valid @ModelAttribute UserJoinData userJoinData, Model model) {
        userService.join(userJoinData);

        model.addAttribute("user", userJoinData);

        return "/user/registration_success";
    }

    // 회원 목록 조회
    @GetMapping
    public String getUsers(Model model) {
        UserListData userListData = userService.getUsers();

        model.addAttribute("totalUserCnt", userListData.getTotalUserCnt());
        model.addAttribute("users", userListData.getUserList());

        return "user/list";
    }

    // 회원 상세 조회 (프로필 조회)
    @GetMapping("/{userId}")
    public String getUser(@PathVariable(name = "userId") Long userId, Model model) {
        UserData userData = userService.getUser(userId);

        model.addAttribute("user", userData);

        return "user/profile";
    }
}
