/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.service;

import co.id.mii.serverside.model.Employee;
import co.id.mii.serverside.model.Role;
import co.id.mii.serverside.model.User;
import co.id.mii.serverside.model.dto.request.EmailRequest;
import co.id.mii.serverside.model.dto.request.EmployeeRequest;
import co.id.mii.serverside.repository.EmployeeRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author MSI-JO
 */
@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private RoleService roleService;
    private JavaMailSenderService javaMailSenderService;
    private PasswordEncoder passwordEncoder; 

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "employee not Found"));
    }

    public Employee create(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setEmail(employeeRequest.getEmail());
        employee.setName(employeeRequest.getName());
        employee.setNumber(employeeRequest.getNumber());

        User user = new User();
        user.setUsername(employeeRequest.getUsername());
        user.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));// Password Encoder ke Encrypt
        user.setIsEnabled(Boolean.TRUE);// Akun itu dikunci atau tidak?
        user.setIsAccountLocked(Boolean.FALSE);// Digunakan untuk verifikasi Akun

        List<Role> role = new ArrayList();
        role.add(roleService.getById(1L)); // Role User
        user.setRoles(role);

        employee.setUser(user);
        user.setEmployee(employee);
        
        Employee e = employeeRepository.save(employee);
        
        javaMailSenderService.sendSimpleEmail(new EmailRequest(employeeRequest.getEmail(), 
                "Welcome Home " + employeeRequest.getUsername(), 
                "Selamat Anda Terlah berhasil terdaftar pada program kami."));
        
        return e;
    }

    public Employee update(Long id, Employee employee) {
        Employee oldEmployee = getById(id);
        employee.setId(id);
        employee.setUser(oldEmployee.getUser());
        return employeeRepository.save(employee);
    }

    public Employee delete(Long id) {
        Employee employee = getById(id);
        employeeRepository.delete(employee);
        return employee;
    }

    public void findByName(String name) {
        if (employeeRepository.findByName(name).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee already exists");
        }
    }

}
