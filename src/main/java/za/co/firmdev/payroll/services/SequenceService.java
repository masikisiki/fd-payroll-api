package za.co.firmdev.payroll.services;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import za.co.firmdev.payroll.data.models.DatabaseSequence;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceService {

    private static final String EMPLOYEE_SEQUENCE = "employee_sequence";
    private static final String SALARY_SEQUENCE = "salary_map_sequence";
    private static final String SALARY_DEDUCTION_SEQUENCE = "salary_deduction_sequence";
    private static final String EMPLOYEE_DEDUCTION_SEQUENCE = "employee_deduction_sequence";

    //    private static final String EMPLOYEE_SEQUENCE = "employee_sequence";


    private final MongoOperations mongoOperations;

    public SequenceService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long nextEmpSequence(){
        return extracted(EMPLOYEE_SEQUENCE);
    }
    public long nextSalSequence(){
        return extracted(SALARY_SEQUENCE);
    }

    public long nextDeductSequence(){
        return extracted(SALARY_DEDUCTION_SEQUENCE);
    }
    public long nexEmpDeductionSequence() {
        return extracted(EMPLOYEE_DEDUCTION_SEQUENCE);
    }
    private long extracted(String sequenceName) {
        DatabaseSequence counter = mongoOperations.findAndModify
                (query(where("_id").is(sequenceName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
