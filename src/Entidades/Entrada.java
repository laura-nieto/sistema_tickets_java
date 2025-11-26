package Entidades;

import java.sql.Timestamp;

public class Entrada {
    private Integer id;
    private String buyerdocument;
    private String buyerName;
    private Espectaculo espectaculo;
    private Ubicacion ubicacion;
    private Usuario vendedor;
    private Timestamp soldAt;


    public Entrada(String doc, String name, Espectaculo espectaculo, Usuario vendedor, Timestamp vendido, Ubicacion ubicacion) {
        this.buyerdocument = doc;
        this.buyerName = name;
        this.espectaculo = espectaculo;
        this.vendedor = vendedor;
        this.soldAt = vendido;
        this.ubicacion = ubicacion;
    }

    public Entrada(Integer id, String doc, String name, Espectaculo espectaculo, Usuario vendedor, Timestamp vendido, Ubicacion ubicacion) {
        this.id = id;
        this.buyerdocument = doc;
        this.buyerName = name;
        this.espectaculo = espectaculo;
        this.vendedor = vendedor;
        this.soldAt = vendido;
        this.ubicacion = ubicacion;
    }

    public Integer getId() {
        return id;
    }

    public String getDocument() {
        return buyerdocument;
    }

    public void setDocument(String document) {
        this.buyerdocument = document;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Espectaculo getEspectaculo() {
        return espectaculo;
    }

    public void setEspectaculo(Espectaculo espectaculo) {
        this.espectaculo = espectaculo;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Timestamp getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(Timestamp soldAt) {
        this.soldAt = soldAt;
    }

    public Ubicacion getUbicacion() {
        return this.ubicacion;
    }

    public void setUbicacion(Ubicacion ub) {
        this.ubicacion = ub;
    }
}
