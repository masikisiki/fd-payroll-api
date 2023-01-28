package za.co.firmdev.payroll.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import za.co.firmdev.payroll.enums.TransactionType;

import java.time.LocalDateTime;

@Data
@Document
public class PayrollTransaction {
    @Id
    private Long id;
    private String batchId;
    private Object targetReference1;
    private String targetReference1Desc;
    private int amount;
    private String account;
    private TransactionType type;
    private LocalDateTime dateTime;


    public static final class PayrollTransactionBuilder {
        private Long id;
        private String batchId;
        private Object targetReference1;
        private String targetReference1Desc;
        private int amount;
        private String account;
        private TransactionType type;
        private LocalDateTime dateTime;

        private PayrollTransactionBuilder() {
        }

        public static PayrollTransactionBuilder aPayrollTransaction() {
            return new PayrollTransactionBuilder();
        }

        public PayrollTransactionBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public PayrollTransactionBuilder withBatchId(String batchId) {
            this.batchId = batchId;
            return this;
        }

        public PayrollTransactionBuilder withTargetReference1(Object targetReference1) {
            this.targetReference1 = targetReference1;
            return this;
        }

        public PayrollTransactionBuilder withTargetReference1Desc(String targetReference1Desc) {
            this.targetReference1Desc = targetReference1Desc;
            return this;
        }

        public PayrollTransactionBuilder withAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public PayrollTransactionBuilder withAccount(String account) {
            this.account = account;
            return this;
        }

        public PayrollTransactionBuilder withType(TransactionType type) {
            this.type = type;
            return this;
        }

        public PayrollTransactionBuilder withDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public PayrollTransaction build() {
            PayrollTransaction payrollTransaction = new PayrollTransaction();
            payrollTransaction.setId(id);
            payrollTransaction.setBatchId(batchId);
            payrollTransaction.setTargetReference1(targetReference1);
            payrollTransaction.setTargetReference1Desc(targetReference1Desc);
            payrollTransaction.setAmount(amount);
            payrollTransaction.setAccount(account);
            payrollTransaction.setType(type);
            payrollTransaction.setDateTime(dateTime);
            return payrollTransaction;
        }
    }
}
