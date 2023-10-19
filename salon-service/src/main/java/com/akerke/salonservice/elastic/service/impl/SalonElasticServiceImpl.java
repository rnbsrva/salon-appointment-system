package com.akerke.salonservice.elastic.service.impl;

import com.akerke.salonservice.elastic.SalonWrapper;
import com.akerke.salonservice.elastic.service.SalonElasticService;
import com.akerke.salonservice.mapper.SalonMapper;
import com.akerke.salonservice.payload.SalonSearchRequest;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.akerke.salonservice.constants.AppConstants.SALON_INDEX;

@Service
@RequiredArgsConstructor
public class SalonElasticServiceImpl implements SalonElasticService {

    private final static RequestOptions DEFAULT = RequestOptions.DEFAULT;
    private final SalonMapper salonMapper;
    private final ElasticsearchOperations elastic;

    @Override
    public List<SalonWrapper> search(
            SalonSearchRequest searchDetails,
            int from,
            int size
    ) {


        return null;
    }

    @Override
    public List<String> fetchSuggestions(String query) {
        var queryBuilder = QueryBuilders
                .wildcardQuery("name", query + "*");

        var searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withPageable(PageRequest.of(0, 10))
                .build();

        SearchHits<SalonWrapper> searchSuggestions =
                elastic.search(searchQuery,
                        SalonWrapper.class,
                        IndexCoordinates.of(SALON_INDEX)
                );

        List<String> suggestions = new ArrayList<>();

        searchSuggestions.getSearchHits().forEach(searchHit -> {
            suggestions.add(searchHit.getContent().getName());
        });

        return suggestions;
    }
}
