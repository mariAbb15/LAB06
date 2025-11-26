package domain;


import java.io.File;

/**
 * Facade class that provides file operations for the Valley simulation.
 * Handles serialization, deserialization, import and export operations.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class Fachada {

    private Valley valley;

    public Fachada(){
        this.valley = new Valley();
    }

    /**
     * Creates a new valley instance.
     */
    public void newValley() throws ValleyException {
        this.valley = new Valley();
    }

    /**
     * New valley.
     */
    public void valleyNew() throws ValleyException {
        throw new ValleyException("Opcion new en construccion." );
    }

    /**
     * Opens a valley from a binary file.
     */
    public void open(File archivo) throws ValleyException {
        throw new ValleyException("Opcion open en construccion. Archivo " + archivo.getName());
    }

    /**
     * Saves the current valley to a binary file.
     */
    public void save(File archivo) throws ValleyException {
        throw new ValleyException("Opcion save en construccion. Archivo " + archivo.getName());
    }

    /**
     * Imports valley data from a text file.
     */
    public void importFile(File archivo) throws ValleyException {
        throw new ValleyException("Opcion import en construccion. Archivo " + archivo.getName());
    }

    /**
     * Exports valley data to a text file.
     */
    public void export(File archivo) throws ValleyException {
        throw new ValleyException("Opcion export en construccion. Archivo " + archivo.getName());
    }
    
    public Valley getValley(){
        return valley;
    }
}

