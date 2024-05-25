package com.lastbyte.Challenge_Literatura_Java_Alura.Service;

public interface IConvierteJsonADatosAPI {
    <T> T conversorJsonADatos(String json, Class <T> clase);
}
