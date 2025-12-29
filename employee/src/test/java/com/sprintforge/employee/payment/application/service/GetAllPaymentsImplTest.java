package com.sprintforge.employee.payment.application.service;

import com.sprintforge.employee.payment.application.port.in.GetAllPaymentsQuery;
import com.sprintforge.employee.payment.application.port.out.persistence.FindPayments;
import com.sprintforge.employee.payment.domain.Payment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllPaymentsImplTest {

    @Mock
    private FindPayments findPayments;

    @InjectMocks
    private GetAllPaymentsImpl serviceToTest;

    @Test
    void shouldReturnPayments() {
        GetAllPaymentsQuery query = mock(GetAllPaymentsQuery.class);

        when(findPayments.findAll(query)).thenReturn(List.of(mock(Payment.class)));

        List<Payment> result = serviceToTest.handle(query);

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(findPayments).findAll(query);
        verifyNoMoreInteractions(findPayments);
    }
}
