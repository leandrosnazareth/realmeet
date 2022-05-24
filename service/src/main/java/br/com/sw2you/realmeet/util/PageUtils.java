package br.com.sw2you.realmeet.util;

import static java.util.Objects.isNull;

import br.com.sw2you.realmeet.exception.InvalidOrderByFieldException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PageUtils {

    private PageUtils() {
    }

    public static Pageable newPageable(
            Integer page,
            Integer limit,
            int maxLimit,
            String orderBy,
            List<String> validSortableFields
    ) {
        int definedPage = isNull(page) ? 0 : page; //se for nulo page zero
        int definedLimit = isNull(limit) ? maxLimit : Math.min(limit, maxLimit);// se for nulo define o valor máximo
        Sort definedSort = parseOrderByFields(orderBy, validSortableFields);
        return PageRequest.of(definedPage, definedLimit, definedSort);
    }

    private static Sort parseOrderByFields(String orderBy, List<String> validSortableFields) {
        if (isNull(validSortableFields) || validSortableFields.isEmpty()) {
            throw new IllegalArgumentException("No valid sortable fields were defined");
        }

        if (StringUtils.isBlank(orderBy)) {
            return Sort.unsorted();
        }

        return Sort.by(
                Stream
                        .of(orderBy.split(","))
                        .map(
                                f -> {
                                    String fieldName;
                                    Sort.Order order;

                                    if (f.startsWith("-")) {
                                        fieldName = f.substring(1);
                                        order = Sort.Order.desc(fieldName);
                                    } else {
                                        fieldName = f;
                                        order = Sort.Order.asc(f);
                                    }

                                    if (!validSortableFields.contains(fieldName)) {
                                        throw new InvalidOrderByFieldException();
                                    }

                                    return order;
                                }
                        )
                        .collect(Collectors.toList())
        );
    }
}
