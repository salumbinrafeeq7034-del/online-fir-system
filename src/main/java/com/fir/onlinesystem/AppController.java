package com.fir.onlinesystem; 

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

    UserDAO userDAO = new UserDAO();
    ComplaintDAO complaintDAO = new ComplaintDAO();

    // --- 0. HOME ROUTE ---
    @GetMapping("/")
    public String showHomePage() {
        return "redirect:/login"; 
    }

    // --- 1. LOGIN ROUTES ---
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; 
    }

    @PostMapping("/processLogin")
    public String processLogin(@RequestParam String username,@RequestParam String email, @RequestParam String password) {
        String role = userDAO.login(username, email, password);
        if (role != null) {
            if (role.equals("Admin")) {
                return "redirect:/adminDashboard"; 
            } else {
                return "redirect:/citizenDashboard"; 
            }
        } else {
            return "redirect:/login?error=true"; 
        }
    }

    // --- 2. CITIZEN DASHBOARD ROUTE ---
    @GetMapping("/citizenDashboard")
    public String showCitizenDashboard() {
        return "citizenDashboard";
    }

    // --- 3. REGISTRATION ROUTES ---
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; 
    }

    @PostMapping("/processRegistration")
    public String processRegistration(@RequestParam String username,@RequestParam String email, @RequestParam String password) {
        boolean success = userDAO.registerCitizen(username, email, password);
        if (success) {
            return "redirect:/login"; 
        } else {
            return "redirect:/register?error=true"; 
        }
    }

    // --- 4. FILE COMPLAINT ROUTE ---
    @PostMapping("/fileComplaint")
    public String processComplaint(@RequestParam String username,
                                   @RequestParam String category, 
                                   @RequestParam String location, 
                                   @RequestParam String description ){
        boolean success = complaintDAO.addComplaint(category, location, description);
        if (success) {
            return "redirect:/citizenDashboard?success=true"; 
        } else {
            return "redirect:/citizenDashboard?error=true"; 
        }
    }

    // --- 5. ADMIN DASHBOARD ROUTE ---
    @GetMapping("/adminDashboard")
    public String showAdminDashboard(Model model) {
        List<Complaint> allComplaints = complaintDAO.getAllComplaints();
        model.addAttribute("complaints", allComplaints);
        return "adminDashboard";
    }

    // --- 6. UPDATE STATUS ROUTE ---
    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam int id, @RequestParam String status) {
        complaintDAO.updateComplaintStatus(id, status);
        return "redirect:/adminDashboard";
    }
}
