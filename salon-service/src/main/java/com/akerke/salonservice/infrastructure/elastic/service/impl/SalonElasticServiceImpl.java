package com.akerke.salonservice.infrastructure.elastic.service.impl;

import com.akerke.salonservice.domain.entity.Salon;
import com.akerke.salonservice.domain.repository.SalonRepository;
import com.akerke.salonservice.infrastructure.elastic.SalonWrapper;
import com.akerke.salonservice.infrastructure.elastic.SalonWrapperRepository;
import com.akerke.salonservice.infrastructure.elastic.service.SalonElasticService;
import com.akerke.salonservice.domain.mapper.SalonMapper;
import com.akerke.salonservice.common.payload.SalonSearchRequest;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.akerke.salonservice.common.constants.AppConstants.SALON_INDEX;

@Service
@RequiredArgsConstructor
public class SalonElasticServiceImpl implements SalonElasticService {

    private final RestHighLevelClient elastic;
    private final SalonMapper salonMapper;
    private final SalonWrapperRepository salonRepository;

    @Override
    public List<SalonWrapper> search(
            SalonSearchRequest searchDetails,
            int from,
            int size
    ) {
        var searchRequest = new SearchRequest();
        var searchSourceBuilder = new SearchSourceBuilder();
        var queryBuilder = QueryBuilders.boolQuery();

        if (searchDetails.name() != null) {
            queryBuilder.must(getFuzzinessQuery("name", searchDetails.name()));
        }

        if (searchDetails.treatmentToFind() != null) {
            queryBuilder.must(getFuzzinessQuery("treatments", searchDetails.treatmentToFind()));
        }

        if (searchDetails.addressDetails() != null) {
            if (searchDetails.addressDetails().state() != null) {
                queryBuilder.should(getFuzzinessQuery("state", searchDetails.addressDetails().state()));
            }
            if (searchDetails.addressDetails().city() != null) {
                queryBuilder.should(getFuzzinessQuery("city", searchDetails.addressDetails().city()));
            }
            if (searchDetails.addressDetails().street() != null) {
                queryBuilder.should(getFuzzinessQuery("street", searchDetails.addressDetails().street()));
            }
            if (searchDetails.addressDetails().houseNumber() != null) {
                queryBuilder.should(getFuzzinessQuery("house_number", String.valueOf(searchDetails.addressDetails().houseNumber())));
            }
        }

        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);

        searchRequest.source(searchSourceBuilder);

        try {
            var searchResponse = elastic.search(searchRequest, RequestOptions.DEFAULT);
            return salonMapper.toListWrapperFromHit(searchResponse.getHits().getHits());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static MatchQueryBuilder getFuzzinessQuery(String fieldName, String fieldValue) {
        return QueryBuilders
                .matchQuery(fieldName, fieldValue);
    }

    @Override
    public List<String> fetchSuggestions(String query) {
        var queryBuilder = QueryBuilders
                .matchQuery("name", query);

        var searchRequest = new SearchRequest();
        var searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchSuggestionsResponse =
                null;
        try {
            searchSuggestionsResponse = elastic.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var suggestions = new ArrayList<String>();

        Arrays.stream(searchSuggestionsResponse.getHits().getHits()).forEach(searchHit -> {
            suggestions.add(searchHit.getSourceAsMap().get("name").toString());
        });

        return suggestions;
    }

    @Override
    public void save(Salon salon) {
        var salonWrapper = salonMapper.toWrapper(salon);
        salonRepository.save(salonWrapper);
    }

    @Override
    public void delete(Salon salon) {
        var salonWrapper = salonMapper.toWrapper(salon);
        salonRepository.delete(salonWrapper);
    }
}
