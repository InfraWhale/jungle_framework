package com.jungle.jungleSpring.Posting.controller;

import com.jungle.jungleSpring.Posting.dto.*;
import com.jungle.jungleSpring.Posting.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posting")
@RequiredArgsConstructor
public class PostingController {

    private final PostingService postingService;

    @PostMapping()
    public addPostingResponseDto addPosting(@RequestBody addPostingRequestDto requestDto) {
        return postingService.addPosting(requestDto);
    }

    @GetMapping("/all")
    public List<getPostingResponseDto> getPostingAll() {
        return postingService.getPostingAll();
    }

    @GetMapping("/{id}")
    public getPostingResponseDto getPosting(@PathVariable("id") Long id) {
        return postingService.getPostingById(id);
    }

    @PostMapping("/{id}")
    public updatePostingResponseDto updatePosting(@PathVariable("id") Long id,
                                                  @RequestBody updatePostingRequestDto requestDto) {
        return postingService.updatePosting(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public String deletePosting(@PathVariable("id") Long id, @RequestBody deletePostingRequestDto requestDto) {
        return postingService.deletePosting(id, requestDto);
    }


}
