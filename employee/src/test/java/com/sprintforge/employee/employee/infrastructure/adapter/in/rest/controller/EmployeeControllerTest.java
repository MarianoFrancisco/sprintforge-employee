package com.sprintforge.employee.employee.infrastructure.adapter.in.rest.controller;

import com.sprintforge.employee.employee.application.port.in.command.*;
import com.sprintforge.employee.employee.application.port.in.query.*;
import com.sprintforge.employee.employee.domain.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.sprintforge.employee.test.fixtures.EmployeeFixture.validEmployee;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    private static final UUID EMPLOYEE_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    private static final UUID POSITION_ID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
    private static final String CUI = "1234567890101";
    private static final String EMAIL = "john.doe@test.com";

    private MockMvc mockMvc;

    @Mock
    private GetAllEmployees getAllEmployees;
    @Mock
    private GetEmployeeByCui getEmployeeByCui;
    @Mock
    private GetEmployeeByEmail getEmployeeByEmail;
    @Mock
    private GetEmployeeById getEmployeeById;
    @Mock
    private HireEmployee hireEmployee;
    @Mock
    private UpdateEmployeeDetail updateEmployeeDetail;
    @Mock
    private IncreaseEmployeeSalary increaseEmployeeSalary;
    @Mock
    private ReinstateEmployee reinstateEmployee;
    @Mock
    private SuspendEmployee suspendEmployee;
    @Mock
    private TerminateEmployee terminateEmployee;

    @InjectMocks
    private EmployeeController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldGetAllEmployees() throws Exception {
        Employee employee = validEmployee();

        when(getAllEmployees.handle(any(GetAllEmployeesQuery.class)))
                .thenReturn(List.of(employee));

        mockMvc.perform(get("/api/v1/employee"))
                .andExpect(status().isOk());

        verify(getAllEmployees).handle(any(GetAllEmployeesQuery.class));
    }

    @Test
    void shouldGetEmployeeByCui() throws Exception {
        Employee employee = validEmployee();

        when(getEmployeeByCui.handle(any(GetEmployeeByCuiQuery.class)))
                .thenReturn(employee);

        mockMvc.perform(get("/api/v1/employee/cui/{cui}", CUI))
                .andExpect(status().isOk());

        verify(getEmployeeByCui).handle(any(GetEmployeeByCuiQuery.class));
    }

    @Test
    void shouldGetEmployeeByEmail() throws Exception {
        Employee employee = validEmployee();

        when(getEmployeeByEmail.handle(any(GetEmployeeByEmailQuery.class)))
                .thenReturn(employee);

        mockMvc.perform(get("/api/v1/employee/email/{email}", EMAIL))
                .andExpect(status().isOk());

        verify(getEmployeeByEmail).handle(any(GetEmployeeByEmailQuery.class));
    }

    @Test
    void shouldGetEmployeeById() throws Exception {
        Employee employee = validEmployee();

        when(getEmployeeById.handle(any(GetEmployeeByIdQuery.class)))
                .thenReturn(employee);

        mockMvc.perform(get("/api/v1/employee/{id}", EMPLOYEE_ID))
                .andExpect(status().isOk());

        verify(getEmployeeById).handle(any(GetEmployeeByIdQuery.class));
    }

    @Test
    void shouldHireEmployeeWithMultipart() throws Exception {
        Employee employee = validEmployee();

        when(hireEmployee.handle(any(HireEmployeeCommand.class)))
                .thenReturn(employee);

        MockMultipartFile profileImage = new MockMultipartFile(
                "profileImage",
                "pic.png",
                MediaType.IMAGE_PNG_VALUE,
                "img".getBytes()
        );

        mockMvc.perform(multipart("/api/v1/employee")
                        .file(profileImage)
                        .param("cui", CUI)
                        .param("email", EMAIL)
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("phoneNumber", "55556666")
                        .param("birthDate", LocalDate.now().minusYears(25).toString())
                        .param("positionId", POSITION_ID.toString())
                        .param("workloadType", "FULL_TIME")
                        .param("salary", "5000.00")
                        .param("startDate", LocalDate.now().minusDays(10).toString())
                        .param("notes", "hire")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isCreated());

        verify(hireEmployee).handle(any(HireEmployeeCommand.class));
    }

    @Test
    void shouldUpdateEmployeeDetailsWithMultipartPatch() throws Exception {
        Employee employee = validEmployee();

        when(updateEmployeeDetail.handle(any(UpdateEmployeeDetailCommand.class)))
                .thenReturn(employee);

        MockMultipartFile profileImage = new MockMultipartFile(
                "profileImage",
                "pic.png",
                MediaType.IMAGE_PNG_VALUE,
                "img".getBytes()
        );

        mockMvc.perform(multipart("/api/v1/employee/{id}", EMPLOYEE_ID)
                        .file(profileImage)
                        .with(request -> {
                            request.setMethod("PATCH");
                            return request;
                        })
                        .param("firstName", "John2")
                        .param("lastName", "Doe2")
                        .param("phoneNumber", "55556666")
                        .param("birthDate", LocalDate.now().minusYears(30).toString())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk());

        verify(updateEmployeeDetail).handle(any(UpdateEmployeeDetailCommand.class));
    }

    @Test
    void shouldIncreaseSalary() throws Exception {
        Employee employee = validEmployee();

        when(increaseEmployeeSalary.handle(any(IncreaseEmployeeSalaryCommand.class)))
                .thenReturn(employee);

        mockMvc.perform(post("/api/v1/employee/{cui}/salary/increase", CUI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "increaseAmount": 250.50,
                                  "date": "2025-01-15",
                                  "notes": "inc"
                                }
                                """))
                .andExpect(status().isOk());

        verify(increaseEmployeeSalary).handle(any(IncreaseEmployeeSalaryCommand.class));
    }

    @Test
    void shouldReinstateEmployee() throws Exception {
        Employee employee = validEmployee();

        when(reinstateEmployee.handle(any(ReinstateEmployeeCommand.class)))
                .thenReturn(employee);

        mockMvc.perform(post("/api/v1/employee/{cui}/reinstate", CUI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "date": "2025-01-15",
                                  "notes": "reinstate"
                                }
                                """))
                .andExpect(status().isOk());

        verify(reinstateEmployee).handle(any(ReinstateEmployeeCommand.class));
    }

    @Test
    void shouldSuspendEmployee() throws Exception {
        Employee employee = validEmployee();

        when(suspendEmployee.handle(any(SuspendEmployeeCommand.class)))
                .thenReturn(employee);

        mockMvc.perform(post("/api/v1/employee/{cui}/suspend", CUI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "date": "2025-01-15",
                                  "notes": "suspend"
                                }
                                """))
                .andExpect(status().isOk());

        verify(suspendEmployee).handle(any(SuspendEmployeeCommand.class));
    }

    @Test
    void shouldTerminateEmployee() throws Exception {
        Employee employee = validEmployee();

        when(terminateEmployee.handle(any(TerminateEmployeeCommand.class)))
                .thenReturn(employee);

        mockMvc.perform(post("/api/v1/employee/{cui}/terminate", CUI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "date": "2025-01-15",
                                  "notes": "terminate"
                                }
                                """))
                .andExpect(status().isOk());

        verify(terminateEmployee).handle(any(TerminateEmployeeCommand.class));
    }
}
