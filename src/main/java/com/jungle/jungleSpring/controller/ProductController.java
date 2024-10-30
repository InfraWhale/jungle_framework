package com.jungle.jungleSpring.controller;

import com.jungle.jungleSpring.dto.ProductMypriceRequestDto;
import com.jungle.jungleSpring.dto.ProductRequestDto;
import com.jungle.jungleSpring.dto.ProductResponseDto;
import com.jungle.jungleSpring.entity.Product;
import com.jungle.jungleSpring.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 관심 상품 등록
    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto, HttpServletRequest request) {
        log.info("상품 등록 시작");
        return productService.createProduct(requestDto, request);
    }

    // 관심 상품 조회
    @GetMapping("/products")
    public Page<Product> getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            HttpServletRequest request
    ) {
        // 응답 보내기
        return productService.getProducts(request, page-1, size, sortBy, isAsc);
    }

    // 관심 상품 최저가 등록
    @PutMapping("/products/{id}")
    public Long updateProducts(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto, HttpServletRequest request) {
        // 응답 보내기 (업데이트된 상품 id)
        return productService.updateProduct(id, requestDto, request);
    }
}
