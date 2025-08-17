import dao.VolDAO;
import dao.PassagerDAO;
import dao.ReservationDAO;
import model.Vol;
import model.Passager;
import model.Reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Main {

    static VolDAO volDAO = new VolDAO();
    static PassagerDAO passagerDAO = new PassagerDAO();
    static ReservationDAO reservationDAO = new ReservationDAO();

    static DefaultTableModel volModel;
    static JTable volsTable;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Systeme de gestions de stock de l'agence Willy_Voyage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        JLabel agencyLabel = new JLabel("Gestions des reservations de vol", SwingConstants.CENTER);
        agencyLabel.setFont(new Font("Arial", Font.BOLD, 26));
        agencyLabel.setForeground(new Color(0, 102, 204));
        frame.add(agencyLabel, BorderLayout.NORTH);

        // Tableau central
        volModel = new DefaultTableModel(new String[]{"Numéro", "Destination", "Date", "Départ", "Arrivée", "Capacité"}, 0);
        volsTable = new JTable(volModel);
        JScrollPane scrollPane = new JScrollPane(volsTable);
        frame.add(scrollPane, BorderLayout.CENTER);
        refreshVolsTable();

        // Barre de menu
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(70, 130, 180));
        menuBar.setForeground(Color.WHITE);

        // --- Menu Vols ---
        JMenu volsMenu = new JMenu("Vols");
        volsMenu.setForeground(Color.WHITE);
        JMenuItem ajouterVolItem = new JMenuItem("Ajouter un vol");
        JMenuItem rechercherVolItem = new JMenuItem("Rechercher un vol");
        JMenuItem supprimerVolItem = new JMenuItem("Supprimer un vol");
        volsMenu.add(ajouterVolItem);
        volsMenu.add(rechercherVolItem);
        volsMenu.add(supprimerVolItem);

        // --- Menu Passagers ---
        JMenu passagersMenu = new JMenu("Passagers");
        passagersMenu.setForeground(Color.WHITE);
        JMenuItem ajouterPassagerItem = new JMenuItem("Ajouter un passager");
        passagersMenu.add(ajouterPassagerItem);

        // --- Menu Réservations ---
        JMenu reservationsMenu = new JMenu("Réservations");
        reservationsMenu.setForeground(Color.WHITE);
        JMenuItem reserverVolItem = new JMenuItem("Réserver un vol");
        JMenuItem annulerReservationItem = new JMenuItem("Annuler réservation");
        reservationsMenu.add(reserverVolItem);
        reservationsMenu.add(annulerReservationItem);

        // --- Menu Statistiques ---
        JMenu statsMenu = new JMenu("Statistiques");
        statsMenu.setForeground(Color.WHITE);
        JMenuItem topDestItem = new JMenuItem("Top Destinations");
        statsMenu.add(topDestItem);

        menuBar.add(volsMenu);
        menuBar.add(passagersMenu);
        menuBar.add(reservationsMenu);
        menuBar.add(statsMenu);
        frame.setJMenuBar(menuBar);

        // --- Actions Vols ---
        ajouterVolItem.addActionListener(e -> ajouterVolPopup(frame));
        rechercherVolItem.addActionListener(e -> rechercherVolPopup(frame));
        supprimerVolItem.addActionListener(e -> supprimerVolPopup(frame));

        // --- Actions Passagers ---
        ajouterPassagerItem.addActionListener(e -> ajouterPassagerPopup(frame));

        // --- Actions Réservations ---
        reserverVolItem.addActionListener(e -> reserverVolPopup(frame));
        annulerReservationItem.addActionListener(e -> annulerReservationPopup(frame));

        // --- Actions Statistiques ---
        topDestItem.addActionListener(e -> topDestinationsPopup(frame));

        frame.setVisible(true);
    }

    // ========================= POPUPS ===========================

    private static void ajouterVolPopup(JFrame frame) {
        JTextField numField = new JTextField();
        JTextField destField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField hdField = new JTextField();
        JTextField haField = new JTextField();
        JTextField capField = new JTextField();

        Object[] message = {
                "Numéro du vol:", numField,
                "Destination:", destField,
                "Date (YYYY-MM-DD):", dateField,
                "Heure départ (HH:mm):", hdField,
                "Heure arrivée (HH:mm):", haField,
                "Capacité:", capField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Ajouter Vol", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Vol v = new Vol(
                    numField.getText(),
                    destField.getText(),
                    dateField.getText(),
                    hdField.getText(),
                    haField.getText(),
                    Integer.parseInt(capField.getText())
            );
            if (volDAO.insert(v)) {
                JOptionPane.showMessageDialog(frame, "Vol ajouté !");
                refreshVolsTable();
            } else {
                JOptionPane.showMessageDialog(frame, "Erreur ajout vol");
            }
        }
    }

    private static void rechercherVolPopup(JFrame frame) {
        String numero = JOptionPane.showInputDialog(frame, "Numéro du vol à rechercher:");
        if (numero != null && !numero.isEmpty()) {
            Vol v = volDAO.findByNumero(numero);
            if (v != null) JOptionPane.showMessageDialog(frame, v.toString());
            else JOptionPane.showMessageDialog(frame, "Vol introuvable !");
        }
    }

    private static void supprimerVolPopup(JFrame frame) {
        String numero = JOptionPane.showInputDialog(frame, "Numéro du vol à supprimer:");
        if (numero != null && !numero.isEmpty()) {
            if (volDAO.delete(numero)) {
                JOptionPane.showMessageDialog(frame, "Vol supprimé !");
                refreshVolsTable();
            } else {
                JOptionPane.showMessageDialog(frame, "Erreur suppression ou vol inexistant !");
            }
        }
    }

    private static void ajouterPassagerPopup(JFrame frame) {
        JTextField nomField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField passeportField = new JTextField();

        Object[] message = {
                "Nom complet:", nomField,
                "Email:", emailField,
                "Numéro passeport:", passeportField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Ajouter Passager", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Passager p = new Passager(nomField.getText(), emailField.getText(), passeportField.getText());
            int id = passagerDAO.insert(p);
            if (id > 0) JOptionPane.showMessageDialog(frame, "Passager ajouté avec id: " + id);
            else JOptionPane.showMessageDialog(frame, "Erreur ajout passager");
        }
    }

    private static void reserverVolPopup(JFrame frame) {
        String num = JOptionPane.showInputDialog(frame, "Numéro du vol à réserver:");
        if (num == null || num.isEmpty()) return;
        Vol v = volDAO.findByNumero(num);
        if (v == null) { JOptionPane.showMessageDialog(frame, "Vol introuvable"); return; }

        String pidStr = JOptionPane.showInputDialog(frame, "ID du passager (0 pour créer nouveau):");
        if (pidStr == null || pidStr.isEmpty()) return;
        int pid = Integer.parseInt(pidStr);
        int passagerId = pid;

        if (pid == 0) {
            ajouterPassagerPopup(frame);
            List<Passager> ps = passagerDAO.listAll();
            if (ps.isEmpty()) { JOptionPane.showMessageDialog(frame, "Aucun passager après création"); return; }
            passagerId = ps.get(ps.size()-1).getId();
        } else {
            Passager p = passagerDAO.findById(passagerId);
            if (p == null) { JOptionPane.showMessageDialog(frame, "Passager introuvable"); return; }
        }

        int confirmes = volDAO.countReservations(num);
        if (confirmes >= v.getCapacite()) { JOptionPane.showMessageDialog(frame, "Vol complet !"); return; }

        Reservation r = new Reservation(num, passagerId, "CONFIRME", java.time.LocalDate.now().toString());
        int id = reservationDAO.insert(r);
        if (id > 0) JOptionPane.showMessageDialog(frame, "Réservation confirmée, id: " + id);
        else JOptionPane.showMessageDialog(frame, "Erreur lors de la réservation");
    }

    private static void annulerReservationPopup(JFrame frame) {
        String idStr = JOptionPane.showInputDialog(frame, "ID réservation à annuler:");
        if (idStr == null || idStr.isEmpty()) return;
        int id = Integer.parseInt(idStr);
        boolean ok = reservationDAO.updateStatut(id, "ANNULE");
        if (ok) JOptionPane.showMessageDialog(frame, "Réservation annulée");
        else JOptionPane.showMessageDialog(frame, "Erreur annulation ou id inexistant");
    }

    private static void topDestinationsPopup(JFrame frame) {
        List<Vol> vols = volDAO.listAll();
        vols.sort((a,b) -> Integer.compare(volDAO.countReservations(b.getNumeroVol()), volDAO.countReservations(a.getNumeroVol())));

        StringBuilder sb = new StringBuilder("Top Destinations:\n");
        for (int i=0; i<Math.min(5, vols.size()); i++) {
            Vol v = vols.get(i);
            sb.append(i+1).append(". ").append(v.getDestination())
              .append(" | Vol: ").append(v.getNumeroVol())
              .append(" | Réservations: ").append(volDAO.countReservations(v.getNumeroVol()))
              .append("\n");
        }

        JOptionPane.showMessageDialog(frame, sb.toString());
    }

    // ========================= UTILITAIRES =========================

    private static void refreshVolsTable() {
        volModel.setRowCount(0);
        for (Vol v : volDAO.listAll()) {
            volModel.addRow(new Object[]{v.getNumeroVol(), v.getDestination(), v.getDateDepart(),
                    v.getHeureDepart(), v.getHeureArrivee(), v.getCapacite()});
        }
    }
}
