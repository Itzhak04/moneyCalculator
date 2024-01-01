package software.ulpgc.moneycalculator;

import java.io.IOException;

public interface ExchangeRateLoader {
    ExchangeRate load(Currency from, Currency to) throws IOException;
}
