package za.co.firmdev.payroll.services;

import java.util.List;
import java.util.stream.Collectors;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(long employeeId) {
        super(String.format("Entity with ID %d could not be found", employeeId));
    }

    public EntityNotFoundException(List<String> names) {
        super(String.format("One or more entity with name %s could not be found", String.join(",", names)));
    }
}
