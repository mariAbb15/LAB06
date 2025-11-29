package domain;

import java.io.*;

/**
 * Facade class that provides file operations for the Valley simulation.
 * Handles serialization, deserialization, import and export operations.
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
     * Opens a valley from a binary .dat file (deserialization).
     */
    public void open(File archivo) throws ValleyException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            valley = (Valley) in.readObject();
        } catch (Exception e) {
            throw new ValleyException("Error al abrir el archivo " + archivo.getName());
        }
    }

    /**
     * Saves the current valley to a binary .dat file (serialization).
     */
    public void save(File archivo) throws ValleyException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(valley);
        } catch (Exception e) {
            throw new ValleyException("Error al guardar el archivo " + archivo.getName());
        }
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

    /**
     * Imports valley data from a text file.
     * Expected format: TypeName row column (one unit per line)
     */
    public void importFile00(File archivo) throws ValleyException {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(archivo));
            valley = new Valley();

            // Clear the valley first
            for (int r = 0; r < valley.getSize(); r++) {
                for (int c = 0; c < valley.getSize(); c++) {
                    valley.setUnit(r, c, null);
                }
            }

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s+");

                if (parts.length != 3) {
                    throw new ValleyException("Error en linea " + lineNumber +
                        ": Formato invalido. Se esperaba: TipoAnimal fila columna");
                }

                String className = parts[0].trim();
                int row = Integer.parseInt(parts[1].trim());
                int col = Integer.parseInt(parts[2].trim());

                if (row < 0 || row >= valley.getSize() || col < 0 || col >= valley.getSize()) {
                    throw new ValleyException("Error en linea " + lineNumber +
                        ": Posicion fuera de rango (" + row + ", " + col + ")");
                }

                createUnit(className, row, col, lineNumber);
            }

        } catch (FileNotFoundException e) {
            throw new ValleyException("Error: El archivo " + archivo.getName() +
                " no fue encontrado.");
        } catch (IOException e) {
            throw new ValleyException("Error al leer el archivo " + archivo.getName() +
                ": " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new ValleyException("Error: Las coordenadas deben ser numeros enteros.");
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException e) {}
            }
        }
    }

    /**
     * Creates a unit instance.
     */
    private void createUnit(String className, int row, int col, int lineNumber)
            throws ValleyException {

        Unit unit = null;

        switch (className) {
            case "Wolf":
                unit = new Wolf(valley, row, col);
                break;
            case "Sheep":
                unit = new Sheep(valley, row, col);
                break;
            case "Fox":
                unit = new Fox(valley, row, col);
                break;
            case "Hay":
                unit = new Hay(row, col, valley);
                break;
            case "Grass":
                unit = new Grass(row, col, valley);
                break;
            case "WolfLotVol":
                unit = new WolfLotVol(valley, row, col);
                break;
            case "SheepLotVol":
                unit = new SheepLotVol(valley, row, col);
                break;
            default:
                throw new ValleyException("Error en linea " + lineNumber +
                    ": Tipo de unidad desconocido '" + className + "'");
        }
    }

    /**
     * Exports valley data to a text file.
     */
    public void export00(File archivo) throws ValleyException {
        PrintWriter writer = null;

        try {
            writer = new PrintWriter(new FileWriter(archivo));

            int size = valley.getSize();

            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    Unit unit = valley.getUnit(row, col);

                    if (unit != null) {
                        String className = unit.getClass().getSimpleName();
                        writer.println(className + " " + row + " " + col);
                    }
                }
            }

            writer.flush();

        } catch (IOException e) {
            throw new ValleyException("Error al exportar el archivo " + archivo.getName() +
                ": " + e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public Valley getValley(){
        return valley;
    }
    }
