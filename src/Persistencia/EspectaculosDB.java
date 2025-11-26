package Persistencia;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entidades.Espectaculo;
import Entidades.Estadio;
import Excepciones.RegistroNotFoundExeption;

public class EspectaculosDB extends BaseH2 implements ICrud<Espectaculo> {

    @Override
    public void save(Espectaculo espectaculo) throws SQLException {
        String sql = "INSERT INTO espectaculos (name, estadio_id, timestamp) VALUES (?,?,?)";
        try {
            updateDeleteInsertSql(sql, espectaculo.getName(), espectaculo.getEstadio().getId(), espectaculo.getTimestamp());
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Espectaculo get(Integer id) throws IOException, ClassNotFoundException, SQLException, RegistroNotFoundExeption {
        String sql = "SELECT id, name, estadio_id, timestamp FROM espectaculos WHERE id = ?";
        ResultSet rs = selectSql(sql, id);

        Espectaculo esp = null;

        if (rs.first()) {
            // Obtengo id del estadio
            Integer estadioId = rs.getInt(3);
            EstadioDB estadioDB = new EstadioDB();
            Estadio estadio = estadioDB.get(estadioId);

            esp = new Espectaculo(rs.getInt(1), rs.getString(2), estadio, rs.getTimestamp(4));
        } else {
            throw new RegistroNotFoundExeption();
        }

        cerrarConexion();
        return esp;
    }

    @Override
    public List<Espectaculo> getList() throws SQLException, IOException, ClassNotFoundException, RegistroNotFoundExeption {
        String sql = "SELECT id, name, estadio_id, timestamp FROM espectaculos";
        ResultSet rs = super.selectSql(sql);
        List<Espectaculo> espectaculos = new ArrayList<>();
        while (rs.next()) {

            EstadioDB estadioDB = new EstadioDB();
            Estadio estadio = estadioDB.get(rs.getInt(3));

            if (estadio != null) {
                espectaculos.add(new Espectaculo(rs.getInt(1), rs.getString(2), estadio, rs.getTimestamp(4)));
            }
        }
        cerrarConexion();
        return espectaculos;
    }

    @Override
    public void update(Espectaculo esp) throws SQLException {
        String sql = "UPDATE espectaculos SET name=?, estadio_id=?, timestamp=? WHERE id = ?";
        try {
            Integer updated = updateDeleteInsertSql(sql, esp.getName(), esp.getEstadio().getId(), esp.getTimestamp(), esp.getId());
            if (updated != 1) {
              throw new SQLException();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void delete(Espectaculo esp) throws SQLException {
        String sql = "DELETE FROM espectaculos WHERE id = ?";
        try {
            Integer updated = updateDeleteInsertSql(sql, esp.getId());
            if (updated != 1) {
              throw new SQLException();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Espectaculo get(String sql, Object... params) throws IOException, ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Espectaculo> getList(String sql, Object... params) throws SQLException, IOException, ClassNotFoundException, RegistroNotFoundExeption {

        ResultSet rs = super.selectSql(sql, params);
        List<Espectaculo> espectaculos = new ArrayList<>();

        while (rs.next()) {

            EstadioDB estadioDB = new EstadioDB();
            Estadio estadio = estadioDB.get(rs.getInt(3));

            if (estadio != null) {
                espectaculos.add(new Espectaculo(rs.getInt(1), rs.getString(2), estadio, rs.getTimestamp(4)));
            }

        }
        cerrarConexion();
        return espectaculos;
    }
}