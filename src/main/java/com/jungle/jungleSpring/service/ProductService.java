package com.jungle.jungleSpring.service;

import com.jungle.jungleSpring.dto.ItemDto;
import com.jungle.jungleSpring.dto.ProductMypriceRequestDto;
import com.jungle.jungleSpring.dto.ProductRequestDto;
import com.jungle.jungleSpring.dto.ProductResponseDto;
import com.jungle.jungleSpring.entity.Product;
import com.jungle.jungleSpring.entity.User;
import com.jungle.jungleSpring.entity.UserRoleEnum;
import com.jungle.jungleSpring.jwt.JwtUtil;
import com.jungle.jungleSpring.repository.ProductRepository;
import com.jungle.jungleSpring.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token을 가져온다.
        String token = jwtUtil.resolveToken(request);
        Claims claims = null;

        // 토큰이 있는 경우에만 관심상품 추가가 가능하다.
        if(token != null) {
            // Token을 검증한다.
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보를 가져온다.
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB를 조회한다.
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            log.info("상품 등록 진행");
            //요청받은 DTO로 DB에 저장할 객체를 만든다.
            Product product = productRepository.saveAndFlush(new Product(requestDto, user.getId()));

            return new ProductResponseDto(product);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public Page<Product> getProducts(HttpServletRequest request, int page, int size, String sortBy, boolean isAsc) {

        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC :Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // Request에서 Token을 가져온다.
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 조회가 가능하다.
        if(token != null) {
            // Token을 검증한다.
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보를 가져온다.
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB를 조회한다.
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () ->new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 사용자 권한을 가져와서 ADMIN이면 전체조회한다. USER 면 본인이 추가한 부분을 조회한다.
            UserRoleEnum userRoleEnum = user.getRole();
            log.info("role = " + userRoleEnum);

            Page<Product> products;

            if (userRoleEnum == UserRoleEnum.USER) {
                products = productRepository.findAllByUserId(user.getId(), pageable);
            } else {
                products = productRepository.findAll(pageable);
            }

            return products;
        } else {
            return null;
        }
    }

    @Transactional
    public Long updateProduct(Long id, ProductMypriceRequestDto requestDto, HttpServletRequest request) {

        // Request에서 Token을 가져온다.
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 조회가 가능하다.
        if(token != null) {
            // Token을 검증한다.
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보를 가져온다.
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB를 조회한다.
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Product product = productRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 상품은 존재하지 않습니다.")
            );

            product.update(requestDto);
            return product.getId();
        } else {
            return null;
        }
    }

    @Transactional
    public void updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 상품은 존재하지 않습니다.")
        );
        product.updateByItemDto(itemDto);
    }
}
