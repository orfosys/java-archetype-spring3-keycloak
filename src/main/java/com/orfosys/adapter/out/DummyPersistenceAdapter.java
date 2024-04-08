package com.orfosys.adapter.out;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.orfosys.common.pagination.Paginator;
import com.orfosys.application.out.FindDummyPersistence;
import com.orfosys.application.out.SaveDummyPersistence;
import com.orfosys.common.annotation.PersistenceAdapter;
import com.orfosys.domain.model.Dummy;

import lombok.AllArgsConstructor;

@PersistenceAdapter
@AllArgsConstructor
public class DummyPersistenceAdapter implements FindDummyPersistence, SaveDummyPersistence {
    
    private final DummyEntityRepository dummyEntityRepository;

    @Override
    public Optional<Dummy> findById(int id) {
        return dummyEntityRepository
                .findById(id)
                .map(DummyMapper::entityToDomain);
    }

    @Override
    public Dummy save(Dummy dummy) {
        var dummyEntity = DummyMapper.domainToEntity(dummy).orElseThrow(IllegalStateException::new);
        dummy = DummyMapper.entityToDomain(dummyEntityRepository.save(dummyEntity));
        return dummy;
    }

    @Override
    public Paginator.Pair<Dummy> findAll(Pageable pageable) {
        var page = dummyEntityRepository.findAll(pageable);
        return Paginator.create(page, DummyMapper::entityToDomain);
    }
}
