package za.co.firmdev.payroll.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.firmdev.payroll.data.models.Deduction;
import za.co.firmdev.payroll.data.repository.DeductionRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/deductions")
public class DeductionController {

    private final DeductionRepository deductionRepository;

    public DeductionController(DeductionRepository deductionRepository) {
        this.deductionRepository = deductionRepository;
    }

    // Create a new Deduction
    @PostMapping
    public Deduction createDeduction(@RequestBody Deduction deduction) {
        return deductionRepository.save(deduction);
    }

    // Get all Deductions
    @GetMapping
    public List<Deduction> getAllDeductions() {
        return deductionRepository.findAll();
    }

    // Get a specific Deduction by ID
    @GetMapping("/{id}")
    public ResponseEntity<Deduction> getDeductionById(@PathVariable("id") long id) {
        Optional<Deduction> optionalDeduction = deductionRepository.findById(id);
        return optionalDeduction.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a Deduction
    @PutMapping("/{id}")
    public ResponseEntity<Deduction> updateDeduction(@PathVariable("id") long id, @RequestBody Deduction updatedDeduction) {
        Optional<Deduction> optionalDeduction = deductionRepository.findById(id);
        if (optionalDeduction.isPresent()) {
            Deduction existingDeduction = optionalDeduction.get();
            existingDeduction.setName(updatedDeduction.getName());
            existingDeduction.setDescription(updatedDeduction.getDescription());
            existingDeduction.setAccount(updatedDeduction.getAccount());
            existingDeduction.setType(updatedDeduction.getType());
            existingDeduction.setRatePercent(updatedDeduction.getRatePercent());
            existingDeduction.setAmount(updatedDeduction.getAmount());
            deductionRepository.save(existingDeduction);
            return ResponseEntity.ok(existingDeduction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Deduction
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeduction(@PathVariable("id") long id) {
        Optional<Deduction> optionalDeduction = deductionRepository.findById(id);
        if (optionalDeduction.isPresent()) {
            deductionRepository.delete(optionalDeduction.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
