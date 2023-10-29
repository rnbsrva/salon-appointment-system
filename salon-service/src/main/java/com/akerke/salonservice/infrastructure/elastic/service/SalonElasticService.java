package com.akerke.salonservice.infrastructure.elastic.service;

import com.akerke.salonservice.infrastructure.elastic.SalonWrapper;
import com.akerke.salonservice.common.payload.SalonSearchRequest;

import java.util.List;

public interface SalonElasticService {

    List<SalonWrapper> search(SalonSearchRequest searchRequest, int from, int size);

    List<String> fetchSuggestions(String query);
}
