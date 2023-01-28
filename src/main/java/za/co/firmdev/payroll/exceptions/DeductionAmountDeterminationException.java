package za.co.firmdev.payroll.exceptions;

public class DeductionAmountDeterminationException extends RuntimeException {
    public DeductionAmountDeterminationException(long employeeId) {
        super("Could not determine deduction amount for employee " + employeeId);
    }
}
