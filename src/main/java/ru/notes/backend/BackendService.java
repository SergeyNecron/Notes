package ru.notes.backend;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BackendService {
    private List<Employee> employees;
    {
        Long id = 42L;
        employees = new ArrayList<>();
        employees.add(new Employee(id++, "Rowena", "Leeming", "rleeming0@bbc.co.uk", "Food Chemist"));
        employees.add(new Employee(id++, "Alvinia", "Delong", "adelong1@altervista.org", "Recruiting Manager"));
        employees.add(new Employee(id++, "Leodora", "Burry", "lburry2@example.com", "Food Chemist"));
    }

    public List<Employee> getEmployees() {
        return employees;
    }

}
