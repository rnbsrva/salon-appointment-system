package com.akerke.salonservice.elastic.service.impl;

import com.akerke.salonservice.elastic.SalonWrapper;
import com.akerke.salonservice.elastic.service.SalonElasticService;
import com.akerke.salonservice.mapper.SalonMapper;
import com.akerke.salonservice.payload.SalonSearchRequest;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.akerke.salonservice.constants.AppConstants.SALON_INDEX;

@Service
@RequiredArgsConstructor
public class SalonElasticServiceImpl implements SalonElasticService {

    private final ElasticsearchOperations elastic;
    private final SalonMapper salonMapper;

    @Override
    public List<SalonWrapper> search(
            SalonSearchRequest searchDetails,
            int from,
            int size
    ) {
        var queryBuilder = QueryBuilders.boolQuery();

        if (searchDetails.name() != null) {
            queryBuilder.must(getFuzzinessQuery("name", searchDetails.name()));
        }

        if (searchDetails.treatmentToFind() != null) {
            queryBuilder.must(getFuzzinessQuery("treatmentToFind", searchDetails.treatmentToFind()));
        }

        if (searchDetails.addressDetails() != null) {
            if (searchDetails.addressDetails().state() != null) {
                queryBuilder.must(getFuzzinessQuery("state", searchDetails.addressDetails().state()));
            }
            if (searchDetails.addressDetails().city() != null) {
                queryBuilder.must(getFuzzinessQuery("city", searchDetails.addressDetails().city()));
            }
            if (searchDetails.addressDetails().street() != null) {
                queryBuilder.must(getFuzzinessQuery("street", searchDetails.addressDetails().street()));
            }
            if (searchDetails.addressDetails().houseNumber() != null) {
                queryBuilder.must(getFuzzinessQuery("house_number", searchDetails.addressDetails().houseNumber().toString()));
            }
        }

        var searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(PageRequest.of(from, size))
                .build();

        SearchHits<SalonWrapper> searchHits =
                elastic.search(
                        searchQuery,
                        SalonWrapper.class,
                        IndexCoordinates.of(SALON_INDEX)
                );

        return salonMapper.toListWrapperFromHit(searchHits);
    }

    private static MatchQueryBuilder getFuzzinessQuery(String fieldName, String fieldValue) {
        return QueryBuilders
                .matchQuery(fieldName, fieldValue)
                .fuzziness("AUTO");
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

        var suggestions = new ArrayList<String>();

        searchSuggestions.getSearchHits().forEach(searchHit -> {
            suggestions.add(searchHit.getContent().getName());
        });

        return suggestions;
    }
}
