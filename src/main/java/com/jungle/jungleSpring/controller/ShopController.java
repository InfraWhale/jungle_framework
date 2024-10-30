package com.jungle.jungleSpring.controller;

import com.jungle.jungleSpring.service.FolderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShopController {

    private final FolderService folderService;

    @GetMapping("/shop")
    public ModelAndView shop() {
        return new ModelAndView("index");
    }

    // 로그인 한 유저가 메인 페이지를 요청할 때, 가지고 있는 폴더를 반환
    @GetMapping("/user-folder")
    public String getUserInfo(Model model, HttpServletRequest request) {
        model.addAttribute("folders", folderService.getFolders(request));

        // 타임리프 프래그먼트 문법
        // 특정 HTML 파일의 일부를 반환한다.
        // :: # fragment -> index.html 파일 내에서 id="fragment"를 가진 요소를 반환
        return "/index :: #fragment";
    }
}
