package org.tutorial_ide.fly_away.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateManyFlightsRequestDto {
    @NotNull
    @Valid
    private List<FlightRequestDto> inputs;

    public List<FlightRequestDto> getInputs() {
        return inputs;
    }

    public void setInputs(List<FlightRequestDto> inputs) {
        this.inputs = inputs;
    }
}