package software.ulpgc.moneycalculator;

import java.io.IOException;

public interface Command {
    void execute() throws IOException;
}
