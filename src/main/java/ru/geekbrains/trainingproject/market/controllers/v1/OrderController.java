package ru.geekbrains.trainingproject.market.controllers.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.trainingproject.market.dtos.OrderDto;
import ru.geekbrains.trainingproject.market.dtos.UserOrderDto;
import ru.geekbrains.trainingproject.market.model.Order;
import ru.geekbrains.trainingproject.market.services.CartService;
import ru.geekbrains.trainingproject.market.services.OrderService;
import ru.geekbrains.trainingproject.market.services.UserService;
import ru.geekbrains.trainingproject.market.utils.UserDataFromHttpRequestUtil;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")

public class OrderController {
    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;


    @GetMapping("")
    public List<Order> findByCurrentUser() {
        System.out.println("Controller = " + userService.getOrderByCurrentUser().toString());
        return userService.getOrderByCurrentUser();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
        orderService.createAndSaveOrder(orderDto);
        cartService.clearCart();
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
