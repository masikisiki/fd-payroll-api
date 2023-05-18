package za.co.firmdev.payroll.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
public class SalaryMap {
    @Id
    private long id;
    private long employeeId;
    private int ctcSalary;
    private LocalDate effectiveDate;
    public static final class SalaryMapBuilder {
        private long id;
        private long employeeId;
        private int ctcSalary;
        private LocalDate effectiveDate;

        private SalaryMapBuilder() {
        }

        public static SalaryMapBuilder aSalaryMap() {
            return new SalaryMapBuilder();
        }

        public SalaryMapBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public SalaryMapBuilder withEmployeeId(long employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public SalaryMapBuilder withCtcSalary(int ctcSalary) {
            this.ctcSalary = ctcSalary;
            return this;
        }

        public SalaryMapBuilder withEffectiveDate(LocalDate effectiveDate) {
            this.effectiveDate = effectiveDate;
            return this;
        }

        public SalaryMap build() {
            SalaryMap salaryMap = new SalaryMap();
            salaryMap.setId(id);
            salaryMap.setEmployeeId(employeeId);
            salaryMap.setCtcSalary(ctcSalary);
            salaryMap.setEffectiveDate(effectiveDate);
            return salaryMap;
        }
    }
}
