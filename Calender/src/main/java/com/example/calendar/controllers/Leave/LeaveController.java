package com.example.calendar.controllers.Leave;
import com.example.calendar.entities.Leave.Leave;
import com.example.calendar.entities.Leave.LeaveType;
import com.example.calendar.services.Leave.LeaveServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/leave/add/{intructorid}")
    public ResponseEntity<?> addLeave(@RequestBody Leave leave, @PathVariable String intructorid) {
        try {
            Leave addedLeave = leaveService.addLeave(leave, intructorid);
            return ResponseEntity.ok(addedLeave);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/leave/addEmergency/{intructorid}")
    public ResponseEntity<?> addLeaveEmerg(@RequestBody Leave leave, @PathVariable String intructorid) {
        try {
            Leave addedLeave = leaveService.emergencyAdd(leave, intructorid);
            return ResponseEntity.ok(addedLeave);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Add an exception handler to catch RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @PutMapping("/leave/update/{leaveId}/{instructorId}")
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

    @PutMapping("/leave/updatebyH/{leaveId}/{instructorId}")
    public ResponseEntity<?> updateLeavebyH(@RequestBody Leave leave, @PathVariable String leaveId, @PathVariable String instructorId) {
        try {
            Leave updatedLeave = leaveService.updateLeavebyHours(leave, leaveId, instructorId);
            return ResponseEntity.ok(updatedLeave);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/leave/delete/{leaveId}/{instructorId}")
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
    @GetMapping("/leave/allByH")
    public List<Leave> listofLeaveByH(){
        return leaveService.allLeaveByHours();
    }
    @GetMapping("/leave/allByD")
    public List<Leave> listofLeaveByD(){
        return leaveService.allLeaveByDays();
    }




    @GetMapping("/leave/search")
    public List<Leave> searchLeaves(@RequestParam(required = false) String status) {
        // Call the service method to search for leaves based on status and instructor name
        return leaveService.leaveBystatus(status);
    }
    @PutMapping("/leave/{leaveId}/accept")
    public ResponseEntity<Leave> acceptLeave(@PathVariable String leaveId) {
        Leave acceptedLeave = leaveService.acceptLeave(leaveId);
        if (acceptedLeave != null) {
            return ResponseEntity.ok(acceptedLeave);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/leave/{leaveId}/refuse")
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
    public List<Leave> getLeaveByInstructorId(@PathVariable String instructorId) {
        return leaveService.getLeaveByInstructorId(instructorId);
    }
    @GetMapping("/leave/leaveDaysById/{instructorId}")
    public List<Leave> allDaysByid(@PathVariable String instructorId) {
        return leaveService.getLeaveInsByDays(instructorId);
    }
    @GetMapping("/leave/leaveHoursById/{instructorId}")
    public List<Leave> allHoursByid(@PathVariable String instructorId) {
        return leaveService.getLeaveInsByHours(instructorId);
    }
    @GetMapping("/leave/totalTime/{instructorId}")
    public long getthis(@PathVariable String instructorId) {
        return leaveService.getTotalTimeTakenByInstructor(instructorId);
    }
    @GetMapping("/leave/getleft/{instructorId}")
    public int[] getremaing(@PathVariable String instructorId) {
        return leaveService.calculDaysLeft(instructorId);
    }

    @GetMapping("/leave/percentage")
    public ResponseEntity<Map<LeaveType, Integer>> getLeaveTypePercentage() {
        Map<LeaveType, Integer> leaveTypePercentage = leaveService.calculateLeaveTypePercentage();
        return new ResponseEntity<>(leaveTypePercentage, HttpStatus.OK);
    }
    @GetMapping("/leave/percentagebymonth")
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
    @PostMapping("/leave/addbyHours/{instructorId}")
    public ResponseEntity<Leave> addLeaveForSpecificHours(@RequestBody Leave leave, @PathVariable String instructorId) {
        Leave addedLeave = leaveService.addLeaveForSpecificHours(leave, instructorId);

        if (addedLeave == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(addedLeave, HttpStatus.CREATED);
        }
    }
}
