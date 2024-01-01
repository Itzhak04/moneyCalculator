package software.ulpgc.moneycalculator.swing;

import software.ulpgc.moneycalculator.*;
import software.ulpgc.moneycalculator.fixerws.FixerCurrencyLoader;
import software.ulpgc.moneycalculator.fixerws.FixerExchangeRateLoader;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwingMain extends JFrame {
    private final Map<String,Command> commands = new HashMap<>();
    private MoneyDisplay moneyDisplay;
    private MoneyDialog moneyDialog;
    private CurrencyDialog currencyDialog;

    public static void main(String[] args) {
        SwingMain main = new SwingMain();
        List<Currency> currencies = new FixerCurrencyLoader().load();
        Command command = new ExchangeMoneyCommand(
                main.moneyDialog().define(currencies),
                main.currencyDialog().define(currencies),
                new FixerExchangeRateLoader(),
                main.moneyDisplay());
        main.add("exchange money", command);
        main.setVisible(true);
    }

    public SwingMain() throws HeadlessException {
        this.setTitle("Money calculator");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.getContentPane().add(settingPanel());
        this.getContentPane().add(calculatePanel());

    }

    private JPanel calculatePanel() {
        JPanel result = new JPanel();
        result.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        result.setLayout(new FlowLayout());
        result.add(createMoneyDisplay());
        result.add(toolbar());
        return result;
    }

    private JPanel settingPanel() {
        JPanel result = new JPanel();
        result.setBorder(BorderFactory.createEmptyBorder(100, 10, 10, 10));
        result.setLayout(new FlowLayout());
        result.add(createMoneyDialog());
        result.add(createCurrencyDialog());
        return result;
    }

    private Component toolbar() {
        JButton button = new JButton("calculate");
        button.addActionListener(e -> {
            try {
                commands.get("exchange money").execute();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return button;
    }

    private Component createMoneyDisplay() {
        SwingMoneyDisplay display = new SwingMoneyDisplay();
        this.moneyDisplay = display;
        return display;
    }

    private Component createCurrencyDialog() {
        SwingCurrencyDialog dialog = new SwingCurrencyDialog();
        this.currencyDialog = dialog;
        return dialog;
    }

    private Component createMoneyDialog() {
        SwingMoneyDialog dialog = new SwingMoneyDialog();
        this.moneyDialog = dialog;
        return dialog;
    }

    private void add(String name, Command command) {
        commands.put(name, command);
    }

    private MoneyDisplay moneyDisplay() {
        return moneyDisplay;
    }

    private CurrencyDialog currencyDialog() {
        return currencyDialog;
    }

    private MoneyDialog moneyDialog() {
        return moneyDialog;
    }
}
