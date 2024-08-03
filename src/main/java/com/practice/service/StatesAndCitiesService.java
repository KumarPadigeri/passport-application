package com.practice.service;

import org.springframework.stereotype.Service;

import java.util.*;

/*
 * @Created 7/28/24
 * @Project passport-application
 * @User Kumar Padigeri
 */
@Service
public class StatesAndCitiesService {

    public static Map<String, List<String>> stateCityMap;

    static {
        stateCityMap = new HashMap<>();
        stateCityMap.put("Andhra Pradesh", Collections.singletonList("Visakhapatnam"));
        stateCityMap.put("Arunachal Pradesh", Collections.singletonList("Itanagar"));
        stateCityMap.put("Assam", Collections.singletonList("Guwahati"));
        stateCityMap.put("Bihar", Collections.singletonList("Patna"));
        stateCityMap.put("Chhattisgarh", Collections.singletonList("Raipur"));
        stateCityMap.put("Goa", Collections.singletonList("Panaji"));
        stateCityMap.put("Gujarat", Collections.singletonList("Ahmedabad"));
        stateCityMap.put("Haryana", Collections.singletonList("Gurgaon"));
        stateCityMap.put("Himachal Pradesh", Collections.singletonList("Shimla"));
        stateCityMap.put("Jharkhand", Collections.singletonList("Ranchi"));
        stateCityMap.put("Karnataka", Collections.singletonList("Bangalore"));
        stateCityMap.put("Kerala", Collections.singletonList("Thiruvananthapuram"));
        stateCityMap.put("Madhya Pradesh", Collections.singletonList("Bhopal"));
        stateCityMap.put("Maharashtra", Collections.singletonList("Mumbai"));
        stateCityMap.put("Manipur", Collections.singletonList("Imphal"));
        stateCityMap.put("Meghalaya", Collections.singletonList("Shillong"));
        stateCityMap.put("Mizoram", Collections.singletonList("Aizawl"));
        stateCityMap.put("Nagaland", Collections.singletonList("Kohima"));
        stateCityMap.put("Odisha", Collections.singletonList("Bhubaneswar"));
        stateCityMap.put("Punjab", Collections.singletonList("Ludhiana"));
        stateCityMap.put("Rajasthan", Collections.singletonList("Jaipur"));
        stateCityMap.put("Sikkim", Collections.singletonList("Gangtok"));
        stateCityMap.put("Tamil Nadu", Collections.singletonList("Chennai"));
        stateCityMap.put("Telangana", Collections.singletonList("Hyderabad"));
        stateCityMap.put("Tripura", Collections.singletonList("Agartala"));
        stateCityMap.put("Uttar Pradesh", Collections.singletonList("Lucknow"));
        stateCityMap.put("Uttarakhand", Collections.singletonList("Dehradun"));
        stateCityMap.put("West Bengal", Collections.singletonList("Kolkata"));
    }


    public List<String> getAllStates() {
        return new ArrayList<>(stateCityMap.keySet());
    }

    public List<String> getCitiesByState(String state) {
        return stateCityMap.getOrDefault(state, Collections.emptyList());
    }
}
