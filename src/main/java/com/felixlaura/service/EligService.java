package com.felixlaura.service;

import com.felixlaura.binding.EligResponse;

public interface EligService {

    public EligResponse determineEligibility(Long caseNum);
}
