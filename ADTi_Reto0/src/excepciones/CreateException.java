package excepciones;

import java.sql.SQLException;

public class CreateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreateException() {
		super();

	}

	public CreateException(String mensaje) {
		super(mensaje);
		
	}

    public CreateException(String error, SQLException e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public CreateException(String string, IllegalArgumentException e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}