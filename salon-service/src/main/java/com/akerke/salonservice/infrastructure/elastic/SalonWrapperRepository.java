package com.akerke.salonservice.infrastructure.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SalonWrapperRepository extends ElasticsearchRepository<SalonWrapper,Long> {
}
