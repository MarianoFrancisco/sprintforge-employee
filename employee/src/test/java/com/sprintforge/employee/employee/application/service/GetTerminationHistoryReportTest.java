package com.sprintforge.employee.employee.application.service;

import com.sprintforge.common.application.port.result.EmployeesByEmploymentHistoryReportResult;
import com.sprintforge.employee.employee.application.port.in.query.GetTerminationHistoryReportQuery;
import com.sprintforge.employee.employee.application.port.out.persistence.LoadEmploymentHistoryReport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetTerminationHistoryReportTest {

    @Test
    void handle_delegatesToPort() {
        LoadEmploymentHistoryReport port = mock(LoadEmploymentHistoryReport.class);
        GetTerminationHistoryReport service = new GetTerminationHistoryReport(port);

        GetTerminationHistoryReportQuery query = mock(GetTerminationHistoryReportQuery.class);
        EmployeesByEmploymentHistoryReportResult expected = mock(EmployeesByEmploymentHistoryReportResult.class);

        when(port.loadTerminated(query)).thenReturn(expected);

        assertSame(expected, service.handle(query));
        verify(port).loadTerminated(query);
        verifyNoMoreInteractions(port);
    }
}
