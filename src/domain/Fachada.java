package domain;


import java.io.File;
import domain.ValleyException;

public class Fachada {

    public void open(File archivo) throws ValleyException {
        throw new ValleyException("Opción open en construcción. Archivo " + archivo.getName());
    }

    public void save(File archivo) throws ValleyException {
        throw new ValleyException("Opción save en construcción. Archivo " + archivo.getName());
    }

    public void importFile(File archivo) throws ValleyException {
        throw new ValleyException("Opción import en construcción. Archivo " + archivo.getName());
    }

    public void export(File archivo) throws ValleyException {
        throw new ValleyException("Opción export en construcción. Archivo " + archivo.getName());
    }
}

