package com.iws_manager.iws_manager_api.controllers;

import java.math.BigDecimal;

import com.iws_manager.iws_manager_api.models.Promoter;
import com.iws_manager.iws_manager_api.services.interfaces.PromoterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/promoters")
public class PromoterController {
    private final PromoterService promoterService;

    @Autowired
    public PromoterController(PromoterService promoterService) {
        this.promoterService = promoterService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Promoter promoter){
        Promoter createPromoter = promoterService.create(promoter);
        return new ResponseEntity<>(createPromoter, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promoter> getById(@PathVariable Long id){
        return  promoterService.findById(id)
                .map( promoter -> new ResponseEntity<>(promoter, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Promoter>> getAll(){
        List<Promoter> promoters = promoterService.findAll();
        return new ResponseEntity<>(promoters, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promoter> update(@PathVariable Long id, @RequestBody Promoter promoterDetails){
        try {
            Promoter updatePromoter = promoterService.update(id, promoterDetails);
            return new ResponseEntity<>(updatePromoter, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        promoterService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/by-city/{city}")
    public ResponseEntity<List<Promoter>> getByCity(@PathVariable String city) {
        List<Promoter> promoters = promoterService.getByCity(city);
        return new ResponseEntity<>(promoters, HttpStatus.OK);
    }

    @GetMapping("/by-country/{countryId}")
    public ResponseEntity<List<Promoter>> getByCountryId(@PathVariable Long countryId) {
        List<Promoter> promoters = promoterService.getByCountryId(countryId);
        return new ResponseEntity<>(promoters, HttpStatus.OK);
    }

    @GetMapping("/by-project-promoter/{projectPromoter}")
    public ResponseEntity<List<Promoter>> getByProjectPromoter(@PathVariable String projectPromoter) {
        List<Promoter> promoters = promoterService.getByProjectPromoter(projectPromoter);
        return new ResponseEntity<>(promoters, HttpStatus.OK);
    }

    @GetMapping("/by-promoter-name1/{promoterName1}")
    public ResponseEntity<List<Promoter>> getByPromoterName1(@PathVariable String promoterName1) {
        List<Promoter> promoters = promoterService.getByPromoterName1(promoterName1);
        return new ResponseEntity<>(promoters, HttpStatus.OK);
    }

    @GetMapping("/by-promoter-name2/{promoterName2}")
    public ResponseEntity<List<Promoter>> getByPromoterName2(@PathVariable String promoterName2) {
        List<Promoter> promoters = promoterService.getByPromoterName2(promoterName2);
        return new ResponseEntity<>(promoters, HttpStatus.OK);
    }

    @GetMapping("/by-promoterno/{promoterNo}")
    public ResponseEntity<List<Promoter>> getByPromoterNo(@PathVariable String promoterNo) {
        List<Promoter> promoters = promoterService.getByPromoterNo(promoterNo);
        return new ResponseEntity<>(promoters, HttpStatus.OK);
    }

    @GetMapping("/by-street/{street}")
    public ResponseEntity<List<Promoter>> getByStreet(@PathVariable String street) {
        List<Promoter> promoters = promoterService.getByStreet(street);
        return new ResponseEntity<>(promoters, HttpStatus.OK);
    }

    @GetMapping("/by-zipCode/{zipCode}")
    public ResponseEntity<List<Promoter>> getByZipCode(@PathVariable String zipCode) {
        List<Promoter> promoters = promoterService.getByZipCode(zipCode);
        return new ResponseEntity<>(promoters, HttpStatus.OK);
    }

    //GET /api/v1/promoters/by-promoter-name1-or-name2?name1=Juan&name2=Pedro

    @GetMapping("/by-promoter-name1-or-name2")
    public ResponseEntity<List<Promoter>> getByPromoterName1OrPromoterName2(
            @RequestParam String name1,
            @RequestParam String name2) {
        List<Promoter> promoters = promoterService.getByPromoterName1OrPromoterName2(name1, name2);
        return new ResponseEntity<>(promoters, HttpStatus.OK);
    }


    @GetMapping("/next-promoter-no")
    public ResponseEntity<Integer> getNextPromoterNo() {
        try {
            Integer nextPromoterNo = promoterService.getNextPromoterNo();
            return ResponseEntity.ok(nextPromoterNo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/with-auto-promoterno")
    public ResponseEntity<Promoter> createWithAutoPromoterNo(@RequestBody Promoter promoter) {
        Promoter created = promoterService.createWithAutoPromoterNo(promoter);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
