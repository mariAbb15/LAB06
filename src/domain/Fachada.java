package domain;

import java.io.*;

/**
 * Facade class that provides file operations for the Valley simulation.
 * Handles serialization, deserialization, import and export operations.
 */
public class Fachada {

    private Valley valley;

    public Fachada() {
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
        throw new ValleyException("Opcion new en construccion.");
    }

    /**
     * Opens a valley from a binary .dat file (deserialization).
     */
    public void open00(File archivo) throws ValleyException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            valley = (Valley) in.readObject();
        } catch (Exception e) {
            throw new ValleyException("Error al abrir el archivo " + archivo.getName());
        }
    }

    /**
     * Opens a valley from a binary .dat file with detailed error handling.
     * 
     * @param archivo File to read the valley from (.dat extension)
     * @throws ValleyException with specific error messages
     */
    public void open01(File archivo) throws ValleyException {
        ObjectInputStream input = null;

        try {
            input = new ObjectInputStream(new FileInputStream(archivo));
            this.valley = (Valley) input.readObject();

        } catch (FileNotFoundException e) {
            throw new ValleyException("Error: El archivo '" + archivo.getName() +
                    "' no fue encontrado. Verifique que el archivo existe.");

        } catch (InvalidClassException e) {
            throw new ValleyException("Error: El archivo '" + archivo.getName() +
                    "' fue creado con una version incompatible del programa.");

        } catch (StreamCorruptedException e) {
            throw new ValleyException("Error: El archivo '" + archivo.getName() +
                    "' esta corrupto o no es un archivo valley valido.");

        } catch (ClassNotFoundException e) {
            throw new ValleyException("Error: El archivo '" + archivo.getName() +
                    "' no contiene un valley valido. Faltan clases necesarias.");

        } catch (IOException e) {
            throw new ValleyException("Error al leer el archivo '" + archivo.getName() +
                    "': " + e.getMessage() + ". Verifique permisos de lectura.");

        } catch (Exception e) {
            throw new ValleyException("Error inesperado al abrir '" + archivo.getName() +
                    "': " + e.getMessage());

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // Ignore close exception
                }
            }
        }
    }

    /**
     * Opens a valley from a binary .dat file with detailed error handling.
     * 
     * @param archivo File to read the valley from (.dat extension)
     * @throws ValleyException with specific error messages
     */
    public void open(File archivo) throws ValleyException {
        ObjectInputStream input = null;

        try {
            input = new ObjectInputStream(new FileInputStream(archivo));
            this.valley = (Valley) input.readObject();

        } catch (FileNotFoundException e) {
            throw new ValleyException("Error: El archivo '" + archivo.getName() +
                    "' no fue encontrado. Verifique que el archivo existe.");

        } catch (InvalidClassException e) {
            throw new ValleyException("Error: El archivo '" + archivo.getName() +
                    "' fue creado con una version incompatible del programa.");

        } catch (StreamCorruptedException e) {
            throw new ValleyException("Error: El archivo '" + archivo.getName() +
                    "' esta corrupto o no es un archivo valley valido.");

        } catch (ClassNotFoundException e) {
            throw new ValleyException("Error: El archivo '" + archivo.getName() +
                    "' no contiene un valley valido. Faltan clases necesarias.");

        } catch (IOException e) {
            throw new ValleyException("Error al leer el archivo '" + archivo.getName() +
                    "': " + e.getMessage() + ". Verifique permisos de lectura.");

        } catch (Exception e) {
            throw new ValleyException("Error inesperado al abrir '" + archivo.getName() +
                    "': " + e.getMessage());

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // Ignore close exception
                }
            }
        }
    }

    /**
     * Saves the current valley to a binary .dat file with detailed error handling.
     * 
     * @param archivo File to save the valley to (.dat extension)
     * @throws ValleyException with specific error messages
     */
    public void save(File archivo) throws ValleyException {
        ObjectOutputStream output = null;

        try {
            output = new ObjectOutputStream(new FileOutputStream(archivo));
            output.writeObject(this.valley);
            output.flush();

        } catch (FileNotFoundException e) {
            throw new ValleyException("Error: No se puede crear el archivo '" +
                    archivo.getName() + "'. Verifique permisos de escritura en el directorio.");

        } catch (NotSerializableException e) {
            throw new ValleyException("Error: El valley contiene objetos que no pueden ser guardados. " +
                    "Clase problematica: " + e.getMessage());

        } catch (IOException e) {
            String message = e.getMessage();
            if (message != null && message.contains("space")) {
                throw new ValleyException("Error: No hay suficiente espacio en disco para guardar '" +
                        archivo.getName() + "'.");
            } else {
                throw new ValleyException("Error al guardar el archivo '" + archivo.getName() +
                        "': " + message + ". Verifique espacio en disco y permisos.");
            }

        } catch (Exception e) {
            throw new ValleyException("Error inesperado al guardar '" + archivo.getName() +
                    "': " + e.getMessage());

        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    // Ignore close exception
                }
            }
        }
    }

    /**
     * Saves the current valley to a binary .dat file (serialization).
     */
    public void save00(File archivo) throws ValleyException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(valley);
        } catch (Exception e) {
            throw new ValleyException("Error al guardar el archivo " + archivo.getName());
        }
    }

    /**
     * Saves the current valley to a binary .dat file with detailed error handling.
     * 
     * @param archivo File to save the valley to (.dat extension)
     * @throws ValleyException with specific error messages
     */
    public void save01(File archivo) throws ValleyException {
        ObjectOutputStream output = null;

        try {
            output = new ObjectOutputStream(new FileOutputStream(archivo));
            output.writeObject(this.valley);
            output.flush();

        } catch (FileNotFoundException e) {
            throw new ValleyException("Error: No se puede crear el archivo '" +
                    archivo.getName() + "'. Verifique permisos de escritura en el directorio.");

        } catch (NotSerializableException e) {
            throw new ValleyException("Error: El valley contiene objetos que no pueden ser guardados. " +
                    "Clase problematica: " + e.getMessage());

        } catch (IOException e) {
            String message = e.getMessage();
            if (message != null && message.contains("space")) {
                throw new ValleyException("Error: No hay suficiente espacio en disco para guardar '" +
                        archivo.getName() + "'.");
            } else {
                throw new ValleyException("Error al guardar el archivo '" + archivo.getName() +
                        "': " + message + ". Verifique espacio en disco y permisos.");
            }

        } catch (Exception e) {
            throw new ValleyException("Error inesperado al guardar '" + archivo.getName() +
                    "': " + e.getMessage());

        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    // Ignore close exception
                }
            }
        }
    }

    /**
     * Imports valley data from a text file.
     */
    public void importFile00(File archivo) throws ValleyException {
        throw new ValleyException("Opcion import en construccion. Archivo " + archivo.getName());
    }

    /**
     * Imports valley data from a text file.
     * Expected format: TypeName row column (one unit per line)
     */
    public void importFile01(File archivo) throws ValleyException {
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
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * Imports valley data from a text file with detailed error handling.
     * Expected format: TypeName row column (one unit per line)
     * 
     * @param archivo File to import valley data from (.txt extension)
     * @throws ValleyException with specific error details including line numbers
     */
    public void importFile(File archivo) throws ValleyException {
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
            int unitsCreated = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                // Skip empty lines and comments
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\s+");

                if (parts.length != 3) {
                    throw new ValleyException("Error en linea " + lineNumber +
                            ": Formato invalido '" + line + "'. " +
                            "Se esperaba: TipoAnimal fila columna (3 valores separados por espacios).");
                }

                String className = parts[0].trim();

                // Validate numeric coordinates
                int row, col;
                try {
                    row = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    throw new ValleyException("Error en linea " + lineNumber +
                            ": La fila '" + parts[1] + "' no es un numero valido. " +
                            "Debe ser un numero entero entre 0 y " + (valley.getSize() - 1) + ".");
                }

                try {
                    col = Integer.parseInt(parts[2].trim());
                } catch (NumberFormatException e) {
                    throw new ValleyException("Error en linea " + lineNumber +
                            ": La columna '" + parts[2] + "' no es un numero valido. " +
                            "Debe ser un numero entero entre 0 y " + (valley.getSize() - 1) + ".");
                }

                // Validate range
                if (row < 0 || row >= valley.getSize()) {
                    throw new ValleyException("Error en linea " + lineNumber +
                            ": La fila " + row + " esta fuera de rango. " +
                            "Debe estar entre 0 y " + (valley.getSize() - 1) + ".");
                }

                if (col < 0 || col >= valley.getSize()) {
                    throw new ValleyException("Error en linea " + lineNumber +
                            ": La columna " + col + " esta fuera de rango. " +
                            "Debe estar entre 0 y " + (valley.getSize() - 1) + ".");
                }

                // Check if position is already occupied
                if (!valley.isEmpty(row, col)) {
                    throw new ValleyException("Error en linea " + lineNumber +
                            ": La posicion (" + row + ", " + col + ") ya esta ocupada. " +
                            "No se pueden colocar dos unidades en la misma posicion.");
                }

                createUnit(className, row, col, lineNumber);
                unitsCreated++;
            }

            if (unitsCreated == 0) {
                throw new ValleyException("Advertencia: El archivo '" + archivo.getName() +
                        "' esta vacio o no contiene unidades validas.");
            }

        } catch (FileNotFoundException e) {
            throw new ValleyException("Error: El archivo '" + archivo.getName() +
                    "' no fue encontrado. Verifique la ruta y el nombre del archivo.");

        } catch (IOException e) {
            throw new ValleyException("Error al leer el archivo '" + archivo.getName() +
                    "': " + e.getMessage() + ". El archivo puede estar bloqueado o corrupto.");

        } catch (ValleyException e) {
            // Re-throw ValleyException without modification
            throw e;

        } catch (Exception e) {
            throw new ValleyException("Error inesperado al importar '" + archivo.getName() +
                    "': " + e.getMessage());

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Ignore close exception
                }
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
                String suggestion = "";
                String lowerClassName = className.toLowerCase();

                if (lowerClassName.contains("wolf")) {
                    suggestion = " ¿Quiso decir 'Wolf' o 'WolfLotVol'?";
                } else if (lowerClassName.contains("sheep") || lowerClassName.contains("oveja")) {
                    suggestion = " ¿Quiso decir 'Sheep' o 'SheepLotVol'?";
                } else if (lowerClassName.contains("fox") || lowerClassName.contains("zorro")) {
                    suggestion = " ¿Quiso decir 'Fox'?";
                } else if (lowerClassName.contains("hay") || lowerClassName.contains("heno")) {
                    suggestion = " ¿Quiso decir 'Hay'?";
                } else if (lowerClassName.contains("grass") || lowerClassName.contains("pasto")) {
                    suggestion = " ¿Quiso decir 'Grass'?";
                }

                throw new ValleyException("Error en linea " + lineNumber +
                        ": Tipo de unidad desconocido '" + className + "'." + suggestion +
                        " Tipos validos: Wolf, Sheep, Fox, Hay, Grass, WolfLotVol, SheepLotVol.");
        }
    }

    /**
     * Exports valley data to a text file.
     */
    public void export00(File archivo) throws ValleyException {
        throw new ValleyException("Opcion export en construccion. Archivo " + archivo.getName());
    }

    /**
     * Exports valley data to a text file.
     */
    public void export01(File archivo) throws ValleyException {
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

    /**
     * Exports valley data to a text file with detailed error handling.
     * Format: TypeName row column (one unit per line)
     * 
     * @param archivo File to export valley data to (.txt extension)
     * @throws ValleyException with specific error messages
     */
    public void export(File archivo) throws ValleyException {
        PrintWriter writer = null;

        try {
            writer = new PrintWriter(new FileWriter(archivo));

            int size = valley.getSize();
            int unitsExported = 0;

            // Add header comment
            writer.println("# Valley Export File");
            writer.println("# Format: UnitType Row Column");
            writer.println("# Generated: " + new java.util.Date());
            writer.println();

            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    Unit unit = valley.getUnit(row, col);

                    if (unit != null) {
                        String className = unit.getClass().getSimpleName();
                        writer.println(className + " " + row + " " + col);
                        unitsExported++;
                    }
                }
            }

            // Add footer comment
            writer.println();
            writer.println("# Total units exported: " + unitsExported);

            writer.flush();

            if (unitsExported == 0) {
                throw new ValleyException("Advertencia: El valley esta vacio. " +
                        "No se exportaron unidades al archivo '" + archivo.getName() + "'.");
            }

        } catch (FileNotFoundException e) {
            throw new ValleyException("Error: No se puede crear el archivo '" +
                    archivo.getName() + "'. Verifique permisos de escritura.");

        } catch (IOException e) {
            throw new ValleyException("Error al exportar al archivo '" + archivo.getName() +
                    "': " + e.getMessage() + ". Verifique espacio en disco y permisos.");

        } catch (ValleyException e) {
            // Re-throw ValleyException
            throw e;

        } catch (Exception e) {
            throw new ValleyException("Error inesperado al exportar '" + archivo.getName() +
                    "': " + e.getMessage());

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public Valley getValley() {
        return valley;
    }
}
