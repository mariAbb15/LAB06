package presentation;

import domain.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * The ValleyGUI class represents the main graphical user interface (GUI)
 * for the valley simulation. It allows users to visualize the valley,
 * display the units (animals and resources), and interact with the simulation
 * through a simple button that advances one step ("Tic-tac").
 *
 * This class follows the Model-View-Controller (MVC) structure:
 * - Model: {@link Valley}
 * - View: {@link PhotoValley}
 * - Controller: this class
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class ValleyGUI extends JFrame {

    // Tamaño en píxeles de cada celda de la cuadrícula.
    public static final int SIDE = 20;

    // Tamaño total del valle (número de celdas por lado).
    public final int SIZE;

    // Botón que ejecuta un paso en la simulación (un "tic-tac").
    private JButton ticTacButton;

    // Panel donde se mostrará gráficamente el valle.
    private PhotoValley photo;

    // Objeto principal que contiene la lógica del valle y sus unidades.
    private Valley theValley;

    /**
     * Constructs the ValleyGUI object, initializing the model,
     * window size, elements, and event actions.
     */
    private ValleyGUI() {
        theValley = new Valley(); // Se crea el modelo del valle
        SIZE = theValley.getSize(); // Se obtiene el tamaño del valle desde el modelo
        prepareElements(); // Configura los elementos gráficos
        prepareActions();  // Asocia los eventos del botón y la ventana
    }

    /**
     * Prepares all graphical elements for the window,
     * including layout, components, and window properties.
     */
    private void prepareElements() {
        setTitle("Schelling Valley"); // Título de la ventana
        photo = new PhotoValley(this); // Crea el panel que dibujará el valle
        ticTacButton = new JButton("Tic-tac"); // Crea el botón de control

        setLayout(new BorderLayout());
        add(photo, BorderLayout.NORTH);  // Agrega el panel superior
        add(ticTacButton, BorderLayout.SOUTH); // Agrega el botón inferior

        // Ajusta el tamaño de la ventana según el tamaño del valle
        setSize(new Dimension(SIDE * SIZE + 15, SIDE * SIZE + 72));
        setResizable(false); // Se evita el cambio de tamaño manual
        photo.repaint();     // Redibuja el valle al iniciar
        
    }

    /**
     * Prepares the actions for GUI events, such as button clicks and window closing.
     */
    private void prepareActions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Cierra el programa al cerrar la ventana

        // Asigna la acción al botón Tic-tac
        ticTacButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ticTacButtonAction(); // Llama al método que ejecuta un paso de simulación
                }
            }
        );
    }

    /**
     * Executes one step in the valley simulation when the user clicks the "Tic-tac" button.
     * This method updates the model and refreshes the graphical representation.
     */
    private void ticTacButtonAction() {
        theValley.ticTac(); // Avanza un ciclo en la simulación
        photo.repaint();    // Redibuja el valle con los cambios
    }

    /**
     * Returns the current Valley model object.
     *
     * @return the Valley instance used by this GUI.
     */
    public Valley gettheValley() {
        return theValley;
    }

    /**
     * Main method that starts the program and displays the GUI window.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        ValleyGUI cg = new ValleyGUI();
        cg.setVisible(true); // Muestra la ventana en pantalla
    }
}

/**
 * The PhotoValley class is responsible for drawing the valley and its contents.
 * It extends JPanel and paints a grid where each unit is represented
 * according to its type (shape, color, and energy if applicable).
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
class PhotoValley extends JPanel {

    /** Referencia a la interfaz principal para acceder al modelo y sus constantes. */
    private ValleyGUI gui;

    /**
     * Constructs a new PhotoValley panel linked to the given ValleyGUI.
     *
     * @param gui Main graphical interface that holds the valley model.
     */
    public PhotoValley(ValleyGUI gui) {
        this.gui = gui; // Se guarda la referencia a la GUI principal
        setBackground(Color.white); // Fondo blanco para el valle
        setPreferredSize(new Dimension(gui.SIDE * gui.SIZE + 10, gui.SIDE * gui.SIZE + 10)); // Tamaño del panel
    }

    /**
     * Draws the grid representing the valley and all units in it.
     * Automatically called by Swing when the panel needs to be redrawn.
     *
     * @param g Graphics object used for drawing operations.
     */
    public void paintComponent(Graphics g) {
        Valley theValley = gui.gettheValley(); // Obtiene el modelo del valle actual
        super.paintComponent(g);

        // --- Dibuja las líneas de la cuadrícula ---
        for (int c = 0; c <= theValley.getSize(); c++) {
            g.drawLine(c * gui.SIDE, 0, c * gui.SIDE, theValley.getSize() * gui.SIDE);
        }
        for (int f = 0; f <= theValley.getSize(); f++) {
            g.drawLine(0, f * gui.SIDE, theValley.getSize() * gui.SIDE, f * gui.SIDE);
        }

        // --- Recorre todas las posiciones para dibujar las unidades ---
        for (int f = 0; f < theValley.getSize(); f++) {
            for (int c = 0; c < theValley.getSize(); c++) {

                if (theValley.getUnit(f, c) != null) {
                    // Color base de la unidad
                    g.setColor(theValley.getUnit(f, c).getColor());

                    // Forma: cuadrado o círculo
                    if (theValley.getUnit(f, c).shape() == Unit.SQUARE) {
                        g.fillRoundRect(gui.SIDE * c + 1, gui.SIDE * f + 1, gui.SIDE - 2, gui.SIDE - 2, 2, 2);
                    } else {
                        g.fillOval(gui.SIDE * c + 1, gui.SIDE * f + 1, gui.SIDE - 2, gui.SIDE - 2);
                    }

                    // Si la unidad es un animal, muestra su estado energético
                    if (theValley.getUnit(f, c).isAnimal()) {
                        g.setColor(Color.red);
                        if (((Animal) theValley.getUnit(f, c)).getEnergy() >= 50) {
                            g.drawString("u", gui.SIDE * c + 6, gui.SIDE * f + 15); // Energía alta
                        } else {
                            g.drawString("~", gui.SIDE * c + 6, gui.SIDE * f + 17); // Energía baja
                        }
                    }
                }
            }
        }
    }
}
