package connection.getpost;

import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Courseservices {
	@Autowired
	Courserepositry courseRepo;
	@Autowired
	registerrepo registerRepo;

	public List<Course> availablecourse() {
		// TODO Auto-generated method stub
		return courseRepo.findAll();
}

	public List<Courseregister> enroll() {
		// TODO Auto-generated method stub
		return registerRepo.findAll();
	}


	public String registercourse(String name, String emailid, String coursename) {
	    List<Course> courses = courseRepo.findAll();
	    for (Course course : courses) {
	        if (course.getCourses().equalsIgnoreCase(coursename)) {
	            if (course.getSeats() > 0) {
	                course.setSeats(course.getSeats() - 1);
	                courseRepo.save(course);
	                Courseregister courseRegistry = new Courseregister(name, emailid, coursename);
	                registerRepo.save(courseRegistry);
	                return "Registered";
	            } else {
	                return "Full";
	            }
	        }
	    }
	    return "Course Not Found";
	}


	
	public void updateSeats(Long courseId, int newSeats) {
	    java.util.Optional<Course> optional = courseRepo.findById(courseId);
	    if (optional.isPresent()) {
	        Course course = optional.get();
	        course.setSeats(newSeats);
	        courseRepo.save(course);
	    }
	}
	  public boolean updateEnrollment(Long id, String name, String emailid, String coursename) {
	        try {
	            java.util.Optional<Courseregister> optional = registerRepo.findById(id);
	            if (optional.isPresent()) {
	                Courseregister reg = optional.get();
	                reg.setName(name);
	                reg.setEmailid(emailid);
	                reg.setCoursename(coursename);
	                registerRepo.save(reg);
	                return true;
	            } else {
	                return false;
	            }
	        } catch (Exception e) {
	            // Log or print exception for debugging
	            System.out.println("Error updating enrollment: " + e.getMessage());
	            return false;
	        }
	    }
	

	public boolean deleteEnrollment(Long id) {
	    if (registerRepo.existsById(id)) {
	        registerRepo.deleteById(id);
	        return true;
	    }
	    return false;
	}
	

	}

	
	
