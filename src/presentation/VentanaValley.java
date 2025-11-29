package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import domain.*;

/**
 * Main window with menu bar and valley visualization for Valley simulation.
 * Integrates menu operations with graphical display of the valley.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class VentanaValley extends JFrame {
    
    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenuItem itemNuevo;
    private JMenuItem itemAbrir;
    private JMenuItem itemGuardar;
    private JMenuItem itemImportar;
    private JMenuItem itemExportar;
    private JMenuItem itemSalir;
    
    private Fachada fachada;
    private PhotoValleyPanel photoValley;
    private JButton ticTacButton;
    
    public static final int SIDE = 20;
    
    public VentanaValley() {
        super("Valley Simulation");
        fachada = new Fachada();
        prepareElementsMenu();
        prepareElementsView();
        prepareActionsMenu();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Prepares menu bar elements.
     */
    private void prepareElementsMenu() {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menuArchivo = new JMenu("Archivo");
        menuBar.add(menuArchivo);

        itemNuevo = new JMenuItem("Nuevo");
        menuArchivo.add(itemNuevo);
        
        menuArchivo.addSeparator();

        itemAbrir = new JMenuItem("Abrir");
        menuArchivo.add(itemAbrir);

        itemGuardar = new JMenuItem("Guardar como");
        menuArchivo.add(itemGuardar);
        
        menuArchivo.addSeparator();

        itemImportar = new JMenuItem("Importar");
        menuArchivo.add(itemImportar);

        itemExportar = new JMenuItem("Exportar como");
        menuArchivo.add(itemExportar);
        
        menuArchivo.addSeparator();

        itemSalir = new JMenuItem("Salir");
        menuArchivo.add(itemSalir);
    }

    /**
     * Prepares the valley visualization panel and control button.
     */
    private void prepareElementsView() {
        photoValley = new PhotoValleyPanel(this);
        ticTacButton = new JButton("Tic-tac");
        
        setLayout(new BorderLayout());
        add(photoValley, BorderLayout.CENTER);
        add(ticTacButton, BorderLayout.SOUTH);
        
        ticTacButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fachada.getValley().ticTac();
                photoValley.repaint();
            }
        });
    }

    /**
     * Configures action listeners for menu items.
     */
    private void prepareActionsMenu() {
        itemNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionNew();
            }
        });
        
        itemAbrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionOpen();
            }
        });
        
        itemGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionSave();
            }
        });
        
        itemImportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionImport();
            }
        });
        
        itemExportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionExport();
            }
        });
        
        itemSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionExit();
            }
        });
    }

    /**
     * Creates a new valley simulation.
     */
    private void optionNew() {
        try {
            fachada.newValley();
            photoValley.repaint();
            JOptionPane.showMessageDialog(this, "Nuevo valle creado exitosamente");
        } catch (ValleyException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens a valley from a .dat file.
     */
    private void optionOpen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Valley files (*.dat)", "dat"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                fachada.open(file);
                photoValley.repaint();
                JOptionPane.showMessageDialog(this, "Archivo abierto correctamente");
            } catch (ValleyException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Saves current valley to a .dat file.
     */
    private void optionSave() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Valley files (*.dat)", "dat"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().endsWith(".dat")) {
                    file = new File(file.getAbsolutePath() + ".dat");
                }
                fachada.save(file);
                JOptionPane.showMessageDialog(this, "Archivo guardado correctamente");
            } catch (ValleyException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Imports valley data from a .txt file.
     */
    private void optionImport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Text files (*.txt)", "txt"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                fachada.importFile00(file);
                photoValley.repaint();
                JOptionPane.showMessageDialog(this, "Archivo importado correctamente");
            } catch (ValleyException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Exports valley data to a .txt file.
     */
    private void optionExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Text files (*.txt)", "txt"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }
                fachada.export00(file);
                JOptionPane.showMessageDialog(this, "Archivo exportado correctamente");
            } catch (ValleyException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Exits the application.
     */
    private void optionExit() {
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Esta seguro que desea salir?",
            "Salir",
            JOptionPane.YES_NO_OPTION
        );
        
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    /**
     * Returns the current valley from the facade.
     * Used by PhotoValleyPanel to render the valley.
     */
    public Valley getValley() {
        return fachada.getValley();
    }
}

/**
 * The PhotoValleyPanel class is responsible for drawing the valley 
 * within VentanaValley.
 * It extends JPanel and paints a grid where each unit is represented
 * according to its type (shape, color, and energy if applicable).
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
class PhotoValleyPanel extends JPanel {

    private VentanaValley ventana;

    /**
     * Constructs a new PhotoValleyPanel linked to the given VentanaValley.
     *
     * @param ventana Main window that holds the valley model.
     */
    public PhotoValleyPanel(VentanaValley ventana) {
        this.ventana = ventana;
        setBackground(Color.white);
        int size = ventana.getValley().getSize();
        setPreferredSize(new Dimension(
            VentanaValley.SIDE * size + 10, 
            VentanaValley.SIDE * size + 10
        ));
    }

    /**
     * Draws the grid representing the valley and all units in it.
     * Automatically called by Swing when the panel needs to be redrawn.
     *
     * @param g Graphics object used for drawing operations.
     */
    @Override
    public void paintComponent(Graphics g) {
        Valley theValley = ventana.getValley();
        super.paintComponent(g);

        int size = theValley.getSize();

        // Draw grid lines
        for (int c = 0; c <= size; c++) {
            g.drawLine(c * VentanaValley.SIDE, 0, 
                      c * VentanaValley.SIDE, size * VentanaValley.SIDE);
        }
        for (int f = 0; f <= size; f++) {
            g.drawLine(0, f * VentanaValley.SIDE, 
                      size * VentanaValley.SIDE, f * VentanaValley.SIDE);
        }

        // Draw units
        for (int f = 0; f < size; f++) {
            for (int c = 0; c < size; c++) {
                Unit unit = theValley.getUnit(f, c);
                
                if (unit != null) {
                    g.setColor(unit.getColor());

                    if (unit.shape() == Unit.SQUARE) {
                        g.fillRoundRect(
                            VentanaValley.SIDE * c + 1, 
                            VentanaValley.SIDE * f + 1, 
                            VentanaValley.SIDE - 2, 
                            VentanaValley.SIDE - 2, 
                            2, 2
                        );
                    } else {
                        g.fillOval(
                            VentanaValley.SIDE * c + 1, 
                            VentanaValley.SIDE * f + 1, 
                            VentanaValley.SIDE - 2, 
                            VentanaValley.SIDE - 2
                        );
                    }

                    // Show energy indicator for animals
                    if (unit.isAnimal()) {
                        g.setColor(Color.red);
                        Animal animal = (Animal) unit;
                        if (animal.getEnergy() >= 50) {
                            g.drawString("u", 
                                VentanaValley.SIDE * c + 6, 
                                VentanaValley.SIDE * f + 15);
                        } else {
                            g.drawString("~", 
                                VentanaValley.SIDE * c + 6, 
                                VentanaValley.SIDE * f + 17);
                        }
                    }
                }
            }
        }
    }
}