package za.co.firmdev.payroll.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class EmployeeDeduction {
    @Id
    private Long id;
    private Long employeeId;
    private Long deductionId;

    public static final class EmployeeDeductionBuilder {
        private Long id;
        private Long employeeId;
        private Long deductionId;

        private EmployeeDeductionBuilder() {
        }

        public static EmployeeDeductionBuilder anEmployeeDeduction() {
            return new EmployeeDeductionBuilder();
        }

        public EmployeeDeductionBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public EmployeeDeductionBuilder withEmployeeId(Long employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public EmployeeDeductionBuilder withDeductionId(Long deductionId) {
            this.deductionId = deductionId;
            return this;
        }

        public EmployeeDeduction build() {
            EmployeeDeduction employeeDeduction = new EmployeeDeduction();
            employeeDeduction.setId(id);
            employeeDeduction.setEmployeeId(employeeId);
            employeeDeduction.setDeductionId(deductionId);
            return employeeDeduction;
        }
    }
}
