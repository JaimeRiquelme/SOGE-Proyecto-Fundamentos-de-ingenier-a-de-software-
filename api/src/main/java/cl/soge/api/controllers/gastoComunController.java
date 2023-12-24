package cl.soge.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.soge.api.services.gastoComunServices;

import java.text.SimpleDateFormat;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
/**
 * Esta clase se encarga de manejar las peticiones relacionadas con los gastos comunes
 */
public class gastoComunController {

    @Autowired
    gastoComunServices gastoComunServices;

    @PostMapping("/registroGastoComun")
    /**
     * Registra un gasto común
     * @param jsonMap - JSON con los datos del gasto común
     * @return
     */
    public ResponseEntity<Map<String, String>> registroGastoComun(@RequestBody Map<String, Object> jsonMap) {
        try {
            // Se llama al servicio para registrar el gasto común
            boolean result = this.gastoComunServices.registrarGastoComun(
                    (String) jsonMap.get("descripcion_gasto"),
                    (Integer) jsonMap.get("monto_gasto"),
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse((String) jsonMap.get("fecha_emision")),
                    Integer.valueOf(jsonMap.get("id_edificio").toString()),
                    (String) jsonMap.get("id_usuario"),
                    (String) jsonMap.get("nombre_categoria")); // Asume que se pasa "nombre_categoria" en el JSON

            if (result) {
                // Si el gasto común se registró con éxito, se retorna un mensaje de éxito
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Gasto común registrado con éxito"));
            } else {
                // Si el gasto común no se registró con éxito, se retorna un mensaje de error
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Error al registrar el gasto común"));
            }
        } catch (Exception error) {
            error.printStackTrace();
            // Si hubo un error, se retorna un mensaje de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error interno del servidor: " + error.getMessage()));
        }
    }

}
