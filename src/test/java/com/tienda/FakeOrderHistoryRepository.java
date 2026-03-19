package com.tienda;

import com.tienda.model.OrderHistory;
import com.tienda.repository.JpaOrderHistoryRepository;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;

public class FakeOrderHistoryRepository implements JpaOrderHistoryRepository {

    private final List<OrderHistory> storage = new ArrayList<>();

    @Override
    public List<OrderHistory> findByOrderIdOrderByChangedAtAsc(String orderId) {
        return storage.stream()
                .filter(h -> h.getOrderId().equals(orderId))
                .toList();
    }

    @Override
    public <S extends OrderHistory> S save(S entity) {
        storage.add(entity);
        return entity;
    }

    @Override
    public <S extends OrderHistory> List<S> saveAll(Iterable<S> entities) { return List.of(); }
    @Override
    public Optional<OrderHistory> findById(Long id) { return Optional.empty(); }
    @Override
    public boolean existsById(Long id) { return false; }
    @Override
    public List<OrderHistory> findAll() { return storage; }
    @Override
    public List<OrderHistory> findAllById(Iterable<Long> ids) { return List.of(); }
    @Override
    public long count() { return storage.size(); }
    @Override
    public void deleteById(Long id) {}
    @Override
    public void delete(OrderHistory entity) {}
    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {}
    @Override
    public void deleteAll(Iterable<? extends OrderHistory> entities) {}
    @Override
    public void deleteAll() { storage.clear(); }
    @Override
    public List<OrderHistory> findAll(Sort sort) { return storage; }
    @Override
    public Page<OrderHistory> findAll(Pageable pageable) { return Page.empty(); }
    @Override
    public <S extends OrderHistory> S saveAndFlush(S entity) { return save(entity); }
    @Override
    public <S extends OrderHistory> List<S> saveAllAndFlush(Iterable<S> entities) { return List.of(); }
    @Override
    public void deleteAllInBatch(Iterable<OrderHistory> entities) {}
    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {}
    @Override
    public void deleteAllInBatch() {}
    @Override
    public OrderHistory getOne(Long id) { return null; }
    @Override
    public OrderHistory getById(Long id) { return null; }
    @Override
    public OrderHistory getReferenceById(Long id) { return null; }
    @Override
    public <S extends OrderHistory> Optional<S> findOne(Example<S> example) { return Optional.empty(); }
    @Override
    public <S extends OrderHistory> List<S> findAll(Example<S> example) { return List.of(); }
    @Override
    public <S extends OrderHistory> List<S> findAll(Example<S> example, Sort sort) { return List.of(); }
    @Override
    public <S extends OrderHistory> Page<S> findAll(Example<S> example, Pageable pageable) { return Page.empty(); }
    @Override
    public <S extends OrderHistory> long count(Example<S> example) { return 0; }
    @Override
    public <S extends OrderHistory> boolean exists(Example<S> example) { return false; }
    @Override
    public <S extends OrderHistory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) { return null; }
    @Override
    public void flush() {}

}
