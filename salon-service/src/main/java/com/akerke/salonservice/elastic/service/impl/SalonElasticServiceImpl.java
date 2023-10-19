package com.akerke.salonservice.elastic.service.impl;

import com.akerke.salonservice.elastic.SalonWrapper;
import com.akerke.salonservice.elastic.service.SalonElasticService;
import com.akerke.salonservice.mapper.SalonMapper;
import com.akerke.salonservice.payload.SalonSearchRequest;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonElasticServiceImpl implements SalonElasticService {

    private final static RequestOptions DEFAULT = RequestOptions.DEFAULT;
    private final SalonMapper salonMapper;
    private final RestHighLevelClient elastic;

    @Override
    public List<SalonWrapper> search(
            SalonSearchRequest searchDetails,
            int from,
            int size
    ) {
        var searchRequest = new SearchRequest();
        var searchSourceBuilder = new SearchSourceBuilder();



        return null;
    }
}
