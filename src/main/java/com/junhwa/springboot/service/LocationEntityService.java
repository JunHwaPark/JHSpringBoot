package com.junhwa.springboot.service;

import com.junhwa.springboot.domain.location.LocationEntity;
import com.junhwa.springboot.domain.location.LocationEntityRepository;
import com.junhwa.springboot.web.dto.LocationEntityListResponseDto;
import com.junhwa.springboot.web.dto.LocationEntityResponseDto;
import com.junhwa.springboot.web.dto.LocationEntitySaveRequestDto;
import com.junhwa.springboot.web.dto.LocationEntityUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LocationEntityService {

    private final LocationEntityRepository locationEntityRepository;

    @Transactional
    public Long save(LocationEntitySaveRequestDto requestDto) {
        return locationEntityRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, LocationEntityUpdateRequestDto requestDto) {
        LocationEntity locationEntity = locationEntityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없습니다. id=" + id));
        locationEntity.update(requestDto.getLatitude(), requestDto.getLongitude(), requestDto.getAddress());

        return id;
    }

    public LocationEntityResponseDto findById (Long id) {
        LocationEntity entity = locationEntityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없습니다. id=" + id));

        return new LocationEntityResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<LocationEntityListResponseDto> findAllDesc() {
        return locationEntityRepository.findAllDesc().stream()
                .map(LocationEntityListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        LocationEntity locationEntity = locationEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없습니다. id=" + id));
        locationEntityRepository.delete(locationEntity);
    }
}
