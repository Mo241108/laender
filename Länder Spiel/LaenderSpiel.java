import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.HashSet;
import java.util.Set;

/**
 * Ein einfaches Java Swing-Programm, das abfragt, wie viele Länder der Nutzer
 * auswendig aufzählen kann. Nur echte Länder werden akzeptiert.
 */
public class LaenderSpiel extends JFrame {
    private JTextField eingabeFeld;
    private JButton hinzufuegenButton;
    private JButton beendenButton;
    private JLabel statusLabel;

    // Speichert eindeutige Einträge in der Reihenfolge des Hinzufügens
    private Set<String> laenderListe;
    // Set mit allen gültigen Ländernamen in Kleinbuchstaben
    private Set<String> validLaender;

    public LaenderSpiel() {
        super("Länder-Auszählspiel");
        laenderListe = new LinkedHashSet<>();
        validLaender = new HashSet<>();
        initValidLaender();
        initUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Initialisiert das Set mit allen echten Ländern aus Locale.
     */
    private void initValidLaender() {
        for (String code : Locale.getISOCountries()) {
            Locale locale = new Locale("", code);
            String country = locale.getDisplayCountry();
            validLaender.add(country.toLowerCase());
        }
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // Eingabefeld
        eingabeFeld = new JTextField();
        add(eingabeFeld, BorderLayout.CENTER);

        // Wenn Enter gedrückt wird, wird der Hinzufügen-Button ausgelöst
        eingabeFeld.addActionListener(e -> hinzufuegenButton.doClick());

        // Statuslabel für Hinweise
        statusLabel = new JLabel("Gib ein echtes Land ein und drücke Hinzufügen.");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        // Panel für Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        hinzufuegenButton = new JButton("Hinzufügen");
        beendenButton = new JButton("Fertig");
        buttonPanel.add(hinzufuegenButton);
        buttonPanel.add(beendenButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // ActionListener für 'Hinzufügen'
        hinzufuegenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String eingabe = eingabeFeld.getText().trim();
                if (eingabe.isEmpty()) {
                    statusLabel.setText("Bitte gib zuerst ein Land ein!");
                } else if (!validLaender.contains(eingabe.toLowerCase())) {
                    statusLabel.setText("'" + eingabe + "' ist kein gültiges Land.");
                } else if (laenderListe.contains(eingabe.toLowerCase())) {
                    statusLabel.setText("Das Land '" + eingabe + "' wurde schon genannt.");
                } else {
                    laenderListe.add(eingabe.toLowerCase());
                    statusLabel.setText("'" + eingabe + "' hinzugefügt. Insgesamt: " + laenderListe.size());
                }
                eingabeFeld.setText("");
                eingabeFeld.requestFocus();
            }
        });

        // ActionListener für 'Fertig'
        beendenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showZusammenfassung();
            }
        });
    }

    private void showZusammenfassung() {
        StringBuilder message = new StringBuilder();
        message.append("Du hast insgesamt ")
               .append(laenderListe.size())
               .append(" verschiedene Länder genannt:\n\n");
        int count = 1;
        for (String land : laenderListe) {
            message.append(count++)
                   .append(". ")
                   .append(capitalize(land))
                   .append("\n");
        }

        JOptionPane.showMessageDialog(this, message.toString(), "Zusammenfassung", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    /**
     * Hilfsmethode, um den ersten Buchstaben eines Landes großzuschreiben.
     */
    private String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LaenderSpiel();
            }
        });
    }
}