package com.rest.vehicle.util;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.*;

public class SortAndFilterUtil {
    private static final String OPERATOR_DELIMITER = ":";
    private static final String PARAM_DELIMITER = "&";
    private static final String VALUE_DELIMITER = "=";
    private static final String GTE = "gte";
    private static final String LTE = "lte";
    private static final String EQUALS = "equals";

    public static void addQueryCriteria(Query query, String sort) {
        String[] params = getSortAndFilterParams(sort);
        if(params == null){
            return;
        }
        createFilterQuery(query, new Criteria(), params);
        addSortQuery(query, params);
    }

    private static void addSortQuery(Query query, String[] params) {
        Arrays.stream(params).forEach(param -> {
            if (isSortParam(param)) {
                sortQuery(query, param);
            }
        });
    }

    private static String[] getSortAndFilterParams(String sort) {
        return sort.contains(PARAM_DELIMITER) ? sort.split(PARAM_DELIMITER) : new String[]{sort};
    }

    private static void createFilterQuery(Query query, Criteria criteria, String[] params) {
        Map<String, String> filterParams = getFilterParams(params);

        for (Map.Entry<String, String> filterEntry : filterParams.entrySet()) {
            query.addCriteria(createFilterCriteria(filterEntry.getKey(), filterEntry.getValue()));
        }
    }

    private static Map<String, String> getFilterParams(String[] params) {
        Map<String, String> tmpFilterParams = new HashMap<>();
        Arrays.stream(params).forEach(param -> {
            if (!isSortParam(param) && param.length() > 0) {
                addFilterParam(tmpFilterParams, param);
            }
        });
        return tmpFilterParams;
    }

    private static void addFilterParam(Map<String, String> tmpFilterParams, String param) {
        String[] keyAndValue = param.split(VALUE_DELIMITER);
        tmpFilterParams.put(keyAndValue[0], keyAndValue[1]);
    }

    private static Criteria createFilterCriteria(String key, String value) {
        String filterValue;
        String operator = "";
        String[] expression = value.split(OPERATOR_DELIMITER);
        if(expression.length > 1){
            operator = expression[0];
            filterValue = expression[1];
        } else {
            filterValue = expression[0];
        }

        switch (operator) {
            case LTE:
                return Criteria.where(key).lte(Double.valueOf(filterValue));
            case GTE:
                return Criteria.where(key).gte(Double.valueOf(filterValue));
            case EQUALS:
                return Criteria.where(key).is(Double.valueOf(filterValue));
            default:
                return Criteria.where(key).regex(".*" + filterValue + ".*", "i");
        }
    }

    private static boolean isSortParam(String param) {
        return param.toUpperCase().contains(Sort.Direction.ASC.toString())
                || param.toUpperCase().contains(Sort.Direction.DESC.toString());
    }

    private static void sortQuery(Query query, String param) {
        String key = param.split(OPERATOR_DELIMITER)[0];
        String direction = param.split(OPERATOR_DELIMITER)[1];

        if (direction.equalsIgnoreCase(Sort.Direction.ASC.toString())) {
            query.with(Sort.by(Sort.Direction.ASC, key));
        } else {
            query.with(Sort.by(Sort.Direction.DESC, key));
        }
    }
}
