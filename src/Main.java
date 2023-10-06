import model.InputData;
import model.Overpayment;
import model.RateType;
import service.*;

import java.math.BigDecimal;
import java.util.Map;

public class Main {


    public static void main(String[] args) {
        InputData inputData = new InputData()
                .withAmount(new BigDecimal(298000)) //Kwota kredytu
                .withMonthDuration(BigDecimal.valueOf(360)) //Liczba miesięcy
                .withOverpaymentSchema(Map.of(
                        4,BigDecimal.valueOf(10000),
                        8,BigDecimal.valueOf(10000),
                        15,BigDecimal.valueOf(10000),
                        28,BigDecimal.valueOf(10000)
                ))
                .withRateType(RateType.CONSTANT)  //Rata (Stała / Malejąca)
                .withOverpaymentReduceWay(Overpayment.REDUCE_RATE); // Ewentualna strategia nadpłaty


        PrintingService printingService = new PrintingServiceImpl();
        RateCalculationService rateCalculationService = new RateCalculationServiceImpl(
                new TimePointServiceImpl(),
                new AmountsCalculationServiceImpl(
                        new ConstantAmountsCalculationServiceImpl(),
                        new DecreasingAmountsCalculationServiceImpl()
                ),
                new OverpaymentCalculationServiceImpl(),
                new ResidualCalculationServiceImpl(),
                new ReferenceCalculationServiceImpl()

        );

        MortageCalculationService mortageCalculationService = new MortgageCalculationServiceImpl(
                printingService,
                rateCalculationService,
                SummaryServiceFactory.create()
        );
        mortageCalculationService.calculate(inputData);

    }
}