package com.pulse.comparison.model.dto;

import com.pulse.comparison.model.ComparisonStatus;

public class ComparisonResultDTO {

    private String keyName;
    private String valueInEnv1;
    private String valueInEnv2;
    private ComparisonStatus status;

    public ComparisonResultDTO() {}

    public ComparisonResultDTO(String keyName, String valueInEnv1, String valueInEnv2, ComparisonStatus status) {
        this.keyName = keyName;
        this.valueInEnv1 = valueInEnv1;
        this.valueInEnv2 = valueInEnv2;
        this.status = status;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getValueInEnv1() {
        return valueInEnv1;
    }

    public void setValueInEnv1(String valueInEnv1) {
        this.valueInEnv1 = valueInEnv1;
    }

    public String getValueInEnv2() {
        return valueInEnv2;
    }

    public void setValueInEnv2(String valueInEnv2) {
        this.valueInEnv2 = valueInEnv2;
    }

    public ComparisonStatus getStatus() {
        return status;
    }

    public void setStatus(ComparisonStatus status) {
        this.status = status;
    }
}
