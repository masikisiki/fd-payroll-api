package za.co.firmdev.payroll.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import za.co.firmdev.payroll.enums.DeductionType;

@Data
@Document
public class Deduction {
    @Id
    private long id;
    private String name;
    private String description;
    private String account;
    private DeductionType type;
    private double ratePercent;
    private int amount;


    public static final class DeductionBuilder {
        private long id;
        private String name;
        private String description;
        private String account;
        private DeductionType type;
        private int ratePercent;
        private int amount;

        private DeductionBuilder() {
        }

        public static DeductionBuilder aDeduction() {
            return new DeductionBuilder();
        }

        public DeductionBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public DeductionBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public DeductionBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public DeductionBuilder withAccount(String account) {
            this.account = account;
            return this;
        }

        public DeductionBuilder withType(DeductionType type) {
            this.type = type;
            return this;
        }

        public DeductionBuilder withRatePercent(int ratePercent) {
            this.ratePercent = ratePercent;
            return this;
        }

        public DeductionBuilder withAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Deduction build() {
            Deduction deduction = new Deduction();
            deduction.setId(id);
            deduction.setName(name);
            deduction.setDescription(description);
            deduction.setAccount(account);
            deduction.setType(type);
            deduction.setRatePercent(ratePercent);
            deduction.setAmount(amount);
            return deduction;
        }
    }
}
