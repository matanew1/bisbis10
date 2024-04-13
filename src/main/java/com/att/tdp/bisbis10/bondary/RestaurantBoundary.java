package com.att.tdp.bisbis10.bondary;

import com.att.tdp.bisbis10.data.RestaurantEntity;
import java.util.List;

public class RestaurantBoundary {

    private String name;
    private Boolean isKosher;
    private List<String> cuisines;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsKosher() {
        return isKosher;
    }

    public void setIsKosher(Boolean kosher) {
        isKosher = kosher;
    }

    public List<String> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<String> cuisines) {

        this.cuisines = cuisines;
    }

    public RestaurantEntity toEntity() {
        RestaurantEntity entity = new RestaurantEntity();
        entity.setName(name);
        entity.setIsKosher(isKosher);
        entity.setCuisines(cuisines);
        return entity;
    }
}
