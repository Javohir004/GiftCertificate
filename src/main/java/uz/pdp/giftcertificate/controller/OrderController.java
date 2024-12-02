package uz.pdp.giftcertificate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.giftcertificate.domain.dto.request.OrderCreateDTO;
import uz.pdp.giftcertificate.domain.dto.response.OrderResponse;
import uz.pdp.giftcertificate.domain.views.OrderView;
import uz.pdp.giftcertificate.service.OrderService;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController{

    @Autowired
    private OrderService orderService;

    @PostMapping
    public OrderResponse create(@RequestBody OrderCreateDTO createDTO) {
        return orderService.save(createDTO);
    }

    @GetMapping("/{userId}")
    public Page<OrderResponse> findUserOrders(
            @PathVariable(name = "userId")UUID userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return orderService.findUserOrders(page, size, userId);
    }

    @GetMapping("/view/{userId}")
    public Page<OrderView> findUserOrdersWithView(
            @PathVariable(name = "userId")UUID userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return orderService.findUserOrdersWithView(page, size, userId);
    }

    @GetMapping("/sort/{userId}")
    public Page<OrderResponse> findUserOrdersSort(
            @PathVariable(name = "userId")UUID userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return orderService.findUserOrdersSort(page, size, userId);
    }

}
