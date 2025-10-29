package Persistencia;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import Excepciones.RegistroNotFoundExeption;

public interface ICrud<T> {
	
	public void save(T t) throws IOException, SQLException;

	public T get(Integer id) throws IOException, ClassNotFoundException, SQLException;

	public T get(String column, String value) throws IOException, ClassNotFoundException, SQLException, RegistroNotFoundExeption;

	public List<T> get() throws SQLException, IOException, ClassNotFoundException;

	public void update(T t);

	public void delete(T t);

}