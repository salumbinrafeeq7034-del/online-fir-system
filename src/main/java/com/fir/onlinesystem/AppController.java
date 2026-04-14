package com.fir.onlinesystem; 

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

    // Connects to your database logic
    UserDAO userDAO = new UserDAO();
    ComplaintDAO complaintDAO = new ComplaintDAO();

    // --- 1. LOGIN ROUTES ---
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; 
    }

    @PostMapping("/processLogin")
    public String processLogin(@RequestParam String username, @RequestParam String password) {
        String role = userDAO.login(username, password);
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
    public String processRegistration(@RequestParam String username, @RequestParam String password) {
        boolean success = userDAO.registerCitizen(username, password);
        if (success) {
            return "redirect:/login"; 
        } else {
            return "redirect:/register?error=true"; 
        }
    }
    // --- 4. FILE COMPLAINT ROUTE ---
    @PostMapping("/fileComplaint")
    public String processComplaint(@RequestParam String category, 
                                   @RequestParam String location, 
                                   @RequestParam String description) {
        
        // Send the data to the database
        boolean success = complaintDAO.addComplaint(category, location, description);
        
        if (success) {
            // Refresh the dashboard and add a success flag to the URL
            return "redirect:/citizenDashboard?success=true"; 
        } else {
            return "redirect:/citizenDashboard?error=true"; 
        }
    }
    // --- 5. ADMIN DASHBOARD ROUTE ---
    @GetMapping("/adminDashboard")
    public String showAdminDashboard(Model model) {
        // 1. Get all complaints from the database
        List<Complaint> allComplaints = complaintDAO.getAllComplaints();
        
        // 2. Attach the list to the HTML page under the name "complaints"
        model.addAttribute("complaints", allComplaints);
        
        // 3. Load the adminDashboard.html page
        return "adminDashboard";
    }
    // --- 6. UPDATE STATUS ROUTE ---
    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam int id, @RequestParam String status) {
        // Send the update to the database
        complaintDAO.updateComplaintStatus(id, status);
        
        // Refresh the admin dashboard so they see the new status instantly
        return "redirect:/adminDashboard";
    }
}