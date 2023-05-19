package za.co.firmdev.payroll.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.firmdev.payroll.data.models.SalaryMap;
import za.co.firmdev.payroll.data.repository.SalaryMapRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/salary-maps")
public class SalaryMapController {

    private final SalaryMapRepository salaryMapRepository;

    public SalaryMapController(SalaryMapRepository salaryMapRepository) {
        this.salaryMapRepository = salaryMapRepository;
    }

    // Create a new SalaryMap
    @PostMapping
    public SalaryMap createSalaryMap(@RequestBody SalaryMap salaryMap) {
        return salaryMapRepository.save(salaryMap);
    }

    // Get all SalaryMaps
    @GetMapping
    public List<SalaryMap> getAllSalaryMaps() {
        return salaryMapRepository.findAll();
    }

    // Get a specific SalaryMap by ID
    @GetMapping("/{id}")
    public ResponseEntity<SalaryMap> getSalaryMapById(@PathVariable("id") long id) {
        Optional<SalaryMap> optionalSalaryMap = salaryMapRepository.findById(id);
        return optionalSalaryMap.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a SalaryMap
    @PutMapping("/{id}")
    public ResponseEntity<SalaryMap> updateSalaryMap(@PathVariable("id") long id, @RequestBody SalaryMap updatedSalaryMap) {
        Optional<SalaryMap> optionalSalaryMap = salaryMapRepository.findById(id);
        if (optionalSalaryMap.isPresent()) {
            SalaryMap existingSalaryMap = optionalSalaryMap.get();
            existingSalaryMap.setEmployeeId(updatedSalaryMap.getEmployeeId());
            existingSalaryMap.setCtcSalary(updatedSalaryMap.getCtcSalary());
            existingSalaryMap.setEffectiveDate(updatedSalaryMap.getEffectiveDate());
            salaryMapRepository.save(existingSalaryMap);
            return ResponseEntity.ok(existingSalaryMap);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a SalaryMap
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalaryMap(@PathVariable("id") long id) {
        Optional<SalaryMap> optionalSalaryMap = salaryMapRepository.findById(id);
        if (optionalSalaryMap.isPresent()) {
            salaryMapRepository.delete(optionalSalaryMap.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
