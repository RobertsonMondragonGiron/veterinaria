package com.sistemadegestiondecentroveterinario.sistemadegestiondecentroveterinario.dto;

import jakarta.validation.constraints.NotBlank;

public class PropietarioDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El documento es obligatorio")
    private String documento;

    private String telefono;

    // Constructor vacío
    public PropietarioDTO() {}

    // Constructor completo
    public PropietarioDTO(Long id, String nombre, String documento, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}