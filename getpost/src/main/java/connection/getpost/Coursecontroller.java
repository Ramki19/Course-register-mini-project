package connection.getpost;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="http://localhost:5500")
public class Coursecontroller {
	
@Autowired
private Courseservices courseServices;

@Autowired
private Courseservices courseSer;

@GetMapping("/course")
public List<Course> availablecourse(){
	return courseServices.availablecourse();
}

@GetMapping("/enroll")
public List<Courseregister> enroll(){
	return courseSer.enroll();
}
@PostMapping("/register")
public String registercourse(@RequestParam("name") String name, @RequestParam("emailid") String emailid, @RequestParam("coursename") String coursename) {
    String result = courseSer.registercourse(name, emailid, coursename);
    if (result.equals("Full")) return "Sorry, the course is full!";
    return "Congratulations " + name + " successfully enrolled";
}

@PutMapping("/course/{id}/seats")
public String updateSeats(@PathVariable Long id, @RequestParam int seats) {
    courseSer.updateSeats(id, seats);
    return "Seat updated";
}
@PutMapping("/enroll/update/{id}")
public String updateEnrollment(@PathVariable Long id,
                               @RequestParam String name,
                               @RequestParam String emailid,
                               @RequestParam String coursename) {
    boolean updated = courseSer.updateEnrollment(id, name, emailid, coursename);
    if (updated) {
        return "Enrollment updated successfully!";
    } else {
        return "Enrollment not found or could not be updated.";
    }
}
@DeleteMapping("/enroll/{id}")
public String deleteEnrollment(@PathVariable Long id) {
    boolean deleted = courseSer.deleteEnrollment(id);
    if (deleted) {
        return "Enrollment deleted successfully!";
    } else {
        return "Enrollment not found or could not be deleted.";
    }
}

}
