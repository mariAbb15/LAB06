package presentation;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import domain.*;

/**
 * Main window with menu bar for Valley simulation.
 * Provides file operations: new, open, save, import, export, exit.
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
    private ValleyGUI valleyGUI;
    
    public VentanaValley() {
        super("Valley");
        fachada = new Fachada();
        prepareElementsMenu();
        prepareActionsMenu();
        
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                fachada.importFile(file);

                if (valleyGUI != null) {
                    valleyGUI.dispose();
                }

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
                fachada.export(file);
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
}