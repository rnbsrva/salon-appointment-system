package com.akerke.salonservice.common.specs;

import com.akerke.salonservice.common.payload.SalonSearch;
import com.akerke.salonservice.domain.entity.Address;
import com.akerke.salonservice.domain.entity.Salon;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SalonSpecifications {

    public static Specification<Salon> nameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Salon> stateLike(String state) {
        return (root, query, criteriaBuilder) -> {
            Join<Salon, Address> addressJoin = root.join("address", JoinType.INNER);
            return criteriaBuilder.like(addressJoin.get("state"), "%" + state + "%");
        };
    }

    public static Specification<Salon> streetLike(String street) {
        return (root, query, criteriaBuilder) -> {
            Join<Salon, Address> addressJoin = root.join("address", JoinType.INNER);
            return criteriaBuilder.like(addressJoin.get("street"), "%" + street + "%");
        };
    }

    public static Specification<Salon> cityLike(String city) {
        return (root, query, criteriaBuilder) -> {
            Join<Salon, Address> addressJoin = root.join("address", JoinType.INNER);
            return criteriaBuilder.like(addressJoin.get("city"), "%" + city + "%");
        };
    }


    public static Specification<Salon> buildSpecification(SalonSearch salonSearch) {
        return ((root, query, criteriaBuilder) -> {

            query.distinct(true);

            if (salonSearch.name() != null) {
                query.where(nameLike(salonSearch.name()).toPredicate(root, query, criteriaBuilder));
            }

            if (salonSearch.city() != null) {
                query.where(cityLike(salonSearch.city()).toPredicate(root, query, criteriaBuilder));
            }

            if (salonSearch.state() != null) {
                query.where(stateLike(salonSearch.state()).toPredicate(root, query, criteriaBuilder));
            }
            if (salonSearch.street() != null) {
                query.where(streetLike(salonSearch.street()).toPredicate(root, query, criteriaBuilder));
            }

            return query.getRestriction();
        });
    }

}
