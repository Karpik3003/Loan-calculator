package service;

import model.InputData;
import model.MortgageResidual;
import model.Rate;
import model.RateAmounts;

public interface ResidalCalculactionService {

    MortgageResidual calculate(RateAmounts rateAmounts, InputData inputData);

    MortgageResidual calculate(RateAmounts rateAmounts, Rate previousRate);
}
