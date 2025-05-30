package com.quipux.apicanciones.controller;

import com.quipux.apicanciones.dto.request.ListRequest;
import com.quipux.apicanciones.dto.response.ListaReproduccionResponse;
import com.quipux.apicanciones.service.ListaReproduccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/lists")
public class ListaReproduccionController {

    private final ListaReproduccionService listaService;

    public ListaReproduccionController(ListaReproduccionService listaService) {
        this.listaService = listaService;
    }

    @GetMapping
    public ResponseEntity<List<ListaReproduccionResponse>> obtenerTodasLasListas() {
        return ResponseEntity.ok(listaService.obtenerTodas());
    }

    @PostMapping
    public ResponseEntity<ListaReproduccionResponse> crearListaReproduccion(@RequestBody ListRequest listRequest){
        ListaReproduccionResponse saved = listaService.crearListaReproduccion(listRequest);
        String encodedName = URLEncoder.encode(saved.getNombre(), StandardCharsets.UTF_8);
        URI location = URI.create("/lists/" + encodedName);
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping("/{listName}")
    public ResponseEntity<ListaReproduccionResponse> obtenerListaPorNombre(@PathVariable("listName") String listName) {
        ListaReproduccionResponse response = listaService.obtenerListaPorNombre(listName);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> eliminarListaReproduccion(@PathVariable String listName) {
        listaService.eliminarListaReproduccion(listName);
        return ResponseEntity.noContent().build();
    }

}
