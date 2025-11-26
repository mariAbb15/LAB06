package domain;

public class ValleyException extends Exception{

    public static final String PRUEBA_VALLEY = "Prueba";
    public static final String EN_CONSTRUCCION = "Se esta construyendo";

    public ValleyException(String message){
        super(message);
    }

}