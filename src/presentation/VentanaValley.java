package presentation;

import javax.swing.*;
import domain.ValleyException;

public class VentanaValley extends JFrame{
     private JMenuBar menuBar;
     private JMenu menuArchivo;
    
    public VentanaValley(){
    super("Valley");
    prepareElementsMenu();

    setSize(800,600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
}

private void prepareElementsMenu(){
    
    menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    JMenu menuArchivo = new JMenu("Archivo"); //Menú principal
    menuBar.add(menuArchivo);

    JMenuItem itemNuevo = new JMenuItem("Nuevo");
    menuArchivo.add(itemNuevo); //Opcion  nuevo

    menuArchivo.addSeparator();

    JMenuItem itemAbrir = new JMenuItem("Abrir");
    menuArchivo.add(itemAbrir);

    JMenuItem itemGuardar = new JMenuItem("Guardar como");
    menuArchivo.add(itemGuardar);

    menuArchivo.addSeparator();

    JMenuItem itemImportar = new JMenuItem("Importar");
    menuArchivo.add(itemImportar);

    JMenuItem itemExportar = new JMenuItem("Exportar Como");
    menuArchivo.add(itemExportar);

    JMenuItem itemSalir = new JMenuItem("Salir");
    menuArchivo.add(itemSalir);

}
}