package com.grupoum.projeto_fera.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class NotaConverter implements AttributeConverter<Feedback.Nota, String> {

    @Override
    public String convertToDatabaseColumn(Feedback.Nota nota) {
        if (nota == null) return null;
        return switch (nota) {
            case UM     -> "1";
            case DOIS   -> "2";
            case TRES   -> "3";
            case QUATRO -> "4";
            case CINCO  -> "5";
        };
    }

    @Override
    public Feedback.Nota convertToEntityAttribute(String dbValue) {
        if (dbValue == null) return null;
        return switch (dbValue) {
            case "1" -> Feedback.Nota.UM;
            case "2" -> Feedback.Nota.DOIS;
            case "3" -> Feedback.Nota.TRES;
            case "4" -> Feedback.Nota.QUATRO;
            case "5" -> Feedback.Nota.CINCO;
            default  -> throw new IllegalArgumentException("Valor de nota inválido: " + dbValue);
        };
    }
}
