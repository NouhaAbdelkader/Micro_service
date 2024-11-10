package com.example.calendar.controllers.Leave;
import com.example.calendar.entities.Leave.Leave;
import com.example.calendar.entities.Leave.LeaveType;
import com.example.calendar.services.Leave.LeaveServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calendar")
@Tag(name = "Leave management")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class LeaveController {
    @Autowired
    LeaveServiceImpl leaveService;
    @PostMapping("/user/leave/add/{intructorid}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> addLeave(@RequestBody Leave leave, @PathVariable String intructorid) {
        try {

            Leave addedLeave = leaveService.addLeave(leave, intructorid);
            return ResponseEntity.ok(addedLeave);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/user/leave/addEmergency/{intructorid}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> addLeaveEmerg(@RequestBody Leave leave, @PathVariable String intructorid) {
        try {
            Leave addedLeave = leaveService.emergencyAdd(leave, intructorid);
            return ResponseEntity.ok(addedLeave);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/Admin/leave/hell")
    @PreAuthorize("hasAuthority('Admin')")
    public String  message() {
    return "hell on earth";
    }
    // Add an exception handler to catch RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @PutMapping("/user/leave/update/{leaveId}/{instructorId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> updateLeave(@RequestBody Leave leave, @PathVariable String leaveId, @PathVariable String instructorId) {
        try {
            Leave updatedLeave = leaveService.updateLeave(leave, leaveId, instructorId);
            return ResponseEntity.ok(updatedLeave);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred.");
        }
    }

    @PutMapping("/user/leave/updatebyH/{leaveId}/{instructorId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> updateLeavebyH(@RequestBody Leave leave, @PathVariable String leaveId, @PathVariable String instructorId) {
        try {
            Leave updatedLeave = leaveService.updateLeavebyHours(leave, leaveId, instructorId);
            return ResponseEntity.ok(updatedLeave);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/user/leave/delete/{leaveId}/{instructorId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> deleteLeave(@PathVariable String leaveId, @PathVariable String instructorId) {
        try {
            leaveService.deleteLeave(leaveId, instructorId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Leave not found
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Instructor not authorized to delete
        }
    }
    @GetMapping("/Admin/leave/allByH")
    @PreAuthorize("hasAuthority('Admin')")
    public List<Leave> listofLeaveByH(){
        return leaveService.allLeaveByHours();
    }
    @GetMapping("/Admin/leave/allByD")
    @PreAuthorize("hasAuthority('Admin')")
    public List<Leave> listofLeaveByD(){
        return leaveService.allLeaveByDays();
    }




    @GetMapping("/Admin/leave/search")
    @PreAuthorize("hasAuthority('Admin')")
    public List<Leave> searchLeaves(@RequestParam(required = false) String status) {
        // Call the service method to search for leaves based on status and instructor name
        return leaveService.leaveBystatus(status);
    }
    @PutMapping("/Admin/leave/{leaveId}/accept")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Leave> acceptLeave(@PathVariable String leaveId) {
        Leave acceptedLeave = leaveService.acceptLeave(leaveId);
        if (acceptedLeave != null) {
            return ResponseEntity.ok(acceptedLeave);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/Admin/leave/{leaveId}/refuse")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Leave> refuseLeave(@PathVariable String leaveId) {
        Leave refuseLeave = leaveService.refuseLeave(leaveId);
        if (refuseLeave != null) {
            return ResponseEntity.ok(refuseLeave);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/leave/pending/count")
    public int countPendingLeaves() {
        return leaveService.countPendingLeaves();
    }
    @GetMapping("/leave/countoday")
    public int countpeop() {
        return leaveService.NbrPeopLeaveToday();
    }


    @GetMapping("/leave/leavebyId/{instructorId}")
    @PreAuthorize("hasAuthority('Admin')")
    public List<Leave> getLeaveByInstructorId(@PathVariable String instructorId) {
        return leaveService.getLeaveByInstructorId(instructorId);
    }
    @GetMapping("/leave/leaveDaysById/{instructorId}")
    public List<Leave> allDaysByid(@PathVariable String instructorId) {
        return leaveService.getLeaveInsByDays(instructorId);
    }
    @GetMapping("/user/leave/leaveHoursById/{instructorId}")
    @PreAuthorize("hasAuthority('user')")
    public List<Leave> allHoursByid(@PathVariable String instructorId) {
        return leaveService.getLeaveInsByHours(instructorId);
    }
    @GetMapping("/leave/totalTime/{instructorId}")
    public long getthis(@PathVariable String instructorId) {
        return leaveService.getTotalTimeTakenByInstructor(instructorId);
    }
    @GetMapping("/user/leave/getleft/{instructorId}")
    @PreAuthorize("hasAuthority('user')")
    public int[] getremaing(@PathVariable String instructorId) {
        return leaveService.calculDaysLeft(instructorId);
    }

    @GetMapping("/Admin/leave/percentage")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Map<LeaveType, Integer>> getLeaveTypePercentage() {
        Map<LeaveType, Integer> leaveTypePercentage = leaveService.calculateLeaveTypePercentage();
        return new ResponseEntity<>(leaveTypePercentage, HttpStatus.OK);
    }
    @GetMapping("/Admin/leave/percentagebymonth")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Map<String, Integer>> getPercentageLeavesByMonth() {
        Map<String, Integer> percentageLeavesByMonth = leaveService.getPercentageLeavesByMonth();
        return new ResponseEntity<>(percentageLeavesByMonth, HttpStatus.OK);
    }
    @GetMapping("/leave/{leaveId}/instructor")
    public ResponseEntity<Object> getInstructorDetailsForLeave(@PathVariable String leaveId) {
        String instructorDetails = leaveService.getInstructorDetailsForLeave(leaveId);
        if (instructorDetails.startsWith("{\"error\"")) {
            // If the response contains an error, return it with status code 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(instructorDetails);
        } else {
            // If the response is successful, return it with status code 200
            return ResponseEntity.ok(instructorDetails);
        }
    }
    @PostMapping("/user/leave/addbyHours/{instructorId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<Leave> addLeaveForSpecificHours(@RequestBody Leave leave, @PathVariable String instructorId) {
        Leave addedLeave = leaveService.addLeaveForSpecificHours(leave, instructorId);

        if (addedLeave == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(addedLeave, HttpStatus.CREATED);
        }
    }
}
