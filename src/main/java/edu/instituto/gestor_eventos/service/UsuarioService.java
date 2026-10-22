package edu.instituto.gestor_eventos.service;

import edu.instituto.gestor_eventos.model.Usuario;
import edu.instituto.gestor_eventos.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService{

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;}

    public String registrar(String nombre, String correo, String contrasena) {
        // Error campos vacios
        if (nombre == null || nombre.isBlank( ))
            return "El nombre de usuario es obligatorio.";
        if (correo == null || correo.isBlank( ))
            return "El correo electrónico es obligatorio.";
        if (contrasena == null || contrasena.isBlank( ))
            return "La contraseña es obligatoria.";
        if (contrasena.length()<6)
            return "La contraseña debe tener al menos 6 caracteres.";
        if (!correo.contains("@"))
            return "El correo electrónico no es válido.";

        // Validar duplicados en la base de datos
        if (usuarioRepository.existsByNombre(nombre))
            return "El nombre de usuario '" + nombre + "' ya está en uso.";
        if (usuarioRepository.existsByCorreo(correo))
            return "El correo '" + correo + "' ya está registrado.";

        // Guardar el usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setContrasena(contrasena);
        usuario.setRol("USER");
        usuarioRepository.save(usuario);
        return null; //registro exitoso
    }

    public Usuario login(String nombre, String contrasena) {
        // Validar campos vacíos
        if (nombre == null || nombre.isBlank()) return null;
        if (contrasena == null || contrasena.isBlank()) return null;

        // Busca en la abse de datos y verificar contraseña
        return usuarioRepository.findByNombre(nombre)
                .filter(u -> u.getContrasena().equals(contrasena))
                .orElse(null);
    }}