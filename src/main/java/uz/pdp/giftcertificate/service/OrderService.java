package uz.pdp.giftcertificate.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.giftcertificate.exception.BaseException;
import uz.pdp.giftcertificate.domain.dto.request.OrderCreateDTO;
import uz.pdp.giftcertificate.domain.dto.response.OrderResponse;
import uz.pdp.giftcertificate.domain.entity.GiftCertificateEntity;
import uz.pdp.giftcertificate.domain.entity.OrderEntity;
import uz.pdp.giftcertificate.domain.entity.UserEntity;
import uz.pdp.giftcertificate.domain.views.OrderView;
import uz.pdp.giftcertificate.repository.GiftCertificateRepository;
import uz.pdp.giftcertificate.repository.OrderRepository;
import uz.pdp.giftcertificate.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService implements BaseService<OrderEntity, OrderResponse, OrderCreateDTO> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GiftCertificateRepository certificateRepository;

    @Transactional
    public OrderResponse save(OrderCreateDTO createDTO) {
        return mapEntityToResponse(orderRepository.save(mapRequestToEntity(createDTO)));
    }

    public Page<OrderResponse> findUserOrders(int page, int size, UUID userId) {
//        wrong, didn't work as expected
        //        Pageable pageable = Pageable.ofSize(size);
        //        pageable.withPage(page);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<OrderEntity> allByUserId = orderRepository.findAllByUserIdOrderByCreatedDate(pageRequest, userId);
        return allByUserId.map(this::mapEntityToResponse);
    }

    public Page<OrderView> findUserOrdersWithView(int page, int size, UUID userId) {
//        wrong, didn't work as expected
        //        Pageable pageable = Pageable.ofSize(size);
        //        pageable.withPage(page);
        PageRequest pageRequest = PageRequest.of(page, size);
        return orderRepository.findAllOrderEntitiesByUserId(pageRequest, userId);

    }

    public Page<OrderResponse> findUserOrdersSort(int page, int size, UUID userId) {
//        wrong, didn't work as expected
        //        Pageable pageable = Pageable.ofSize(size);
        //        pageable.withPage(page);


        // Sort.by(Orders)
        Sort sort = Sort.by(List.of(Sort.Order.by("createdDate")));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<OrderEntity> allByUserId = orderRepository.findAllByUserId(pageRequest, userId);
        return allByUserId.map(this::mapEntityToResponse);
    }




    @Override
    public OrderEntity mapRequestToEntity(OrderCreateDTO orderCreateDTO) {
        UserEntity userEntity = userRepository.findById(orderCreateDTO.getUserId())
                .orElseThrow(() -> new BaseException("user not found with id: " + orderCreateDTO.getUserId()));
        GiftCertificateEntity giftCertificateEntity = certificateRepository.findById(orderCreateDTO.getCertificateId())
                .orElseThrow(() -> new BaseException("certificate not found with id: " + orderCreateDTO.getCertificateId()));
        return new OrderEntity(giftCertificateEntity, userEntity, giftCertificateEntity.getPrice());
    }

    @Override
    public OrderResponse mapEntityToResponse(OrderEntity entity) {
        OrderResponse orderResponse = new OrderResponse(entity.getCertificate().getName(), entity.getCertificate().getId(), entity.getPrice());
        orderResponse.setId(entity.getId());
        orderResponse.setUpdatedDate(entity.getUpdatedDate());
        orderResponse.setCreatedDate(entity.getCreatedDate());
        return orderResponse;
    }
}
