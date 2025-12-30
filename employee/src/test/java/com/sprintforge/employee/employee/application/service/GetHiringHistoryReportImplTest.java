package com.sprintforge.employee.employee.application.service;

import com.sprintforge.common.application.port.result.EmployeesByEmploymentHistoryReportResult;
import com.sprintforge.employee.employee.application.port.in.query.GetHiringHistoryReportQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.LoadEmploymentHistoryReport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetHiringHistoryReportImplTest {

    @Test
    void handle_delegatesToPort() {
        LoadEmploymentHistoryReport port = mock(LoadEmploymentHistoryReport.class);
        GetHiringHistoryReportImpl service = new GetHiringHistoryReportImpl(port);

        GetHiringHistoryReportQuery query = mock(GetHiringHistoryReportQuery.class);
        EmployeesByEmploymentHistoryReportResult expected = mock(EmployeesByEmploymentHistoryReportResult.class);

        when(port.loadHired(query)).thenReturn(expected);

        assertSame(expected, service.handle(query));
        verify(port).loadHired(query);
        verifyNoMoreInteractions(port);
    }
}
