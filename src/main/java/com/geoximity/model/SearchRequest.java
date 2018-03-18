package com.geoximity.model;

import java.util.List;

public class SearchRequest {

public int exactLimit;
public int limit;
public int pageSize;
public String searchKey;
public String type;

public List<SearchSelect> searchSelects;
public MapCriteria mapCriteria;

}
