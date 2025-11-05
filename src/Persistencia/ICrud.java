package Persistencia;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import Excepciones.RegistroNotFoundExeption;

public interface ICrud<T> {
	
	public void save(T t) throws SQLException;

	public T get(Integer id) throws IOException, ClassNotFoundException, SQLException, RegistroNotFoundExeption;

	public T get(String sql, String value) throws IOException, ClassNotFoundException, SQLException;

	public List<T> get() throws SQLException, IOException, ClassNotFoundException;

	public void update(T t) throws SQLException;

	public void delete(T t) throws SQLException;

}