package uz.pdp.giftcertificate.service;
import uz.pdp.giftcertificate.domain.entity.BaseEntity;


public interface BaseService<E extends BaseEntity, Response, Request> {

    E mapRequestToEntity(Request request);

    Response mapEntityToResponse(E entity);
}
