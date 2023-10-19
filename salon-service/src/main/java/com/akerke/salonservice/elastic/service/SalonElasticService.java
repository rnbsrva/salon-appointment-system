package com.akerke.salonservice.elastic.service;

import com.akerke.salonservice.elastic.SalonWrapper;
import com.akerke.salonservice.payload.SalonSearchRequest;

import java.util.List;

public interface SalonElasticService {

    List<SalonWrapper> search(SalonSearchRequest searchRequest, int from, int size);

    List<String> fetchSuggestions(String query);
}
