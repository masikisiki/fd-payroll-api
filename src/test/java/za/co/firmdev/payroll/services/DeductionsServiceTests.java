package za.co.firmdev.payroll.services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.firmdev.payroll.data.models.Deduction;
import za.co.firmdev.payroll.data.repository.DeductionRepository;
import za.co.firmdev.payroll.data.repository.EmployeeDeductionRepository;
import za.co.firmdev.payroll.data.repository.EmployeeRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static za.co.firmdev.payroll.enums.Deductions.DISCOVERY_KEYCARE_MED;
import static za.co.firmdev.payroll.enums.Deductions.TAX;

@ExtendWith(MockitoExtension.class)
public class DeductionsServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DeductionRepository deductionRepository;
    @Mock
    private EmployeeDeductionRepository employeeDeductionRepository;
    @Mock
    private SequenceService sequenceService;
    @InjectMocks
    private DeductionsService deductionsService;

    @Test
    void shouldMapDeductionToEmployee() {
        //Arrange
        Mockito.when(employeeRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(deductionRepository.existsAllByNameIn(anyList())).thenReturn(true);
        Mockito.when(deductionRepository.findAllByNameIn(anyList())).thenReturn(List.of(
                Deduction.DeductionBuilder.aDeduction()
                        .withId(1)
                        .withRatePercent(16)
                        .withName(TAX.name())
                        .withAccount("PAYROLL")
                        .build(),
                Deduction.DeductionBuilder.aDeduction()
                        .withId(2)
                        .withAmount(1800)
                        .withName(DISCOVERY_KEYCARE_MED.name())
                        .withAccount("PAYROLL")
                        .build()
        ));
        //Act
        deductionsService.map(1, List.of(TAX, DISCOVERY_KEYCARE_MED));
        //Assert
        Mockito.verify(employeeRepository, times(1)).existsById(1l);
        Mockito.verify(deductionRepository, times(1)).existsAllByNameIn(List.of(TAX.name(), DISCOVERY_KEYCARE_MED.name()));
        Mockito.verify(employeeDeductionRepository, times(2)).save(argThat(a -> a.getEmployeeId().equals(1l)));
    }
}
