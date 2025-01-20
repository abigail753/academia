package net.ausiasmarch.academia.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

import net.ausiasmarch.academia.bean.LogindataBean;
import net.ausiasmarch.academia.entity.UsuarioEntity;
import net.ausiasmarch.academia.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    JWTService JWTHelper;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    public boolean checkLogin(LogindataBean oLogindataBean) {
        if (oUsuarioRepository.findByCorreoAndPassword(oLogindataBean.getCorreo(), oLogindataBean.getPassword())
                .isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    private Map<String, String> getClaims(String email) {
        Map<String, String> claims = new HashMap<>();
        claims.put("email", email);
        return claims;
    };

    public String getToken(String email) {
        return JWTHelper.generateToken(getClaims(email));
    }

    public UsuarioEntity getUsuarioFromToken() {
        String correo = oHttpServletRequest.getAttribute("correo").toString();
        return oUsuarioRepository.findByCorreo(correo).get();
    }

    public boolean isSessionActive() {
        return oHttpServletRequest.getAttribute("correo") != null;
    }

    public boolean isAdmin() {
        return this.getUsuarioFromToken().getTipo_usuario() == "Administrador";
    }

    public boolean isProfesor() {
        return this.getUsuarioFromToken().getTipo_usuario() == "Profesor";
    }

    public boolean isEstudiante() {
        return this.getUsuarioFromToken().getTipo_usuario() == "Estudiante";
    }

    public boolean isAdminOrProfesor() {
        return this.isAdmin() || this.isProfesor();
    }

    public boolean isEstudianteWithItsOwnData(Long id) {
        UsuarioEntity oUsuarioEntity = this.getUsuarioFromToken();
        return this.isEstudiante() && oUsuarioEntity.getId() == id;
    }


}
