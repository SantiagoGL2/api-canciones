package com.quipux.apicanciones.controller;

import com.quipux.apicanciones.dto.request.ListRequest;
import com.quipux.apicanciones.dto.response.ListaReproduccionResponse;
import com.quipux.apicanciones.service.ListaReproduccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/lists")
@Tag(name = "Listas de Reproducción", description = "API para gestionar listas de reproducción musicales")
public class ListaReproduccionController {

    private final ListaReproduccionService listaService;

    public ListaReproduccionController(ListaReproduccionService listaService) {
        this.listaService = listaService;
    }

    @Operation(summary = "Obtener todas las listas", description = "Devuelve todas las listas de reproducción disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listas obtenidas exitosamente"),
    })
    @GetMapping
    public ResponseEntity<List<ListaReproduccionResponse>> obtenerTodasLasListas() {
        return ResponseEntity.ok(listaService.obtenerTodas());
    }

    @Operation(summary = "Crear una nueva lista de reproducción", description = "Crea una lista con sus canciones. Requiere autenticación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lista creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<ListaReproduccionResponse> crearListaReproduccion(@RequestBody ListRequest listRequest){
        ListaReproduccionResponse saved = listaService.crearListaReproduccion(listRequest);
        String encodedName = URLEncoder.encode(saved.getNombre(), StandardCharsets.UTF_8);
        URI location = URI.create("/lists/" + encodedName);
        return ResponseEntity.created(location).body(saved);
    }

    @Operation(summary = "Obtener una lista por nombre", description = "Devuelve los detalles de una lista según su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista encontrada"),
            @ApiResponse(responseCode = "404", description = "Lista no encontrada")
    })
    @GetMapping("/{listName}")
    public ResponseEntity<ListaReproduccionResponse> obtenerListaPorNombre(@PathVariable("listName") String listName) {
        ListaReproduccionResponse response = listaService.obtenerListaPorNombre(listName);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar una lista por nombre", description = "Elimina una lista de reproducción existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lista eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Lista no encontrada")
    })
    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> eliminarListaReproduccion(@PathVariable String listName) {
        listaService.eliminarListaReproduccion(listName);
        return ResponseEntity.noContent().build();
    }

}
