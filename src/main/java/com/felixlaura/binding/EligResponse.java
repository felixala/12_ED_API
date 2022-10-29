package com.felixlaura.binding;

import lombok.Data;
import java.time.LocalDate;

/**
 * This binding class represents the UI Eligibility Determination. See screenshot elibilityDetermination.jpg
 */

@Data
public class EligResponse {

    private String planName;

    private String planStatus;

    private LocalDate planStartDate;

    private LocalDate planEndDate;

    private Double benefitAmt;

    private String denialReason;

}
