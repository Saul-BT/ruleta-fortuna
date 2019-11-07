package com.example.ruletadelafortuna;

public enum Gajo {
    CERO(0),
    CIEN(100),
    CINCUENTA(50),
    VEINTICINCO(25),
    SETENTA_Y_CINCO(75),
    CIENTO_CINCUENTA(150),
    // --------------------------------------------
    PREMIO("Premio"),
    QUIEBRA("Quiebra"),
    COMODIN("Comodín"),
    INTERROGACION("?"),
    GRAN_PREMIO("Gran premio"),
    AYUDA_FINAL("Ayuda final"),
    DOBLE_LETRA("Doble letra"),
    ME_LO_QUEDO("Me lo quedo"),
    PIERDE_TURNO("Pierde turno");


    private Object valor;

    Gajo(Object valor) { this.valor = valor; }

    public Object getValor() { return valor; }
}
